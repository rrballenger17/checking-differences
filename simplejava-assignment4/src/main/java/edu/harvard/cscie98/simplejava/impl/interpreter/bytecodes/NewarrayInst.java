package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.impl.interpreter.InterpreterUtils;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectBuilder;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class NewarrayInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame, final ObjectBuilder objectBuilder) {
    final int typeCode = InterpreterUtils.getByteUnsigned(code, pc + 1);
    final TypeName type = TypeFactory.fromArrayTypeCode((byte) typeCode);
    final int length = (int) frame.pop();
    final HeapObject obj = objectBuilder.createArray(type, length);
    frame.push(obj.getAddress());
    frame.setProgramCounter(pc + 2);
  }
}
