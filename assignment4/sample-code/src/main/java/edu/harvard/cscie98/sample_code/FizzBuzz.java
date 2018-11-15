package edu.harvard.cscie98.sample_code;

public class FizzBuzz {

  public static void main(final String[] args) {
    for (int i = 1; i <= 100; i++) {
      if (i % 3 != 0 && i % 5 != 0) {
        System.out.print(i + " ");
      }
      if (i % 3 == 0) {
        System.out.print("fizz");
      }
      if (i % 5 == 0) {
        System.out.print("buzz");
      }
      System.out.println();
    }
  }

}
