package edu.harvard.cscie98.simplejava.impl.interpreter;

import static org.junit.Assert.assertEquals;

import org.apache.bcel.Constants;
import org.junit.Before;
import org.junit.Test;


public class AloadBytecodeTest extends BytecodeTest {

  private Object obj;

  @Override
  @Before
  public void setUp() {
    super.setUp();
    obj = new Object();
    frame.setLocalVariable(4, obj);
  }

  @Override
  protected int getMaxStack() {
    return 1;
  }

  @Override
  protected int getMaxLocals() {
    return 5;
  }

  @Override
  protected byte[] getBytes() {
    return new byte[] { (byte) Constants.ALOAD, 4 };
  }

  @Test
  public void testStackLayout() {
    interpreter.interpretBytecode(frame);
    assertEquals(1, frame.getStack().size());
    assertEquals(obj, frame.pop());
  }

  @Test
  public void testProgramCounter() {
    interpreter.interpretBytecode(frame);
    assertEquals(2, frame.getProgramCounter());
  }

}
