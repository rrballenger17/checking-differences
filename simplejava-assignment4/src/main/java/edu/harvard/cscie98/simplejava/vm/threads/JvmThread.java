package edu.harvard.cscie98.simplejava.vm.threads;

/**
 * A thread of execution inside the VM. Since SimpleJava is single-threaded,
 * there will only ever be one {@code JvmThread} in the system.
 */
public interface JvmThread {

  /**
   * Get the execution stack for this thread. Each item in this stack is an
   * instance of the {@link StackFrame} interface containing the execution state
   * of a single SimpleJava method.
   *
   * @return The {@link JvmStack} associated with this thread.
   */
  JvmStack getStack();

}
