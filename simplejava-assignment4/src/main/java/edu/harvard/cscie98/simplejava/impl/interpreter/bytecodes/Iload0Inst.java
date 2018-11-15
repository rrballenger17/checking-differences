package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import org.apache.bcel.Constants;

import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class Iload0Inst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    IloadInst.load(Constants.ILOAD_0, Constants.ILOAD, Constants.ILOAD_0, pc, code, frame);
  }
}
