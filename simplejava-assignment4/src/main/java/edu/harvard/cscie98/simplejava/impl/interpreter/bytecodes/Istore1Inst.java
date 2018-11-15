package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import org.apache.bcel.Constants;

import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class Istore1Inst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    IstoreInst.store(Constants.ISTORE_1, Constants.ISTORE, Constants.ISTORE_0, pc, code, frame);
  }
}
