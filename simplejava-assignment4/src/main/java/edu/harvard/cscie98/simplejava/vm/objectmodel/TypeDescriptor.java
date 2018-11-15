package edu.harvard.cscie98.simplejava.vm.objectmodel;

import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;

/**
 * This interface contains the type information referred to by the
 * {@link ObjectHeader#CLASS_DESCRIPTOR_WORD} of an object or array's
 * {@link ObjectHeader}.
 */
public interface TypeDescriptor {

  /**
   * Get the name of the type represented by this descriptor.
   *
   * @return a {@link TypeName} containing the type's name.
   */
  TypeName getTypeName();

  /**
   * Determine whether the type represented by this descriptor is an array.
   *
   * @return true if the type is an array, false if it is an object or primitive
   *         type.
   */
  boolean isArray();

  /**
   * Determine whether the type represented by this descriptor is primitive.
   * <P>
   * This method may return true if the type descriptor is set as the element
   * type for an array.
   *
   * @return true if the type is primitive, false if it is an object or array
   *         type.
   */
  boolean isPrimitive();

  /**
   * Determine whether the type represented by this descriptor is an object.
   *
   * @return true if the type is an object, false if it is an array or primitive
   *         type.
   */
  boolean isObject();

}
