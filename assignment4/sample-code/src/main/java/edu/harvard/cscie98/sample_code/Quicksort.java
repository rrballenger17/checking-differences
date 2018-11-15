package edu.harvard.cscie98.sample_code;

public class Quicksort {

  public static void main(final String[] args) {
    final int[] values = new int[] { 8, 6, 1, 7, 8, 8, 4, 3, 6, 7, 1, 6, 8, 1,
        4, 8, 3, 6, 3, 3, 4 };
    print(values);
    quickSort(values, 0, values.length - 1);
    print(values);
  }

  private static void print(final int[] values) {
    for (final int i : values) {
      System.out.print(i + " ");
    }
    System.out.println();
  }

  static void quickSort(final int[] values, final int start, final int end) {
    final int pivot = values[(start + end) / 2];
    int i = start;
    int j = end;
    while (i <= j) {
      while (values[i] < pivot) {
        i++;
      }
      while (values[j] > pivot) {
        j--;
      }
      if (i <= j) {
        final int tmp = values[i];
        values[i] = values[j];
        values[j] = tmp;
        i++;
        j--;
      }
    }

    if (start < i - 1) {
      quickSort(values, start, i - 1);
    }
    if (i < end) {
      quickSort(values, i, end);
    }
  }

}
