package edu.harvard.cscie98.simplejava.impl.objectmodel;

import java.util.Map;

import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ReferenceLocation;


public class InternTableReferenceLocation implements ReferenceLocation {

  private final String key;
  private final Map<String, HeapObject> internTable;

  public InternTableReferenceLocation(final String key, final Map<String, HeapObject> internTable) {
    this.key = key;
    this.internTable = internTable;
  }

  @Override
  public HeapPointer getValue() {
    return internTable.get(key).getAddress();
  }

  @Override
  public void setValue(final HeapPointer val) {
    internTable.put(key, val.dereference());
  }
}
