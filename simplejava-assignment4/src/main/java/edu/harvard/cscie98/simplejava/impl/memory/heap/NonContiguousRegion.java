package edu.harvard.cscie98.simplejava.impl.memory.heap;

import edu.harvard.cscie98.simplejava.vm.memory.Region;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapObject;
import edu.harvard.cscie98.simplejava.vm.objectmodel.HeapPointer;

//import edu.harvard.cscie98.simplejava.impl.memory.heap.Tuple;

import java.util.*;

public class NonContiguousRegion implements Region {

	private List<HeapPointer> freeList = null;
	private Map<HeapPointer, Long> freeListSize = null;

	private Set<HeapPointer> allocated = null;
	private Map<HeapPointer, Long> allocatedSize = null;

	private HeapPointer constructorAddress = null;
	private long constructorExtent = -1;

	NonContiguousRegion(final HeapPointer baseAddress, final long extent) {
		freeList = new ArrayList<HeapPointer>();
		freeListSize = new HashMap<HeapPointer, Long>();
		
		freeList.add(baseAddress);
		freeListSize.put(baseAddress, extent);

		constructorAddress = baseAddress;
		constructorExtent = extent;

		allocated = new HashSet<HeapPointer>();
		allocatedSize = new HashMap<HeapPointer, Long>();
	}

	public long availableBytes(){

		long total=0;
		for(HeapPointer t: freeList){
			  total += freeListSize.get(t);
		}
		return total;
	}

	public Set<HeapPointer> getActivePointers(){
		return allocated;
	}

	private void combineFreeChunks(int i){

		if(i < 0) return;

		if(i + 1 >= freeList.size()) return;

		HeapPointer behindPointer = freeList.get(i);
		long behindSize = freeListSize.get(behindPointer);
		HeapPointer frontPointer = freeList.get(i+1);
		long frontSize = freeListSize.get(frontPointer);

		long nextByte = behindPointer.toLong() + behindSize;
		if(nextByte == frontPointer.toLong()){
			long combineSize = behindSize + frontSize;
			freeList.remove(i+1);
			freeListSize.remove(frontPointer);
			freeListSize.put(behindPointer, combineSize);
		}

	}


	@Override
	public void reset(){
		throw new RuntimeException("NonContiguousRegion doesn't support the reset method.");
	}

	@Override
	public void free(final HeapObject obj) {

		HeapPointer hp = obj.getAddress();
		allocated.remove(hp);
		allocatedSize.remove(hp);

		// add to free list and return 
		int length = freeList.size();
		for(int i=0; i< length; i++){

			HeapPointer listPointer = freeList.get(i);

			if(listPointer.toLong() > hp.toLong()){

				freeList.add(i, hp);
				freeListSize.put(hp, obj.getSize());
				combineFreeChunks(i);
				combineFreeChunks(i-1);
				return;
			}
		}

		// add to the end of the free list
		if(!pointerInRegion(hp)){
			throw new RuntimeException("Pointer to be freed is not contained in the region.");
		}

		freeList.add(hp);
		freeListSize.put(hp, obj.getSize());
	}

	@Override
	public HeapPointer allocate(final long bytes) {

		HeapPointer toReturn = HeapPointer.NULL;

		int length=freeList.size();
		for(int i=0; i<length; i++){

			HeapPointer loc = freeList.get(i);
			long locSize = freeListSize.get(loc);

			if(locSize >= bytes){
				freeList.remove(i);
				freeListSize.remove(loc);

				toReturn=loc;

				if(locSize > bytes){
					HeapPointer freeBlock = loc.add(bytes);
					freeList.add(i, freeBlock);
					freeListSize.put(freeBlock, locSize - bytes);
				}
				break;
			}

		}

		if(toReturn != HeapPointer.NULL){
			allocated.add(toReturn);
			allocatedSize.put(toReturn, bytes);
		}

		return toReturn;
	}



