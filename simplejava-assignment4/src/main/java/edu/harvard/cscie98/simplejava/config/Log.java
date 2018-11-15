package edu.harvard.cscie98.simplejava.config;

import java.util.HashSet;
import java.util.Set;

public class Log {

  public enum Component {
    Allocator, ClassLoader, Config, GC, Interpreter, JIT, Stats, Inliner, Exceptions
  }

  private static Set<Component> verbose;

  static {
    verbose = new HashSet<Component>();
  }

  public static void setVerbose(final Component c) {
    verbose.add(c);
  }

  public static void clearVerbose() {
    verbose = new HashSet<Component>();
  }

  public static void cl(final String msg) {
    if (verbose.contains(Component.ClassLoader)) {
      System.out.println("[classloading] " + msg);
    }
  }

  public static void config(final String msg) {
    if (verbose.contains(Component.Config)) {
      System.out.println("[config]       " + msg);
    }
  }

  public static void interpreter(final String msg) {
    if (verbose.contains(Component.Interpreter)) {
      System.out.println("[interpreter]  " + msg);
    }
  }

  public static void alloc(final String msg) {
    if (verbose.contains(Component.Allocator)) {
      System.out.println("[allocation]   " + msg);
    }
  }

  public static void gc(final String msg) {
    if (verbose.contains(Component.GC)) {
      System.out.println("[garbage collection]   " + msg);
    }
  }

  public static void jit(final String msg) {
    if (verbose.contains(Component.JIT)) {
      System.out.println("[JIT]   " + msg);
    }
  }

  public static void inliner(final String msg) {
    if (verbose.contains(Component.Inliner)) {
      System.out.println("[Inliner] " + msg);
    }
  }

  public static void stats(final String msg) {
    if (verbose.contains(Component.Stats)) {
      System.out.println("[stats]   " + msg);
    }
  }

  public static void exception(final String msg) {
    if (verbose.contains(Component.Exceptions)) {
      System.out.println("[exceptions] " + msg);
    }
  }

}
