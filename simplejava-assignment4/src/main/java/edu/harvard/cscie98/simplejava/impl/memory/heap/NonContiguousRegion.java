package edu.harvard.cscie98.simplejava.impl.memory.heap;

import edu.harvard.cscie98.simplejava.vm.memory.Region;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;

public class NonContiguousRegion implements Region {

  NonContiguousRegion(final HeapPointer baseAddress, final long extent) {
    throw new RuntimeException("TODO: Your implementation for Assignment 4 goes here");
  }

  @Override
  public void reset() {
  }

  @Override
  public void free(final HeapObject obj) {
  }

  @Override
  public HeapPointer allocate(final long bytes) {
    return null;
  }

  @Override
  public boolean pointerInRegion(final HeapPointer ptr) {
    return false;
  }

}
