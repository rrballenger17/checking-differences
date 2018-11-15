package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import org.apache.bcel.Constants;

import edu.harvard.cscie98.simplejava.impl.interpreter.InterpreterUtils;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class IstoreInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    store(Constants.ISTORE, Constants.ISTORE, Constants.ISTORE_0, pc, code, frame);
  }

  static void store(final short opcode, final short baseOpcode, final short zeroOpcode,
      final int pc, final byte[] code, final StackFrame frame) {
    final int idx;
    if (opcode == baseOpcode) {
      idx = InterpreterUtils.getByteUnsigned(code, pc + 1);
      frame.setProgramCounter(pc + 2);
    } else {
      idx = opcode - zeroOpcode;
      frame.setProgramCounter(pc + 1);
    }
    final Object value = frame.pop();
    frame.setLocalVariable(idx, value);
  }

}
