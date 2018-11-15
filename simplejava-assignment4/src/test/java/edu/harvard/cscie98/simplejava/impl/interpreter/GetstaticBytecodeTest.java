package edu.harvard.cscie98.simplejava.impl.interpreter;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import edu.harvard.cscie98.simplejava.impl.interpreter.BytecodeTest;
import org.junit.*;
import org.apache.bcel.Constants;

public class GetstaticBytecodeTest extends BytecodeTest {

  @Before
  public void setUp() {
    super.setUp();
    
  }

  @Override
  protected int getMaxStack() {
    return -1;
  }

  @Override
  protected int getMaxLocals() {
    return -1;
  }

  @Override
  protected byte[] getBytes() {
    return new byte[] { (byte)Constants.GETSTATIC };
  }

  @Test
  @Ignore
  public void testStackLayout() {
    interpreter.interpretBytecode(frame);
    assertEquals(0, frame.getStack().size());
    
  }

  @Test
  @Ignore
  public void testLocals() {
    interpreter.interpretBytecode(frame);
    
  }

  @Test
  @Ignore
  public void testProgramCounter() {
    interpreter.interpretBytecode(frame);
    assertEquals(1, frame.getProgramCounter());
  }

}
