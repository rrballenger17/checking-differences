package edu.harvard.cscie98.simplejava.impl.threads;

import java.util.LinkedList;
import java.util.List;

import edu.harvard.cscie98.simplejava.vm.classloader.VmConstantPool;
import edu.harvard.cscie98.simplejava.vm.classloader.VmMethod;
import edu.harvard.cscie98.simplejava.vm.execution.ExecutionException;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class StackFrameImpl implements StackFrame {

  private final List<Object> stack;
  private final int maxStack;
  private final Object[] locals;
  private final VmConstantPool constantPool;
  private int pc;
  private final byte[] instructionStream;
  private final VmMethod mthd;

  public StackFrameImpl(final int maxStack, final int maxLocals, final VmConstantPool constantPool,
      final byte[] instructionStream, final VmMethod mthd) {
    this.instructionStream = instructionStream;
    this.mthd = mthd;
    this.stack = new LinkedList<Object>();
    this.maxStack = maxStack;
    locals = new Object[maxLocals];
    this.constantPool = constantPool;
    pc = 0;
  }

  @Override
  public void push(final Object value) {
    if (stack.size() >= maxStack) {
      throw new ExecutionException("Maximum stack size exceeded");
    }
    stack.add(value);
  }

  @Override
  public Object pop() {
    return stack.remove(stack.size() - 1);
  }

  @Override
  public void setLocalVariable(final int variableIndex, final Object value) {
    locals[variableIndex] = value;
  }

  @Override
  public Object getLocalVariable(final int variableIndex) {
    return locals[variableIndex];
  }

  @Override
  public VmConstantPool getConstantPool() {
    return constantPool;
  }

  @Override
  public int getProgramCounter() {
    return pc;
  }

  @Override
  public void setProgramCounter(final int pc) {
    this.pc = pc;
  }

  @Override
  public byte[] getInstructionStream() {
    return instructionStream;
  }

  @Override
  public VmMethod getVmMethod() {
    return mthd;
  }

  public List<Object> getStack() {
    return stack;
  }

  public Object[] getLocalVariables() {
    return locals;
  }

  public HeapPointer getStackValue(final int idx) {
    return (HeapPointer) stack.get(idx);
  }

  public void setStackValue(final int idx, final HeapPointer val) {
    stack.set(idx, val);
  }

  @Override
  public String toString() {
    String str = "PC: " + pc + ", Stack:  " + stack + " Locals: [";
    for (final Object o : locals) {
      str += o + " ";
    }
    return str + "]";
  }

}
