package edu.harvard.cscie98.simplejava.impl.objectmodel;

import java.util.Map;

import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ReferenceLocation;

public class MapReferenceLocation implements ReferenceLocation {

  private final HeapPointer key;
  private final Map<HeapPointer, HeapPointer> map;

  public MapReferenceLocation(final HeapPointer key, final Map<HeapPointer, HeapPointer> map) {
    this.key = key;
    this.map = map;
  }

  @Override
  public HeapPointer getValue() {
    return map.get(key);
  }

  @Override
  public void setValue(final HeapPointer val) {
    map.put(key, val);
  }
}
