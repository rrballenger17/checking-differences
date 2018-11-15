package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class SaloadInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    final int index = (int) frame.pop();
    final HeapPointer arrayRef = (HeapPointer) frame.pop();
    final int value = (int) arrayRef.dereference().getValueAtOffset(index);
    frame.push(value);
    frame.setProgramCounter(pc + 1);
  }
}
