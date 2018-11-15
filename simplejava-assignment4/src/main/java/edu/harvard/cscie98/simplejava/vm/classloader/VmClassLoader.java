package edu.harvard.cscie98.simplejava.vm.classloader;

import java.util.List;
import java.util.Set;

import edu.harvard.cscie98.simplejava.vm.execution.Interpreter;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ReferenceLocation;

/**
 * Interface to the class loading mechanism for the VM. Class loading in the
 * SimpleJava language differs from that of regular Java in a few ways:
 * <ul>
 * <li>There is a single class loader for both system and application classes.
 * There is no delegation model as in Java.
 * <li>Class data can only be read from standard class files; SimpleJava does
 * not support loading classes from Jar files or any other location.
 * </ul>
 */
public interface VmClassLoader {

  /**
   * Set the interpreter that will execute code loaded by this class loader
   *
   * @param interpreter
   *          an {@link Interpreter} instance.
   */
  void setInterpreter(Interpreter interpreter);

  /**
   * Read and parse a class file from the VM's class path.
   * <P>
   * This method will invoke the {@link Interpreter} associated with the class
   * loader to run any static initializer ({@code <clinit>}) method declared in
   * the class.
   *
   * @param typeName
   *          the name of the class to be loaded.
   *
   * @return a {@link VmClass} instance that contains the parsed class file.
   *
   * @throws RuntimeException
   *           if an error occurs during parsing.
   */
  VmClass loadClass(TypeName typeName);

  /**
   * Get the locations of all reference types contained in static variables
   * loaded by this class loader.
   * <P>
   * This method scans all {@code VmClass} objects loaded by this class loader
   * and creates {@link ReferenceLocation} objects for any reference typed
   * static variables. The variables pointed to by the {@code ReferenceLocation}
   * objects may be {@link HeapPointer#NULL}.
   *
   * @return a {@code List} of all reference locations found in static
   *         variables.
   */
  List<ReferenceLocation> getStaticReferenceLocations();

  /**
   * Get all classes that have been loaded by this class loader
   *
   * @return a {@code Set} of {@code VmClass} objects containing all classes
   *         loaded by this class loader.
   */
  Set<VmClass> getLoadedClasses();

  /**
   * Configure the ClassPath variable that tells the class loader where to look
   * for bytecode files.
   *
   * @param classPath
   *          the new ClassPath setting.
   */
  void setClassPath(String classPath);

}
