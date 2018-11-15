package edu.harvard.cscie98.simplejava.vm.objectmodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import edu.harvard.cscie98.simplejava.config.HeapParameters;
import edu.harvard.cscie98.simplejava.config.SimpleJavaOutOfScopeException;
import edu.harvard.cscie98.simplejava.impl.memory.heap.HeapImpl;
import edu.harvard.cscie98.simplejava.impl.objectmodel.HeapObjectImpl;
import edu.harvard.cscie98.simplejava.impl.objectmodel.HeapPointerImpl;
import edu.harvard.cscie98.simplejava.impl.objectmodel.ObjectBuilderImpl;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClass;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.classloader.VmField;
import edu.harvard.cscie98.simplejava.vm.memory.MemoryManager;

public class ObjectBuilderTests {

  private TypeName intType;
  private ArrayTypeDescriptor intArrayDesc;
  private VmClass intClass;

  private TypeName byteType;
  private TypeName byteArrayType;
  private ArrayTypeDescriptor byteArrayDesc;
  private VmClass byteClass;

  private TypeName objType;
  private ArrayTypeDescriptor objArrayDesc;
  private ObjectTypeDescriptor objDesc;
  private VmClass objClass;

  private TypeName strType;
  private ObjectTypeDescriptor strDesc;
  private VmClass strClass;

  private final long ALLOCATED_OBJ_ADDRESS = 0x12345L;
  private HeapObject allocatedObj;
  private HeapPointer allocatedObjPtr;

  private VmClassLoader cl;
  private MemoryManager mm;
  private HeapImpl heap;
  private ObjectBuilderImpl builder;

  @Before
  public void setup() {
    intType = TypeFactory.fromBinaryName("int");
    intClass = mock(VmClass.class);
    intArrayDesc = mock(ArrayTypeDescriptor.class);
    when(intClass.getArrayTypeDescriptor()).thenReturn(intArrayDesc);
    when(intArrayDesc.getTypeName()).thenReturn(intType);

    byteType = TypeFactory.fromBinaryName("byte");
    byteArrayType = TypeFactory.fromBinaryName("byte", true);
    byteClass = mock(VmClass.class);
    byteArrayDesc = mock(ArrayTypeDescriptor.class);
    when(byteClass.getArrayTypeDescriptor()).thenReturn(byteArrayDesc);
    when(byteArrayDesc.getTypeName()).thenReturn(byteType);

    objType = TypeFactory.fromBinaryName("java.lang.Object");
    objClass = mock(VmClass.class);
    objArrayDesc = mock(ArrayTypeDescriptor.class);
    objDesc = mock(ObjectTypeDescriptor.class);
    when(objClass.getArrayTypeDescriptor()).thenReturn(objArrayDesc);
    when(objClass.getObjectTypeDescriptor()).thenReturn(objDesc);
    when(objArrayDesc.getTypeName()).thenReturn(objType);

    strType = TypeFactory.fromBinaryName("java.lang.String");
    strClass = mock(VmClass.class);
    strDesc = mock(ObjectTypeDescriptor.class);
    when(strClass.getObjectTypeDescriptor()).thenReturn(strDesc);
    final List<VmField> flds = setupFields(byteArrayType);
    when(strDesc.getFields()).thenReturn(flds);

    setupClassLoader();

    mm = mock(MemoryManager.class);
    heap = mock(HeapImpl.class);
    allocatedObjPtr = new HeapPointerImpl(ALLOCATED_OBJ_ADDRESS, heap);
    allocatedObj = new HeapObjectImpl(allocatedObjPtr, objDesc);
    when(mm.allocate(anyLong())).thenReturn(allocatedObjPtr);
    when(heap.objectAt(allocatedObjPtr)).thenReturn(allocatedObj);

    builder = new ObjectBuilderImpl();
    builder.setClassLoader(cl);
    builder.setHeap(heap);
    builder.setMemoryManager(mm);
  }

  private void setupClassLoader() {
    cl = mock(VmClassLoader.class);
    when(cl.loadClass(intType)).thenReturn(intClass);
    when(cl.loadClass(byteType)).thenReturn(byteClass);
    when(cl.loadClass(objType)).thenReturn(objClass);
    when(cl.loadClass(strType)).thenReturn(strClass);
  }

  @Test
  public void createArray() {
    final HeapObject arr = builder.createArray(intType, 100);
    assertNotNull(arr);
  }

  @Test
  @Ignore
  public void createZeroSizeArray() {
    final HeapObject arr = builder.createArray(intType, 0);
    assertNotNull(arr);
    assertEquals(0, arr.getHeader().getWord(ObjectHeader.ARRAY_LENGTH_WORD));
  }

  @Test
  public void testArrayAllocationSize() {
    builder.createArray(intType, 100);
    final long size = (HeapParameters.BYTES_IN_WORD
        * (100 + ObjectHeader.HEADER_SIZE_WORDS));
    verify(mm).allocate(size);
  }

  @Test
  public void testZeroElementArrayAllocationSize() {
    builder.createArray(intType, 0);
    final long size = (HeapParameters.BYTES_IN_WORD
        * (ObjectHeader.HEADER_SIZE_WORDS));
    verify(mm).allocate(size);
  }

  @Test
  public void triggerClassLoadingForArray() {
    builder.createArray(intType, 100);
    verify(cl).loadClass(intType);
  }

