package java.lang;

public class Boolean {
  private final boolean val;

  public Boolean(final boolean b) {
    this.val = b;
  }

  public static Boolean valueOf(final boolean b) {
    return new Boolean(b);
  }

  public boolean booleanValue() {
    return val;
  }

}
