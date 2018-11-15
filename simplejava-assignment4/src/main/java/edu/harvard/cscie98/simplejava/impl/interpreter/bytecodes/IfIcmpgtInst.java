package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.impl.interpreter.InterpreterUtils;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class IfIcmpgtInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    final int target = InterpreterUtils.getSignedDoubleByte(code, pc + 1);
    final int val2 = (int) frame.pop();
    final int val1 = (int) frame.pop();
    if (val1 > val2) {
      frame.setProgramCounter(pc + target);
    } else {
      frame.setProgramCounter(pc + 3);
    }
  }
}
