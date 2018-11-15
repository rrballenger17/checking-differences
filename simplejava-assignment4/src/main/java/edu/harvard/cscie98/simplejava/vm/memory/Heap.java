package edu.harvard.cscie98.simplejava.vm.memory;

import edu.harvard.cscie98.simplejava.config.HeapParameters;
import edu.harvard.cscie98.simplejava.impl.memory.heap.BumpPointerRegion;
import edu.harvard.cscie98.simplejava.impl.memory.heap.NonContiguousRegion;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;

/**
 * A representation of heap memory in SimpleJava. Since Java does not allow
 * direct memory access, the {@code Heap} simulates a large contiguous block of
 * memory. The heap is a contiguous area of memory, with its base address and
 * extent determined by the {@link HeapParameters} object configured during VM
 * startup.
 *
 * Memory in the heap is split into {@link Region} instances which can be
 * managed independently. SimpleJava supports two different types of region:
 * <ul>
 * <li>A {@link BumpPointerRegion} allocates memory by placing each new object
 * immediately after the previously-allocated object.
 * <li>A {@link NonContiguousRegion} places objects based on a first-fit
 * algorithm, allowing memory fragmentation between objects.
 * </ul>
 * All allocation happens in the context of a region, not in the heap itself.
 * See {@link Region#allocate(long)}.
 */
public interface Heap {

  /**
   * Create a new bump pointer region in the heap.
   *
   * A region is a contiguous area of memory set aside on the heap to be managed
   * independently. This method reserves a region of memory to be allocated
   * using a bump pointer allocator.
   *
   * The region of memory defined by the baseAddress and extent parameters must
   * not overlap with any other regions.
   *
   * @param baseAddress
   *          A {@code HeapPointer} that refers to the address at which this
   *          memory region will start.
   * @param extent
   *          The number of bytes to be assigned to the region.
   *
   * @return A new {@link BumpPointerRegion} that is configured to allocate
   *         memory to the address range specified by the parameters.
   */
  BumpPointerRegion getBumpPointerRegion(HeapPointer baseAddress, long extent);

  /**
   * Create a new non-contiguous region in the heap.
   *
   * A region is a contiguous area of memory set aside on the heap to be managed
   * independently. This method reserves a region of memory to be allocated
   * using a first-fit allocation strategy.
   *
   * The region of memory defined by the baseAddress and extent parameters must
   * not overlap with any other regions.
   *
   * @param baseAddress
   *          A {@code HeapPointer} that refers to the address at which this
   *          memory region will start.
   * @param extent
   *          The number of bytes to be assigned to the region.
   *
   * @return A new {@link NonContiguousRegion} that is configured to allocate
   *         memory to the address range specified by the parameters.
   */
  NonContiguousRegion getNonContiguousRegion(HeapPointer baseAddress, long extent);

  /**
   * Copy an object from one location in the heap to another.
   *
   * This method performs a shallow copy of the object; it copies all values and
   * pointers found in the header and fields of the objects, but does not copy
   * the objects to which any pointers refer.
   *
   * @param from
   *          A {@link HeapPointer} that indicates the object to be copied. The
   *          pointer must refer to the start of an object.
   * @param to
   *          A location in memory to which the object will be copied.
   */
  void memcpy(HeapPointer from, HeapPointer to);

}
