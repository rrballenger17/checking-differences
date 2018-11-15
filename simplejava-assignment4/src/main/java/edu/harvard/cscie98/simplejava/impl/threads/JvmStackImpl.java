package edu.harvard.cscie98.simplejava.impl.threads;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import edu.harvard.cscie98.simplejava.vm.classloader.VmConstantPool;
import edu.harvard.cscie98.simplejava.vm.classloader.VmMethod;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ReferenceLocation;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class JvmStackImpl implements JvmStack {

  List<StackFrame> stack;

  public JvmStackImpl() {
    this.stack = new LinkedList<StackFrame>();
  }

  @Override
  public StackFrame push(final VmMethod method) {
    final int maxStack = method.getMaxStack();
    final int maxLocals = method.getMaxLocals();
    final VmConstantPool constantPool = method.getConstantPool();
    final byte[] instructionStream = method.getInstructionStream();
    final StackFrameImpl frame = new StackFrameImpl(maxStack, maxLocals, constantPool,
        instructionStream, method);
    stack.add(frame);
    return frame;
  }

  @Override
  public StackFrame pop() {
    if (stack.size() == 0) {
      return null;
    }
    return stack.remove(stack.size() - 1);
  }

  @Override
  public boolean isEmpty() {
    return stack.isEmpty();
  }

  @Override
  public StackFrame peek() {
    if (stack.size() == 0) {
      return null;
    }
    return stack.get(stack.size() - 1);
  }

  @Override
  public List<ReferenceLocation> getStackAndLocalReferenceLocations() {
    final List<ReferenceLocation> refs = new ArrayList<ReferenceLocation>();
    for (final StackFrame f : stack) {
      final List<Object> stackEntries = ((StackFrameImpl) f).getStack();
      for (int i = 0; i < stackEntries.size(); i++) {
        if (stackEntries.get(i) instanceof HeapPointer) {
          refs.add(new StackReferenceLocation((StackFrameImpl) f, i));
        }
      }
      final Object[] localEntries = ((StackFrameImpl) f).getLocalVariables();
      for (int i = 0; i < localEntries.length; i++) {
        if (localEntries[i] instanceof HeapPointer) {
          refs.add(new LocalReferenceLocation((StackFrameImpl) f, i));
        }
      }

    }
    return refs;
  }

}
