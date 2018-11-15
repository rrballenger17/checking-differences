package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import java.util.List;

import edu.harvard.cscie98.simplejava.config.Log;
import edu.harvard.cscie98.simplejava.impl.interpreter.InterpreterUtils;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.classloader.VmMethod;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectBuilder;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class InvokespecialInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame, final VmClassLoader classLoader,
      final ObjectBuilder objectBuilder) {
    final VmMethod mthd = InterpreterUtils.getStaticMethodRef(pc, code, frame, classLoader);
    if (mthd == null) {
      final TypeName exceptionName = TypeFactory
          .fromBinaryName("java.lang.AbstractMethodException");
      InterpreterUtils.throwException(exceptionName, stack, frame, pc, objectBuilder);
      return;
    }

    final List<TypeName> params = mthd.getParamters();
    final StackFrame newFrame = stack.push(mthd);
    for (int i = params.size(); i > 0; i--) {
      newFrame.setLocalVariable(i, frame.pop());
    }

    final HeapPointer objref = (HeapPointer) frame.pop();
    if (objref == HeapPointer.NULL) {
      final TypeName exceptionName = TypeFactory.fromBinaryName("java.lang.NullPointerException");
      stack.pop();
      InterpreterUtils.throwException(exceptionName, stack, frame, pc, objectBuilder);
      return;
    }
    newFrame.setLocalVariable(0, objref);

    frame.setProgramCounter(pc + 3);
    Log.interpreter("Invoking " + mthd.getDefiningClass() + "." + mthd);
  }
}
