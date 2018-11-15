package edu.harvard.cscie98.simplejava.impl.threads;

import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ReferenceLocation;

public class StackReferenceLocation implements ReferenceLocation {

  private final StackFrameImpl frame;
  private final int idx;

  public StackReferenceLocation(final StackFrameImpl f, final int i) {
    this.frame = f;
    this.idx = i;
  }

  @Override
  public HeapPointer getValue() {
    return frame.getStackValue(idx);
  }

  @Override
  public void setValue(final HeapPointer val) {
    frame.setStackValue(idx, val);
  }

}
