package edu.harvard.cscie98.sample_code;

public class FieldAccess {

  int intField = 42;
  int total;

  public static void main(final String[] args) {
    final FieldAccess f = new SubFieldAccess();
    f.intField = 10;
    ((SubFieldAccess) f).intField = 20;
    final int thisfield = f.intField;
    final int subfield = ((SubFieldAccess) f).intField;
    f.total = thisfield + subfield;
    System.out.println("FieldAccess.intField: " + f.intField);
    System.out.println("FieldAccess.total: " + f.total);
    System.out.println("SubFieldAccess.intField: " + ((SubFieldAccess) f).intField);
    System.out.println("SubFieldAccess.total: " + ((SubFieldAccess) f).total);
  }
}

class SubFieldAccess extends FieldAccess {
  int intField = 43;
  int total;
}