package edu.harvard.cscie98.simplejava.vm.memory;

import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectBuilder;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ReferenceLocation;
import edu.harvard.cscie98.simplejava.vm.threads.JvmThread;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

/**
 * Instances of this interface implement the various memory management
 * strategies inside the VM. A memory manager consists of an allocator and
 * garbage collector; the two mechanisms are closely related and cannot be
 * separated.
 * <P>
 * The memory manager is a reasonably stand-alone component of the VM. The
 * {@link #allocate} method allows other VM components to reserve a chunk of
 * uninitialized memory. If there is insufficient memory available to honor the
 * memory request, the {@code allocate} method will trigger a garbage collection
 * and then retry the allocation.
 * <P>
 * Regardless of the memory management algorithm, the roots used to determine
 * reachability are the same:
 * <ul>
 * <li>References in the local variables and execution stack in a thread's
 * {@link StackFrame}s;
 * <li>References in static variables, managed by the {@link VmClassLoader};
 * <li>References to {@code String} objects stored in the intern table, managed
 * by the VM's {@link ObjectBuilder}.
 * </ul>
 */
public interface MemoryManager {

  /**
   * Assign a block of memory from the Java heap.
   * <P>
   * This method allocates memory according to the system's memory management
   * strategy. It does not initialize the contents of the memory, or assign any
   * object header information.
   * <P>
   * If there is insufficient memory in the region to which the memory manager
   * allocates, this method will trigger a garbage collection. After reclaiming
   * memory it will attempt to perform the allocation again. If, after
   * potentially multiple GC cycles, a block of the appropriate size cannot be
   * found, this method will return {@link HeapPointer#NULL}.
   *
   * @param size
   *          The number of bytes to allocate. This must be greater than zero.
   * @return A chunk of memory with a size greater than or equal to the number
   *         of bytes requested, or {@link HeapPointer#NULL} if insufficient
   *         memory is available. The contents of the memory is undefined; it is
   *         the responsibility of the caller to initialize the object with
   *         default values.
   */
  HeapPointer allocate(long size);

  /**
   * Manually trigger a garbage collection. The memory manager will immediately
   * perform a garbage collection cycle (without first attempting to allocate
   * any data).
   * <P>
   * If the current memory manager supports multiple forms of garbage collection
   * (as in the case of a generational collector), this method guarantees that
   * at least some minimal amount of garbage collection work is performed.
   * Depending on the memory management implementation, and the current state of
   * the heap, this method may trigger a full-heap garbage collection.
   * <P>
   * This method is primarily intended for testing; it is not expected that
   * other components in the VM will have to manually trigger a GC.
   */
  void garbageCollect();

  /**
   * Configure the memory manager with the single {@link JvmThread} running in
   * the SimpleJava VM. The VM guarantees that all stack and local reference
   * locations are reachable via this object.
   *
   * @see ReferenceLocation
   *
   * @param thread
   *          the {@code JvmThread} that maintains the VM's execution stack.
   */
  void setThread(JvmThread thread);

  /**
   * Configure the memory manager with the {@link ObjectBuilder} instance used
   * to construct all objects in the VM. The VM guarantees that the locations of
   * all references to interned objects, and all locations of objects under
   * construction, are reachable via this object.
   *
   * @see ReferenceLocation
   *
   * @param objectBuilder
   *          the {@code ObjectBuilder} instance that creates all objects and
   *          arrays in the VM.
   */
  void setObjectBuilder(ObjectBuilder objectBuilder);

  /**
   * Configure the memory manager with the VM's class loader instance. The VM
   * guarantees that the locations of all references to heap objects that are
   * stored in static variables are reachable through this object.
   *
   * @see ReferenceLocation
   *
   * @param classLoader
   *          the {@link VmClassLoader} instance that loads all classes in the
   *          VM.
   */
  void setClassLoader(VmClassLoader classLoader);

  /**
   * Configure the memory manager with a reference to the {@link WriteBarrier}
   * installed in the VM. The VM guarantees that this barrier will be called for
   * every object or array reference update on the heap. The barrier is not
   * called for stack, local variable or static reference updates.
   *
   * @param barrier
   *          the {@code WriteBarrier} instance that is called on all heap
   *          pointer writes.
   */
  void setBarrier(WriteBarrier barrier);
}
