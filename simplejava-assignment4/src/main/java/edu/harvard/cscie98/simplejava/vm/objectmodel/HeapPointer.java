package edu.harvard.cscie98.simplejava.vm.objectmodel;

import edu.harvard.cscie98.simplejava.impl.objectmodel.HeapPointerImpl;
import edu.harvard.cscie98.simplejava.vm.memory.Heap;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

/**
 * The HeapPointer class represents a location in the heap. Since Java does not
 * provide direct memory access, the heap in the SimpleJava VM is simulated by
 * an implementation of the {@link Heap} interface. A {@code HeapPointer} allows
 * VM developers to access object in this simulated heap; it can be though of as
 * the SimpleJava equivalent to a {@code void*} in C.
 *
 * The {@code HeapPointer} interface is one of two types that can exist on the
 * execution stack in a {@link StackFrame}.
 */
public interface HeapPointer extends Comparable<HeapPointer> {

  /**
   * A singleton representing the null pointer. This implementation assumes that
   * the heap address range never starts at zero.
   */
  public static final HeapPointer NULL = new HeapPointerImpl(0L, null);

  /**
   * Create a new heap pointer at a fixed offset from this pointer. This
   * operation allows the SimpleJava version of pointer arithmetic.
   *
   * @param extent
   *          The number of bytes to offset from this pointer.
   *
   * @return a new {@code HeapPointer} obtained by adding the {@code extent} to
   *         the address of this pointer.
   */
  HeapPointer add(long extent);

  /**
   * Get the {@link HeapObject} referred to by this {@code HeapPointer}.
   * Attempting to dereference {@link #NULL} will result in a Java-level
   * {@code NullPointerException}.
   *
   * @return The {@code HeapObject} that this {@code HeapPointer} refers to.
   *
   * @throws RuntimeException
   *           if this HeapPointer does not point to a valid {@code HeapObject}
   */
  HeapObject dereference();

  /**
   * Get a {@code long} representation of this pointer. This value can be used
   * to initialize a new heap pointer to refer to the same location in memory.
   * For pointer arithmetic, see {@link #add(long)}.
   *
   * @return a {@code} long that contains the memory location pointed to by this
   *         {@link HeapPointer} object.
   */
  long toLong();

}
