package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class AconstNullInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    frame.push(HeapPointer.NULL);
    frame.setProgramCounter(pc + 1);
  }
}
