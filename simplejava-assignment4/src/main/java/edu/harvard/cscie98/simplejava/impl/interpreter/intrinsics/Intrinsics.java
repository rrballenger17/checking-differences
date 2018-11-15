package edu.harvard.cscie98.simplejava.impl.interpreter.intrinsics;

import java.util.HashMap;
import java.util.Map;

import edu.harvard.cscie98.simplejava.vm.classloader.VmMethod;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class Intrinsics {

  private final Map<String, Intrinsic> intrinsics;

  public Intrinsics() {
    intrinsics = new HashMap<String, Intrinsic>();
    intrinsics.put("java.io.PrintStream.print(Ljava/lang/String;)V", new Print_String());
    intrinsics.put("java.io.PrintStream.println(Ljava/lang/String;)V", new Println_String());
    intrinsics.put("java.io.PrintStream.print(I)V", new Print_Int());
    intrinsics.put("java.io.PrintStream.println(I)V", new Println_Int());
    intrinsics.put("java.io.PrintStream.print(Z)V", new Print_Boolean());
    intrinsics.put("java.io.PrintStream.println(Z)V", new Println_Boolean());
  }

  public boolean hasIntrinsic(final VmMethod mthd) {
    return intrinsics.containsKey(mthd.toString());
  }

  public Object callIntrinsic(final StackFrame frame) {
    return intrinsics.get(frame.getVmMethod().toString()).execute(frame);
  }
}
