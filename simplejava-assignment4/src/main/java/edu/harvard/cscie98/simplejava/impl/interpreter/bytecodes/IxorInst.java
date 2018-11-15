package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class IxorInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    final int val1 = (int) frame.pop();
    final int val2 = (int) frame.pop();
    frame.push(val1 ^ val2);
    frame.setProgramCounter(pc + 1);
  }

}
