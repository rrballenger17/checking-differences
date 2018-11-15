package edu.harvard.cscie98.simplejava.vm.classloader;

import java.util.List;

import edu.harvard.cscie98.simplejava.vm.objectmodel.ArrayTypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectTypeDescriptor;

public interface VmClass {

  /**
   * Read the class file for this class and populate internal data structures.
   * <P>
   * This method should be called exactly once before any other methods on this
   * class are called.
   * 
   * @param classLoader
   *          The class loader instance to use when reading the class file
   * 
   * @throws ClassLoadingException
   *           If an error occurs while parsing the class file.
   */
  void parse(VmClassLoader classLoader) throws ClassLoadingException;

  /**
   * Get the methods defined in this class.
   * <P>
   * Note that this method does not return any methods defined in super classes.
   * 
   * @return a {@code List} of {@link VmMethod} objects that represent the
   *         methods defined in this class.
   */
  List<VmMethod> getMethods();

  /**
   * Get the descriptor that will be associated with instances of this class.
   * <P>
   * This method returns the same {@link ObjectTypeDescriptor} instance on all
   * subsequent calls.
   * 
   * @return an {@code ObjectTypeDescriptor} instance associated with this
   *         class.
   */
  ObjectTypeDescriptor getObjectTypeDescriptor();

  /**
   * Get the descriptor that will be associated with arrays containing this
   * class.
   * <P>
   * This method returns the same {@link ArrayTypeDescriptor} instance on all
   * subsequent calls.
   * 
   * @return an {@code ArrayTypeDescriptor} instance associated with this class.
   */
  ArrayTypeDescriptor getArrayTypeDescriptor();

  /**
   * Get the name of this class.
   * 
   * @return A {@link TypeName} instance that represents the name of this class.
   */
  TypeName getName();

  /**
   * Get a specific field defined by this class.
   * 
   * @param fieldName
   *          The name of the field to find.
   * @param fieldSignature
   *          A {@link TypeName} containing the type of the field.
   * @return a field matching the name and signature provided, or {@code null}
   *         if the field is not found.
   */
  VmField getField(String fieldName, TypeName fieldSignature);

  /**
   * Get a specific method defined by this class.
   * 
   * @param methodName
   *          The name of the field to find.
   * @param methodSignature
   *          The signature of the method, as defined in the JVM Spec, Section
   *          4.3.3.
   * @return A method matching the name and signature provided, or {@code null}
   *         if the method is not found.
   */
  VmMethod getMethod(String methodName, String methodSignature);

  /**
   * Get this class's super class.
   * 
   * @return a {@code VmClass} representing the super class, or {@code null} if
   *         the class has no super class.
   */
  VmClass getSuperClass();

  /**
   * Set the value of a static field defined in this class.
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
   * Get the value of a static field defined in this class.
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

  /**
   * Get all static fields defined in this class.
   * 
   * @return A {@code List} of {@link VmField} objects representing the static
   *         fields in the class.
   */
  List<VmField> getStaticFields();

  /**
   * Get the name of the file in which this class was defined.
   * 
   * This method returns only the name of the file, not the path at which the
   * file was located.
   * 
   * @return A {@code String} containing the file name where this class was
   *         defined.
   */
  String getFileName();
}
