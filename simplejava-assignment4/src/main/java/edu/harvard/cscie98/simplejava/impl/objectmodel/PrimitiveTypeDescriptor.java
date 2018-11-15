package edu.harvard.cscie98.simplejava.impl.objectmodel;

import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.objectmodel.TypeDescriptor;

public class PrimitiveTypeDescriptor implements TypeDescriptor {

  private final TypeName name;

  public PrimitiveTypeDescriptor(final TypeName name) {
    this.name = name;
  }

  @Override
  public TypeName getTypeName() {
    return name;
  }

  @Override
  public boolean isArray() {
    return false;
  }

  @Override
  public boolean isPrimitive() {
    return true;
  }

  @Override
  public boolean isObject() {
    return false;
  }

}
