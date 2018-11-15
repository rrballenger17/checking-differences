package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class Iconst3Inst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    frame.push(3);
    frame.setProgramCounter(pc + 1);
  }
}
