package edu.harvard.cscie98.simplejava.vm.objectmodel;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;

public class TypeNameTests {

  @Test
  public void createBinaryClassType() {
    final TypeName t = TypeFactory.fromBinaryName("java.lang.Object");
    verifyObject(t);
  }

  @Test
  public void createClassFileType() {
    final TypeName t = TypeFactory.fromClassFileName("java/lang/Object");
    verifyObject(t);
  }

  @Test
  public void createDescriptorType() {
    final TypeName t = TypeFactory.fromDescriptor("Ljava/lang/Object;");
    verifyObject(t);
  }

  @Test
  public void createBinaryArrayType() {
    final TypeName t = TypeFactory.fromBinaryName("java.lang.Object", true);
    verifyObjectArray(t);
  }

  @Test
  public void createClassArrayType() {
    final TypeName t = TypeFactory.fromClassFileName("java/lang/Object", true);
    verifyObjectArray(t);
  }

  @Test
  public void createDescriptorArrayType() {
    final TypeName t = TypeFactory.fromDescriptor("[Ljava/lang/Object;");
    verifyObjectArray(t);
  }

  @Test
  public void createBinaryPrimitiveType() {
    final TypeName t = TypeFactory.fromBinaryName("int");
    verifyInt(t);
  }

  @Test
  public void createPrimitveClassFileType() {
    final TypeName t = TypeFactory.fromClassFileName("int");
    verifyInt(t);
  }

  @Test
  public void createPrimitiveDescriptorType() {
    final TypeName t = TypeFactory.fromDescriptor("I");
    verifyInt(t);
  }

  @Test
  public void createPrimitiveArrayCodeType() {
    final TypeName t = TypeFactory.fromArrayTypeCode((byte) 10);
    verifyInt(t);
  }

  @Test
  public void createPrimitiveBinaryArrayType() {
    final TypeName t = TypeFactory.fromBinaryName("int[]");
    verifyIntArray(t);
  }

  @Test
  public void createPrimitiveBinaryArrayTypeParam() {
    final TypeName t = TypeFactory.fromBinaryName("int", true);
    verifyIntArray(t);
  }

  @Test
  public void createPrimitiveBinaryDescriptorType() {
    final TypeName t = TypeFactory.fromDescriptor("[I");
    verifyIntArray(t);
  }

  private void verifyObject(final TypeName t) {
    assertEquals("Object", t.getSimpleName());
    assertEquals("java.lang.Object", t.getBinaryName());
    assertEquals("java/lang/Object", t.getClassFileName());
    final File baseDir = new File("/tmp/test");
    assertEquals(new File("/tmp/test/java/lang/Object.class"), t.getClassFile(baseDir));
  }

  private void verifyObjectArray(final TypeName t) {
    assertEquals("Object[]", t.getSimpleName());
    assertEquals("java.lang.Object[]", t.getBinaryName());
  }

  private void verifyInt(final TypeName t) {
    assertEquals("int", t.getSimpleName());
    assertEquals("int", t.getBinaryName());
    assertEquals("int", t.getClassFileName());
  }

  private void verifyIntArray(final TypeName t) {
    assertEquals("int[]", t.getSimpleName());
    assertEquals("int[]", t.getBinaryName());
  }

  @Test
  public void testObjectDefaultValue() {
    final TypeName t = TypeFactory.fromBinaryName("java.lang.Object");
    assertEquals(HeapPointer.NULL, t.getDefaultValue());
  }

  @Test
  public void testArrayDefaultValue() {
    final TypeName t = TypeFactory.fromBinaryName("java.lang.Object", true);
    assertEquals(HeapPointer.NULL, t.getDefaultValue());
  }

  @Test
  public void testIntDefaultValue() {
    final TypeName t = TypeFactory.fromBinaryName("int");
    assertEquals(0, t.getDefaultValue());
  }

}
