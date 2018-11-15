package edu.harvard.cscie98.simplejava.impl.interpreter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;

import org.apache.bcel.Constants;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;

public class CastoreBytecodeTest extends BytecodeTest {

  private HeapObject arr;

  @Override
  @Before
  public void setUp() {
    super.setUp();
    final HeapPointer arrayRef = getArrayRef(10);
    arr = arrayRef.dereference();
    frame.push(arrayRef);
    frame.push(5);
    frame.push(42);
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
    return new byte[] { (byte) Constants.CASTORE };
  }

  @Test
  @Ignore
  public void testStackLayout() {
    interpreter.interpretBytecode(frame);
    assertEquals(0, frame.getStack().size());
  }

  @Test
  @Ignore
  public void checkArrayValue() {
    interpreter.interpretBytecode(frame);
    verify(arr).setValueAtOffset(5, 42);
  }

  @Test
  @Ignore
  public void testProgramCounter() {
    interpreter.interpretBytecode(frame);
    assertEquals(1, frame.getProgramCounter());
  }

}
