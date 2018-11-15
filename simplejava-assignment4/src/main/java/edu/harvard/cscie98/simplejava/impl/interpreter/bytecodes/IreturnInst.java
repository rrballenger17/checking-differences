package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.config.Log;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class IreturnInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    final Object returnVal = frame.pop();
    stack.pop();
    stack.peek().push(returnVal);
    Log.interpreter("Method returning " + returnVal);
  }
}
