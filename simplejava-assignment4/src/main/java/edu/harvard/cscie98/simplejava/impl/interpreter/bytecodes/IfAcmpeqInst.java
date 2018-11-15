package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.impl.interpreter.InterpreterUtils;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class IfAcmpeqInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    final int target = InterpreterUtils.getSignedDoubleByte(code, pc + 1);
    final HeapPointer val1 = (HeapPointer) frame.pop();
    final HeapPointer val2 = (HeapPointer) frame.pop();
    if (val1.equals(val2)) {
      frame.setProgramCounter(pc + target);
    } else {
      frame.setProgramCounter(pc + 3);
    }
  }
}
