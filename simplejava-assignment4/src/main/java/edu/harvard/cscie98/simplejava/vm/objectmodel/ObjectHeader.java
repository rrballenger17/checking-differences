package edu.harvard.cscie98.simplejava.vm.objectmodel;

public interface ObjectHeader {

  /**
   * The number of words required by each heap-allocated object's header.
   */
  public static final int HEADER_SIZE_WORDS = 2;

  /**
   * The offset of the word containing an object's {@code TypeDescriptor}
   * reference
   */
  public static final int CLASS_DESCRIPTOR_WORD = 0;

  /**
   * The offset of the word containing an array's length. For an object that is
   * not an array, the value of the word at this offset is undefined.
   */
  public static final int ARRAY_LENGTH_WORD = 1;

  /**
   * Get a word from this object's header.
   * 
   * @param idx
   *          The offset into the header from which to retrieve the word.
   * @return The content of the requested word.
   */
  Object getWord(int idx);

  /**
   * Set the value of a word in the header.
   * 
   * @param idx
   *          The offset into the header at which to set the word.
   * @param value
   *          The new value of the word at the given offset.
   */
  void setWord(int idx, Object value);

  void setMarkBit(boolean bit);

  boolean getMarkBit();

}
