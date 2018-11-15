package edu.harvard.cscie98.simplejava.impl.interpreter.intrinsics;

import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public interface Intrinsic {
  Object execute(StackFrame frame);
}
