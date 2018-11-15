package edu.harvard.cscie98.simplejava.impl.memory.memorymanager;

import edu.harvard.cscie98.simplejava.config.HeapParameters;
import edu.harvard.cscie98.simplejava.vm.classloader.VmClassLoader;
import edu.harvard.cscie98.simplejava.vm.memory.MemoryManager;
import edu.harvard.cscie98.simplejava.vm.memory.WriteBarrier;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;
import edu.harvard.cscie98.simplejava.vm.objectmodel.ObjectBuilder;
import edu.harvard.cscie98.simplejava.vm.threads.JvmThread;

import java.util.*;

import edu.harvard.cscie98.simplejava.impl.memory.heap.NonContiguousRegion;
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


public class GenerationalMemoryManager implements MemoryManager {


	private HeapParameters heapParams;
	private Heap heap;
	private JvmThread thread;
	private ObjectBuilder objectBuilder;
	private VmClassLoader classLoader;
	private WriteBarrier barrier;
	private long nurserySize = -1;
	private HeapPointer base= null;
	private HeapPointer pointer=null;
	private MarkSweepMemoryManager matureMemory = null;
	private List<HeapPointer> pointersToScan = null;



	public GenerationalMemoryManager(final HeapParameters heapParams) {
		//throw new RuntimeException("TODO: Your implementation for Assignment 4 goes here");
		this.heapParams = heapParams;
		heap = heapParams.getHeap();
		nurserySize = heapParams.getNurserySize();
		base=heapParams.getBaseAddress();
		pointer = base;
		matureMemory = new MarkSweepMemoryManager(heapParams);
	}


	@Override
	public void setThread(final JvmThread thread) {
		this.thread = thread;
		matureMemory.setThread(thread);
	}

	@Override
	public void setObjectBuilder(final ObjectBuilder objectBuilder) {
		this.objectBuilder = objectBuilder;
		matureMemory.setObjectBuilder(objectBuilder);
	}

	@Override
	public void setClassLoader(final VmClassLoader classLoader) {
		this.classLoader = classLoader;
		matureMemory.setClassLoader(classLoader);
	}

	@Override
	public void setBarrier(final WriteBarrier barrier) {
		this.barrier = barrier;
		matureMemory.setBarrier(barrier);
	}


	@Override
	public HeapPointer allocate(final long bytes) {
		
		long endOfNursery = base.toLong() + nurserySize;
		long proposedEnd = pointer.toLong() + bytes;
		if(proposedEnd > endOfNursery){
			// garbage collect
			garbageCollect();
			if(matureMemory.countFreeBytes() <= nurserySize * 2){
				matureMemory.garbageCollect();
			}
			if(bytes > nurserySize) return matureMemory.allocate(bytes);
		}
		HeapPointer returnPointer = pointer;
		pointer = pointer.add(bytes);

		return returnPointer;
	}

	private int copies=0;

	@Override
	public void garbageCollect() {

		copies=0;
		
		pointersToScan = new ArrayList<HeapPointer>();
		
		// move roots to NCR
		// copied roots are added to pointersToScan
		copyRoots();

		// add remembered set to pointersToScan
		Set<HeapObject> matureObjects = barrier.getRememberedSet();
		for(HeapObject x: matureObjects){
			pointersToScan.add(x.getAddress());
		}

		// scan objects and copy other alive objects
		while(pointersToScan.size() != 0){
			updateFields(pointersToScan.get(0).dereference());
			pointersToScan.remove(0);
		}

		barrier.clearRememberedSet();

		pointer=base;

		//System.out.println("Minor GC: " + copies + " copies");

		//testNoNurseryPointers();

	}


	private boolean pointerInNursery(HeapPointer ptr){

		if(ptr == HeapPointer.NULL){
			return false;
		}

		if(ptr.toLong() < base.toLong()){
			return false;
		}

		if(ptr.toLong() >= (base.toLong()+nurserySize)){
			return false;
		}

		return true;
	}



