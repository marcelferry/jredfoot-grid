package org.jredfoot.utgrid.tasks;

import java.util.Iterator;

public interface IDataProvider<T1> {
	boolean hasNext();
	T1 next();
	Iterator<T1> getPagedData(long offset, long pageSize);
}
