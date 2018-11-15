package edu.harvard.cscie98.simplejava.vm;

public class VmInternalError extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public VmInternalError(final String msg) {
    super(msg);
  }

  public VmInternalError(final Throwable t) {
    super(t);
  }

  public VmInternalError(final String msg, final Throwable t) {
    super(msg, t);
  }
}
