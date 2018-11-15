package edu.harvard.cscie98.simplejava.impl.memory.heap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import edu.harvard.cscie98.simplejava.config.Log;
import edu.harvard.cscie98.simplejava.impl.objectmodel.HeapObjectImpl;
import edu.harvard.cscie98.simplejava.vm.memory.Heap;
import edu.harvard.cscie98.simplejava.vm.memory.Region;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ArrayTypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectHeader;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectTypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.objectmodel.TypeDescriptor;

public class HeapImpl implements Heap {
  protected NavigableMap<HeapPointer, HeapObject> heap;
  private final List<Region> regions;

  public HeapImpl() {
    heap = new TreeMap<HeapPointer, HeapObject>();
    regions = new ArrayList<Region>();
  }

  public List<Region> getRegions() {
    return regions;
  }

  public void addObject(final HeapObject obj) {
    final HeapPointer ptr = obj.getAddress();
    final HeapPointer nextObj = ptr.add(obj.getSize());
    heap.put(ptr, obj);
    final Set<HeapPointer> rem = new HashSet<HeapPointer>();
    HeapPointer next = heap.higherKey(ptr);
    while (next != null && next.compareTo(nextObj) < 0) {
      rem.add(next);
      next = heap.higherKey(next);
    }
    for (final HeapPointer p : rem) {
      heap.remove(p);
    }
  }

  public HeapObject objectAt(final HeapPointer ptr) {
    return heap.get(ptr);
  }

  @Override
  public BumpPointerRegion getBumpPointerRegion(final HeapPointer baseAddress, final long extent) {
    final BumpPointerRegion r = new BumpPointerRegion(baseAddress, extent);
    regions.add(r);
    return r;
  }

  @Override
  public NonContiguousRegion getNonContiguousRegion(final HeapPointer baseAddress,
      final long extent) {
    final NonContiguousRegion r = new NonContiguousRegion(baseAddress, extent);
    regions.add(r);
    return r;
  }

  @Override
  public void memcpy(final HeapPointer from, final HeapPointer to) {
    Log.gc("Copying " + from + " to " + to);
    final HeapObjectImpl oldObj = (HeapObjectImpl) from.dereference();
    final TypeDescriptor cls = (TypeDescriptor) from.dereference().getHeader()
        .getWord(ObjectHeader.CLASS_DESCRIPTOR_WORD);
    final HeapObjectImpl newObj;
    if (cls instanceof ObjectTypeDescriptor) {
      final ObjectTypeDescriptor desc = (ObjectTypeDescriptor) cls;
      newObj = new HeapObjectImpl(to, desc);
      for (int i = 0; i < desc.getFields().size(); i++) {
        newObj.setValueAtOffset(i, oldObj.getValueAtOffset(i));
      }
    } else {
      final ArrayTypeDescriptor desc = (ArrayTypeDescriptor) cls;
      final int length = (int) oldObj.getHeader().getWord(ObjectHeader.ARRAY_LENGTH_WORD);
      newObj = new HeapObjectImpl(to, desc, length);
      for (int i = 0; i < length; i++) {
        newObj.setValueAtOffset(i, oldObj.getValueAtOffset(i));
      }
    }
    addObject(newObj);
  }

}
