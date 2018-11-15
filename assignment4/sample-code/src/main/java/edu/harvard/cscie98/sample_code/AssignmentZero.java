package edu.harvard.cscie98.sample_code;

public class AssignmentZero {
  public static void main(final String[] args) {
    final Integer[] values = new Integer[256];
    populateArray(values);
    printArray(values);
    StackReverse.reverse(values);
    printArray(values);
  }

  private static void populateArray(final Integer[] values) {
    for (int i = 0; i < values.length; i++) {
      values[i] = i;
    }
  }

  private static void printArray(final Integer[] values) {
    System.out.print("[");
    for (int i = 0; i < values.length; i++) {
      final int value = values[i];
      System.out.print(value + " ");
    }
    System.out.println("]");
  }
}

class ArrayStack {

  private final Object[] data;
  private int ptr;

  public ArrayStack() {
    this.data = new Object[50];
    this.ptr = 0;
  }

  public void push(final Object obj) {
    data[ptr++] = obj;
  }

  public Object pop() {
    return data[--ptr];
  }

  public boolean isEmpty() {
    return ptr == 0;
  }

  public int getMaxSize() {
    return 50;
  }
}

class StackReverse {

  public static void reverse(final Integer[] array) {
    final int numArrays = array.length % 50 == 0 ? array.length / 50
        : (array.length / 50) + 1;
    final ArrayStack[] stacks = new ArrayStack[numArrays];
    for (int i = 0; i < numArrays; i++) {
      stacks[i] = new ArrayStack();
    }
    for (int i = 0; i < array.length; i++) {
      final ArrayStack stack = stacks[i / 50];
      stack.push(array[i]);
    }

    for (int i = 0; i < array.length; i++) {
      final ArrayStack stack = stacks[(array.length - i - 1) / 50];
      array[i] = (Integer) stack.pop();
    }
  }
}
