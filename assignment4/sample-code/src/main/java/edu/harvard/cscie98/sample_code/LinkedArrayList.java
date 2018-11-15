package edu.harvard.cscie98.sample_code;

public class LinkedArrayList {

  static LinkedArrayList head;
  static int counter = 1;
  private static int[] staticArray;

  private final int expirationDate;
  private LinkedArrayList next;
  private LinkedArrayList prev;
  private final int[] payload;

  public LinkedArrayList(final int expirationDate) {
    this.expirationDate = expirationDate;
    this.payload = new int[counter++];
    if (counter > 20) {
      counter = 1;
    }
  }

  public static void main(final String[] args) {
    staticArray = new int[100];
    final Integer[] localArray = new Integer[100];
    for (int i = 0; i < 100; i++) {
      staticArray[i] = i;
      localArray[i] = Integer.valueOf(i);
    }
    final LinkedArrayList localList = new LinkedArrayList(9999);
    final int payloadSize = localList.payload.length;
    head = new LinkedArrayList(10);
    for (int i = 0; i < 50; i++) {
      for (int j = 0; j < 1000; j++) {
        final LinkedArrayList tmp = new LinkedArrayList(i + (j % 10));
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
    if (localList.payload.length != payloadSize) {
      System.err.println("Wrong payload size");
    }
    for (int i = 0; i < 100; i++) {
      if (staticArray[i] != localArray[i].intValue()) {
        System.err.println("Incorrect value in arrays");
      }
    }
  }

  private static int countElements() {
    LinkedArrayList e = head;
    int cnt = 0;
    while (e != null) {
      e = e.next;
      cnt++;
    }
    return cnt;
  }

  private static void expireElements(final int tick) {
    LinkedArrayList e = head;
    int expired = 0;
    while (e != null) {
      final LinkedArrayList next = e.next;
      if ((e.expirationDate < tick + 7)) {
        expired++;
        final LinkedArrayList prev = e.prev;

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
