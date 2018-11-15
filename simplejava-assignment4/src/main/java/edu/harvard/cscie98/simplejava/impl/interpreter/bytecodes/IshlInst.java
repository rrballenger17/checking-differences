package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class IshlInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    final int val = (int) frame.pop();
    final int shift = ((int) frame.pop()) & 0x1f;
    frame.push(val << shift);
    frame.setProgramCounter(pc + 1);
  }
}
