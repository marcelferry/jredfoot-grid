package org.jredfoot.utgrid.tasks;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LocalDataPackage<T1> implements Serializable {
	private TreeMap<Long, Map<String, T1>> data = new TreeMap<Long, Map<String,T1>>();
	private Long size = 0L;
	private Long pos = 0L;
	
	public long size(){
		return size + 1;
	}
	
	public long getCurrentDataPosition(){
		return pos;
	}
	
	public void setCurrentDataPosition(long pos){
		if(pos <= size){
			this.pos = pos;
		} else {
			throw new IndexOutOfBoundsException();
		}
	}
	
	public void addNewData(){
		size++;
		pos = size;
		data.put(size, new HashMap<String, T1>());	
	}
	
	
	
}
