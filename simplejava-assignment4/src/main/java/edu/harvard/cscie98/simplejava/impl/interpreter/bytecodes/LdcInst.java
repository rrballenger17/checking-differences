package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantInteger;
import org.apache.bcel.classfile.ConstantString;

import edu.harvard.cscie98.simplejava.config.SimpleJavaOutOfScopeException;
import edu.harvard.cscie98.simplejava.impl.interpreter.InterpreterUtils;
import edu.harvard.cscie98.simplejava.vm.classloader.VmConstantPool;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectBuilder;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class LdcInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame, final ObjectBuilder objectBuilder) {
    final VmConstantPool cp = frame.getConstantPool();
    final int index = InterpreterUtils.getByteUnsigned(code, pc + 1);
    final Constant c = cp.getConstantEntry(index);
    final Object val;
    if (c instanceof ConstantString) {
      final int utfIdx = ((ConstantString) c).getStringIndex();
      final String s = cp.getUtf8Entry(utfIdx);
      val = objectBuilder.internString(s).getAddress();
    } else if (c instanceof ConstantInteger) {
      val = cp.getIntegerEntry(index);
    } else {
      throw new SimpleJavaOutOfScopeException("Out of scope.");
    }
    frame.push(val);
    frame.setProgramCounter(pc + 2);
  }
}
