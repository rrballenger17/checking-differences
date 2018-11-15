package edu.harvard.cscie98.simplejava.impl.memory.memorymanager;

import java.util.Set;
import java.util.*;

import edu.harvard.cscie98.simplejava.config.HeapParameters;
import edu.harvard.cscie98.simplejava.vm.memory.WriteBarrier;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;

public class GenerationalWriteBarrier implements WriteBarrier {


	private HeapParameters hp = null;
	private Set<HeapObject> matures = null;
	
	private HeapPointer base = null;
	private HeapPointer endOfNursery = null;
	private HeapPointer endOfMemory = null;

	public GenerationalWriteBarrier(final HeapParameters heapParams) {
		//throw new RuntimeException("TODO: Your implementation for Assignment 4 goes here");
		
		hp=heapParams;

		base = heapParams.getBaseAddress();
		endOfNursery = base.add(heapParams.getNurserySize());
		endOfMemory = base.add(heapParams.getExtent());

		matures = new HashSet<HeapObject>();
		
	}

	@Override
	public void onPointerWrite(final HeapObject obj, final HeapPointer ptr) {


		long objLong = obj.getAddress().toLong();

		// obj isn't in the mature region
		if( objLong < endOfNursery.toLong() || objLong >= endOfMemory.toLong()){
			//testForMiss(obj, ptr);
			return;
		}


		long ptrLong = ptr.toLong();

		// ptr isn't in the nursery
		if( ptrLong < base.toLong() || ptrLong >= endOfNursery.toLong()){
			//testForMiss(obj, ptr);
			return ;
		}

		matures.add(obj);

	}

	@Override
	public Set<HeapObject> getRememberedSet() {
		return matures;
	}

	@Override
	public void clearRememberedSet() {
		matures=new HashSet<HeapObject>();
	}
/*
	private void testForMiss(final HeapObject obj, final HeapPointer ptr) {

		if(ptr == HeapPointer.NULL){
			return;
		}

		if(obj.getAddress().toLong() < hp.getBaseAddress().add(hp.getNurserySize()).toLong()){
			return;
		}

		if(ptr.toLong() >= hp.getBaseAddress().add(hp.getNurserySize()).toLong() && ptr.toLong() < hp.getBaseAddress().add(hp.getExtent()).toLong()){
			return;
		}

		System.out.println("BARRIER MISS");

		System.exit(1);


	}*/


}
