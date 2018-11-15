package edu.harvard.cscie98.simplejava.stubs;

import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantFieldref;
import org.apache.bcel.classfile.ConstantInteger;
import org.apache.bcel.classfile.ConstantMethodref;
import org.apache.bcel.classfile.ConstantNameAndType;

import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmConstantPool;

public class ConstantPoolStub implements VmConstantPool {

  // 1: Class : 2
  // 2: UTF-8 : some/class/Name
  // 3: Integer: 42
  // 4: NameAndType: 5, 6
  // 5: UTF-8 : methodName
  // 6: UTF-8 : ()V
  // 7: MethodRef : 1, 4
  // 8: FieldRef : 1, 9
  // 9: NameAndType: 10, 11
  // 10: UTF-8 : fieldName
  // 11: UTF-8 : Lsome/class/Name;
  // 12: FieldRef : 13, 14
  // 13: Class : 15
  // 14: NameAndType: 10, 11
  // 15: UTF-8 : some/other/Class
  // 16: MethodRef : 1, 17
  // 17: NameAndType: 5, 18
  // 18: UTF-8 : (I)V
  // 19: MethodRef : 1, 20
  // 20: NameAndType: 5, 21
  // 21: UTF-8 : (ILjava/lang/Object;II)V
  // 22: MethodRef : 13, 4

  @Override
  public ConstantMethodref getMethodEntry(final int idx) {
    switch (idx) {
    case 7:
      return new ConstantMethodref(1, 4);
    case 16:
      return new ConstantMethodref(1, 17);
    case 19:
      return new ConstantMethodref(1, 20);
    case 22:
      return new ConstantMethodref(13, 4);
    }
    throw new RuntimeException("No UTF constant at " + idx);
  }

  @Override
  public String getUtf8Entry(final int idx) {
    switch (idx) {
    case 2:
      return "some/class/Name";
    case 5:
      return "methodName";
    case 6:
      return "()V";
    case 10:
      return "fieldName";
    case 11:
      return "Lsome/class/Name;";
    case 18:
      return "(I)V";
    case 21:
      return "(ILjava/lang/Object;II)V";
    }
    throw new RuntimeException("No UTF constant at " + idx);
  }

  @Override
  public Integer getIntegerEntry(final int idx) {
    switch (idx) {
    case 3:
      return 42;
    }
    throw new RuntimeException("No Integer constant at " + idx);
  }

  @Override
  public ConstantNameAndType getNameAndTypeEntry(final int idx) {
    switch (idx) {
    case 4:
      return new ConstantNameAndType(5, 6);
    case 9:
    case 14:
      return new ConstantNameAndType(10, 11);
    case 17:
      return new ConstantNameAndType(5, 18);
    case 20:
      return new ConstantNameAndType(5, 21);
    }
    throw new RuntimeException("No NameAndType constant at " + idx);
  }

  @Override
  public TypeName getClassEntry(final int idx) {
    switch (idx) {
    case 1:
      return TypeFactory.fromBinaryName("some.class.Name");
    case 13:
      return TypeFactory.fromBinaryName("some.other.Class");
    }
    throw new RuntimeException("No Class constant at " + idx);
  }

  @Override
  public ConstantFieldref getFieldEntry(final int idx) {
    switch (idx) {
    case 8:
      return new ConstantFieldref(1, 9);
    case 12:
      return new ConstantFieldref(13, 14);
    }
    throw new RuntimeException("No FieldRef constant at " + idx);
  }

  @Override
  public Constant getConstantEntry(final int idx) {
    switch (idx) {
    case 3:
      return new ConstantInteger(42);
    }
    throw new RuntimeException("No Constant at " + idx);
  }

  public TypeName getFieldType() {
    return TypeFactory.fromBinaryName("some.class.Name");
  }

  public String getFieldName() {
    return "fieldName";
  }

}
