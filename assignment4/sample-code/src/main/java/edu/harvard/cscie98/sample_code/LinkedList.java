package edu.harvard.cscie98.sample_code;

public class LinkedList {

  static LinkedList head;

  private final int expirationDate;
  private LinkedList next;
  private LinkedList prev;

  public LinkedList(final int expirationDate) {
    this.expirationDate = expirationDate;
  }

  public static void main(final String[] args) {
    final LinkedList localList = new LinkedList(9999);
    head = new LinkedList(10);
    for (int i = 0; i < 25; i++) {
      for (int j = 0; j < 1000; j++) {
        final LinkedList tmp = new LinkedList(i + (j % 10));
        head.prev = tmp;
        tmp.next = head;
        head = tmp;
      }
      expireElements(i);
    }
    final int cnt = countElements();
    System.out.println("There are " + cnt + " elements in the list");
    if (localList.expirationDate != 9999) {
      System.err.println("Wrong expiration date");
    }
  }

  private static int countElements() {
    LinkedList e = head;
    int cnt = 0;
    while (e != null) {
      e = e.next;
      cnt++;
    }
    return cnt;
  }

  private static void expireElements(final int tick) {
    LinkedList e = head;
    int expired = 0;
    while (e != null) {
      final LinkedList next = e.next;
      if (e.expirationDate == tick) {
        expired++;
        final LinkedList prev = e.prev;

        if (prev == null) {
          head = next;
        } else {
          prev.next = next;
        }
        if (next != null) {
          next.prev = prev;
        }
      }
      e = next;
    }
    System.out.println("Expiring " + expired + " at " + tick);
  }
}
