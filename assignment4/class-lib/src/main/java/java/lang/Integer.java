package java.lang;

public class Integer {

  private final int val;

  public Integer(final int i) {
    this.val = i;
  }

  public static Integer valueOf(final int i) {
    return new Integer(i);
  }

  public int intValue() {
    return val;
  }
}
