package edu.harvard.cscie98.simplejava.impl.interpreter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.apache.bcel.Constants;
import org.junit.Before;
import org.junit.Test;

import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;

public class AreturnBytecodeTest extends BytecodeTest {

  private HeapPointer ref;

  @Override
  @Before
  public void setUp() {
    super.setUp();
    ref = mock(HeapPointer.class);
    frame.push(ref);
  }

  @Override
  protected int getMaxStack() {
    return 1;
  }

  @Override
  protected int getMaxLocals() {
    return 0;
  }

  @Override
  protected byte[] getBytes() {
    return new byte[] { (byte) Constants.ARETURN };
  }

  @Test
  public void testStackLayout() {
    interpreter.interpretBytecode(frame);
    assertEquals(0, frame.getStack().size());
    assertEquals(prevFrame, stack.peek());
  }

  @Test
  public void testReturnedValue() {
    interpreter.interpretBytecode(frame);
    assertEquals(ref, prevFrame.pop());
  }

}
