package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.impl.interpreter.InterpreterUtils;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectBuilder;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class GetfieldInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame, final VmClassLoader classLoader, final ObjectBuilder objectBuilder) {
    final int idx = InterpreterUtils.getFieldIndex(pc, code, frame, classLoader);
    final HeapPointer objPtr = (HeapPointer) frame.pop();

    if (objPtr == HeapPointer.NULL) {
      final TypeName exceptionName = TypeFactory.fromBinaryName("java.lang.NullPointerException");
      InterpreterUtils.throwException(exceptionName, stack, frame, pc, objectBuilder);
      return;
    }
    final Object val = objPtr.dereference().getValueAtOffset(idx);
    frame.push(val);
    frame.setProgramCounter(pc + 3);
  }
}
