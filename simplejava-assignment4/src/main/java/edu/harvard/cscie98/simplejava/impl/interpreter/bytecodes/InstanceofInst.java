package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.impl.interpreter.InterpreterUtils;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmConstantPool;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectHeader;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectTypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.objectmodel.TypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class InstanceofInst {

  public static void interpret(final int pc, final byte[] code, final JvmStack stack,
      final StackFrame frame) {
    final HeapPointer obj = (HeapPointer) frame.pop();
    final int classIndex = InterpreterUtils.getUnsignedDoubleByte(code, pc + 1);
    final VmConstantPool cp = frame.getConstantPool();
    final TypeName type = cp.getClassEntry(classIndex);
    if (checkCast(obj, type)) {
      frame.push(1);
    } else {
      frame.push(0);
    }
    frame.setProgramCounter(pc + 3);
  }

  private static boolean checkCast(final HeapPointer obj, final TypeName type) {
    final TypeName objType = TypeFactory.fromBinaryName("java.lang.Object");
    if (type.equals(objType)) {
      return true;
    }
    if (obj == HeapPointer.NULL) {
      return false;
    }

    TypeDescriptor desc = (TypeDescriptor) obj.dereference().getHeader()
        .getWord(ObjectHeader.CLASS_DESCRIPTOR_WORD);
    TypeName runtimeType = desc.getTypeName();
    while (!runtimeType.equals(objType)) {
      if (runtimeType.equals(type)) {
        return true;
      }
      if (desc.isArray()) {
        return false;
      }
      desc = ((ObjectTypeDescriptor) desc).getSuperClassDescriptor();
      runtimeType = desc.getTypeName();
    }
    return false;
  }

}
