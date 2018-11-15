package edu.harvard.cscie98.simplejava.impl.classloader;

import java.util.List;

import edu.harvard.cscie98.simplejava.impl.objectmodel.ArrayTypeDescriptorImpl;
import edu.harvard.cscie98.simplejava.impl.objectmodel.PrimitiveTypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.VmInternalError;
import edu.harvard.cscie98.simplejava.vm.classloader.ClassLoadingException;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClass;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.classloader.VmField;
import edu.harvard.cscie98.simplejava.vm.classloader.VmMethod;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ArrayTypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectTypeDescriptor;

public class PrimitiveVmClass implements VmClass {

  private final TypeName name;
  private final ArrayTypeDescriptor arrayDescriptor;

  public PrimitiveVmClass(final TypeName name) {
    this.name = name;
    final PrimitiveTypeDescriptor desc = new PrimitiveTypeDescriptor(name);
    this.arrayDescriptor = new ArrayTypeDescriptorImpl(desc, name);
  }

  @Override
  public void parse(final VmClassLoader classLoader) throws ClassLoadingException {
  }

  @Override
  public List<VmMethod> getMethods() {
    throw new VmInternalError("Can't get methods from a primitive type");
  }

  @Override
  public ObjectTypeDescriptor getObjectTypeDescriptor() {
    throw new VmInternalError("Can't get class descriptor from a primitive type");
  }

  @Override
  public ArrayTypeDescriptor getArrayTypeDescriptor() {
    return arrayDescriptor;
  }

  @Override
  public TypeName getName() {
    return name;
  }

  @Override
  public VmField getField(final String fieldName, final TypeName fieldSignature) {
    throw new VmInternalError("Can't get field from a primitive type");
  }

  @Override
  public VmClass getSuperClass() {
    throw new VmInternalError("Can't get super class from a primitive type");
  }

  @Override
  public VmMethod getMethod(final String methodName, final String methodSignature) {
    throw new VmInternalError("Can't get method from a primitive type");
  }

  @Override
  public void setStaticField(final String fieldName, final TypeName fieldSignature,
      final Object value) {
    throw new VmInternalError("Can't set static field on a primitive type");
  }

  @Override
  public Object getStaticField(final String fieldName, final TypeName fieldSignature) {
    throw new VmInternalError("Can't get static field from a primitive type");
  }

  @Override
  public List<VmField> getStaticFields() {
    throw new VmInternalError("Can't get static fields from a primitive type");
  }

  @Override
  public String toString() {
    return "Primitive " + name;
  }

  @Override
  public String getFileName() {
    throw new VmInternalError("Can't get file name from a primitive type");
  }

}
