package edu.harvard.cscie98.simplejava.impl.classloader;

import org.apache.bcel.classfile.Field;
import org.apache.bcel.generic.BasicType;

import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClass;
import edu.harvard.cscie98.simplejava.vm.classloader.VmField;

public class BcelVmField implements VmField {
  private final Field field;
  private final VmClass definingClass;
  private final TypeName type;

  public BcelVmField(final Field field, final VmClass definingClass) {
    this.field = field;
    this.definingClass = definingClass;
    this.type = TypeFactory.fromDescriptor(field.getSignature());
  }

  @Override
  public boolean isPrimitive() {
    return field.getType() instanceof BasicType;
  }

  @Override
  public boolean isReference() {
    return !isPrimitive();
  }

  @Override
  public boolean isStatic() {
    return field.isStatic();
  }

  @Override
  public String getName() {
    return field.getName();
  }

  @Override
  public TypeName getType() {
    return type;
  }

  @Override
  public VmClass getDefiningClass() {
    return definingClass;
  }

}
