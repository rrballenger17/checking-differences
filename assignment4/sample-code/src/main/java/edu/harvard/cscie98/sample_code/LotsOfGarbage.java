package edu.harvard.cscie98.sample_code;

public class LotsOfGarbage {

  int field1;
  int field2;

  public LotsOfGarbage(final int arg1, final int arg2) {
    field1 = arg1;
    field2 = arg2;
  }

  public static void main(final String[] args) {
    for (int i = 0; i < 10000000; i++) {
      new LotsOfGarbage(1, 2);
    }
  }

}
