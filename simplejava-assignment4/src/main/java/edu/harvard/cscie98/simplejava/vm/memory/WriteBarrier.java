package edu.harvard.cscie98.simplejava.vm.memory;

import java.util.Set;

import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;

/**
 * Interface for the code and related data structures that is executed before
 * any update to a reference on the heap. This interface is designed for a
 * generational-style write barrier, where a remembered set of updated objects
 * must be maintained to track references between regions.
 */
public interface WriteBarrier {
  /**
   * Register a method update on a heap object.
   *
   * This method is called on before reference write to an object on the heap.
   * The target of the write may either be a field in a heap object or an entry
   * in an array. If a heap location is written to multiple times, this method
   * will be called once for every write.
   *
   * @param obj
   *          The object or array to which a new value has been written. This
   *          pointer must refer to a valid object on the heap.
   * @param ptr
   *          The new value written to the heap object or array. This value may
   *          be {@link HeapPointer#NULL}.
   */
  void onPointerWrite(HeapObject obj, HeapPointer ptr);

  /**
   * Get the set of objects that may point from the mature space to the nursery.
   *
   * This set contains all objects that have had a reference from the mature
   * space to the nursery written since the previous call of
   * {@link WriteBarrier#clearRememberedSet()}, or since the start of the
   * program execution if {@code clearRememberedSet} has never been called.
   *
   * @return A {@code Set} of {@link HeapObject} instances that contains any
   *         object that may contain a reference from the mature space to the .
   *         nursery.
   */
  Set<HeapObject> getRememberedSet();

  /**
   * Remove all objects from the remembered set.
   *
   * This method is called at the end of a garbage collection cycle in order to
   * reset the set of objects that may point to the nursery from the mature
   * space.
   */
  void clearRememberedSet();

}
