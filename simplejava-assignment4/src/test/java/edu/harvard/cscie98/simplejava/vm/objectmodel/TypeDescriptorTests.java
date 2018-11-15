package edu.harvard.cscie98.simplejava.vm.objectmodel;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.harvard.cscie98.simplejava.impl.objectmodel.ObjectTypeDescriptorImpl;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClass;
import edu.harvard.cscie98.simplejava.vm.classloader.VmField;

public class TypeDescriptorTests {

  @Test
  public void testObjectSize() {
    final VmField cls = mock(VmField.class);
    final List<VmField> fields = new ArrayList<VmField>();
    for (int i = 0; i < 10; i++) {
      fields.add(cls);
    }
    final ObjectTypeDescriptor desc = new ObjectTypeDescriptorImpl(fields,
        TypeFactory.fromDescriptor("Ljava/lang/Object;"), mock(VmClass.class));
    assertEquals(desc.getSize(), 80);
  }
}
