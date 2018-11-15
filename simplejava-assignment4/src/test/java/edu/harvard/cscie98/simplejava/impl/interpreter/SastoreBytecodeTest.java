package edu.harvard.cscie98.simplejava.impl.interpreter;

import static org.junit.Assert.assertEquals;

import org.apache.bcel.Constants;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;

public class SastoreBytecodeTest extends BytecodeTest {

  private HeapPointer ref;
  private int val;

  @Override
  @Before
  public void setUp() {
    super.setUp();
    ref = getArrayRef(5);
    val = 42;
    frame.push(ref);
    frame.push(2);
    frame.push(val);
  }

  @Override
  protected int getMaxStack() {
    return 3;
  }

  @Override
  protected int getMaxLocals() {
    return 0;
  }

  @Override
  protected byte[] getBytes() {
    return new byte[] { (byte) Constants.SASTORE };
  }

  @Test
  @Ignore
  public void testStackLayout() {
    interpreter.interpretBytecode(frame);
    assertEquals(0, frame.getStack().size());
  }

  @Test
  @Ignore
  public void testProgramCounter() {
    interpreter.interpretBytecode(frame);
    assertEquals(1, frame.getProgramCounter());
  }

}
