package edu.harvard.cscie98.simplejava.impl.memory.heap;

import edu.harvard.cscie98.simplejava.config.Log;
import edu.harvard.cscie98.simplejava.vm.VmInternalError;
import edu.harvard.cscie98.simplejava.vm.memory.Region;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;

public class BumpPointerRegion implements Region {
  private final HeapPointer baseAddress;
  private final HeapPointer limit;
  private final long extent;
  private HeapPointer pointer;

  /**
   * Create a new region.
   * 
   * @param baseAddress
   *          The first address managed by this region.
   * @param extent
   *          The number of bytes in the region.
   */
  BumpPointerRegion(final HeapPointer baseAddress, final long extent) {
    this.baseAddress = baseAddress;
    this.limit = baseAddress.add(extent);
    this.extent = extent;
    Log.gc("Region: " + this);
    reset();
  }

  @Override
  public HeapPointer allocate(final long bytes) {
    if (bytes <= 0) {
      throw new VmInternalError("Allocation must be for > 0 bytes.");
    }
    if (pointer.add(bytes).compareTo(limit) <= 0) {
      final HeapPointer allocation = pointer;
      pointer = pointer.add(bytes);
      return allocation;
    }
    return HeapPointer.NULL;
  }

  @Override
  public void free(final HeapObject obj) throws VmInternalError {
    throw new VmInternalError("Can't free from a bump pointer region");
  }

  @Override
  public void reset() {
    pointer = baseAddress;
  }

  @Override
  public boolean pointerInRegion(final HeapPointer ptr) {
    if (ptr == HeapPointer.NULL) {
      return false;
    }
    if (ptr.compareTo(baseAddress) < 0) {
      return false;
    }
    if (ptr.compareTo(limit) >= 0) {
      return false;
    }
    return true;
  }

  /**
   * Get a pointer to the first address in this region.
   * 
   * @return A {@link HeapPointer} that refers to the region base.
   */
  public HeapPointer getBase() {
    return baseAddress;
  }

  /**
   * Get the current allocation pointer.
   * 
   * @return A {@link HeapPointer} that refers to the memory location where the
   *         next object will be allocated.
   */
  public HeapPointer getAllocationPointer() {
    return pointer;
  }

  public long getExtent() {
    return extent;
  }

  @Override
  public String toString() {
    return "Start: " + baseAddress + ", extent: 0x" + Long.toHexString(extent) + ", limit: "
        + limit + " pointer: " + pointer;
  }

}
