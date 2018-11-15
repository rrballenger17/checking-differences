package edu.harvard.cscie98.simplejava.impl.interpreter;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.bcel.Constants;
import org.junit.Before;
import org.junit.Test;

import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;

public class AnewarrayBytecodeTest extends BytecodeTest {

  private TypeName objType;
  private int arrayLength;
  private HeapPointer arrayRef;
  private HeapObject arrayObject;

  @Override
  @Before
  public void setUp() {
    super.setUp();
    objType = TypeFactory.fromBinaryName("some.class.Name");
    arrayLength = 42;
    arrayObject = mock(HeapObject.class);
    arrayRef = getArrayRef(arrayLength);
    when(arrayObject.getAddress()).thenReturn(arrayRef);
    when(objectBuilder.createArray(objType, arrayLength)).thenReturn(arrayObject);
    frame.push(arrayLength);
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
    return new byte[] { (byte) Constants.ANEWARRAY, 00, 01 };
  }

  @Test
  public void testStackLayout() {
    interpreter.interpretBytecode(frame);
    assertEquals(1, frame.getStack().size());
    assertEquals(arrayRef, frame.pop());
  }

  @Test
  public void testProgramCounter() {
    interpreter.interpretBytecode(frame);
    assertEquals(3, frame.getProgramCounter());
  }

}
