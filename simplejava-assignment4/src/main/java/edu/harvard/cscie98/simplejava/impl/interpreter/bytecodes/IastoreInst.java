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

public class IastoreInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame, final ObjectBuilder objectBuilder) {
    final int val = (int) frame.pop();
    final int index = (int) frame.pop();
    final HeapPointer arrayRef = (HeapPointer) frame.pop();

    if (arrayRef == HeapPointer.NULL) {
      final TypeName exceptionName = TypeFactory.fromBinaryName("java.lang.NullPointerException");
      InterpreterUtils.throwException(exceptionName, stack, frame, pc, objectBuilder);
      return;
    }
    final int length = (int) arrayRef.dereference().getHeader()
        .getWord(ObjectHeader.ARRAY_LENGTH_WORD);
    if (index >= length) {
      final TypeName exceptionName = TypeFactory
          .fromBinaryName("java.lang.ArrayIndexOutOfBoundsException");
      InterpreterUtils.throwException(exceptionName, stack, frame, pc, objectBuilder);
      return;
    }

    final HeapObject obj = arrayRef.dereference();
    obj.setValueAtOffset(index, val);

    frame.setProgramCounter(pc + 1);
  }
}
