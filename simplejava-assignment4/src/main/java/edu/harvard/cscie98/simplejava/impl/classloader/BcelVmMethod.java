package edu.harvard.cscie98.simplejava.impl.classloader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.bcel.classfile.LineNumber;
import org.apache.bcel.classfile.LineNumberTable;
import org.apache.bcel.generic.CodeExceptionGen;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.MethodGen;

import edu.harvard.cscie98.simplejava.vm.VmInternalError;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClass;
import edu.harvard.cscie98.simplejava.vm.classloader.VmConstantPool;
import edu.harvard.cscie98.simplejava.vm.classloader.VmExceptionHandler;
import edu.harvard.cscie98.simplejava.vm.classloader.VmMethod;

public class BcelVmMethod implements VmMethod {

  private final String name;
  private final String signature;
  private final MethodGen method;
  private final VmConstantPool constantPool;
  private final VmClass definingClass;
  private final List<VmExceptionHandler> exceptionHandlers;
  private final Map<Integer, Integer> lineNumbers;

  public BcelVmMethod(final MethodGen mg, final VmClass definingClass) {
    this.definingClass = definingClass;
    this.name = mg.getName();
    this.signature = mg.getSignature();
    this.method = mg;
    this.constantPool = new BcelConstantPool(mg.getConstantPool());
    this.exceptionHandlers = new ArrayList<VmExceptionHandler>();
    for (final CodeExceptionGen ex : mg.getExceptionHandlers()) {
      exceptionHandlers.add(new BcelVmExceptionHandler(ex));
    }
    this.lineNumbers = new HashMap<Integer, Integer>();
    final LineNumberTable lines = mg.getLineNumberTable(mg.getConstantPool());
    for (final LineNumber n : lines.getLineNumberTable()) {
      lineNumbers.put(n.getStartPC(), n.getLineNumber());
    }
  }

  @Override
  public List<VmExceptionHandler> getExceptionHandlers() {
    return exceptionHandlers;
  }

  @Override
  public Map<Integer, Integer> getLineNumberTable() {
    return lineNumbers;
  }

  @Override
  public byte[] getInstructionStream() {
    return method.getMethod().getCode().getCode();
  }

  @Override
  public VmClass getDefiningClass() {
    return definingClass;
  }

  @Override
  public String getSignature() {
    return signature;
  }

  @Override
  public List<TypeName> getParamters() {
    int idx = 1;
    final List<TypeName> params = new ArrayList<TypeName>();
    while (signature.charAt(idx) != ')') {
      int dim = 0;
      while (signature.charAt(idx) == '[') {
        dim++;
        idx++;
      }
      if (signature.charAt(idx) == 'L') {
        final int typeStart = idx + 1;
        int typeEnd = typeStart;
        while (signature.charAt(idx++) != ';') {
          typeEnd++;
        }
        final String className = signature.substring(typeStart, typeEnd - 1);
        params.add(TypeFactory.fromClassFileName(className, dim > 0));
      } else {
        String descriptor = "";
        if (dim > 0) {
          descriptor += "[";
        }
        descriptor += signature.charAt(idx++);
        params.add(TypeFactory.fromDescriptor(descriptor));
      }
    }
    return params;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getMaxStack() {
    return method.getMaxStack();
  }

  @Override
  public int getMaxLocals() {
    return method.getMaxLocals();
  }

  @Override
  public VmConstantPool getConstantPool() {
    return constantPool;
  }

  @Override
  public String toString() {
    return definingClass.getName().toString() + "." + name + signature;
  }

  @Override
  public String getInstructionsAsString() {
    return method.getMethod().getCode().toString();
  }

  public Object getCallSiteIdentifier(final int byteOffset) {
    for (final InstructionHandle handle : method.getInstructionList().getInstructionHandles()) {
      if (handle.getPosition() == byteOffset
          && handle.getInstruction() instanceof InvokeInstruction) {
        return handle;
      }
    }
    throw new VmInternalError(
        "No invoke instruction at offset " + byteOffset + " in method " + this);
  }

  public MethodGen getMethodGen() {
    return method;
  }

  @Override
  public boolean isFinal() {
    return method.isFinal();
  }

  @Override
  public boolean isStatic() {
    return method.isStatic();
  }

  @Override
  public boolean equals(final Object other) {
    if (!(other instanceof BcelVmMethod)) {
      return false;
    }
    return toString().equals(((BcelVmMethod) other).toString());
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }
}
