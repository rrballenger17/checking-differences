package edu.harvard.cscie98.simplejava.impl.interpreter;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import edu.harvard.cscie98.simplejava.stubs.HeapObjectStub;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClass;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.classloader.VmField;
import edu.harvard.cscie98.simplejava.vm.classloader.VmMethod;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectHeader;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectTypeDescriptor;

public class BytecodeTestingUtils {

  public static VmClass mockClass(final TypeName type, final VmClassLoader cl) {
    final VmClass cls = mock(VmClass.class);
    final ObjectTypeDescriptor desc = mock(ObjectTypeDescriptor.class);
    when(cls.getObjectTypeDescriptor()).thenReturn(desc);
    when(desc.getTypeName()).thenReturn(type);
    when(cls.getName()).thenReturn(type);
    when(desc.getSize()).thenReturn(10);
    when(cl.loadClass(type)).thenReturn(cls);
    return cls;
  }

  public static HeapPointer getObjectReference(final VmClass cls) {
    final HeapPointer ptr = mock(HeapPointer.class);
    final ObjectHeader header = mock(ObjectHeader.class);
    final ObjectTypeDescriptor desc = cls.getObjectTypeDescriptor();
    when(header.getWord(ObjectHeader.CLASS_DESCRIPTOR_WORD)).thenReturn(desc);
    final HeapObject obj = new HeapObjectStub(header, ptr);
    when(ptr.dereference()).thenReturn(obj);
    return ptr;
  }

  public static void addFieldToObject(final HeapPointer ptr, final VmClass cls,
      final VmClass definingClass, final int offset, final String name, final TypeName sig) {
    final HeapObject obj = ptr.dereference();
    final ObjectTypeDescriptor desc = (ObjectTypeDescriptor) obj.getHeader().getWord(
        ObjectHeader.CLASS_DESCRIPTOR_WORD);
    final VmField fld = mock(VmField.class);
    when(fld.getDefiningClass()).thenReturn(definingClass);
    when(fld.getName()).thenReturn(name);
    when(desc.getFieldOffset(definingClass, fld)).thenReturn(offset);
    when(definingClass.getField(name, sig)).thenReturn(fld);
    List<VmField> fields = desc.getFields();
    if (fields == null) {
      fields = new ArrayList<VmField>();
    }
    fields.add(fld);
    when(desc.getFields()).thenReturn(fields);
  }

  public static void setSubClass(final VmClass sprClass, final VmClass subClass) {
    when(subClass.getSuperClass()).thenReturn(sprClass);
    final ObjectTypeDescriptor sprDesc = sprClass.getObjectTypeDescriptor();
    when(subClass.getObjectTypeDescriptor().getSuperClassDescriptor()).thenReturn(sprDesc);
  }

  public static void addMethodToClass(final VmClass cls, final String name, final TypeName ret,
      final List<TypeName> params, final int maxLocals, final int maxStack) {
    final VmMethod mthd = mock(VmMethod.class);
    final String desc = generateDesc(params, ret);
    when(mthd.getParamters()).thenReturn(params);
    when(mthd.getMaxLocals()).thenReturn(maxLocals);
    when(mthd.getMaxStack()).thenReturn(maxStack);
    when(mthd.getDefiningClass()).thenReturn(cls);
    when(cls.getMethod(name, desc)).thenReturn(mthd);
  }

  private static String generateDesc(final List<TypeName> params, final TypeName ret) {
    String desc = "(";
    for (final TypeName param : params) {
      desc += param.getDescriptor();
    }
    desc += ")" + ret.getDescriptor();
    return desc;
  }

}