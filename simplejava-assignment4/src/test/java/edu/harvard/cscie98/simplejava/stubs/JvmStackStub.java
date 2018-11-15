package edu.harvard.cscie98.simplejava.stubs;

import java.util.ArrayList;
import java.util.List;

import edu.harvard.cscie98.simplejava.impl.threads.StackFrameImpl;
import edu.harvard.cscie98.simplejava.vm.classloader.VmMethod;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ReferenceLocation;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class JvmStackStub implements JvmStack {

  List<StackFrame> frames;

  public JvmStackStub() {
    frames = new ArrayList<StackFrame>();
  }

  @Override
  public StackFrame push(final VmMethod method) {
    return push(new StackFrameImpl(method.getMaxStack(), method.getMaxLocals(),
        method.getConstantPool(), method.getInstructionStream(), method));
  }

  public StackFrame push(final StackFrame frame) {
    frames.add(frame);
    return frame;
  }

  @Override
  public StackFrame pop() {
    return frames.remove(frames.size() - 1);
  }

  @Override
  public StackFrame peek() {
    return frames.get(frames.size() - 1);
  }

  @Override
  public boolean isEmpty() {
    return frames.size() == 0;
  }

  @Override
  public List<ReferenceLocation> getStackAndLocalReferenceLocations() {
    throw new RuntimeException("Not implemented");
  }

}
