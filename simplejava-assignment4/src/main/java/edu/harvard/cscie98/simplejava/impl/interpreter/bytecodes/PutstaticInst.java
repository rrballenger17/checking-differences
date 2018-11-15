package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.impl.interpreter.InterpreterUtils;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class PutstaticInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame, final VmClassLoader classLoader) {
    final Object val = frame.pop();
    InterpreterUtils.getOrSetStatic(pc, code, frame, classLoader, val);
    frame.setProgramCounter(pc + 3);
  }
}
