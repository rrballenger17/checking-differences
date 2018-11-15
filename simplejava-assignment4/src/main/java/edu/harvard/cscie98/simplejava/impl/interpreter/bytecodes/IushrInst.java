package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class IushrInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    final int shift = ((int) frame.pop()) & 0x1f;
    final int val = (int) frame.pop();
    frame.push(val >> shift);
    frame.setProgramCounter(pc + 1);
  }

}
