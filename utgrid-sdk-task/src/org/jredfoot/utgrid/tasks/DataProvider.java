package org.jredfoot.utgrid.tasks;

import java.util.Iterator;

public abstract class DataProvider<T1, T2> implements IDataProvider<T2> {

	
	protected T1 dataIn;
	protected Iterator<T2> data;
	protected long pageSize = 0;
	protected long offset = 0;
	
	public DataProvider(T1 in, long pageSize) {
		this.dataIn = in;
		this.pageSize = pageSize;
		prepareData();
	}

	private void prepareData() {
		this.data = getPagedData(offset, pageSize);
		offset = offset + pageSize;
	}
	
	public boolean hasNext(){
		if(data.hasNext()){
			return true;
		} else {
			prepareData();
			if(data != null){
				return data.hasNext();
			}
		}
		return false;
	}
	
	public T2 next(){
		return data.next();
	}
	
}
