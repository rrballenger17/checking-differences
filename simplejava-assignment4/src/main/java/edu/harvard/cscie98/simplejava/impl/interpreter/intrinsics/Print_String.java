package edu.harvard.cscie98.simplejava.impl.interpreter.intrinsics;

import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectHeader;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class Print_String implements Intrinsic {

  @Override
  public Object execute(final StackFrame frame) {
    final String str = getStringFromHeapPointer(
        (HeapPointer) frame.getLocalVariable(1));
    System.out.print(str);
    return null;
  }

  static String getStringFromHeapPointer(final HeapPointer str) {
    if (str != HeapPointer.NULL) {
      final HeapObject strObj = str.dereference();
      final HeapObject strBuff = ((HeapPointer) strObj.getValueAtOffset(0))
          .dereference();
      final int strLen = (int) strBuff.getHeader()
          .getWord(ObjectHeader.ARRAY_LENGTH_WORD);
      final byte[] bytes = new byte[strLen];
      for (int j = 0; j < strLen; j++) {
        final byte bte = ((Integer) strBuff.getValueAtOffset(j)).byteValue();
        bytes[j] = bte;
      }
      return new String(bytes);
    }
    return null;
  }

}
