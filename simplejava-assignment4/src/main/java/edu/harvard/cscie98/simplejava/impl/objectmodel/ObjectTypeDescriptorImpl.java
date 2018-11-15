package edu.harvard.cscie98.simplejava.impl.objectmodel;

import java.util.ArrayList;
import java.util.List;

import edu.harvard.cscie98.simplejava.config.HeapParameters;
import edu.harvard.cscie98.simplejava.vm.VmInternalError;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClass;
import edu.harvard.cscie98.simplejava.vm.classloader.VmField;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectTypeDescriptor;

public class ObjectTypeDescriptorImpl implements ObjectTypeDescriptor {

  private final int size;
  private final List<VmField> fields;
  private final TypeName name;
  private final ObjectTypeDescriptor superClass;
  private final VmClass cls;
  private final ArrayList<Object> staticValues;
  private final ArrayList<VmField> staticFields;

  public ObjectTypeDescriptorImpl(final List<VmField> fields, final TypeName name, final VmClass cls) {
    this.fields = fields;
    this.cls = cls;
    final VmClass superClass = cls.getSuperClass();
    this.superClass = superClass != null ? superClass.getObjectTypeDescriptor() : null;
    this.size = (int) (fields.size() * HeapParameters.BYTES_IN_WORD);
    this.name = name;
    this.staticFields = new ArrayList<VmField>();
    this.staticValues = new ArrayList<Object>();
    for (final VmField fld : fields) {
      if (fld.isStatic()) {
        staticFields.add(fld);
        staticValues.add(fld.getType().getDefaultValue());
      }
    }
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
    return false;
  }

  @Override
  public boolean isObject() {
    return true;
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  public List<VmField> getFields() {
    return fields;
  }

  @Override
  public ObjectTypeDescriptor getSuperClassDescriptor() {
    return superClass;
  }

  @Override
  public int getFieldOffset(final VmClass definingClass, final VmField field) {
    for (int i = 0; i < fields.size(); i++) {
      final VmField f = fields.get(i);
      if (f.getName().equals(field.getName()) && f.getType().equals(field.getType())
          && f.getDefiningClass().equals(definingClass)) {
        return i;
      }
    }
    throw new VmInternalError("Field " + field + " not found in class " + definingClass);
  }

  @Override
  public void setStaticField(final String fieldName, final TypeName fieldSignature,
      final Object value) {
    cls.setStaticField(fieldName, fieldSignature, value);
  }

  @Override
  public Object getStaticField(final String fieldName, final TypeName fieldSignature) {
    return cls.getStaticField(fieldName, fieldSignature);
  }

  @Override
  public String toString() {
    return "Descriptor for " + name;
  }
}
