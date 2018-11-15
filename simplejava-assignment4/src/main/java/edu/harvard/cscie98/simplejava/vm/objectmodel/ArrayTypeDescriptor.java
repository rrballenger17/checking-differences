package edu.harvard.cscie98.simplejava.vm.objectmodel;

/**
 * Subtype of the {@link TypeDescriptor} interface that describes the type of an
 * array. Arrays differ from other heap-allocated objects in that all of their
 * fields are of the same type (as described by the {@code TypeDescriptor}
 * returned by {@link #getElementDescriptor()}.
 */
public interface ArrayTypeDescriptor extends TypeDescriptor {

  /**
   * Get the type of the elements contained in an instance of this array type.
   *
   * @return a {@code TypeDescriptor} representing the contents of the array.
   *         The content type may be either an {@link ObjectTypeDescriptor} if
   *         the contents are object types, or a {@link TypeDescriptor} if the
   *         contents are primitive. SimpleJava does not allow multidimensional
   *         arrays.
   */
  TypeDescriptor getElementDescriptor();

}
