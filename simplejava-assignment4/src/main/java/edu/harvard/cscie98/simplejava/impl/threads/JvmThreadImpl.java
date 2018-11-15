package edu.harvard.cscie98.simplejava.impl.threads;

import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.JvmThread;

public class JvmThreadImpl implements JvmThread {

  private final JvmStackImpl stack;

  public JvmThreadImpl() {
    this.stack = new JvmStackImpl();
  }

  @Override
  public JvmStack getStack() {
    return stack;
  }

}
