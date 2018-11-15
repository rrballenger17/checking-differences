package edu.harvard.cscie98.simplejava.stubs;

import java.util.HashMap;
import java.util.Map;

import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectHeader;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectTypeDescriptor;

public class HeapObjectStub implements HeapObject {

  private final ObjectHeader header;
  private final Map<Integer, Object> values;
  private final HeapPointer ptr;
  private final long size;

  public HeapObjectStub(final ObjectHeader header, final HeapPointer ptr) {
    this.header = header;
    this.ptr = ptr;
    this.size = ((ObjectTypeDescriptor) header.getWord(ObjectHeader.CLASS_DESCRIPTOR_WORD))
        .getSize();
    values = new HashMap<Integer, Object>();
  }

  @Override
  public ObjectHeader getHeader() {
    return header;
  }

  @Override
  public Object getValueAtOffset(final int offset) {
    return values.get(offset);
  }

  @Override
  public void setValueAtOffset(final int offset, final Object val) {
    values.put(offset, val);
  }

  @Override
  public HeapPointer getAddress() {
    return ptr;
  }

  @Override
  public long getSize() {
    return size;
  }

}
