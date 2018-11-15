package edu.harvard.cscie98.simplejava.impl.memory.memorymanager;

import java.util.Set;

import edu.harvard.cscie98.simplejava.config.HeapParameters;
import edu.harvard.cscie98.simplejava.vm.memory.WriteBarrier;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;

public class GenerationalWriteBarrier implements WriteBarrier {

  public GenerationalWriteBarrier(final HeapParameters heapParams) {
    throw new RuntimeException("TODO: Your implementation for Assignment 4 goes here");
  }

  @Override
  public void onPointerWrite(final HeapObject obj, final HeapPointer ptr) {
  }

  @Override
  public Set<HeapObject> getRememberedSet() {
    return null;
  }

  @Override
  public void clearRememberedSet() {
  }

}
