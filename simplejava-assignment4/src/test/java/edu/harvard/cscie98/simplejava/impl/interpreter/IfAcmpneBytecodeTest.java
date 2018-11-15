package edu.harvard.cscie98.simplejava.impl.interpreter;

import static org.junit.Assert.assertEquals;

import org.apache.bcel.Constants;
import org.junit.Before;
import org.junit.Test;

import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;

public class IfAcmpneBytecodeTest extends BytecodeTest {

  @Override
  @Before
  public void setUp() {
    super.setUp();
    final HeapPointer ptr1 = getRef(getObjectDescriptor(TypeFactory
        .fromBinaryName("java.lang.Object")));
    final HeapPointer ptr2 = getRef(getObjectDescriptor(TypeFactory
        .fromBinaryName("java.lang.Object")));
    frame.push(ptr1);
    frame.push(ptr2);
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
    return new byte[] { (byte) Constants.IF_ACMPNE, 0, 42 };
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
