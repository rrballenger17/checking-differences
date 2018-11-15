package edu.harvard.cscie98.simplejava.impl.objectmodel;

import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ArrayTypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.objectmodel.TypeDescriptor;

public class ArrayTypeDescriptorImpl implements ArrayTypeDescriptor {

  private final TypeName name;
  private final TypeDescriptor elementDescriptor;

  public ArrayTypeDescriptorImpl(final TypeDescriptor elementDescriptor, final TypeName name) {
    this.elementDescriptor = elementDescriptor;
    this.name = name;
  }

  @Override
  public TypeDescriptor getElementDescriptor() {
    return elementDescriptor;
  }

  @Override
  public TypeName getTypeName() {
    return name;
  }

  @Override
  public boolean isArray() {
    return true;
  }

  @Override
  public boolean isPrimitive() {
    return false;
  }

  @Override
  public boolean isObject() {
    return false;
  }

}
