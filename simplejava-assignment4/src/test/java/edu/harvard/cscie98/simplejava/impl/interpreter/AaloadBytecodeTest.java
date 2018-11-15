package edu.harvard.cscie98.simplejava.impl.interpreter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.apache.bcel.Constants;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;

public class AaloadBytecodeTest extends BytecodeTest {

  private HeapPointer ref;
  private Object obj;

  @Override
  @Before
  public void setUp() {
    super.setUp();
    ref = getArrayRef(5);
    obj = new Object();
    frame.push(ref);
    frame.push(2);
    when(ref.dereference().getValueAtOffset(2)).thenReturn(obj);
  }

  @Override
  protected int getMaxStack() {
    return 2;
  }

  @Override
  protected int getMaxLocals() {
    return 0;
  }

  @Override
  protected byte[] getBytes() {
    return new byte[] { (byte) Constants.AALOAD };
  }

  @Test
  @Ignore
  public void testStackLayout() {
    interpreter.interpretBytecode(frame);
    assertEquals(1, frame.getStack().size());
    assertEquals(obj, frame.pop());
  }

  @Test
  @Ignore
  public void testProgramCounter() {
    interpreter.interpretBytecode(frame);
    assertEquals(1, frame.getProgramCounter());
  }

}
