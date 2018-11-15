package edu.harvard.cscie98.sample_code;

public class Arrays {

  public static void main(final String[] args) {
    final Object[] arr = new Object[100];
    arr[0] = new Object();
    for (int i = 1; i < 100; i++) {
      arr[i] = arr[i - 1];
    }

    final int[] intArr = new int[100];
    intArr[0] = 42;
    for (int i = 1; i < 100; i++) {
      intArr[i] = intArr[i - 1];
    }

  }
}
