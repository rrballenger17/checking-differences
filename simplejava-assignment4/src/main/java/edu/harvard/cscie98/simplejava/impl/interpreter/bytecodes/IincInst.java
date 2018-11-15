package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.impl.interpreter.InterpreterUtils;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class IincInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    final int index = InterpreterUtils.getByteUnsigned(code, pc + 1);
    final int increment = InterpreterUtils.getByteImmediate(code, pc + 2);
    final int value = (int) frame.getLocalVariable(index);
    frame.setLocalVariable(index, value + increment);
    frame.setProgramCounter(pc + 3);
  }
}
