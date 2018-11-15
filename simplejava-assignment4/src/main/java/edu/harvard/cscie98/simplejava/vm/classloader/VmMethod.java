package edu.harvard.cscie98.simplejava.vm.classloader;

import java.util.List;
import java.util.Map;

/**
 * This interface represents a single method loaded into the VM. Implementations
 * of this interface must define equality by comparing the values of the
 * methods's name, signature and defining class. Note that it is not necessary
 * for a two method's bytecode to be identical for an equality comparison to
 * succeed.
 */
public interface VmMethod {

  /**
   * Get the class that defined this method.
   * 
   * @return the {@link VmClass} that defined the method.
   */
  VmClass getDefiningClass();

  /**
   * Get the bytecode for this method
   * 
   * @return A byte array containing the raw bytecode for the method.
   */
  byte[] getInstructionStream();

  /**
   * Get a string representation of the instruction stream, for debug purposes.
   * 
   * @return a {@code String} representing the method's instructions.
   */
  String getInstructionsAsString();

  /**
   * Get the signature for the method as defined in the JVM Spec, Section 4.3.3.
   * 
   * @return a {@code String} representation of the method's signature.
   */
  String getSignature();

  /**
   * Get the method's name
   * 
   * @return a {@code String} containing the name of the method.
   */
  String getName();

  /**
   * Get the maximum size of the operand stack required in this method.
   * <P>
   * Note that this is the maximum stack for the method only, not the execution
   * stack of the program as a whole. See Section 2.6.2 of the JVM Spec for more
   * details on the operand stack.
   * 
   * @return the maximum operand stack size for this method.
   */
  int getMaxStack();

  /**
   * Get the number of local variable slots required by this method.
   * 
   * @return the count of local variable slots used by the method.
   */
  int getMaxLocals();

  /**
   * Get the constant pool for this method.
   * 
   * @return a {@link VmConstantPool} object containing the constants used by
   *         this method.
   */
  VmConstantPool getConstantPool();

  /**
   * Get a parsed list of parameter types.
   * 
   * @return An ordered {@code List} of {@link TypeName} objects containing the
   *         parameter types for this method.
   */
  List<TypeName> getParamters();

  /**
   * Determine whether this method is final.
   * 
   * @return true if the method is final, false if it is not.
   */
  boolean isFinal();

  /**
   * Determine whether this method is static.
   * 
   * @return true if the method is static, false if it is not.
   */
  boolean isStatic();

  /**
   * Get the exception handler table for this method.
   * 
   * Returns all exception handlers for this method. See the description of
   * {@link VmExceptionHandler} for details of the handler object.
   * 
   * @return a {@code List} of {@code VmExceptionHandler} objects. The order of
   *         handlers in the returned list is the same as the order in the class
   *         file.
   */
  List<VmExceptionHandler> getExceptionHandlers();

  /**
   * Get the bytecode index at which each line in the original source code
   * begins.
   * 
   * @return A {@code Map} where the keys are bytecode indices and the values
   *         are line numbers.
   */
  Map<Integer, Integer> getLineNumberTable();

}
