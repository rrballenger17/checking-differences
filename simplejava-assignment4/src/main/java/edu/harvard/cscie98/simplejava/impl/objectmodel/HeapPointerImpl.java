package edu.harvard.cscie98.simplejava.impl.objectmodel;

import edu.harvard.cscie98.simplejava.impl.memory.heap.HeapImpl;
import edu.harvard.cscie98.simplejava.vm.VmInternalError;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;

public class HeapPointerImpl implements HeapPointer {

  private final long ptr;
  private final HeapImpl heap;

  public HeapPointerImpl(final long ptr, final HeapImpl heap) {
    this.ptr = ptr;
    this.heap = heap;
  }

  @Override
  public HeapObject dereference() {
    if (this == HeapPointer.NULL) {
      throw new NullPointerException("Attempting to dereference HeapPointer.NULL");
    }
    final HeapObject obj = heap.objectAt(this);
    if (obj == null) {
      throw new VmInternalError("Heap Pointer " + this + " does not refer to a valid object");
    }
    return obj;
  }

  @Override
  public int compareTo(final HeapPointer o) {
    final long diff = ptr - ((HeapPointerImpl) o).ptr;
    if (diff > 0) {
      return 1;
    }
    if (diff < 0) {
      return -1;
    }
    return 0;
  }

  @Override
  public boolean equals(final Object o) {
    if (!(o instanceof HeapPointerImpl)) {
      return false;
    }
    return compareTo((HeapPointerImpl) o) == 0;
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  @Override
  public HeapPointer add(final long extent) {
    return new HeapPointerImpl(ptr + extent, heap);
  }

  public HeapImpl getHeap() {
    return heap;
  }

  @Override
  public String toString() {
    return "0x" + Long.toHexString(ptr);
  }

  @Override
  public long toLong() {
    return ptr;
  }

}