	// citation: semi-space-memory-manager
	private void copyRoots() {
		for (final ReferenceLocation ref : thread.getStack().getStackAndLocalReferenceLocations()) {
			//if (ref.getValue() != HeapPointer.NULL) {
			if (ref.getValue() != HeapPointer.NULL && pointerInNursery(ref.getValue())) {
				final HeapPointer newLoc = copyObject(ref.getValue());
				ref.setValue(newLoc);
			}
		}

		for (final ReferenceLocation ref : classLoader.getStaticReferenceLocations()) {
			//if (ref.getValue() != HeapPointer.NULL) {
			if (ref.getValue() != HeapPointer.NULL && pointerInNursery(ref.getValue())) {
				final HeapPointer newLoc = copyObject(ref.getValue());
				ref.setValue(newLoc);
			}
		}

		for (final ReferenceLocation ref : objectBuilder.getInternTableReferences()) {
			//if (ref.getValue() != HeapPointer.NULL) {
			if (ref.getValue() != HeapPointer.NULL && pointerInNursery(ref.getValue())) {
				final HeapPointer newLoc = copyObject(ref.getValue());
				ref.setValue(newLoc);
			}
		}
	}

	// citation: semi-space-memory-manager
	private HeapPointer copyObject(final HeapPointer fromPtr) {

		final HeapObject fromObj = fromPtr.dereference();
		final Object descWord = fromObj.getHeader().getWord(ObjectHeader.CLASS_DESCRIPTOR_WORD);
		if (descWord instanceof HeapPointer) {
			return (HeapPointer) descWord;
		}

		// +
		copies+=1;

		final long size = fromObj.getSize();
		//final HeapPointer toPtr = toSpace.allocate(size);
		final HeapPointer toPtr = matureMemory.allocate(size);
		if (toPtr.equals(HeapPointer.NULL)) {
			throw new OutOfMemoryError();
		}
		heap.memcpy(fromPtr, toPtr);

		// +
		pointersToScan.add(toPtr);

		//if (toSpace.pointerInRegion(fromPtr)) {
		//  throw new VmInternalError("Copying from the wrong space");
		//}
		fromObj.getHeader().setWord(ObjectHeader.CLASS_DESCRIPTOR_WORD, toPtr);
		//copied += size;
		return toPtr;
	}


	// citation: semi-space-memory-manager
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
	private void updateObjectFields(final HeapObject obj, final ObjectTypeDescriptor desc) {
		final List<VmField> fields = desc.getFields();
		for (int i = 0; i < fields.size(); i++) {
			final VmField fld = fields.get(i);
			if (fld.isReference()) {
				final HeapPointer ptr = (HeapPointer) obj.getValueAtOffset(i);
				//if (ptr != HeapPointer.NULL && !toSpace.pointerInRegion(ptr)) {
				if (ptr != HeapPointer.NULL && pointerInNursery(ptr)) {
					final HeapPointer toPtr = copyObject(ptr);
					obj.setValueAtOffset(i, toPtr);
				}
			}
		}
	}

