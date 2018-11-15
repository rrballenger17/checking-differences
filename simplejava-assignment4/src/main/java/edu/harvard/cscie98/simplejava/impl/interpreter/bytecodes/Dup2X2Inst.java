package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class Dup2X2Inst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    final Object val1 = frame.pop();
    final Object val2 = frame.pop();
    final Object val3 = frame.pop();
    final Object val4 = frame.pop();
    frame.push(val2);
    frame.push(val1);
    frame.push(val4);
    frame.push(val3);
    frame.push(val2);
    frame.push(val1);
    frame.setProgramCounter(pc + 1);
  }
}
