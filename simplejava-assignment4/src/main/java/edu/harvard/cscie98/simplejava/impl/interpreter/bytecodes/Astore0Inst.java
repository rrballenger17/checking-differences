package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import org.apache.bcel.Constants;

import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class Astore0Inst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    AstoreInst.store(Constants.ASTORE_0, Constants.ASTORE, Constants.ASTORE_0, pc, code, frame);
  }
}
