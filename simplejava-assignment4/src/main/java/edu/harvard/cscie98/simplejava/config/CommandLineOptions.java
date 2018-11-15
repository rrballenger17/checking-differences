package edu.harvard.cscie98.simplejava.config;

import java.util.HashSet;
import java.util.Set;

import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;

public class CommandLineOptions {

  static final String MAX_HEAP = "-maxHeap";
  static final String NURSERY_SIZE = "-nurserySize";
  static final String JIT_THRESHOLD = "-jitThreshold";
  static final String NO_INLINE = "-noInline";
  static final String VERBOSE_ALLOC = "-verboseAllocation";
  static final String VERBOSE_GC = "-verboseGC";
  static final String VERBOSE_CL = "-verboseClassLoading";
  static final String VERBOSE_CONFIG = "-verboseConfig";
  static final String VERBOSE_INTERPRET = "-verboseInterpreter";
  static final String VERBOSE_JIT = "-verboseJIT";
  static final String VERBOSE_INLINER = "-verboseInliner";
  static final String VERBOSE_EXCEPTIONS = "-verboseExceptions";
  static final String VERBOSE_STATS = "-verboseStats";

  private String classPath;
  private TypeName mainClass;
  private int maxHeap = 1024;
  private int nurserySize = 16;
  private int jitThreshold = 10;
  private boolean inline = true;
  private Set<Log.Component> verbose = new HashSet<Log.Component>();

  public static void printUsage() {
    String usage = "The SimpleJavaVm requires two arguments:\n";
    usage += "    ClassPath:  A String containing the location of classes to be loaded by the SimpleJavaVM.\n";
    usage += "                Note that this does not include the location of the SimpleJavaVm itself or its dependencies/\n";
    usage += "                On Unix and OSX the paths are separated by the ':' character.\n";
    usage += "                On Windows the paths are separated by the ';' character.\n";
    usage += "    Main Class: The binary name of the main class (eg, edu.harvard.cscie98.sample_code.Empty).\n";
    usage += "The VM can also accept the following optional arguments:\n";
    usage += "    " + NO_INLINE + "Turn off inlining.\n";
    usage += "    " + MAX_HEAP + "<size>: The maximum heap size, in kb.\n";
    usage += "    " + NURSERY_SIZE + "<size>: The fixed size of the nursery, in kb.\n";
    usage += "    " + JIT_THRESHOLD
        + " The number of times a method should be called before it can be JIT compiled.\n";
    usage += "    " + VERBOSE_ALLOC + ": Print verbose allocation details.\n";
    usage += "    " + VERBOSE_GC + ": Print verbose garbage collection details.\n";
    usage += "    " + VERBOSE_CL + ": Print verbose class loading details.\n";
    usage += "    " + VERBOSE_CONFIG + ": Print verbose configuration details.\n";
    usage += "    " + VERBOSE_INTERPRET + ": Print verbose interpretation details.\n";
    usage += "    " + VERBOSE_JIT + ": Print verbose JIT compilation details.\n";
    usage += "    " + VERBOSE_INLINER + ": Print verbose inline algorithm details.\n";
    usage += "    " + VERBOSE_EXCEPTIONS + ": Print verbose exception handling details.\n";
    usage += "    " + VERBOSE_STATS + ": Print execution statistics.\n";
    System.out.println(usage);
  }

  public static CommandLineOptions parseCommandLine(String[] args) {
    CommandLineOptions opts = null;
    try {
      if (args.length < 2) {
        throw new RuntimeException();
      }
      opts = new CommandLineOptions(args[0], args[1]);
      for (int i = 2; i < args.length; i++) {
        if (args[i].startsWith(MAX_HEAP)) {
          final String size = args[i].substring(MAX_HEAP.length());
          opts.maxHeap = Integer.parseInt(size);
        } else if (args[i].startsWith(NURSERY_SIZE)) {
          final String size = args[i].substring(NURSERY_SIZE.length());
          opts.nurserySize = Integer.parseInt(size);
        } else if (args[i].startsWith(JIT_THRESHOLD)) {
          final String threshold = args[i].substring(JIT_THRESHOLD.length());
          opts.jitThreshold = Integer.parseInt(threshold);
        } else {
          switch (args[i]) {
          case NO_INLINE:
            opts.inline = false;
            break;
          case VERBOSE_ALLOC:
            opts.verbose.add(Log.Component.Allocator);
            break;
          case VERBOSE_GC:
            opts.verbose.add(Log.Component.GC);
            break;
          case VERBOSE_CL:
            opts.verbose.add(Log.Component.ClassLoader);
            break;
          case VERBOSE_CONFIG:
            opts.verbose.add(Log.Component.Config);
            break;
          case VERBOSE_INTERPRET:
            opts.verbose.add(Log.Component.Interpreter);
            break;
          case VERBOSE_JIT:
            opts.verbose.add(Log.Component.JIT);
            break;
          case VERBOSE_INLINER:
            opts.verbose.add(Log.Component.Inliner);
            break;
          case VERBOSE_EXCEPTIONS:
            opts.verbose.add(Log.Component.Exceptions);
            break;
          case VERBOSE_STATS:
            opts.verbose.add(Log.Component.Stats);
            break;
          default:
            throw new RuntimeException();
          }
        }
      }
    } catch (final RuntimeException e) {
      printUsage();
      System.exit(1);
    }
    return opts;
  }

  public CommandLineOptions(String classPath, String mainClassName) {
    this.classPath = classPath;
    this.mainClass = TypeFactory.fromBinaryName(mainClassName);
  }

  public String getClassPath() {
    return classPath;
  }

  public TypeName getMainClass() {
    return mainClass;
  }

  public int getMaxHeap() {
    return maxHeap;
  }

  public int getNurserySize() {
    return nurserySize;
  }

  public int getJitThreshold() {
    return jitThreshold;
  }

  public boolean getInline() {
    return inline;
  }

  public Set<Log.Component> getVerbose() {
    return verbose;
  }

  public void setClassPath(String classPath) {
    this.classPath = classPath;
  }

  public void setMainClass(TypeName mainClass) {
    this.mainClass = mainClass;
  }

  public void setMaxHeap(int maxHeap) {
    this.maxHeap = maxHeap;
  }

  public void setNurserySize(int nurserySize) {
    this.nurserySize = nurserySize;
  }

  public void setJitThreshold(int jitThreshold) {
    this.jitThreshold = jitThreshold;
  }

  public void setInline(boolean inline) {
    this.inline = inline;
  }

  public void setVerbose(Set<Log.Component> verbose) {
    this.verbose = verbose;
  }

}
