package edu.harvard.cscie98.simplejava.impl.interpreter;

import static org.junit.Assert.assertEquals;

import org.apache.bcel.Constants;
import org.junit.Before;
import org.junit.Test;

public class IfneBytecodeTest extends BytecodeTest {

  @Override
  @Before
  public void setUp() {
    super.setUp();
    frame.push(1);
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
    return new byte[] { (byte) Constants.IFNE, 0, 42 };
  }

  @Test
  public void testStackLayout() {
    interpreter.interpretBytecode(frame);
    assertEquals(0, frame.getStack().size());
  }

  @Test
  public void testProgramCounter() {
    interpreter.interpretBytecode(frame);
    assertEquals(42, frame.getProgramCounter());
  }

}
