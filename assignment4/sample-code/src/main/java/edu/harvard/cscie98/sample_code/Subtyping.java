package edu.harvard.cscie98.sample_code;

public class Subtyping {

  public static void main(final String[] args) {
    final BaseClass cls = new SubClassC();
    final int answer = cls.arithmetic(10, 1);
    System.out.println(answer);
  }
}

class BaseClass {
  public int arithmetic(final int a, final int b) {
    return a + b;
  }
}

class SubClassA extends BaseClass {
  @Override
  public int arithmetic(final int a, final int b) {
    return a - b;
  }
}

class SubClassB extends SubClassA {
  @Override
  public int arithmetic(final int a, final int b) {
    return super.arithmetic(a, b) + b;
  }
}

class SubClassC extends SubClassA {

}