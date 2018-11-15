package edu.harvard.cscie98.simplejava.impl.objectmodel;

import edu.harvard.cscie98.simplejava.config.HeapParameters;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectHeader;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectTypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.objectmodel.TypeDescriptor;

public class HeapObjectImpl implements HeapObject {

  private final ObjectHeaderImpl header;
  private final Object[] fields;
  private final HeapPointer ptr;

  public HeapObjectImpl(final HeapPointer ptr, final TypeDescriptor desc, final int length) {
    this.header = new ObjectHeaderImpl();
    this.header.setWord(ObjectHeader.CLASS_DESCRIPTOR_WORD, desc);
    this.header.setWord(ObjectHeader.ARRAY_LENGTH_WORD, length);
    this.fields = new Object[length];
    this.ptr = ptr;
  }

  public HeapObjectImpl(final HeapPointer ptr, final ObjectTypeDescriptor desc) {
    this.header = new ObjectHeaderImpl();
    this.header.setWord(ObjectHeader.CLASS_DESCRIPTOR_WORD, desc);
    this.fields = new Object[desc.getFields().size()];
    this.ptr = ptr;
  }

  @Override
  public ObjectHeader getHeader() {
    return header;
  }

  @Override
  public Object getValueAtOffset(final int fieldOffset) {
    return fields[fieldOffset];
  }

  @Override
  public void setValueAtOffset(final int fieldOffset, final Object val) {
    fields[fieldOffset] = val;
  }

  @Override
  public HeapPointer getAddress() {
    return ptr;
  }

  @Override
  public long getSize() {
    return (fields.length + ObjectHeader.HEADER_SIZE_WORDS) * HeapParameters.BYTES_IN_WORD;
  }

  @Override
  public String toString() {
    String s = header.toString() + " [";
    for (final Object f : fields) {
      s += f + " ";
    }
    return s + "]";
  }

}
