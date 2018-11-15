package edu.harvard.cscie98.sample_code;

public class ExceptionsAsControlFlow {

  public static void main(final String[] args) {
    try {
      fib(0, 1, 10);
    } catch (final FibonacciException e) {
      System.out.println("Result: " + e.getVal());
    }

  }

  private static int fib(final int v1, final int v2, final int iterations) {
    if (iterations == 0) {
      throw new FibonacciException(v2);
    }
    return fib(v2, v1 + v2, iterations - 1);
  }

}

class FibonacciException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private final int val;

  public FibonacciException(final int val) {
    this.val = val;
  }

  int getVal() {
    return val;
  }

}