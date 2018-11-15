package edu.harvard.cscie98.simplejava.config;

import edu.harvard.cscie98.simplejava.impl.classloader.DirectoryClassLoader;
import edu.harvard.cscie98.simplejava.impl.interpreter.SwitchInterpreter;
import edu.harvard.cscie98.simplejava.impl.memory.memorymanager.EmptyWriteBarrier;
import edu.harvard.cscie98.simplejava.impl.memory.memorymanager.InfiniteMemoryManager;
import edu.harvard.cscie98.simplejava.impl.objectmodel.ObjectBuilderImpl;
import edu.harvard.cscie98.simplejava.impl.threads.JvmThreadImpl;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.execution.Interpreter;
import edu.harvard.cscie98.simplejava.vm.memory.MemoryManager;
import edu.harvard.cscie98.simplejava.vm.memory.WriteBarrier;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectBuilder;
import edu.harvard.cscie98.simplejava.vm.threads.JvmThread;

public class VmConfiguration {
  // The class loader is responsible for reading .class files from the disk
  // and translating them into an internal representation that the rest of the
  // VM understands.
  private VmClassLoader classLoader;

  // Program execution is performed by a bytecode interpreter. It takes each
  // bytecode in turn and modifies the state of the VM based on its
  // parameters.
  private Interpreter interpreter;

  // The SimpleJava VM, thre is a single thread that runs both the VM and
  // application code. The JvmThread object contains the current state of the
  // program's execution.
  private JvmThread thread;

  // The HeapParameters describe the layout and size of the heap.
  private final HeapParameters heapParams;

  // The ObjectBuilder creates properly-formed objects that live on the
  // SimpleJava heap. It initializes headers, sets up type information and so
  // on, and gives access to the String intern table.
  private ObjectBuilder objectBuilder;

  // The memory manager is responsible for creating and destroying objects on
  // the heap. It consists of an allocator and a garbage collector; there are
  // many possible algorithms for memory management.
  private MemoryManager memoryManager;

  // The write barrier is a piece of code that is executed before the
  // interpreter performs any write to a reference field. This allows the
  // memory manager to take any action that must be performed on reference
  // writes, such as updating internal data structures.
  private WriteBarrier barrier;

  // Command line options that were used to launch the VM.
  private final CommandLineOptions commandLineOptions;

  /**
   * Create a default VM configuration. This will create a minimally-functional
   * VM with placeholder implementations for some components. Override the
   * default components by using the set methods defined in this class.
   *
   * @param cl
   *          the command line options passed to the main method for the VM.
   * @param heapParams
   *          the definition of the heap layout.
   */
  public VmConfiguration(final CommandLineOptions cl, final HeapParameters heapParams) {
    this.commandLineOptions = cl;
    this.classLoader = new DirectoryClassLoader();
    this.interpreter = new SwitchInterpreter();
    this.thread = new JvmThreadImpl();
    this.heapParams = heapParams;
    this.objectBuilder = new ObjectBuilderImpl();
    this.memoryManager = new InfiniteMemoryManager(heapParams);
    this.barrier = new EmptyWriteBarrier();

    updateDependencies();
  }

  private void updateDependencies() {
    classLoader.setClassPath(commandLineOptions.getClassPath());
    classLoader.setInterpreter(interpreter);
    interpreter.setClassLoader(classLoader);
    interpreter.setVmThread(thread);
    interpreter.setObjectBuilder(objectBuilder);
    interpreter.setBarrier(barrier);
    objectBuilder.setClassLoader(classLoader);
    objectBuilder.setHeap(heapParams.getHeap());
    objectBuilder.setInterpreter(interpreter);
    objectBuilder.setMemoryManager(memoryManager);
    memoryManager.setThread(thread);
    memoryManager.setObjectBuilder(objectBuilder);
    memoryManager.setClassLoader(classLoader);
    memoryManager.setBarrier(barrier);
  }

  public VmClassLoader getClassLoader() {
    return classLoader;
  }

  public void setClassLoader(final VmClassLoader classLoader) {
    this.classLoader = classLoader;
    updateDependencies();
  }

  public Interpreter getInterpreter() {
    return interpreter;
  }

  public void setInterpreter(final Interpreter interpreter) {
    this.interpreter = interpreter;
    updateDependencies();
  }

  public JvmThread getThread() {
    return thread;
  }

  public void setThread(final JvmThread thread) {
    this.thread = thread;
    updateDependencies();
  }

  public HeapParameters getHeapParams() {
    return heapParams;
  }

  public ObjectBuilder getObjectBuilder() {
    return objectBuilder;
  }

  public void setObjectBuilder(final ObjectBuilder objectBuilder) {
    this.objectBuilder = objectBuilder;
    updateDependencies();
  }

  public MemoryManager getMemoryManager() {
    return memoryManager;
  }

  public void setMemoryManager(final MemoryManager memoryManager) {
    this.memoryManager = memoryManager;
    updateDependencies();
  }

  public WriteBarrier getBarrier() {
    return barrier;
  }

  public void setBarrier(final WriteBarrier barrier) {
    this.barrier = barrier;
    updateDependencies();
  }

  public void logConfig() {
    Log.config("Class Loader   = " + classLoader);
    Log.config("Interpreter    = " + interpreter);
    Log.config("Thread         = " + thread);
    Log.config("Object Builder = " + objectBuilder);
    Log.config("Memory Manager = " + memoryManager);
    Log.config("Barrier        = " + barrier);
  }
}
