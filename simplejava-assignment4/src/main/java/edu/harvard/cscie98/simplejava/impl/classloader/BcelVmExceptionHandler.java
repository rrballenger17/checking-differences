package edu.harvard.cscie98.simplejava.impl.classloader;

import org.apache.bcel.generic.CodeExceptionGen;

import edu.harvard.cscie98.simplejava.vm.classloader.VmExceptionHandler;

public class BcelVmExceptionHandler implements VmExceptionHandler {

  private final CodeExceptionGen exception;

  public BcelVmExceptionHandler(final CodeExceptionGen ex) {
    this.exception = ex;
  }

  @Override
  public int getStartPc() {
    return exception.getStartPC().getPosition();
  }

  @Override
  public int getEndPc() {
    return exception.getEndPC().getNext().getPosition();
  }

  @Override
  public int getHandlerPc() {
    return exception.getHandlerPC().getPosition();
  }

  @Override
  public String getCatchType() {
    if (exception.getCatchType() == null) {
      return "any";
    }
    return exception.getCatchType().getClassName();
  }

  @Override
  public String toString() {
    return "catches " + getCatchType() + " [" + getStartPc() + ", " + getEndPc() + "), target: "
        + getHandlerPc();
  }

}
