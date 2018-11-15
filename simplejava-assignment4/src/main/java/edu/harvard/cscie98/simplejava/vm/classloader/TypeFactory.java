package edu.harvard.cscie98.simplejava.vm.classloader;

import edu.harvard.cscie98.simplejava.impl.classloader.TypeNameImpl;

public class TypeFactory {

  /**
   * Create a new {@link TypeName} object using the Binary Name defined in the
   * JLS Section 13.1.
   * <P>
   * A binary name takes the form {@code java.lang.Object}.This method should
   * not be used when constructing a {@code TypeName} from a Constant Pool entry
   * (see the Java VM Spec Section 4.2.1).
   * <P>
   * Any {@code TypeName} created by this method is assumed not to be an array.
   * 
   * @param type
   *          A {@code String} representation of the binary name.
   * 
   * @return a {@code TypeName} object constructed from the binary name.
   */
  public static TypeName fromBinaryName(final String type) {
    return TypeNameImpl.fromBinaryName(type, false);
  }

  /**
   * Create a new {@link TypeName} object using the Binary Name defined in the
   * JLS Section 13.1.
   * <P>
   * A binary name takes the form {@code java.lang.Object}.This method should
   * not be used when constructing a {@code TypeName} from a Constant Pool entry
   * (see the Java VM Spec Section 4.2.1).
   * 
   * @param type
   *          A {@code String} representation of the binary name.
   * @param isArray
   *          True if the created {@code TypeName} represents an array, false if
   *          not.
   * 
   * @return a {@code TypeName} object constructed from the binary name.
   */
  public static TypeName fromBinaryName(final String type, final boolean isArray) {
    return TypeNameImpl.fromBinaryName(type, isArray);
  }

  /**
   * Create a new {@link TypeName} object using the {@code class} file form.
   * <P>
   * A {@code class} file name takes the form {@code java/lang/Object}.This
   * method should be used when constructing a {@code TypeName} from a Constant
   * Pool entry (see the Java VM Spec Section 4.2.1).
   * <P>
   * Any {@code TypeName} created by this method is assumed not to be an array.
   * 
   * @param type
   *          A {@code String} representation of the class name.
   * 
   * @return a {@code TypeName} object constructed from the class name.
   */
  public static TypeName fromClassFileName(final String type) {
    return TypeNameImpl.fromClassFileName(type, false);
  }

  /**
   * Create a new {@link TypeName} object using the {@code class} file form.
   * <P>
   * A {@code class} file name takes the form {@code java/lang/Object}.This
   * method should be used when constructing a {@code TypeName} from a Constant
   * Pool entry (see the Java VM Spec Section 4.2.1).
   * 
   * @param type
   *          A {@code String} representation of the class name.
   * @param isArray
   *          True if the created {@code TypeName} represents an array, false if
   *          not.
   * 
   * @return a {@code TypeName} object constructed from the class name.
   */
  public static TypeName fromClassFileName(final String type, final boolean isArray) {
    return TypeNameImpl.fromClassFileName(type, isArray);
  }

  /**
   * Create a new {@link TypeName} object using the Java bytecode descriptor
   * format defined in the Java VM Spec Section 4.3.
   * <P>
   * A descriptor class name takes the form {@code Ljava/lang/Object;}, an array
   * takes the form {@code [Ljava.lang.Object;} and a primitive takes the form
   * {@code I}, {@code J}, {@code Z} etc.
   * <P>
   * The descriptor defines whether the resulting {@code TypeName} represents an
   * array.
   * 
   * @param type
   *          A {@code String} representation of the descriptor.
   * 
   * @return a {@code TypeName} object constructed from the descriptor.
   */
  public static TypeName fromDescriptor(final String type) {
    return TypeNameImpl.fromDescriptor(type);
  }

  /**
   * Create a new {@link TypeName} object using byte values in the
   * {@code newarray} instructure defined in the Java VM Spec Section 6.5.
   * <P>
   * The {@code newarray} instruction defines the type of an array to be created
   * using a single-byte value. the {@code TypeName} generated will represent an
   * array of the primitive type represented by that value.
   * 
   * @param typeCode
   *          A {@code byte} containing the array type.
   * 
   * @return a {@code TypeName} object constructed from the array type.
   */
  public static TypeName fromArrayTypeCode(final byte typeCode) {
    return TypeNameImpl.fromArrayTypeCode(typeCode);
  }

}
