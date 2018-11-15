package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import org.apache.bcel.Constants;

import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class Aload2Inst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    AloadInst.load(Constants.ALOAD_2, Constants.ALOAD, Constants.ALOAD_0, pc, code, frame);
  }
}
