package edu.harvard.cscie98.simplejava.impl.classloader;

import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.ConstantFieldref;
import org.apache.bcel.classfile.ConstantInteger;
import org.apache.bcel.classfile.ConstantMethodref;
import org.apache.bcel.classfile.ConstantNameAndType;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantUtf8;
import org.apache.bcel.generic.ConstantPoolGen;

import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmConstantPool;

public class BcelConstantPool implements VmConstantPool {

  private final ConstantPool constantPool;

  public BcelConstantPool(final ConstantPoolGen constantPool) {
    this.constantPool = constantPool.getConstantPool();
  }

  @Override
  public ConstantMethodref getMethodEntry(final int idx) {
    return (ConstantMethodref) constantPool.getConstant(idx);
  }

  @Override
  public String getUtf8Entry(final int idx) {
    return ((ConstantUtf8) constantPool.getConstant(idx)).getBytes();
  }

  @Override
  public Integer getIntegerEntry(final int idx) {
    return (Integer) ((ConstantInteger) constantPool.getConstant(idx))
        .getConstantValue(constantPool);
  }

  @Override
  public ConstantNameAndType getNameAndTypeEntry(final int idx) {
    return (ConstantNameAndType) constantPool.getConstant(idx);
  }

  @Override
  public TypeName getClassEntry(final int idx) {
    final String descriptor = ((ConstantClass) constantPool.getConstant(idx))
        .getBytes(constantPool);
    return TypeFactory.fromClassFileName(descriptor);
  }

  @Override
  public ConstantFieldref getFieldEntry(final int idx) {
    return (ConstantFieldref) constantPool.getConstant(idx);
  }

  @Override
  public Constant getConstantEntry(final int index) {
    return constantPool.getConstant(index);
  }
}
