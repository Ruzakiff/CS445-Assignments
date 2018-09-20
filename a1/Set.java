package cs445.a1;

/**
 * @author ryan
 *
 */
//want to use private methods, don't have to errorcatch cause we are only ones calling
public class Set<E> implements SetInterface {
	
	private E[] setArray;
	private boolean initialized=false;
	private int numberEntries;
	private static final int defaultSize=10;
	private int capacity=0;
	private boolean operating=false;

	@SuppressWarnings("unchecked")
	public Set(int c){
		if(c<=0){
			throw new IllegalArgumentException("Capacity must be greater than 0");
		}
		else{
			capacity=c;
			numberEntries=0;
			setArray=(E[])new Object[capacity];
			initialized=true;
		}
	}
	
	public Set(){
		this(defaultSize);
	}
	
	public Set(E[] entries) throws NullPointerException, SetFullException{
		this(entries.length);
		for(int i=0;i<entries.length;i++){
			add(entries[i]);
			
		}
	}
	
	private void checkInitialization(){
		if(!initialized){
			throw new SecurityException("Set not properly initialized");
		}
	}
	
	private boolean setFull(){
		return numberEntries>=setArray.length;
	}
	
	
    /**
     * Determines the current number of entries in this set.
     *
     * @return  The integer number of entries currently in this set
     */
	@Override
	public int getSize() {
		return numberEntries;
	}

	/**
     * Determines whether this set is empty.
     *
     * @return  true if this set is empty; false if not
     */
	@Override
	public boolean isEmpty() {
		return numberEntries==0;
	}

    /**
     * Adds a new entry to this set, avoiding duplicates.
     *
     * <p> If newEntry is not null, this set does not contain newEntry, and this
     * set has available capacity (if applicable), then add modifies the set so
     * that it contains newEntry. All other entries remain unmodified.
     * Duplicates are determined using the .equals() method.
     *
     * <p> If newEntry is null, then add throws NullPointerException without
     * modifying the set. If this set already contains newEntry, then add
     * returns false without modifying the set. If this set has a capacity
     * limit, and does not have available capacity, then add throws
     * SetFullException without modifying the set. If this set does not have a
     * capacity limit (i.e., if it resizes as needed), then it will never throw
     * SetFullException.
     *
     * @param newEntry  The object to be added as a new entry
     * @return  true if the addition is successful; false if the item already is
     * in this set
     * @throws SetFullException  If this set has a fixed capacity and does not
     * have the capacity to store an additional entry
     * @throws NullPointerException  If newEntry is null
     */
	@SuppressWarnings("unchecked")
	@Override
	public boolean add(Object newEntry) throws SetFullException, NullPointerException {
		checkInitialization();
		
		if(newEntry==null){
			throw new NullPointerException("Cannot Add Null Element");
		}
		if(setFull()){
			E[] temp=(E[])new Object[capacity*2];
			capacity=capacity*2;
			for(int i=0;i<setArray.length;i++){
				temp[i]=setArray[i];
			}
			setArray=temp;
		}
		if(!contains(newEntry)){
			setArray[numberEntries]=(E) newEntry;
			numberEntries++;
			return true;
		}
		else{
			return false;
		}
		
	}

	/**
     * Removes a specific entry from this set, if possible.
     *
     * <p> If this set contains the entry, remove modifies the set so that it no
     * longer contains entry. All other entries remain unmodified. Identifying
     * this entry is accomplished using the .equals() method. The removed entry
     * will be returned.
     *
     * <p> If this set does not contain entry, remove will return null without
     * modifying the set. Because null cannot be added, a return value of null
     * will never indicate a successful removal.
     *
     * <p> If the specified entry is null, then remove throws
     * NullPointerException without modifying the set.
     *
     * @param entry  The entry to be removed
     * @return  The removed entry if removal was successful; null otherwise
     * @throws NullPointerException  If entry is null
     */
	@Override
	public E remove(Object entry) throws NullPointerException {
		@SuppressWarnings("unchecked")
		int i=indexOf((E)entry);//private indexOf
		E removed=removeAt(i);
		return removed;
	}
	private int indexOf(E entry){
		int i=0;
		int where=-1;
		while(i<numberEntries&&where==-1){//end of size, the populated array
			if(setArray[i].equals(entry)){
				where=i;
			}
			i++;
		}
		return where;
	}
	
	private E removeAt(int i){
		E result=null;
		if(i>=0){
			result=setArray[i];
			setArray[i]=setArray[--numberEntries];//no if statement to check if i==size
			//not frequent enough to actually save
			setArray[numberEntries]=null;
		}
		return result;
}


    /**
     * Removes an arbitrary entry from this set, if possible.
     *
     * <p> If this set contains at least one entry, remove will modify the set
     * so that it no longer contains one of its entries. All other entries
     * remain unmodified. The removed entry will be returned.
     *
     * <p> If this set is empty, remove will return null without modifying the
     * set. Because null cannot be added, a return value of null will never
     * indicate a successful removal.
     *
     * @return  The removed entry if the removal was successful; null otherwise
     */
	@Override
	public E remove() {
		return removeAt(numberEntries);
	}
	
	 /**
     * Removes all entries from this set.
     *
     * <p> If this set is already empty, clear will not modify the set.
     * Otherwise, the set will be modified so that it contains no entries.
     */
	@Override
	public void clear() {
		if(!isEmpty()){
			for(int i=0;i<numberEntries;i++){
				setArray[i]=null;
			}
			numberEntries=0;
		}
		// TODO Auto-generated method stub
		
	}
	
	/**
     * Tests whether this set contains a given entry. Equality is determined
     * using the .equals() method.
     *
     * <p> If this set contains entry, then contains returns true. Otherwise
     * (including if this set is empty), contains returns false. If entry is
     * null, then remove throws NullPointerException. The method never modifies
     * this set.
     *
     * @param entry  The entry to locate
     * @return  true if this set contains entry; false if not
     * @throws NullPointerException  If entry is null
     */
	@SuppressWarnings("unchecked")
	@Override
	public boolean contains(Object entry) throws NullPointerException {
		for(int i=0;i<numberEntries;i++){
			if(((E)entry).equals(setArray[i])){//.equals for null will crash, fix
				return true;
			}
		}
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
     * Retrieves all entries that are in this set.
     *
     * <p> An array is returned that contains a reference to each of the entries
     * in this set. The returned array's length will be equal to the number of
     * elements in this set, and thus the array will contain no null values.
     *
     * <p> If the implementation of set is array-backed, toArray will not return
     * the private backing array. Instead, a new array will be allocated with
     * exactly the appropriate capacity (including an array of size 0, if the
     * set is empty).
     *
     * @return  A newly-allocated array of all the entries in this set
     */
	@Override
	public Object[] toArray() {
		@SuppressWarnings("unused")
		E[] result=(E[])new Object[numberEntries];
		for(int i=0;i<numberEntries;i++){
			result[i]=setArray[i];
		}
		return result;
	}
}
