package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.impl.interpreter.InterpreterUtils;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectBuilder;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectHeader;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class ArraylengthInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame, final ObjectBuilder objectBuilder) {
    final HeapPointer arrayPtr = (HeapPointer) frame.pop();

    if (arrayPtr == HeapPointer.NULL) {
      final TypeName exceptionName = TypeFactory.fromBinaryName("java.lang.NullPointerException");
      InterpreterUtils.throwException(exceptionName, stack, frame, pc, objectBuilder);
      return;
    }
    final HeapObject array = arrayPtr.dereference();
    final int len = (int) array.getHeader().getWord(ObjectHeader.ARRAY_LENGTH_WORD);
    frame.push(len);
    frame.setProgramCounter(pc + 1);
  }
}
