package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.impl.interpreter.InterpreterUtils;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class IfnonnullInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    final int target = InterpreterUtils.getSignedDoubleByte(code, pc + 1);
    final HeapPointer val = (HeapPointer) frame.pop();
    if (val != HeapPointer.NULL) {
      frame.setProgramCounter(pc + target);
    } else {
      frame.setProgramCounter(pc + 3);
    }
  }
}
