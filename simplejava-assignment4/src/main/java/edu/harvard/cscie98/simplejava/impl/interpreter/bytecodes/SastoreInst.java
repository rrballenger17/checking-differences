package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class SastoreInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    final int val = (int) frame.pop();
    final int index = (int) frame.pop();
    final HeapPointer arrayRef = (HeapPointer) frame.pop();

    final HeapObject obj = arrayRef.dereference();
    obj.setValueAtOffset(index, val);

    frame.setProgramCounter(pc + 1);
  }
}
