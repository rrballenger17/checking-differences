package edu.harvard.cscie98.simplejava.impl.memory.memorymanager;

import edu.harvard.cscie98.simplejava.config.HeapParameters;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.memory.MemoryManager;
import edu.harvard.cscie98.simplejava.vm.memory.Region;
import edu.harvard.cscie98.simplejava.vm.memory.WriteBarrier;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectBuilder;
import edu.harvard.cscie98.simplejava.vm.threads.JvmThread;

public class InfiniteMemoryManager implements MemoryManager {

  private final Region region;

  public InfiniteMemoryManager(final HeapParameters heapParams) {
    region = heapParams.getHeap().getBumpPointerRegion(heapParams.getBaseAddress(),
        heapParams.getExtent());
  }

  @Override
  public HeapPointer allocate(final long bytes) {
    final HeapPointer allocated = region.allocate(bytes);
    return allocated;
  }

  @Override
  public void garbageCollect() {
    // The Infinite Memory Manager never garbage collects
  }

  @Override
  public void setThread(final JvmThread thread) {
  }

  @Override
  public void setObjectBuilder(final ObjectBuilder objectBuilder) {
  }

  @Override
  public void setClassLoader(final VmClassLoader classLoader) {
  }

  @Override
  public void setBarrier(final WriteBarrier barrier) {
  }

}
