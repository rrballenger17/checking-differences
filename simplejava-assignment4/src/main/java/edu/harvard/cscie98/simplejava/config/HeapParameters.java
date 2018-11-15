package edu.harvard.cscie98.simplejava.config;

import edu.harvard.cscie98.simplejava.impl.memory.heap.HeapImpl;
import edu.harvard.cscie98.simplejava.impl.objectmodel.HeapPointerImpl;
import edu.harvard.cscie98.simplejava.vm.memory.Heap;
import edu.harvard.cscie98.simplejava.vm.memory.Region;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;

/**
 * Configuration object that describes the size and layout of the SimpleJava
 * heap. This object is initialized during the VM's startup phase, and is
 * guaranteed to be populated before the memory manager is created.
 */
public class HeapParameters {

  public static final long BYTES_IN_KB = 1024;
  public static final long BYTES_IN_MB = 1024 * 1024;
  public static final long BYTES_IN_GB = 1024 * 1024 * 1024;
  public static final long BYTES_IN_WORD = 8;

  private final long extent;
  private final Heap heap;
  private final long base;
  private final long nurserySize;

  public HeapParameters(final long base, final long extent, final long nurserySize) {
    this.base = base;
    this.extent = extent;
    this.nurserySize = nurserySize;
    this.heap = new HeapImpl();
  }

  public HeapParameters(final long base, final long extent, final long nurserySize,
      final Heap heap) {
    this.base = base;
    this.extent = extent;
    this.nurserySize = nurserySize;
    this.heap = heap;
  }

  /**
   * Get the total size of the heap in bytes. This represents the maximum amount
   * of space that can be split among the regions on the heap.
   *
   * @see Heap
   * @see Region
   *
   * @return the number of bytes available to the heap.
   */
  public long getExtent() {
    return extent;
  }

  /**
   * Get the size of the nursery region in a two-region generational garbage
   * collector. This method is guaranteed to return a smaller value than
   * {@link #getExtent()}; the size of the mature space can be determined as the
   * difference between the values returned by these two methods
   *
   * @return the size of the nursery space, in bytes.
   */
  public long getNurserySize() {
    return nurserySize;
  }

  /**
   * Get the starting address for the heap. The heap is defined as a contiguous
   * area of memory starting at this address, with the number of bytes returned
   * by {@link #getExtent()}.
   *
   * @return a {@link HeapPointer} that indicates the start of the memory space
   *         reserved for the heap.
   */
  public HeapPointer getBaseAddress() {
    return new HeapPointerImpl(base, (HeapImpl) heap);
  }

  @Override
  public String toString() {
    return "Base: " + getBaseAddress() + " extent: 0x" + Long.toHexString(extent) + " end: "
        + getBaseAddress().add(extent);
  }

  /**
   * Get the singleton {@link Heap} instance for the VM. Since the VM always has
   * exactly one {@code Heap} object, this method is guaranteed to return the
   * same instance each time it is called, so it is safe for clients to cache
   * the result.
   *
   * @return a {@code Heap} instance from which memory can be allocated.
   */
  public Heap getHeap() {
    return heap;
  }
}
