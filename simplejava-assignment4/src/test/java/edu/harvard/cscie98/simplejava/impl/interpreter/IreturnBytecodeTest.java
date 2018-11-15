package edu.harvard.cscie98.simplejava.impl.interpreter;

import static org.junit.Assert.assertEquals;

import org.apache.bcel.Constants;
import org.junit.Before;
import org.junit.Test;

public class IreturnBytecodeTest extends BytecodeTest {

  private int val;

  @Override
  @Before
  public void setUp() {
    super.setUp();
    val = 42;
    frame.push(val);
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
    return new byte[] { (byte) Constants.IRETURN };
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
    assertEquals(val, prevFrame.pop());
  }

}
