package edu.harvard.cscie98.simplejava.impl.interpreter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;

import edu.harvard.cscie98.simplejava.impl.memory.heap.HeapImpl;
import edu.harvard.cscie98.simplejava.impl.threads.StackFrameImpl;
import edu.harvard.cscie98.simplejava.stubs.ConstantPoolStub;
import edu.harvard.cscie98.simplejava.stubs.JvmStackStub;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClass;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.classloader.VmField;
import edu.harvard.cscie98.simplejava.vm.memory.MemoryManager;
import edu.harvard.cscie98.simplejava.vm.memory.WriteBarrier;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ArrayTypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectBuilder;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectHeader;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectTypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.objectmodel.TypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.threads.JvmThread;

public abstract class BytecodeTest {

  protected SwitchInterpreter interpreter;
  protected StackFrameImpl frame;
  protected StackFrameImpl prevFrame;
  protected ConstantPoolStub constantPool;
  protected JvmThread thread;
  protected JvmStackStub stack;
  protected ObjectBuilder objectBuilder;
  protected VmClassLoader classLoader;
  protected MemoryManager memoryManager;
  protected HeapImpl heap;
  protected WriteBarrier barrier;

  @Before
  public void setUp() {
    constantPool = new ConstantPoolStub();
    classLoader = mock(VmClassLoader.class);
    memoryManager = mock(MemoryManager.class);
    heap = mock(HeapImpl.class);
    objectBuilder = mock(ObjectBuilder.class);
    stack = new JvmStackStub();
    thread = mock(JvmThread.class);
    barrier = mock(WriteBarrier.class);
    when(thread.getStack()).thenReturn(stack);
    frame = new StackFrameImpl(getMaxStack(), getMaxLocals(), constantPool, getBytes(), null);
    frame.setProgramCounter(0);
    prevFrame = new StackFrameImpl(getMaxStack(), getMaxLocals(), constantPool, getBytes(), null);
    prevFrame.setProgramCounter(0);
    stack.push(prevFrame);
    stack.push(frame);
    interpreter = new SwitchInterpreter();
    interpreter.setBarrier(barrier);
    interpreter.setClassLoader(classLoader);
    interpreter.setObjectBuilder(objectBuilder);
    interpreter.setVmThread(thread);
  }

  protected abstract int getMaxStack();

  protected abstract int getMaxLocals();

  protected abstract byte[] getBytes();

  protected ObjectTypeDescriptor getObjectDescriptor(final TypeName type) {
    final ObjectTypeDescriptor desc = mock(ObjectTypeDescriptor.class);
    when(desc.getTypeName()).thenReturn(type);
    return desc;
  }

  protected HeapPointer getRef(final TypeDescriptor desc) {
    final HeapObject obj = mock(HeapObject.class);
    final HeapPointer ref = mock(HeapPointer.class);
    final ObjectHeader header = mock(ObjectHeader.class);
    when(ref.dereference()).thenReturn(obj);
    when(obj.getAddress()).thenReturn(ref);
    when(obj.getHeader()).thenReturn(header);
    when(header.getWord(ObjectHeader.CLASS_DESCRIPTOR_WORD)).thenReturn(desc);
    return ref;
  }

  protected HeapPointer getArrayRef(final int length) {
    final ArrayTypeDescriptor desc = mock(ArrayTypeDescriptor.class);
    final HeapPointer ref = getRef(desc);
    final ObjectHeader header = ref.dereference().getHeader();
    when(header.getWord(ObjectHeader.ARRAY_LENGTH_WORD)).thenReturn(length);
    return ref;
  }

  // Get a pointer to an object that includes a field that can be resolved
  // through the constant pool.
  public HeapPointer getRefWithFieldInCp(final int fieldOffset) {
    final TypeName fldType = constantPool.getFieldType();
    final ObjectTypeDescriptor desc = getObjectDescriptor(fldType);
    final VmField fld = mock(VmField.class);
    final VmClass cls = mock(VmClass.class);
    when(cls.getObjectTypeDescriptor()).thenReturn(desc);
    when(cls.getField(constantPool.getFieldName(), fldType)).thenReturn(fld);
    when(desc.getFieldOffset(cls, fld)).thenReturn(fieldOffset);
    final HeapPointer ref = getRef(desc);
    when(classLoader.loadClass(fldType)).thenReturn(cls);
    return ref;
  }

}
