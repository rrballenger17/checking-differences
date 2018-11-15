package edu.harvard.cscie98.simplejava.vm.memory;

import edu.harvard.cscie98.simplejava.vm.VmInternalError;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;

public interface Region {

  /**
   * Reset this region, allowing subsequent allocation requests to overwrite
   * previously allocated space.
   * <P>
   * Note that this method does not remove any objects that may currently exist
   * in the region.
   *
   * @throws VmInternalError
   *           if the region does not support a reset operation.
   */
  void reset();

  /**
   * Assign a contiguous block of memory in this region. If there is
   * insufficient space to allocate the requested amount of memory, this method
   * will return {@link HeapPointer#NULL}. Note the difference between this
   * value and the Java-level {@code null} value.
   *
   * @param bytes
   *          The number of bytes to be allocated. The space actually allocated
   *          may be more than the value passed here, but will not be less.
   * @return A {@link HeapPointer} to the newly-allocated space, or
   *         {@link HeapPointer#NULL} if the object could not be allocated.
   * @throws RuntimeException
   *           If the allocation size is not greater than zero.
   */
  HeapPointer allocate(long bytes);

  /**
   * Return an object to the pool of available space.
   *
   * @param obj
   *          The object to free.
   * @throws VmInternalError
   *           if the region does not support an explicit free operation.
   */
  void free(HeapObject obj) throws VmInternalError;

  /**
   * Test whether a heap pointer falls inside this region.
   *
   * @param ptr
   *          the {@link HeapPointer} to test.
   *
   * @return true if the pointer refers to a memory address inside this region,
   *         false if it refers to an address outside the region.
   */
  boolean pointerInRegion(HeapPointer ptr);

}
