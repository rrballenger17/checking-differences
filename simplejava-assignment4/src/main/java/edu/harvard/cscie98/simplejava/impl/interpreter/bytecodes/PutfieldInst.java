package edu.harvard.cscie98.simplejava.impl.interpreter.bytecodes;

import edu.harvard.cscie98.simplejava.impl.interpreter.InterpreterUtils;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeFactory;
import edu.harvard.cscie98.simplejava.vm.classloader.TypeName;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.memory.WriteBarrier;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectBuilder;
import edu.harvard.cscie98.simplejava.vm.threads.JvmStack;
import edu.harvard.cscie98.simplejava.vm.threads.StackFrame;

public class PutfieldInst {

	public static void interpret(final int pc, final byte[] code, final JvmStack stack,
			final StackFrame frame, final VmClassLoader classLoader, final ObjectBuilder objectBuilder,
			final WriteBarrier barrier) {
		final int idx = InterpreterUtils.getFieldIndex(pc, code, frame, classLoader);
		final Object val = frame.pop();
		final HeapPointer objPtr = (HeapPointer) frame.pop();

		if (objPtr == HeapPointer.NULL) {
			final TypeName exceptionName = TypeFactory.fromBinaryName("java.lang.NullPointerException");
			InterpreterUtils.throwException(exceptionName, stack, frame, pc, objectBuilder);
			return;
		}

		final HeapObject obj = objPtr.dereference();

		if(val instanceof HeapPointer){
			barrier.onPointerWrite(obj, (HeapPointer) val);
		}

		obj.setValueAtOffset(idx, val);
		frame.setProgramCounter(pc + 3);
	}
}
