package edu.harvard.cscie98.simplejava.vm.objectmodel;

/**
 * The {@code ReferenceLocation} interface gives memory managers an abstract way
 * to examine and update references on the stack, local variables, static data
 * or intern tables. The interface can be thought of as a C void** variable.
 */
public interface ReferenceLocation {

  /**
   * Get the value of the reference at this location.
   *
   * @return The {@code HeapPointer} located at this location. The value
   *         returned may be {@link HeapPointer#NULL}.
   */
  HeapPointer getValue();

  /**
   * Update the value of the reference at this location.
   *
   * @param val
   *          The new value to set the reference to.
   */
  void setValue(HeapPointer val);

}
