package edu.harvard.cscie98.simplejava.vm.objectmodel;

import java.util.List;

import edu.harvard.cscie98.simplejava.impl.memory.heap.HeapImpl;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.execution.Interpreter;
import edu.harvard.cscie98.simplejava.vm.memory.Heap;
import edu.harvard.cscie98.simplejava.vm.memory.MemoryManager;

/**
 * An implementation of this interface is responsible for creating all
 * heap-allocated data (objects and arrays), and for maintaining the VM's String
 * intern table.
 *
 * The object and array creation methods use the {@link MemoryManager} interface
 * to allocate space for new objects, and handle their initialization. They are
 * also responsible for registering any new objects with the heap (see
 * {@link HeapImpl#addObject(HeapObject)}).
 */
public interface ObjectBuilder {

  /**
   * Allocate and initialize a new array on the heap.
   * <P>
   * This method creates a new array of the given type and size. It stores an
   * {@link ArrayTypeDescriptor} reference and the array length in the object
   * header, and initializes all array entries to the appropriate default value
   * (see {@link TypeName#getDefaultValue} for more details).
   * <P>
   * This method will trigger class loading if the {@code elementType} is not
   * already loaded in the VM.
   *
   * @param elementType
   *          The type of value to be stored in the array. For example, to
   *          create an {@code int[]} object, pass an {@code elementType} of
   *          {@code int}.
   * @param length
   *          The number of elements to be initialized in the array.
   * @return The newly-created {@link HeapObject} that provides storage for the
   *         array
   */
  HeapObject createArray(TypeName elementType, int length);

  /**
   * Allocate and initialize a new object on the heap.
   * <P>
   * This method creates a new non-array object of a given type. It stores a
   * {@link ObjectTypeDescriptor} reference in the header and initializes all
   * fields to the appropriate default value (see
   * {@link TypeName#getDefaultValue} for more details). The contents of the
   * array length header word is undefined.
   * <P>
   * This method will trigger class loading if the {@code elementType} is not
   * already loaded in the VM.
   *
   * @param type
   *          The name of the type for which an object is created.
   * @param callDefaultConstructor
   *          If true, initialize the new object using the class' default
   *          (no-argument) constructor. This operation will fail if the class
   *          does not have a default constructor.
   * @return The newly-created {@link HeapObject} that contains an instance of
   *         the required type.
   */
  HeapObject createObject(TypeName type, boolean callDefaultConstructor);

  /**
   * Store a single version of a given string value.
   * <P>
   * This method creates a single copy of the passed string to speed up equality
   * comparisons.
   *
   * @param str
   *          The string to be interned.
   * @return A {@link HeapObject} containing the interned string. If this method
   *         is called multiple times with a semantically equal String value,
   *         the same {@code HeapObject} will be returned for each call.
   */
  HeapObject internString(String str);

  /**
   * Get the locations of all reference types contained in the String intern
   * table, and all object currently under construction.
   * <P>
   * This method scans the intern table and creates {@link ReferenceLocation}
   * objects for all Strings.
   *
   * @return a {@code List} of all reference locations from the intern table,
   *         and the locations of any objects that have been allocated but not
   *         yet initialized.
   */
  List<ReferenceLocation> getInternTableReferences();

  /**
   * Set the Class Loader component for the VM.
   *
   * @param classLoader
   *          the {@code VmClassLoader} instance.
   */
  void setClassLoader(VmClassLoader classLoader);

  /**
   * Set the heap component where new objects will be allocated.
   *
   * @param heap
   *          the {@code Heap} component.
   */
  void setHeap(Heap heap);

  /**
   * Set the memory manager that will control objects allocated on the heap.
   *
   * @param memoryManager
   *          the {@code MemoryManager} component.
   */
  void setMemoryManager(MemoryManager memoryManager);

  /**
   * Set the interpreter that executes methods in the VM.
   *
   * @param interpreter
   *          the {@code Interpreter} component.
   */
  void setInterpreter(Interpreter interpreter);

}
