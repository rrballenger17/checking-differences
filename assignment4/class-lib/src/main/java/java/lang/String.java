package java.lang;

public class String {
  private byte[] bytes;

  private String() {
  }

  public String(final byte[] bytes) {
    this.bytes = bytes;
  }

  public byte[] getBytes() {
    return bytes;
  }

  public void append(final char c) {
    final byte[] tmp = new byte[bytes.length + 1];
    for (int i = 0; i < bytes.length; i++) {
      tmp[i] = bytes[i];
    }
    tmp[bytes.length] = (byte) c;
    bytes = tmp;
  }

  public static java.lang.String valueOf(int val) {
    final String s = new String();
    final byte[] tmp = new byte[20];
    final boolean neg = val < 0;
    if (neg) {
      val *= -1;
    }
    int position = 0;
    while (val > 9) {
      final int code = ('0' + val % 10);
      tmp[position++] = (byte) code;
      val = val / 10;
    }
    final int code = ('0' + val % 10);
    tmp[position++] = (byte) code;
    if (neg) {
      tmp[position++] = '-';
    }
    s.bytes = new byte[position];
    for (int i = 0; i < position; i++) {
      s.bytes[position - 1 - i] = tmp[i];
    }
    return s;
  }

  public static String valueOf(final Object o) {
    if (o instanceof String) {
      return (String) o;
    }
    return new String(new byte[] { '<', 'O', 'b', 'j', 'e', 'c', 't', '>' });
  }

}
