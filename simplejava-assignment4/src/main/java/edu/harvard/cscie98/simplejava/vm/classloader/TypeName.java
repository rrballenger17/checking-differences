package edu.harvard.cscie98.simplejava.vm.classloader;

import java.io.File;

public interface TypeName extends Comparable<TypeName> {

  /**
   * Resolve this {@code TypeName} to a class file within a given directory.
   * <P>
   * This method creates a {@code File} object, but does not verify whether the
   * resulting file actually exists. It assumes that any class files will use
   * the suffix '{@code .class}' in lower case.
   * 
   * @param directory
   *          a {@code File} representing the directory in which the class file
   *          is located.
   * 
   * @return a {@code File} object containing the absolute path of the class
   *         file.
   */
  File getClassFile(final File directory);

  /**
   * Get the name of this type without the qualifying package.
   * <P>
   * For example, for class {@code java.lang.Object}, this method would return '
   * {@code Object}'.
   * 
   * @return the simple version of the type name.
   */
  String getSimpleName();

  /**
   * Get the binary name as defined in the JLS Section 13.1.
   * <P>
   * Note that this value is not equal to the class name found in a constant
   * pool (see the Java VM Spec Section 4.2.1). For class
   * {@code java.lang.Object}, this method would return '
   * {@code java.lang.Object}'.
   * 
   * @return the binary name for this type.
   */
  String getBinaryName();

  /**
   * Get the descriptor for this type
   * <P>
   * For example, for class {@code java.lang.Object}, this method would return '
   * {@code Ljava/lang/Object;}'.
   * 
   * @return the descriptor for this type.
   */
  String getDescriptor();

  /**
   * Get the type name in class file format as defined in the Java VM Spec
   * Section 4.2.1.
   * <P>
   * This method returns the type name in the format found in a constant pool.
   * For class {@code java.lang.Object}, this method would return '
   * {@code java/lang/Object}'.
   * 
   * @return the class file name for this type.
   */
  String getClassFileName();

  /**
   * Get the value that a field of this type would contain in a newly-allocated
   * object.
   * <P>
   * For primitive types this value is zero (or {@code false} for boolean
   * fields. For objects or arrays, this is {@code HeapPointer.NULL}.
   * 
   * @return the default value associated with this type.
   */
  Object getDefaultValue();

  /**
   * Determine whether this {@code TypeName} represents an array.
   * 
   * @return true if the type is an array, false if it is not.
   */
  boolean isArray();

}
