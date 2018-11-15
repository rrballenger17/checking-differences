package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class BaloadInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    final int idx = (int) frame.pop();
    final HeapPointer arrayPtr = (HeapPointer) frame.pop();
    final HeapObject array = arrayPtr.dereference();
    final int val = (int) array.getValueAtOffset(idx);
    frame.push(val);
    frame.setProgramCounter(pc + 1);
  }
}
