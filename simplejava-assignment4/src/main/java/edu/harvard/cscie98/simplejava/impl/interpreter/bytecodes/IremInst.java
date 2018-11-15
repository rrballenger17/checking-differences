package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.impl.interpreter.InterpreterUtils;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectBuilder;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class IremInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame, final ObjectBuilder objectBuilder) {
    final int val2 = (int) frame.pop();
    final int val1 = (int) frame.pop();

    if (val2 == 0) {
      final TypeName exceptionName = TypeFactory.fromBinaryName("java.lang.ArithmeticException");
      InterpreterUtils.throwException(exceptionName, stack, frame, pc, objectBuilder);
      return;
    }
    frame.push(val1 % val2);
    frame.setProgramCounter(frame.getProgramCounter() + 1);
  }
}