	@Override
	public boolean pointerInRegion(final HeapPointer ptr) {

		long endExclusive = constructorAddress.toLong() + constructorExtent;

		if(ptr.toLong() >= constructorAddress.toLong() && ptr.toLong() < endExclusive){
			return true;
		}

		return false;
	}

/*
	public void testClimbTwo(){


		HeapPointer pointer = constructorAddress;

		HeapPointer end = constructorAddress.add(constructorExtent);

		boolean freeBlock = false;

		int fCount=0;
		int aCount=0;

		while(true){

			if(pointer.equals(end)){
				System.out.println("\nGC: CLIMBED FREE LIST -> free blocks: " + freeList.size() + " allocated blocks: " + allocated.size() + "\n");

				if(freeList.size() != freeListSize.size()){
					System.out.println("ERROR - f list sizes");
					System.exit(1);					
				}

				if(allocated.size() != allocatedSize.size()){
					System.out.println("ERROR - a list sizes");
					System.exit(1);					
				}

				if(freeList.size() != fCount){
					System.out.println("ERROR - not all frees used");
					System.exit(1);					
				}

				if(allocated.size() != aCount){
					System.out.println("ERROR - not all allocs used");
					System.exit(1);					
				}

				return;
			}

			if(freeList.contains(pointer)){
				if(freeBlock){
					System.out.println("ERROR - two free blocks");
					System.exit(1);
				}

				pointer = pointer.add(freeListSize.get(pointer));
				freeBlock = true;
				fCount++;
				continue;
			}

			if(allocated.contains(pointer)){

				pointer=pointer.add(allocatedSize.get(pointer));
				freeBlock = false;
				aCount++;
				continue;
			}


		}




	}*/




}




/*
	public void freeDeadSpace(List<HeapPointer> valid){

			testFunctionClimbLists();

			Set<HeapPointer> toFree = new HashSet<HeapPointer>();

			for(HeapPointer x: allocated){
				  if(!valid.contains(x)){
						toFree.add(x);
				  }
			}

			System.out.println("Major GC (NCR), objects freed: " + toFree.size());

			for(HeapPointer x: toFree){
				  free(x.dereference());
			}

			testFunctionClimbLists();
	}

*/


	  /*

	  public long printFreeList(){

			long total = 0;
			for(Tuple t: freeList){
				  System.out.println("pointer: " + t.getPointer().toLong() + " size: " + t.getSize());
				  total+= t.getSize();

			}

			System.out.println("total free size: " + total);

			return total;
	  }*/



/*
	  // climb from start to finish through allocated and free lists
	  public void testFunctionClimbLists(){


	  	if(freeList.size() != freeListSize.size()){
	  		System.out.println("SIZE ERROR");
	  		System.exit(1);
	  	}
	  	if(allocated.size() != allocatedSize.size()){
	  		System.out.println("SIZE ERROR");
	  		System.exit(1);
	  	}

	  	int allocCount = 0;

		int index=0;
		HeapPointer end = constructorAddress.add(constructorExtent);

		// climb through tangent allocated blocks at beginning
		HeapPointer firstPackPtr = constructorAddress;
		while(allocated.contains(firstPackPtr)){
			firstPackPtr = firstPackPtr.add(allocatedSize.get(firstPackPtr));
			allocCount++;
		}

		// get first free block, test it matches the above pointer
		//Tuple freeListTuple = freeList.get(0);
		HeapPointer spot = freeList.get(0); //freeListTuple.getPointer();
		if(!spot.equals(firstPackPtr)){
			System.out.println("ERROR3");
			System.exit(1);
		}


		while(true){

			if(freeListSize.get(spot) == null){
				System.out.println("ERROR: null key");
				System.exit(1);
			}

			// add free size to the pointer
			spot = spot.add(freeListSize.get(spot));
			//System.out.println(spot.toLong());

			// check for end of memory and free list
			if(spot.equals(end) && index == freeList.size() - 1 && allocCount == allocated.size()){

				System.out.println("END REACHED2 " + "free blocks: " + freeList.size() + " allocated blocks: " + allocated.size() + "");
				return;
			}

			// hop through allocated blocks to next free, exit if frees are adjacent
			boolean enter = false;
			while(allocated.contains(spot)){
				spot = spot.add(allocatedSize.get(spot));
				allocCount++;
				enter=true;
			}
			if(!enter){
				System.out.println("ERROR4");
				System.exit(1);
			}

			// check for end again
			if(spot.equals(end) && index == freeList.size() - 1 && allocCount == allocated.size()){
				System.out.println("END REACHED " + "free blocks: " + freeList.size() + " allocated blocks: " + allocated.size());
				return;
			}

			index++;

			// confirm next free block is where its expected
			HeapPointer nextSpot = freeList.get(index);
			if(!nextSpot.equals(spot)){
				System.out.println("ERROR");
				System.exit(1);
			}
			//freeListTuple=freeList.get(index);

		}



	  }
*/




