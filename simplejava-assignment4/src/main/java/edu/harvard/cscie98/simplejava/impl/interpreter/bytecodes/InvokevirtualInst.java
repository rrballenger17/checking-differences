package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import java.util.ArrayList;
import java.util.List;

import edu.harvard.cscie98.simplejava.config.Log;
import edu.harvard.cscie98.simplejava.impl.interpreter.InterpreterUtils;
import edu.harvard.cscie98.simplejava.impl.interpreter.intrinsics.Intrinsics;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClass;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.classloader.VmConstantPool;
import edu.harvard.cscie98.simplejava.vm.classloader.VmMethod;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectBuilder;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectHeader;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectTypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class InvokevirtualInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame, final VmClassLoader classLoader,
      final ObjectBuilder objectBuilder, final Intrinsics intrinsics) {
    final int methodIdx = InterpreterUtils.getUnsignedDoubleByte(code, pc + 1);
    final VmConstantPool constantPool = frame.getConstantPool();
    final String methodName = InterpreterUtils.getMethodName(constantPool, methodIdx);
    final String methodSig = InterpreterUtils.getMethodSignature(constantPool, methodIdx);

    final List<TypeName> paramTypes = InterpreterUtils.getParamsFromDescriptor(methodSig);
    final List<Object> paramValues = new ArrayList<Object>();
    for (int i = 0; i < paramTypes.size(); i++) {
      paramValues.add(frame.pop());
    }

    final HeapPointer target = (HeapPointer) frame.pop();
    if (target == HeapPointer.NULL) {
      final TypeName exceptionName = TypeFactory.fromBinaryName("java.lang.NullPointerException");
      InterpreterUtils.throwException(exceptionName, stack, frame, pc, objectBuilder);
      return;
    }
    final ObjectHeader header = target.dereference().getHeader();

    ObjectTypeDescriptor desc = (ObjectTypeDescriptor) header
        .getWord(ObjectHeader.CLASS_DESCRIPTOR_WORD);
    TypeName clsName = desc.getTypeName();
    VmClass cls = classLoader.loadClass(clsName);
    VmMethod mthd = cls.getMethod(methodName, methodSig);
    while (mthd == null) {
      desc = desc.getSuperClassDescriptor();
      if (desc == null) {
        final TypeName exceptionName = TypeFactory
            .fromBinaryName("java.lang.AbstractMethodException");
        InterpreterUtils.throwException(exceptionName, stack, frame, pc, objectBuilder);
        return;
      }
      clsName = desc.getTypeName();
      cls = classLoader.loadClass(clsName);
      mthd = cls.getMethod(methodName, methodSig);
    }

    final StackFrame newFrame = stack.push(mthd);
    newFrame.setLocalVariable(0, target);
    for (int i = 1; i <= paramValues.size(); i++) {
      newFrame.setLocalVariable(i, paramValues.get(paramValues.size() - i));
    }
    if (intrinsics.hasIntrinsic(mthd)) {
      Log.interpreter("Calling intrinsic for " + mthd);
      intrinsics.callIntrinsic(stack.pop());
    } else {
      Log.interpreter("Invoking " + cls.getName() + "." + methodName + " " + methodSig);
    }

    frame.setProgramCounter(pc + 3);
  }
}
