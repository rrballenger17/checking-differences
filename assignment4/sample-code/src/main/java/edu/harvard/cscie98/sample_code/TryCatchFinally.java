package edu.harvard.cscie98.sample_code;

public class TryCatchFinally {
  public static void main(final String[] args) {
    try {
      System.out.println("Normal Execution");
      throw new RuntimeException();
    } catch (final Exception e) {
      System.out.println("Caught Exception");
    } finally {
      System.out.println("Finally");
    }
  }
}
