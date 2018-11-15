package edu.harvard.cscie98.simplejava.vm.classloader;

public interface VmField {

  /**
   * Determine whether this field is a primitive type
   * 
   * @return True if the field is primitive, false if it is an object or array.
   */
  boolean isPrimitive();

  /**
   * Determine whether this field is a reference type.
   * 
   * @return True if the field is an object or an array, false if it is
   *         primitive.
   */
  boolean isReference();

  /**
   * Get the field's name.
   * 
   * @return The name of the field.
   */
  String getName();

  /**
   * Get the type of the field.
   * 
   * @return A {@link TypeName} object that contains the field's type, derived
   *         from its signature in the class file.
   */
  TypeName getType();

  /**
   * Get the class in which this field was defined.
   * 
   * @return A {@link VmClass} that represents the class that defined this
   *         field.
   */
  VmClass getDefiningClass();

  /**
   * Determine whether the field is static.
   * 
   * @return True if the field is static, or false if it is an instance field.
   */
  boolean isStatic();

}
