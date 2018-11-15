package edu.harvard.cscie98.sample_code;

public class ExceptionWithStackTrace {
  public static void main(final String[] args) {
    try {
      ExceptionThrower.throwMethod(0);
    } catch (final Throwable t) {
      t.printStackTrace();
    }
  }

}

class ExceptionThrower {

  static void throwMethod(final int i) {
    if (i == 10) {
      throw new RuntimeException();
    }
    throwMethod(i + 1);
  }
}