	// citation: semi-space-memory-manager
	private void updateArrayFields(final HeapObject arr, final ArrayTypeDescriptor desc) {
		if (!desc.getElementDescriptor().isPrimitive()) {
			final int length = (int) arr.getHeader().getWord(ObjectHeader.ARRAY_LENGTH_WORD);
			for (int i = 0; i < length; i++) {
				final HeapPointer ptr = (HeapPointer) arr.getValueAtOffset(i);
				//if (ptr != HeapPointer.NULL && !toSpace.pointerInRegion(ptr)) {
				if (ptr != HeapPointer.NULL && pointerInNursery(ptr)) {
					final HeapPointer toPtr = copyObject(ptr);
					arr.setValueAtOffset(i, toPtr);
				}
			}
		}
	}







/*

	private List<HeapPointer> testPointers = null;

	private void testNoNurseryPointers(){

		testPointers= new ArrayList<HeapPointer>();

		testCopyRoots();

		int i = 0;

		int nulls = 0;

		while(i < testPointers.size()){
			HeapPointer hp = testPointers.get(i);

			if(hp == HeapPointer.NULL){
				i++;
				nulls++;
				continue;
			}


			if(hp.toLong() < base.add(nurserySize).toLong() || hp.toLong() >= base.add(heapParams.getExtent()).toLong()){
				System.out.println("REF ERROR");
				System.exit(1);
			}

			testUpdateFields(hp.dereference());

			i++;

		}

		System.out.println("No Nursery Pointers: " + (i-nulls) + " checked");


	}



	private void testCopyRoots() {
		for (final ReferenceLocation ref : thread.getStack().getStackAndLocalReferenceLocations()) {
			if (ref.getValue() != HeapPointer.NULL) {
				//final HeapPointer newLoc = copyObject(ref.getValue());
				//ref.setValue(newLoc);
				HeapPointer hp = ref.getValue();
				if(!testPointers.contains(hp))testPointers.add(hp);
			}
		}

		for (final ReferenceLocation ref : classLoader.getStaticReferenceLocations()) {
			if (ref.getValue() != HeapPointer.NULL) {
				//final HeapPointer newLoc = copyObject(ref.getValue());
				//ref.setValue(newLoc);
				HeapPointer hp = ref.getValue();
				if(!testPointers.contains(hp))testPointers.add(hp);
			}
		}

		for (final ReferenceLocation ref : objectBuilder.getInternTableReferences()) {
			if (ref.getValue() != HeapPointer.NULL) {
				//final HeapPointer newLoc = copyObject(ref.getValue());
				//ref.setValue(newLoc);
				HeapPointer hp = ref.getValue();
				if(!testPointers.contains(hp))testPointers.add(hp);
			}
		}
	}
	private void testUpdateFields(final HeapObject current) {
		final TypeDescriptor typeDesc = (TypeDescriptor) current.getHeader()
				.getWord(ObjectHeader.CLASS_DESCRIPTOR_WORD);
		if (typeDesc.isObject()) {
			testUpdateObjectFields(current, (ObjectTypeDescriptor) typeDesc);
		} else {
			testUpdateArrayFields(current, (ArrayTypeDescriptor) typeDesc);
		}
	}

	private void testUpdateObjectFields(final HeapObject obj, final ObjectTypeDescriptor desc) {
		final List<VmField> fields = desc.getFields();
		for (int i = 0; i < fields.size(); i++) {
			final VmField fld = fields.get(i);
			if (fld.isReference()) {
				final HeapPointer ptr = (HeapPointer) obj.getValueAtOffset(i);
				if (ptr != HeapPointer.NULL ){//&& !toSpace.pointerInRegion(ptr)) {
					//final HeapPointer toPtr = copyObject(ptr);
					//obj.setValueAtOffset(i, toPtr);
					if(!testPointers.contains(ptr)) testPointers.add(ptr);
				}
			}
		}
	}

	private void testUpdateArrayFields(final HeapObject arr, final ArrayTypeDescriptor desc) {
		if (!desc.getElementDescriptor().isPrimitive()) {
			final int length = (int) arr.getHeader().getWord(ObjectHeader.ARRAY_LENGTH_WORD);
			for (int i = 0; i < length; i++) {
				final HeapPointer ptr = (HeapPointer) arr.getValueAtOffset(i);
				if (ptr != HeapPointer.NULL){// && !toSpace.pointerInRegion(ptr)) {
					//final HeapPointer toPtr = copyObject(ptr);
					//arr.setValueAtOffset(i, toPtr);
					if(!testPointers.contains(ptr)) testPointers.add(ptr);
				}
			}
		}
	}

*/



		/*
		ArrayList<HeapPointer> al = new ArrayList<HeapPointer>();
		Set<HeapPointer> shp = new HashSet<HeapPointer>();
		System.out.println("base " + base.toLong());
		HeapPointer baseFour = base.add(4);
		HeapPointer baseFourCopy = base.add(4);
		if(baseFour.equals(baseFourCopy)){
			System.out.println("1: yes");
		}
		al.add(base);
		al.add(baseFour);
		if(al.contains(baseFourCopy)){
			System.out.println("2: yes");		
		} 
		shp.add(base);
		shp.add(baseFour);
		if(shp.contains(baseFourCopy)){
			System.out.println("3: yes");
		}
		*/

		//Map<HeapPointer, Long> map = new HashMap<HeapPointer, Long>();
		//map.put(base, new Long(5));
		//map.put(base.add(3), new Long(15));
		//map.put(base.add(5), new Long(25));
		//System.out.println("MAPA: " + map.get(base.add(3)));







}
