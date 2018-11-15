package edu.harvard.cscie98.simplejava;

import edu.harvard.cscie98.simplejava.config.CommandLineOptions;
import edu.harvard.cscie98.simplejava.config.HeapParameters;
import edu.harvard.cscie98.simplejava.config.Log;
import edu.harvard.cscie98.simplejava.config.VmConfiguration;
import edu.harvard.cscie98.simplejava.impl.memory.memorymanager.GenerationalMemoryManager;
import edu.harvard.cscie98.simplejava.impl.memory.memorymanager.GenerationalWriteBarrier;
import edu.harvard.cscie98.simplejava.impl.memory.memorymanager.InfiniteMemoryManager;
import edu.harvard.cscie98.simplejava.impl.memory.memorymanager.SemiSpaceMemoryManager;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClass;
import edu.harvard.cscie98.simplejava.vm.classloader.VmMethod;
import edu.harvard.cscie98.simplejava.vm.execution.ExecutionException;
import edu.harvard.cscie98.simplejava.vm.execution.UncaughtException;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectBuilder;

/**
 * Main entry point to the VM. See {@link CommandLineOptions} for a description
 * of the options that can be passed to the main method.
 *
 * To run from the command line using maven, execute:
 *
 * mvn -q exec:java \
 * -Dexec.mainClass="edu.harvard.cscie98.simplejava.SimpleJavaVm" \ -Dexec.args=
 * "[class path] [main class] [options]"
 */
public class SimpleJavaVm {

  private static final int ASSIGNMENT = 4;

  // Standard name and signature for the entry method of a SimpleJava program.
  // This is the first method that the VM will execute.
  private static final String MAIN_METHOD_NAME = "main";
  private static final String MAIN_METHOD_SIGNATURE = "([Ljava/lang/String;)V";

  // The set of components that will be used for the VM. The implementations of
  // these components will change over the course of the class, as new concepts
  // are introduced.
  private final VmConfiguration config;

  /**
   * Run the SimpleJava VM. The main method uses the following algorithm:
   * <ol>
   * <li>Parse the command line to get the classpath, main class and any
   * options.
   * <li>Create the VM using the set of components appropriate for the
   * assignment.
   * <li>Run the main method of the specified SimpleJava application.
   * <li>Check for any exceptions thrown by the main method.
   * <li>Print VM profiling statistics.
   * </ol>
   *
   * @param args
   *          the command line arguments passed to the VM.
   */
  public static void main(final String[] args) {
    final CommandLineOptions opts = CommandLineOptions.parseCommandLine(args);
    for (final Log.Component component : opts.getVerbose()) {
      Log.setVerbose(component);
    }
    final VmConfiguration configuration = configForAssignment(opts, ASSIGNMENT);
    configuration.logConfig();

    final SimpleJavaVm vm = new SimpleJavaVm(configuration);

    final TypeName mainClass = opts.getMainClass();
    final UncaughtException exception = vm.runMainClass(args, mainClass);

    if (exception != null) {
      vm.printUncaughtException(exception);
    }
    vm.printStats();
  }

  static VmConfiguration configForAssignment(final CommandLineOptions opts, final int assignment) {
    final HeapParameters heapParams = new HeapParameters(0x100000,
        opts.getMaxHeap() * HeapParameters.BYTES_IN_KB,
        opts.getNurserySize() * HeapParameters.BYTES_IN_KB);
    final VmConfiguration config = new VmConfiguration(opts, heapParams);
    switch (assignment) {
    case 0:
      break;
    case 1:
      break;
    case 2:
      config.setMemoryManager(new SemiSpaceMemoryManager(heapParams));
      break;
    case 3:
      config.setMemoryManager(new InfiniteMemoryManager(heapParams));
      break;
    case 4:
      config.setBarrier(new GenerationalWriteBarrier(heapParams));
      config.setMemoryManager(new GenerationalMemoryManager(heapParams));
      break;
    }
    return config;
  }

  public SimpleJavaVm(final VmConfiguration config) {
    this.config = config;
  }

  UncaughtException runMainClass(final String[] args, final TypeName mainClass) {
    final VmClass cls = config.getClassLoader().loadClass(mainClass);
    final VmMethod mainMethod = cls.getMethod(MAIN_METHOD_NAME, MAIN_METHOD_SIGNATURE);
    if (mainMethod == null) {
      throw new ExecutionException("No main method in class " + mainClass);
    }

    final HeapPointer argArray = buildCommandLineArgArray(args);
    try {
      config.getInterpreter().executeMethod(mainMethod, argArray);
    } catch (final UncaughtException e) {
      Log.exception("Uncaught Exception");
      return e;
    }
    return null;
  }

  HeapPointer buildCommandLineArgArray(final String[] args) {
    final ObjectBuilder objectBuilder = config.getObjectBuilder();
    final TypeName stringType = TypeFactory.fromBinaryName("java.lang.String");
    final HeapObject array = objectBuilder.createArray(stringType, args.length);
    for (int i = 0; i < args.length; i++) {
      final HeapObject internString = objectBuilder.internString(args[i]);
      array.setValueAtOffset(i, internString.getAddress());
    }
    return array.getAddress();
  }

  private void printUncaughtException(final UncaughtException exception) {
    final VmClass cls = config.getClassLoader()
        .loadClass(TypeFactory.fromBinaryName("java.lang.Throwable"));
    final VmMethod mthd = cls.getMethod("printStackTrace", "()V");
    config.getInterpreter().executeMethod(mthd, exception.getException());
  }

  private void printStats() {
    config.getInterpreter().printStats();
  }

}
