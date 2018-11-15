package edu.harvard.cscie98.simplejava.impl.objectmodel;

import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectHeader;

public class ObjectHeaderImpl implements ObjectHeader {

  private final Object[] words;
  private boolean markBit;

  public ObjectHeaderImpl() {
    words = new Object[HEADER_SIZE_WORDS];
    markBit = false;
  }

  @Override
  public Object getWord(final int idx) {
    return words[idx];
  }

  @Override
  public void setWord(final int idx, final Object val) {
    words[idx] = val;
  }

  @Override
  public void setMarkBit(final boolean bit) {
    markBit = bit;
  }

  @Override
  public boolean getMarkBit() {
    return markBit;
  }

  @Override
  public String toString() {
    return "0: " + words[0] + ", 1: " + words[1] + ", markBit: " + markBit;
  }

}
