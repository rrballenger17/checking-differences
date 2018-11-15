package edu.harvard.cscie98.simplejava.impl.memory.memorymanager;

import java.util.List;

import edu.harvard.cscie98.simplejava.config.HeapParameters;
import edu.harvard.cscie98.simplejava.config.Log;
import edu.harvard.cscie98.simplejava.impl.memory.heap.BumpPointerRegion;
import edu.harvard.cscie98.simplejava.vm.VmInternalError;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.classloader.VmField;
import edu.harvard.cscie98.simplejava.vm.memory.Heap;
import edu.harvard.cscie98.simplejava.vm.memory.MemoryManager;
import edu.harvard.cscie98.simplejava.vm.memory.WriteBarrier;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ArrayTypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectBuilder;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectHeader;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectTypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ReferenceLocation;
import edu.harvard.cscie98.simplejava.vm.objectmodel.TypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.threads.JvmThread;

public class SemiSpaceMemoryManager implements MemoryManager {

  private final BumpPointerRegion region1;
  private final BumpPointerRegion region2;
  private BumpPointerRegion toSpace;
  private JvmThread thread;
  private final Heap heap;
  private ObjectBuilder objectBuilder;
  private VmClassLoader classLoader;
  int gcCount = 0;
  long copied;

  public SemiSpaceMemoryManager(final HeapParameters heapParams) {
    heap = heapParams.getHeap();
    final long semiSpaceSize = heapParams.getExtent() / 2;
    final HeapPointer base1 = heapParams.getBaseAddress();
    final HeapPointer base2 = heapParams.getBaseAddress().add(semiSpaceSize);
    region1 = heap.getBumpPointerRegion(base1, semiSpaceSize);
    region2 = heap.getBumpPointerRegion(base2, semiSpaceSize);
    toSpace = region1;
    Log.gc("Region 1: " + region1.getBase() + "-" + region1.getBase().add(region1.getExtent()));
    Log.gc("Region 2: " + region2.getBase() + "-" + region2.getBase().add(region2.getExtent()));
  }

  @Override
  public void setThread(final JvmThread thread) {
    this.thread = thread;
  }

  @Override
  public void setObjectBuilder(final ObjectBuilder objectBuilder) {
    this.objectBuilder = objectBuilder;
  }

  @Override
  public void setClassLoader(final VmClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  @Override
  public void setBarrier(final WriteBarrier barrier) {
  }

  @Override
  public HeapPointer allocate(final long bytes) {
    HeapPointer allocated = toSpace.allocate(bytes);
    if (allocated == HeapPointer.NULL) {
      try {
        garbageCollect();
      } catch (final OutOfMemoryError e) {
        Log.gc("Ran out of memory during GC");
        return HeapPointer.NULL;
      }
      allocated = toSpace.allocate(bytes);
    }
    return allocated;
  }

  @Override
  public void garbageCollect() {
    Log.gc("GC " + gcCount + " starting");
    gcCount++;
    copied = 0;
    flipRegion();
    copyRoots();
    scanNewSpace();
    printGcDetails();
    Log.gc("GC complete. Copied " + copied + " bytes");
  }

  private void printGcDetails() {
    Log.gc("  Base: " + toSpace.getBase() + " alloc: " + toSpace.getAllocationPointer());
  }

  private void flipRegion() {
    if (toSpace == region1) {
      toSpace = region2;
    } else {
      toSpace = region1;
    }
    toSpace.reset();
  }

  private void copyRoots() {
    for (final ReferenceLocation ref : thread.getStack().getStackAndLocalReferenceLocations()) {
      if (ref.getValue() != HeapPointer.NULL) {
        final HeapPointer newLoc = copyObject(ref.getValue());
        ref.setValue(newLoc);
      }
    }

    for (final ReferenceLocation ref : classLoader.getStaticReferenceLocations()) {
      if (ref.getValue() != HeapPointer.NULL) {
        final HeapPointer newLoc = copyObject(ref.getValue());
        ref.setValue(newLoc);
      }
    }

    for (final ReferenceLocation ref : objectBuilder.getInternTableReferences()) {
      if (ref.getValue() != HeapPointer.NULL) {
        final HeapPointer newLoc = copyObject(ref.getValue());
        ref.setValue(newLoc);
      }
    }
  }

  private void scanNewSpace() {
    HeapPointer finger = toSpace.getBase();
    HeapPointer end = toSpace.getAllocationPointer();
    while (finger.compareTo(end) < 0) {
      final HeapObject current = finger.dereference();
      updateFields(current);
      finger = finger.add(current.getSize());
      end = toSpace.getAllocationPointer();
    }
  }

  private void updateFields(final HeapObject current) {
    final TypeDescriptor typeDesc = (TypeDescriptor) current.getHeader()
        .getWord(ObjectHeader.CLASS_DESCRIPTOR_WORD);
    if (typeDesc.isObject()) {
      updateObjectFields(current, (ObjectTypeDescriptor) typeDesc);
    } else {
      updateArrayFields(current, (ArrayTypeDescriptor) typeDesc);
    }
  }

  private void updateObjectFields(final HeapObject obj, final ObjectTypeDescriptor desc) {
    final List<VmField> fields = desc.getFields();
    for (int i = 0; i < fields.size(); i++) {
      final VmField fld = fields.get(i);
      if (fld.isReference()) {
        final HeapPointer ptr = (HeapPointer) obj.getValueAtOffset(i);
        if (ptr != HeapPointer.NULL && !toSpace.pointerInRegion(ptr)) {
          final HeapPointer toPtr = copyObject(ptr);
          obj.setValueAtOffset(i, toPtr);
        }
      }
    }
  }

  private void updateArrayFields(final HeapObject arr, final ArrayTypeDescriptor desc) {
    if (!desc.getElementDescriptor().isPrimitive()) {
      final int length = (int) arr.getHeader().getWord(ObjectHeader.ARRAY_LENGTH_WORD);
      for (int i = 0; i < length; i++) {
        final HeapPointer ptr = (HeapPointer) arr.getValueAtOffset(i);
        if (ptr != HeapPointer.NULL && !toSpace.pointerInRegion(ptr)) {
          final HeapPointer toPtr = copyObject(ptr);
          arr.setValueAtOffset(i, toPtr);
        }
      }
    }
  }

  private HeapPointer copyObject(final HeapPointer fromPtr) {
    final HeapObject fromObj = fromPtr.dereference();
    final Object descWord = fromObj.getHeader().getWord(ObjectHeader.CLASS_DESCRIPTOR_WORD);
    if (descWord instanceof HeapPointer) {
      return (HeapPointer) descWord;
    }
    final long size = fromObj.getSize();
    final HeapPointer toPtr = toSpace.allocate(size);
    if (toPtr.equals(HeapPointer.NULL)) {
      throw new OutOfMemoryError();
    }
    heap.memcpy(fromPtr, toPtr);
    if (toSpace.pointerInRegion(fromPtr)) {
      throw new VmInternalError("Copying from the wrong space");
    }
    fromObj.getHeader().setWord(ObjectHeader.CLASS_DESCRIPTOR_WORD, toPtr);
    copied += size;
    return toPtr;
  }
}