  @Test
  @Ignore
  public void checkArraySizeHeader() {
    final HeapObject arr = builder.createArray(intType, 100);
    assertEquals(100, arr.getHeader().getWord(ObjectHeader.ARRAY_LENGTH_WORD));
  }

  @Test
  @Ignore
  public void checkArrayDescriptorHeader() {
    final HeapObject arr = builder.createArray(intType, 100);
    assertEquals(intArrayDesc,
        arr.getHeader().getWord(ObjectHeader.CLASS_DESCRIPTOR_WORD));
  }

  @Test
  @Ignore
  public void checkPrimitiveArrayDefaultValues() {
    final HeapObject arr = builder.createArray(intType, 100);
    for (int i = 0; i < 100; i++) {
      assertEquals(0, arr.getValueAtOffset(i));
    }
  }

  @Test
  @Ignore
  public void checkObjectArrayDefaultValues() {
    final HeapObject arr = builder.createArray(objType, 100);
    for (int i = 0; i < 100; i++) {
      assertEquals(HeapPointer.NULL, arr.getValueAtOffset(i));
    }
  }

  @Test(expected = SimpleJavaOutOfScopeException.class)
  public void failWhenCreatingMultiDimArray() {
    builder.createArray(TypeFactory.fromBinaryName("int", true), 100);
  }

  @Test
  @Ignore
  public void verifyNewArrayStoredInHeap() {
    final HeapObject arr = builder.createArray(intType, 100);
    verify(heap).addObject(arr);
  }

  @Test
  public void checkObjectDescriptorHeader() {
    final HeapObject obj = builder.createObject(objType, false);
    assertEquals(objDesc,
        obj.getHeader().getWord(ObjectHeader.CLASS_DESCRIPTOR_WORD));
  }

  @Test
  public void checkObjectDefaultValues() {
    final HeapObject obj = builder.createObject(objType, false);
    assertEquals(objDesc,
        obj.getHeader().getWord(ObjectHeader.CLASS_DESCRIPTOR_WORD));
  }

  @Test
  public void createObject() {
    final HeapObject obj = builder.createObject(objType, false);
    assertNotNull(obj);
  }

  private List<VmField> setupFields(final TypeName... types) {
    final List<VmField> fields = new ArrayList<VmField>();
    for (int i = 0; i < types.length; i++) {
      final VmField fld = mock(VmField.class);
      fields.add(fld);
      if (i % 2 == 0) {
        when(fld.getType()).thenReturn(types[i]);
      } else {
        when(fld.getType()).thenReturn(types[i]);
      }
    }
    return fields;
  }

  private TypeName setupCustomType(final TypeName... types) {
    final List<VmField> flds = setupFields(types);
    final TypeName customType = TypeFactory.fromBinaryName("custom.type.Name");
    final VmClass customClass = mock(VmClass.class);
    final ObjectTypeDescriptor customDesc = mock(ObjectTypeDescriptor.class);
    when(customDesc.getFields()).thenReturn(flds);
    when(customDesc.getSize())
    .thenReturn((int) (types.length * HeapParameters.BYTES_IN_WORD));
    when(customClass.getObjectTypeDescriptor()).thenReturn(customDesc);
    when(cl.loadClass(customType)).thenReturn(customClass);
    return customType;
  }

  @Test
  public void createObjectWithNoFields() {
    final TypeName customType = setupCustomType();
    final HeapObject obj = builder.createObject(customType, false);
    assertNotNull(obj);
  }

  @Test
  public void createObjectWithFields() {
    final TypeName customType = setupCustomType(objType, intType, objType,
        intType);
    final HeapObject obj = builder.createObject(customType, false);
    assertNotNull(obj);
  }

  @Test
  public void checkObjectAllocationSize() {
    final TypeName customType = setupCustomType(objType, intType, objType,
        intType);
    builder.createObject(customType, false);
    final long size = HeapParameters.BYTES_IN_WORD
        * (4 + ObjectHeader.HEADER_SIZE_WORDS);
    verify(mm).allocate(size);
  }

  @Test
  public void checkNoFieldObjectAllocationSize() {
    final TypeName customType = setupCustomType();
    builder.createObject(customType, false);
    final long size = (HeapParameters.BYTES_IN_WORD
        * (ObjectHeader.HEADER_SIZE_WORDS));
    verify(mm).allocate(size);
  }

  @Test
  public void triggerClassLoadingForObject() {
    builder.createObject(objType, false);
    verify(cl).loadClass(objType);
  }

  @Test
  @Ignore
  public void verifyNewObjectStoredInHeap() {
    final HeapObject obj = builder.createObject(objType, false);
    verify(heap).addObject(obj);
  }

  @Test
  @Ignore
  public void internString() {
    final HeapObject interned = builder.internString("STRING 1");
    assertNotNull(interned);
  }

  @Test
  @Ignore
  public void checkEqualityForRepeatedStringIntern() {
    final HeapObject interned1 = builder.internString("STRING 1");
    final HeapObject interned2 = builder.internString("STRING 1");
    assertEquals(interned1, interned2);
  }

  @Test
  @Ignore
  public void checkNonEqualityForDifferentInternedStrings() {
    final HeapObject interned1 = builder.internString("STRING 1");
    final HeapObject interned2 = builder.internString("STRING 2");
    assertFalse(interned1.equals(interned2));
  }

  @Test
  @Ignore
  public final void testInternTableReferenceLocations() {

  }
}
