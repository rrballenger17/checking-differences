package edu.harvard.cscie98.simplejava.impl.memory.memorymanager;

import edu.harvard.cscie98.simplejava.config.HeapParameters;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.memory.MemoryManager;
import edu.harvard.cscie98.simplejava.vm.memory.WriteBarrier;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectBuilder;
import edu.harvard.cscie98.simplejava.vm.threads.JvmThread;

public class MarkSweepMemoryManager implements MemoryManager {

  public MarkSweepMemoryManager(final HeapParameters heapParams) {
    throw new RuntimeException("TODO: Your implementation for Assignment 4 goes here");
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

  @Override
  public HeapPointer allocate(final long bytes) {
    return null;
  }

  @Override
  public void garbageCollect() {
  }

}
