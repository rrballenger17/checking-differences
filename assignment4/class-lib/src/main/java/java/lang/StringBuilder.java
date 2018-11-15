package java.lang;

public class StringBuilder {

  private final byte[] bytes;
  int idx;

  public StringBuilder() {
    this.bytes = new byte[1024];
  }

  public StringBuilder(final String str) {
    this.bytes = new byte[1024];
    final byte[] strBytes = str.getBytes();
    for (idx = 0; idx < strBytes.length; idx++) {
      this.bytes[idx] = strBytes[idx];
    }
  }

  public StringBuilder append(final int val) {
    final String str = String.valueOf(val);
    final byte[] strBytes = str.getBytes();
    for (int i = 0; i < strBytes.length; i++) {
      this.bytes[idx++] = strBytes[i];
    }
    return this;
  }

  public StringBuilder append(final String s) {
    final byte[] strBytes = s.getBytes();
    for (int i = 0; i < strBytes.length; i++) {
      this.bytes[idx++] = strBytes[i];
    }
    return this;
  }

  public StringBuilder append(final Object o) {
    final byte[] strBytes = String.valueOf(o).getBytes();
    for (int i = 0; i < strBytes.length; i++) {
      this.bytes[idx++] = strBytes[i];
    }
    return this;
  }

  public String toString() {
    final byte[] tmp = new byte[idx];
    for (int i = 0; i < idx; i++) {
      tmp[i] = bytes[i];
    }
    return new String(tmp);
  }
}
