package edu.harvard.cscie98.simplejava.impl.memory.memorymanager;

import java.util.HashSet;
import java.util.Set;

import edu.harvard.cscie98.simplejava.vm.memory.WriteBarrier;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;

public class EmptyWriteBarrier implements WriteBarrier {

  @Override
  public void onPointerWrite(HeapObject obj, HeapPointer ptr) {
  }

  @Override
  public Set<HeapObject> getRememberedSet() {
    return new HashSet<HeapObject>();
  }

  @Override
  public void clearRememberedSet() {
  }

}
