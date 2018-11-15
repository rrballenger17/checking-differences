package java.lang;

public class Throwable {

  String type;
  private final String[] trace;

  public Throwable() {
    this.trace = new String[1024];
  }

  public void printStackTrace() {
    System.out.println(type);
    for (final String s : trace) {
      if (s != null) {
        System.out.println(s);
      }
    }
  }
}
