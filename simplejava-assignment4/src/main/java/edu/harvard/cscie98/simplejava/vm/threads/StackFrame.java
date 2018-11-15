package edu.harvard.cscie98.simplejava.vm.threads;

import edu.harvard.cscie98.simplejava.vm.classloader.VmConstantPool;
import edu.harvard.cscie98.simplejava.vm.classloader.VmMethod;
import edu.harvard.cscie98.simplejava.vm.execution.ExecutionException;

/**
 * A Stack Frame contains the information required to execute a single method.
 * The internal stack in the {@code StackFrame} type is a separate data
 * structure from the {@link JvmStack} that contains the full execution context
 * for the thread.
 * <P>
 * The documentation in this interface assumes the convention that stacks grow
 * upward.
 */
public interface StackFrame {

  /**
   * Add a value to the top of the frame's internal stack.
   * 
   * @param value
   *          the value to be added to the stack
   * 
   * @throws ExecutionException
   *           if the stack height exceeds the stated maximum stack size for
   *           this method.
   */
  public void push(Object value);

  /**
   * Remove the top element from the frame's internal stack.
   * 
   * @return The element that was removed from the stack.
   */
  public Object pop();

  /**
   * Set a value in the stack frame's local variable area.
   * 
   * @param variableIndex
   *          The index of the variable to set.
   * @param value
   *          The new value of the variable.
   */
  public void setLocalVariable(int variableIndex, Object value);

  /**
   * Fetch a value from the stack frame's local variable area.
   * 
   * @param variableIndex
   *          The index of the variable to retrieve.
   * 
   * @return The current value of the variable.
   */
  public Object getLocalVariable(int variableIndex);

  /**
   * Get the constant pool associated with the method represented by this frame.
   * 
   * @return The {@link VmConstantPool} object associated with the stack frame.
   */
  public VmConstantPool getConstantPool();

  /**
   * Get the current program counter for the method being executed.
   * <P>
   * The program counter is an offset into the bytecode stream. It is
   * incremented as the last operation when executing an instruction.
   * 
   * @return The current program counter.
   */
  public int getProgramCounter();

  /**
   * Set the program counter for the method being executed.
   * <P>
   * The program counter is an offset into the bytecode stream. It is
   * incremented as the last operation when executing an instruction.
   * 
   * @param pc
   *          The new value for the program counter.
   */
  public void setProgramCounter(int pc);

  /**
   * Get the bytecode that make up the instructions for the method being
   * executed.
   * 
   * @return An array of {@code byte} values that contains the bytecode stream
   *         from the method associated with this stack frame.
   */
  byte[] getInstructionStream();

  /**
   * Get the {@link VmMethod} object associated with this stack frame.
   * 
   * @return a {@code VmMethod} object containing the class file representation
   *         of the method.
   */
  VmMethod getVmMethod();

}
