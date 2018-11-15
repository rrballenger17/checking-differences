package edu.harvard.cscie98.simplejava.impl.interpreter.intrinsics;

import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class Println_Int implements Intrinsic {

  @Override
  public Object execute(final StackFrame frame) {
    final Integer i = (Integer) frame.getLocalVariable(1);
    System.out.println(i);
    return null;
  }

}
