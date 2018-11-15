package edu.harvard.cscie98.simplejava.vm.classloader;

import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantFieldref;
import org.apache.bcel.classfile.ConstantMethodref;
import org.apache.bcel.classfile.ConstantNameAndType;

public interface VmConstantPool {

  /**
   * Get a method reference from the constant pool.
   * 
   * @param idx
   *          the index of the method entry in the constant pool.
   * 
   * @return a {@link ConstantMethodref} object containing the constant pool
   *         method reference. See the BCEL documentation for details.
   */
  ConstantMethodref getMethodEntry(int idx);

  /**
   * Get a UTF-8 reference from the constant pool.
   * 
   * @param idx
   *          the index of the UTF-8 entry in the constant pool.
   * 
   * @return a {@code String} containing the data from the UTF-8 entry.
   */
  String getUtf8Entry(int idx);

  /**
   * Get an integer from the constant pool.
   * 
   * @param idx
   *          the index of the integer entry in the constant pool.
   * 
   * @return the {@code Integer} value returned from the constant pool.
   */
  Integer getIntegerEntry(int idx);

  /**
   * Get a name and type tuple from the constant pool.
   * 
   * @param idx
   *          the index of the name and type entry in the constant pool.
   * @return a {@link ConstantNameAndType} object containing the name and type
   *         data from the constant pool. See the BCEL documentation for
   *         details.
   */
  ConstantNameAndType getNameAndTypeEntry(int idx);

  /**
   * Get a class name from the constant pool.
   * 
   * @param idx
   *          the index of the class entry in the constant pool.
   * @return a {@link TypeName} representing the class in the constant pool.
   */
  TypeName getClassEntry(int idx);

  /**
   * Get a field reference from the constant pool.
   * 
   * @param idx
   *          the index of the field entry in the constant pool.
   * @return a {@link ConstantFieldref} object containing the constant pool
   *         field reference. See the BCEL documentation for details.
   */
  ConstantFieldref getFieldEntry(int idx);

  /**
   * Get a constant value from the constant pool.
   * 
   * @param idx
   *          the index of the constant entry in the constant pool.
   * @return a {@link ConstantFieldref} object containing the constant pool
   *         entry. See the BCEL documentation for details.
   */
  Constant getConstantEntry(int idx);

}
