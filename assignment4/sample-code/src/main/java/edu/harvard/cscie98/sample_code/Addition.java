package edu.harvard.cscie98.sample_code;

public class Addition {

  // The initial value must be declared as a non-private static variable to
  // prevent javac from performing constant folding and optimizing out the
  // IADD instruction.
  static int intVar = 42;

  public static void main(final String[] args) {
    intVar += 10;
  }

}
