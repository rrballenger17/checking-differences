package edu.harvard.cscie98.simplejava.impl.threads;

import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ReferenceLocation;

public class LocalReferenceLocation implements ReferenceLocation {

  private final StackFrameImpl frame;
  private final int idx;

  public LocalReferenceLocation(final StackFrameImpl f, final int i) {
    this.frame = f;
    this.idx = i;
  }

  @Override
  public HeapPointer getValue() {
    return (HeapPointer) frame.getLocalVariable(idx);
  }

  @Override
  public void setValue(final HeapPointer val) {
    frame.setLocalVariable(idx, val);
  }

}
