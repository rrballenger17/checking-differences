package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class BastoreInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    final int val = (int) frame.pop();
    final int idx = (int) frame.pop();
    final HeapPointer arrayPtr = (HeapPointer) frame.pop();
    final HeapObject array = arrayPtr.dereference();

    array.setValueAtOffset(idx, val);
    frame.setProgramCounter(pc + 1);
  }
}
