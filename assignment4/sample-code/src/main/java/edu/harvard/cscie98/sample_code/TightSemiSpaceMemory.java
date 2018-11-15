package edu.harvard.cscie98.sample_code;

// Should just run with 64kb heap
public class TightSemiSpaceMemory {
  public static void main(final String[] args) {
    final Integer[] array = new Integer[950];
    for (int i = 0; i < 100000; i++) {
      array[i % 950] = new Integer(42);
    }
  }
}
