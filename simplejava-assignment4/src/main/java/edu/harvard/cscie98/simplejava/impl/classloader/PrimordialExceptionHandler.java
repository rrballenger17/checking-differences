package edu.harvard.cscie98.simplejava.impl.classloader;

import edu.harvard.cscie98.simplejava.vm.classloader.VmExceptionHandler;

public class PrimordialExceptionHandler implements VmExceptionHandler {

  private final int start;
  private final int end;
  private final int pc;

  public PrimordialExceptionHandler(final int start, final int end, final int pc) {
    this.start = start;
    this.end = end;
    this.pc = pc;
  }

  @Override
  public int getStartPc() {
    return start;
  }

  @Override
  public int getEndPc() {
    return end;
  }

  @Override
  public String getCatchType() {
    return "any";
  }

  @Override
  public int getHandlerPc() {
    return pc;
  }

  @Override
  public String toString() {
    return "Primordial handler catches " + getCatchType() + " [" + getStartPc() + ", " + getEndPc()
        + "), target: " + getHandlerPc();
  }

}
