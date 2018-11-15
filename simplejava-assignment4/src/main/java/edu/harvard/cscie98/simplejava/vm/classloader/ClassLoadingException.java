package edu.harvard.cscie98.simplejava.vm.classloader;

public class ClassLoadingException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public ClassLoadingException(final Exception e) {
    super(e);
  }

  public ClassLoadingException(final String msg) {
    super(msg);
  }

}
