package edu.harvard.cscie98.simplejava.vm.classloader;

/**
 * This interface represents a single exception handler block in the context of
 * a method.
 * 
 * The start PC is inclusive and the end PC is exclusive; that is, the exception
 * handler must be active while the program counter is within the interval
 * [start_pc, end_pc).
 */
public interface VmExceptionHandler {

  /**
   * Get the bytecode index at which this exception handler becomes active.
   * 
   * @return The start of the range in which the exception handler is active.
   */
  int getStartPc();

  /**
   * Get the bytecode index at which this exception handler is no longer active.
   * 
   * @return The end of the range in which the exception handler is active.
   */
  int getEndPc();

  /**
   * Get the type of {@code Throwable} object that this handler block will
   * catch.
   * 
   * @return A {@code String} containing the binary name of the the type or
   *         supertype of throwable objects that this block handles, or the
   *         constant String "any" for a handler that catches all exceptions.
   */
  String getCatchType();

  /**
   * Get the bytecode index to which control will pass when this handler is
   * triggered.
   * 
   * @return An index into the bytecode of the current method that indicates
   *         where to transfer control when handling this exception.
   */
  int getHandlerPc();

}
