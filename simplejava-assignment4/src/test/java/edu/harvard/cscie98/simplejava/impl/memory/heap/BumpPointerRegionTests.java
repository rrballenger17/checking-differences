package edu.harvard.cscie98.simplejava.impl.memory.heap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import edu.harvard.cscie98.simplejava.impl.objectmodel.HeapPointerImpl;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;

public class BumpPointerRegionTests {

  private HeapPointer base;
  private long extent;
  private HeapImpl heap;

  @Before
  public void setUp() {
    heap = mock(HeapImpl.class);
    base = new HeapPointerImpl(0x1000L, heap);
    extent = 1024;
  }

  @Test
  public void testBasicAllocation() {
    final BumpPointerRegion region = new BumpPointerRegion(base, extent);
    final HeapPointer ptr = region.allocate(10);
    assertNotNull(ptr);
    assertEquals(base, ptr);
  }

  @Test
  public void testConsecutiveAllocations() {
    final BumpPointerRegion region = new BumpPointerRegion(base, extent);
    final HeapPointer ptr1 = region.allocate(10);
    final HeapPointer ptr2 = region.allocate(10);
    assertNotNull(ptr1);
    assertNotNull(ptr2);
    assertEquals(base, ptr1);
    assertEquals(base.add(10), ptr2);
  }

  @Test(expected = RuntimeException.class)
  public void testZeroSizeAllocation() {
    final BumpPointerRegion region = new BumpPointerRegion(base, extent);
    region.allocate(0);
  }

  @Test
  public void allocateExactFill() {
    final BumpPointerRegion region = new BumpPointerRegion(base, extent);
    final HeapPointer ptr = region.allocate(extent);
    assertNotNull(ptr);
    assertEquals(base, ptr);
  }

  @Test
  public void allocateExactFillPlusOne() {
    final BumpPointerRegion region = new BumpPointerRegion(base, extent);
    final HeapPointer ptr = region.allocate(extent + 1);
    assertNotNull(ptr);
    assertEquals(HeapPointer.NULL, ptr);
  }

  @Test
  public void allocateVeryLargeObject() {
    final BumpPointerRegion region = new BumpPointerRegion(base, extent);
    final HeapPointer ptr = region.allocate(Long.MAX_VALUE);
    assertNotNull(ptr);
    assertEquals(HeapPointer.NULL, ptr);
  }

  @Test
  public void resetAndAllocate() {
    final BumpPointerRegion region = new BumpPointerRegion(base, extent);
    region.allocate(100);
    region.reset();
    final HeapPointer ptr = region.allocate(10);
    assertNotNull(ptr);
    assertEquals(base, ptr);
  }

  @Test
  public void resetBeforeAllocation() {
    final BumpPointerRegion region = new BumpPointerRegion(base, extent);
    region.reset();
    final HeapPointer ptr = region.allocate(10);
    assertNotNull(ptr);
    assertEquals(base, ptr);
  }

  @Test
  @Ignore
  public void testPointerInRegion() {

  }
}
