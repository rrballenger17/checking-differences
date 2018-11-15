package edu.harvard.cscie98.simplejava.impl.interpreter;

import static org.junit.Assert.assertEquals;

import org.apache.bcel.Constants;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class DupX2BytecodeTest extends BytecodeTest {

  private Object v1;
  private Object v2;
  private Object v3;

  @Override
  @Before
  public void setUp() {
    super.setUp();
    v1 = new Object();
    v2 = new Object();
    v3 = new Object();
    frame.push(v3);
    frame.push(v2);
    frame.push(v1);
  }

  @Override
  protected int getMaxStack() {
    return 4;
  }

  @Override
  protected int getMaxLocals() {
    return 0;
  }

  @Override
  protected byte[] getBytes() {
    return new byte[] { (byte) Constants.DUP_X2 };
  }

  @Test
  @Ignore
  public void testStackLayout() {
    interpreter.interpretBytecode(frame);
    assertEquals(4, frame.getStack().size());
    assertEquals(v1, frame.pop());
    assertEquals(v2, frame.pop());
    assertEquals(v3, frame.pop());
    assertEquals(v1, frame.pop());
  }

  @Test
  @Ignore
  public void testProgramCounter() {
    interpreter.interpretBytecode(frame);
    assertEquals(1, frame.getProgramCounter());
  }

}
