package edu.harvard.cscie98.simplejava.vm.objectmodel;

/**
 * A {@code HeapObject} represents any heap-allocated piece of data (either an
 * Object or an Array). It allows access to the object's header (see
 * {@link ObjectHeader}), and read or write access to the fields of an object or
 * array.
 */
public interface HeapObject {

  /**
   * Get the header for this heap-allocated object.
   *
   * @return The {@link ObjectHeader} associated with this object.
   */
  ObjectHeader getHeader();

  /**
   * Get the field or array entry located at a particular offset.
   *
   * @param offset
   *          The field or array entry to fetch.
   *
   * @return The object at that offset. If the entry is a primitive type, its
   *         boxed version will be returned.
   */
  Object getValueAtOffset(int offset);

  /**
   * Set the field or array entry located at a particular offset.
   * <P>
   * This method does not verify that value has the correct type.
   *
   * @param offset
   *          The field or array entry to set.
   *
   * @param val
   *          The value to set at the offset.
   */
  void setValueAtOffset(int offset, Object val);

  /**
   * Get the address of this object in the heap.
   *
   * @return a {@link HeapPointer} containing the address of this object.
   */
  HeapPointer getAddress();

  /**
   * Get the size of this object or array, including the header.
   *
   * @return The size in bytes of this object.
   */
  long getSize();

}
