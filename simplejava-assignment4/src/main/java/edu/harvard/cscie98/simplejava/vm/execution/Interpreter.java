package edu.harvard.cscie98.simplejava.vm.execution;

import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.classloader.VmMethod;
import edu.harvard.cscie98.simplejava.vm.memory.WriteBarrier;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectBuilder;
import edu.harvard.cscie98.simplejava.vm.threads.JvmThread;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public interface Interpreter {

  /**
   * Execute the given method using the current thread.
   * <P>
   * This method will execute the instructions of the given method, including
   * any additional methods called by those instructions. It will return either
   * once the outermost method (ie, the method passed in) returns or if an
   * uncaught exception escapes the method's scope.
   *
   * @param mthd
   *          The method to execute.
   * @param argArray
   *          An ordered list of argument values to be passed to the method.
   * @return The {@link StackFrame} representing the executed method, containing
   *         the state that it held on exiting the method.
   */
  StackFrame executeMethod(VmMethod mthd, HeapPointer argArray);

  /**
   * Get the thread associated with this execution engine.
   * <P>
   * Note that in the current implementation there is always exactly one
   * {@code JvmThread}.
   *
   * @return The {@code JvmThread} managed by this {@code ExecutionEngine}.
   */
  JvmThread getThread();

  /**
   * Print the count of each instruction interpreted during the program's run.
   *
   * The VM must be run using the -verboseStats option for this method to output
   * anything.
   */
  void printStats();

  /**
   * Configure the class loader to be used by this interpreter.
   *
   * @param classLoader
   *          the new class loader for the interpreter to access.
   */
  void setClassLoader(VmClassLoader classLoader);

  /**
   * Set the single JVM thread that this interpreter instance will use to
   * execute code.
   *
   * @param thread
   *          the {@code JvmThread} instance that the interpreter will use.
   */
  void setVmThread(JvmThread thread);

  /**
   * Set the ObjectBuilder component that this interpreter will use to create
   * new heap-allocated objects and arrays.
   *
   * @param objectBuilder
   *          the new {@code ObjectBuilder}
   */
  void setObjectBuilder(ObjectBuilder objectBuilder);

  /**
   * Set the write barrier that is required by the memory management algorithm.
   * This barrier will be called when the appropriate bytecodes are executed.
   *
   * @param barrier
   *          the {@code WriteBarrier} instance.
   */
  void setBarrier(WriteBarrier barrier);

}
