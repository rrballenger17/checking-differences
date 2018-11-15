package edu.harvard.cscie98.sample_code;

public class Fibonacci {

  public static void main(final String[] args) {
    final int result = fib(0, 1, 10);
    System.out.println("Result: " + result);
  }

  private static int fib(final int v1, final int v2, final int iterations) {
    if (iterations == 0) {
      return v2;
    }
    return fib(v2, v1 + v2, iterations - 1);
  }

}
