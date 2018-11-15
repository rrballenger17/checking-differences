package edu.harvard.cscie98.simplejava.vm.execution;

public class ExecutionException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ExecutionException(final Exception e) {
    super(e);
  }

  public ExecutionException(final String msg) {
    super(msg);
  }

}
