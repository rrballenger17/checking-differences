package edu.harvard.cscie98.simplejava.impl.memory.memorymanager;

import edu.harvard.cscie98.simplejava.config.HeapParameters;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.memory.MemoryManager;
import edu.harvard.cscie98.simplejava.vm.memory.WriteBarrier;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectBuilder;
import edu.harvard.cscie98.simplejava.vm.threads.JvmThread;

import edu.harvard.cscie98.simplejava.impl.memory.heap.NonContiguousRegion;
import java.util.List;
import java.util.*;
import edu.harvard.cscie98.simplejava.config.Log;
import edu.harvard.cscie98.simplejava.impl.memory.heap.BumpPointerRegion;
import edu.harvard.cscie98.simplejava.vm.VmInternalError;
import edu.harvard.cscie98.simplejava.vm.classloader.VmField;
import edu.harvard.cscie98.simplejava.vm.memory.Heap;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ArrayTypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectHeader;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectTypeDescriptor;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ReferenceLocation;
import edu.harvard.cscie98.simplejava.vm.objectmodel.TypeDescriptor;


public class MarkSweepMemoryManager implements MemoryManager {

	private HeapParameters heapParams;
	private final NonContiguousRegion region;
	private JvmThread thread;
	private ObjectBuilder objectBuilder;
	private VmClassLoader classLoader;
	private WriteBarrier barrier;
	private List<HeapPointer> valid = null;

	public MarkSweepMemoryManager(final HeapParameters heapParams) {
		this.heapParams = heapParams;
		//throw new RuntimeException("TODO: Your implementation for Assignment 4 goes here");
		Heap heap = heapParams.getHeap();
		long nSize = heapParams.getNurserySize();
		HeapPointer start = heapParams.getBaseAddress().add(nSize);
		region = heap.getNonContiguousRegion(start, heapParams.getExtent()-nSize);
	}

	public long countFreeBytes(){
		return region.availableBytes();
	}

	@Override
	public void setThread(final JvmThread thread) {
		this.thread = thread;
	}

	@Override
	public void setObjectBuilder(final ObjectBuilder objectBuilder) {
		this.objectBuilder = objectBuilder;
	}

	@Override
	public void setClassLoader(final VmClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	@Override
	public void setBarrier(final WriteBarrier barrier) {
		this.barrier = barrier;
	}


	@Override
	public HeapPointer allocate(final long bytes) {
		return region.allocate(bytes);
	}


	@Override
	public void garbageCollect() {

		valid = new ArrayList<HeapPointer>();

		// get root references
		copyRoots();

		// scan objects and get references
		int index=0;
		while(index < valid.size()){
			updateFields(valid.get(index).dereference());
			index++;
		}

		Set<HeapPointer> active = region.getActivePointers();
		List<HeapPointer> toFree = new ArrayList<HeapPointer>();
		for(HeapPointer a: active){
			if(!valid.contains(a)){
				toFree.add(a);
			}
		}

		//region.testClimbTwo();

		//System.out.println("Major GC (NCR), objects freed: " + toFree.size());

		for(HeapPointer f: toFree){
			region.free(f.dereference());
		}

		//region.testClimbTwo();

	}

	// citation: semi-space-memory-manager
	// NEW
	private void copyRoots() {
		for (final ReferenceLocation ref : thread.getStack().getStackAndLocalReferenceLocations()) {
			if (ref.getValue() != HeapPointer.NULL) {
				//final HeapPointer newLoc = copyObject(ref.getValue());
				//ref.setValue(newLoc);
				HeapPointer ptr = ref.getValue();
				if(!valid.contains(ptr)) valid.add(ptr);
			}
		}

		for (final ReferenceLocation ref : classLoader.getStaticReferenceLocations()) {
			if (ref.getValue() != HeapPointer.NULL) {
				//final HeapPointer newLoc = copyObject(ref.getValue());
				//ref.setValue(newLoc);
				HeapPointer ptr = ref.getValue();
				if(!valid.contains(ptr)) valid.add(ptr);
			}
		}

		for (final ReferenceLocation ref : objectBuilder.getInternTableReferences()) {
			if (ref.getValue() != HeapPointer.NULL) {
				//final HeapPointer newLoc = copyObject(ref.getValue());
				//ref.setValue(newLoc);
				HeapPointer ptr = ref.getValue();
				if(!valid.contains(ptr)) valid.add(ptr);
			}
		}
	}

	// citation: semi-space-memory-manager
	// NEW
	private void updateFields(final HeapObject current) {
		final TypeDescriptor typeDesc = (TypeDescriptor) current.getHeader()
				.getWord(ObjectHeader.CLASS_DESCRIPTOR_WORD);
		if (typeDesc.isObject()) {
			updateObjectFields(current, (ObjectTypeDescriptor) typeDesc);
		} else {
			updateArrayFields(current, (ArrayTypeDescriptor) typeDesc);
		}
	}

	// citation: semi-space-memory-manager
	// NEW
	private void updateObjectFields(final HeapObject obj, final ObjectTypeDescriptor desc) {
		final List<VmField> fields = desc.getFields();
		for (int i = 0; i < fields.size(); i++) {
			final VmField fld = fields.get(i);
			if (fld.isReference()) {
				final HeapPointer ptr = (HeapPointer) obj.getValueAtOffset(i);
				//if (ptr != HeapPointer.NULL && !toSpace.pointerInRegion(ptr)) {
				if (ptr != HeapPointer.NULL){
					//final HeapPointer toPtr = copyObject(ptr);
					//obj.setValueAtOffset(i, toPtr);
					if(!valid.contains(ptr)) valid.add(ptr);
				}
			}
		}
	}

	// citation: semi-space-memory-manager
	// NEW
	private void updateArrayFields(final HeapObject arr, final ArrayTypeDescriptor desc) {
		if (!desc.getElementDescriptor().isPrimitive()) {
			final int length = (int) arr.getHeader().getWord(ObjectHeader.ARRAY_LENGTH_WORD);
			for (int i = 0; i < length; i++) {
				final HeapPointer ptr = (HeapPointer) arr.getValueAtOffset(i);
				//if (ptr != HeapPointer.NULL && !toSpace.pointerInRegion(ptr)) {
				if (ptr != HeapPointer.NULL){
					//final HeapPointer toPtr = copyObject(ptr);
					//arr.setValueAtOffset(i, toPtr);
					if(!valid.contains(ptr)) valid.add(ptr);
				}
			}
		}
	}

}













		/*HeapPointer ptr = heapParams.getBaseAddress();



		int count=0;
		while(true){

			System.out.println(count + " " + allocCount);

			HeapObject ho = null;

			//try{
				ho = ptr.dereference();
			//}catch(Exception e){
			//	break;
			//}

			ptr = ptr.add(ho.getSize());
			

			//if(ho.getSize() == 0) break;
			//System.out.println("size: " + ho.getSize())

			if(!region.pointerInRegion(ptr)) break;

			


			count++;
			//System.out.println(heapParams.getBaseAddress() + " " + ptr.toLong() + " " + (heapParams.getBaseAddress().toLong() + heapParams.getExtent()));
		}

		//System.out.println("Garbage Collect: " + allocCount + " " + count);

		//System.exit(1);*/



