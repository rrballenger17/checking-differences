package edu.harvard.cscie98.simplejava.impl.interpreter.intrinsics;

import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class Println_String implements Intrinsic {

  @Override
  public Object execute(final StackFrame frame) {
    final String str = Print_String.getStringFromHeapPointer((HeapPointer) frame
        .getLocalVariable(1));
    System.out.println(str);
    return null;
  }

}
