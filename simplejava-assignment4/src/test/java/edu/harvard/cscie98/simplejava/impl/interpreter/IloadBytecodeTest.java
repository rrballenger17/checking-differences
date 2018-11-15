package edu.harvard.cscie98.simplejava.impl.interpreter;

import static org.junit.Assert.assertEquals;

import org.apache.bcel.Constants;
import org.junit.Before;
import org.junit.Test;

public class IloadBytecodeTest extends BytecodeTest {

  @Override
  @Before
  public void setUp() {
    super.setUp();
    frame.setLocalVariable(6, 42);
  }

  @Override
  protected int getMaxStack() {
    return 1;
  }

  @Override
  protected int getMaxLocals() {
    return 7;
  }

  @Override
  protected byte[] getBytes() {
    return new byte[] { (byte) Constants.ILOAD, 6 };
  }

  @Test
  public void testStackLayout() {
    interpreter.interpretBytecode(frame);
    assertEquals(1, frame.getStack().size());
    assertEquals(42, frame.pop());
  }

  @Test
  public void testLocals() {
    interpreter.interpretBytecode(frame);
    assertEquals(42, frame.getLocalVariable(6));
  }

  @Test
  public void testProgramCounter() {
    interpreter.interpretBytecode(frame);
    assertEquals(2, frame.getProgramCounter());
  }
}
