package edu.harvard.cscie98.simplejava.vm.objectmodel;

import java.util.List;

import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClass;
import edu.harvard.cscie98.simplejava.vm.classloader.VmField;

/**
 * Subtype of the {@link TypeDescriptor} interface that represents any
 * heap-allocated object that is not an array.
 */
public interface ObjectTypeDescriptor extends TypeDescriptor {

  /**
   * Get the size of an instance of this object.
   * <P>
   * The size returned by this method is determined by adding the sizes of all
   * fields in the object. It includes fields determined by this type and any
   * super types, but excludes any object header.
   *
   * @return The size in bytes required to store the fields of an instance of
   *         this object.
   */
  int getSize();

  /**
   * Get a list of the fields in this object.
   * <P>
   * This method makes no guarantees around the ordering of fields in the
   * object; to determine the offset of a field, use {@link #getFieldOffset}.
   * <P>
   * The returned list contains only fields declared in this class; there may be
   * other fields defined by super classes that do not appear in this list.
   *
   * @return A {@code List} of {@link VmField} objects representing the fields
   *         defined by this type.
   */
  List<VmField> getFields();

  /**
   * Get the descriptor of this type's super class.
   *
   * @return An {@code ObjectTypeDescriptor} representing the super class, or
   *         {@code null} if this class has no super class.
   */
  ObjectTypeDescriptor getSuperClassDescriptor();

  /**
   * Get the offset of a given field within the object.
   *
   * @param definingClass
   *          the {@link VmClass} that defines the field. This class must
   *          represent either this type or one of its super classes.
   * @param field
   *          the {@link VmField} to locate within the object.
   * @return The offset of the field wihin the object.
   *
   * @throws RuntimeException
   *           if the field is not found in the defining class.
   */
  int getFieldOffset(VmClass definingClass, VmField field);

  /**
   * Set the value of a static field defined in the class represented by this
   * descriptor.
   * <P>
   * Note that this method only sets fields defined by this class, not any of
   * its super classes.
   *
   * @param fieldName
   *          The name of the field to set. This must correspond to a static
   *          field in the class.
   * @param fieldSignature
   *          A {@link TypeName} representing the signature of the field to set.
   * @param value
   *          The value to set for the static field.
   */
  void setStaticField(String fieldName, TypeName fieldSignature, Object value);

  /**
   * Get the value of a static field defined in the class represented by this
   * descriptor.
   * <P>
   * Note that this method only gets fields defined by this class, not any of
   * its super classes.
   *
   * @param fieldName
   *          The name of the field to access. This must correspond to a static
   *          field in the class.
   * @param fieldSignature
   *          A {@link TypeName} representing the signature of the field to set.
   * @return The current value of the static field.
   */
  Object getStaticField(String fieldName, TypeName fieldSignature);

}
