package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.impl.interpreter.InterpreterUtils;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class SipushInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    int value = InterpreterUtils.getUnsignedDoubleByte(code, pc + 1);
    if ((value & 0x8000) == 0x8000){
      value |= 0xFFFF0000;
    }
    frame.push(value);
    frame.setProgramCounter(pc + 3);
  }
}
