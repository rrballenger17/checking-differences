package edu.harvard.cscie98.simplejava.impl.interpreter;

import org.apache.bcel.Constants;

import edu.harvard.cscie98.simplejava.config.Log;
import edu.harvard.cscie98.simplejava.config.SimpleJavaOutOfScopeException;
import edu.harvard.cscie98.simplejava.impl.classloader.PrimordialMainMethod;
import edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes.*;
import edu.harvard.cscie98.simplejava.impl.interpreter.intrinsics.Intrinsics;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.classloader.VmMethod;
import edu.harvard.cscie98.simplejava.vm.execution.Interpreter;
import edu.harvard.cscie98.simplejava.vm.execution.UncaughtException;
import edu.harvard.cscie98.simplejava.vm.memory.WriteBarrier;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectBuilder;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.JvmThread;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class SwitchInterpreter implements Interpreter {

  private ObjectBuilder objectBuilder;
  private VmClassLoader classLoader;
  private JvmThread thread;
  private JvmStack stack;
  private final long[] callStats;
  private WriteBarrier barrier;
  private final Intrinsics intrinsics;

  public SwitchInterpreter() {
    this.callStats = new long[256];
    this.intrinsics = new Intrinsics();
  }

  @Override
  public void setClassLoader(final VmClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  @Override
  public void setVmThread(final JvmThread thread) {
    this.thread = thread;
    this.stack = thread.getStack();
  }

  @Override
  public void setObjectBuilder(final ObjectBuilder objectBuilder) {
    this.objectBuilder = objectBuilder;
  }

  @Override
  public void setBarrier(final WriteBarrier barrier) {
    this.barrier = barrier;
  }

  @Override
  public JvmThread getThread() {
    return thread;
  }

  @Override
  public StackFrame executeMethod(final VmMethod mthd, final HeapPointer argArray) {
    // Primordial stack frame acts as a catch block for any exception
    final StackFrame primordial = stack.push(new PrimordialMainMethod());
    primordial.push(HeapPointer.NULL);
    primordial.setProgramCounter(3);

    final StackFrame stackFrame = stack.push(mthd);
    if (argArray != null) {
      stackFrame.setLocalVariable(0, argArray);
    }
    stackFrame.setProgramCounter(0);
    return interpret(mthd, stackFrame);
  }

  private StackFrame interpret(final VmMethod method, StackFrame stackFrame) {
    StackFrame previousStackFrame;
    for (;;) {
      interpretBytecode(stackFrame);

      previousStackFrame = stackFrame;
      stackFrame = stack.peek();
      if (stackFrame.getVmMethod() instanceof PrimordialMainMethod) {
        final StackFrame primordial = stack.pop();
        final HeapPointer uncaught = (HeapPointer) primordial.pop();
        if (uncaught != HeapPointer.NULL) {
          throw new UncaughtException(uncaught);
        }
        Log.interpreter("Empty stack. Exiting from call of " + method);
        return previousStackFrame;
      }
    }
  }

  void interpretBytecode(final StackFrame frame) {
    final int pc = frame.getProgramCounter();
    final byte[] code = frame.getInstructionStream();
    final short opcode = (short) InterpreterUtils.getByteUnsigned(code, pc);
    callStats[opcode]++;

    Log.interpreter(
        frame.getVmMethod() + " " + frame + " " + BytecodeUtils.getOpcodeName((byte) opcode));

    switch (opcode) {
    case Constants.NOP:
      NopInst.interpret(pc, code, stack, frame);
      break;
    case Constants.ACONST_NULL:
      AconstNullInst.interpret(pc, code, stack, frame);
      break;
    case Constants.ICONST_M1:
      IconstM1Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.ICONST_0:
      Iconst0Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.ICONST_1:
      Iconst1Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.ICONST_2:
      Iconst2Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.ICONST_3:
      Iconst3Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.ICONST_4:
      Iconst4Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.ICONST_5:
      Iconst5Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.BIPUSH:
      BipushInst.interpret(pc, code, stack, frame);
      break;
    case Constants.SIPUSH:
      SipushInst.interpret(pc, code, stack, frame);
      break;
    case Constants.LDC:
      LdcInst.interpret(pc, code, stack, frame, objectBuilder);
      break;
    case Constants.ILOAD:
      IloadInst.interpret(pc, code, stack, frame);
      break;
    case Constants.ALOAD:
      AloadInst.interpret(pc, code, stack, frame);
      break;
    case Constants.ILOAD_0:
      Iload0Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.ILOAD_1:
      Iload1Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.ILOAD_2:
      Iload2Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.ILOAD_3:
      Iload3Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.ALOAD_0:
      Aload0Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.ALOAD_1:
      Aload1Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.ALOAD_2:
      Aload2Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.ALOAD_3:
      Aload3Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.IALOAD:
      IaloadInst.interpret(pc, code, stack, frame, objectBuilder);
      break;
    case Constants.AALOAD:
      AaloadInst.interpret(pc, code, stack, frame, objectBuilder);
      break;
    case Constants.BALOAD:
      BaloadInst.interpret(pc, code, stack, frame);
      break;
    case Constants.CALOAD:
      CaloadInst.interpret(pc, code, stack, frame);
      break;
    case Constants.SALOAD:
      SaloadInst.interpret(pc, code, stack, frame);
      break;
    case Constants.ISTORE:
      IstoreInst.interpret(pc, code, stack, frame);
      break;
    case Constants.ASTORE:
      AstoreInst.interpret(pc, code, stack, frame);
      break;
    case Constants.ISTORE_0:
      Istore0Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.ISTORE_1:
      Istore1Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.ISTORE_2:
      Istore2Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.ISTORE_3:
      Istore3Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.ASTORE_0:
      Astore0Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.ASTORE_1:
      Astore1Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.ASTORE_2:
      Astore2Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.ASTORE_3:
      Astore3Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.IASTORE:
      IastoreInst.interpret(pc, code, stack, frame, objectBuilder);
      break;
    case Constants.AASTORE:
      AastoreInst.interpret(pc, code, stack, frame, objectBuilder, barrier);
      break;
    case Constants.BASTORE:
      BastoreInst.interpret(pc, code, stack, frame);
      break;
    case Constants.CASTORE:
      CastoreInst.interpret(pc, code, stack, frame);
      break;
    case Constants.SASTORE:
      SastoreInst.interpret(pc, code, stack, frame);
      break;
    case Constants.POP:
      PopInst.interpret(pc, code, stack, frame);
      break;
    case Constants.POP2:
      Pop2Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.DUP:
      DupInst.interpret(pc, code, stack, frame);
      break;
    case Constants.DUP_X1:
      DupX1Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.DUP_X2:
      DupX2Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.DUP2:
      Dup2Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.DUP2_X1:
      Dup2X1Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.DUP2_X2:
      Dup2X2Inst.interpret(pc, code, stack, frame);
      break;
    case Constants.SWAP:
      SwapInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IADD:
      IaddInst.interpret(pc, code, stack, frame);
      break;
    case Constants.ISUB:
      IsubInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IMUL:
      ImulInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IDIV:
      IdivInst.interpret(pc, code, stack, frame, objectBuilder);
      break;
    case Constants.IREM:
      IremInst.interpret(pc, code, stack, frame, objectBuilder);
      break;
    case Constants.INEG:
      InegInst.interpret(pc, code, stack, frame);
      break;
    case Constants.ISHL:
      IshlInst.interpret(pc, code, stack, frame);
      break;
    case Constants.ISHR:
      IshrInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IUSHR:
      IushrInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IAND:
      IandInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IOR:
      IorInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IXOR:
      IxorInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IINC:
      IincInst.interpret(pc, code, stack, frame);
      break;
    case Constants.I2B:
      I2bInst.interpret(pc, code, stack, frame);
      break;
    case Constants.I2C:
      I2cInst.interpret(pc, code, stack, frame);
      break;
    case Constants.I2S:
      I2sInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IFEQ:
      IfeqInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IFNE:
      IfneInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IFLT:
      IfltInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IFGE:
      IfgeInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IFGT:
      IfgtInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IFLE:
      IfleInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IF_ICMPEQ:
      IfIcmpeqInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IF_ICMPNE:
      IfIcmpneInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IF_ICMPLT:
      IfIcmpltInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IF_ICMPGE:
      IfIcmpgeInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IF_ICMPGT:
      IfIcmpgtInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IF_ICMPLE:
      IfIcmpleInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IF_ACMPEQ:
      IfAcmpeqInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IF_ACMPNE:
      IfAcmpneInst.interpret(pc, code, stack, frame);
      break;
    case Constants.GOTO:
      GotoInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IRETURN:
      IreturnInst.interpret(pc, code, stack, frame);
      break;
    case Constants.ARETURN:
      AreturnInst.interpret(pc, code, stack, frame);
      break;
    case Constants.RETURN:
      ReturnInst.interpret(pc, code, stack, frame);
      break;
    case Constants.GETSTATIC:
      GetstaticInst.interpret(pc, code, stack, frame, classLoader);
      break;
    case Constants.PUTSTATIC:
      PutstaticInst.interpret(pc, code, stack, frame, classLoader);
      break;
    case Constants.GETFIELD:
      GetfieldInst.interpret(pc, code, stack, frame, classLoader, objectBuilder);
      break;
    case Constants.PUTFIELD:
      PutfieldInst.interpret(pc, code, stack, frame, classLoader, objectBuilder, barrier);
      break;
    case Constants.INVOKEVIRTUAL:
      InvokevirtualInst.interpret(pc, code, stack, frame, classLoader, objectBuilder,
          intrinsics);
      break;
    case Constants.INVOKESPECIAL:
      InvokespecialInst.interpret(pc, code, stack, frame, classLoader, objectBuilder);
      break;
    case Constants.INVOKESTATIC:
      InvokestaticInst.interpret(pc, code, stack, frame, classLoader, objectBuilder);
      break;
    case Constants.NEW:
      NewInst.interpret(pc, code, stack, frame, objectBuilder);
      break;
    case Constants.NEWARRAY:
      NewarrayInst.interpret(pc, code, stack, frame, objectBuilder);
      break;
    case Constants.ANEWARRAY:
      AnewarrayInst.interpret(pc, code, stack, frame, objectBuilder);
      break;
    case Constants.ARRAYLENGTH:
      ArraylengthInst.interpret(pc, code, stack, frame, objectBuilder);
      break;
    case Constants.ATHROW:
      AthrowInst.interpret(pc, code, stack, frame, objectBuilder);
      break;
    case Constants.CHECKCAST:
      CheckcastInst.interpret(pc, code, stack, frame);
      break;
    case Constants.INSTANCEOF:
      InstanceofInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IFNULL:
      IfnullInst.interpret(pc, code, stack, frame);
      break;
    case Constants.IFNONNULL:
      IfnonnullInst.interpret(pc, code, stack, frame);
      break;

    default:
      throw new SimpleJavaOutOfScopeException("Bytecode 0x" + Long.toHexString(opcode));
    }
  }

  @Override
  public void printStats() {
    long total = 0;
    for (int i = 0; i < callStats.length; i++) {
      if (callStats[i] > 0) {
        Log.stats("  " + BytecodeUtils.getOpcodeName((byte) i) + ": " + callStats[i]);
        total += callStats[i];
      }
    }
    Log.stats("Total bytecodes interpreted: " + total);
  }

  public long[] getCallStats() {
    return callStats;
  }
}
