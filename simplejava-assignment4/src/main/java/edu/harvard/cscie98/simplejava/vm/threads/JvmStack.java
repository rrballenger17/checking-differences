package edu.harvard.cscie98.simplejava.vm.threads;

import java.util.List;

import edu.harvard.cscie98.simplejava.vm.classloader.VmMethod;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ReferenceLocation;

/**
 * A JVM stack represents the execution stack for a single thread in the VM.
 * Each {@link StackFrame} in the JVM stack corresponds to a method being
 * executed by the thread.
 * <P>
 * The documentation in this interface assumes the convention that stacks grow
 * upward.
 */
public interface JvmStack {

  /**
   * Add a new stack frame to the execution stack.
   * <P>
   * This method creates a new {@link StackFrame} containing the context
   * required to execute a single method's code.
   * 
   * @param method
   *          The {@link VmMethod} instance containing the method to be
   *          executed.
   * 
   * @return The newly-created {@code StackFrame} instance that has been added
   *         to the JVM stack.
   */
  StackFrame push(VmMethod method);

  /**
   * Remove the top frame from the JVM stack.
   * 
   * @return The {@link StackFrame} that has been removed from the stack.
   */
  StackFrame pop();

  /**
   * Fetch the top frame of the JVM stack without removing it.
   * 
   * @return The current {@link StackFrame} at the top of the stack.
   */
  StackFrame peek();

  /**
   * Determine whether the JVM stack contains any {@code StackFrame} objects.
   * 
   * @return True if the stack is empty, false if it is not.
   */
  boolean isEmpty();

  /**
   * Get the locations of all reference types contained in {@code StackFrame}
   * objects on this JVM stack.
   * <P>
   * This method scans all {@code StackFrame} objects on the JVM stack and
   * creates {@link ReferenceLocation} objects for any reference typed
   * variables. The variables pointed to by the {@code ReferenceLocation}
   * objects may be {@link HeapPointer#NULL}.
   * 
   * @return a {@code List} of all reference locations on this JVM stack.
   */
  List<ReferenceLocation> getStackAndLocalReferenceLocations();
}
