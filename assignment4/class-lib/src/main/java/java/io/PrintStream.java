package java.io;

public class PrintStream {

  private static final String[] buffer = new String[1024];
  private static int idx;

  public void println(final String str) {
    print(str);
    println();
  }

  public void println() {
    print("\n");
  }

  public void print(final String str) {
    buffer[idx++] = str;
  }

  public void print(final int i) {
    print("" + i);
  }

  public void println(final int i) {
    print("" + i + "\n");
  }

}
