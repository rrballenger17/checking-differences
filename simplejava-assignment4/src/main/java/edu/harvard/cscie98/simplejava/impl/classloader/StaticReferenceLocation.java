package edu.harvard.cscie98.simplejava.impl.classloader;

import edu.harvard.cscie98.simplejava.vm.classloader.VmClass;
import edu.harvard.cscie98.simplejava.vm.classloader.VmField;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ReferenceLocation;

public class StaticReferenceLocation implements ReferenceLocation {

  private final VmField fld;
  private final VmClass cls;

  public StaticReferenceLocation(final VmClass cls, final VmField fld) {
    this.fld = fld;
    this.cls = cls;
  }

  @Override
  public HeapPointer getValue() {
    return (HeapPointer) cls.getStaticField(fld.getName(), fld.getType());
  }

  @Override
  public void setValue(final HeapPointer val) {
    cls.setStaticField(fld.getName(), fld.getType(), val);
  }

}
