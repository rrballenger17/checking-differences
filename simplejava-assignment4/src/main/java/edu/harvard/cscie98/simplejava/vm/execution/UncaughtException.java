package edu.harvard.cscie98.simplejava.vm.execution;

import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;

public class UncaughtException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final HeapPointer exception;

  public UncaughtException(final HeapPointer exception) {
    this.exception = exception;
  }

  public HeapPointer getException() {
    return exception;
  }

}
