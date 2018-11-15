package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class IaddInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    final int op1 = (int) frame.pop();
    final int op2 = (int) frame.pop();
    frame.push(op1 + op2);
    frame.setProgramCounter(frame.getProgramCounter() + 1);
  }
}
