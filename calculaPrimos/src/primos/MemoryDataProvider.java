package primos;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jredfoot.utgrid.tasks.DataProvider;


public class MemoryDataProvider extends DataProvider<Long, Long> {
	

	public MemoryDataProvider(Long in) {
		super(in, 1000);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Iterator<Long> getPagedData(long offset, long pageSize) {
		List<Long> array = new ArrayList<Long>();		
		long termino = offset + pageSize;
		
		if(termino > dataIn){
			termino = dataIn;
		}		
		for(long i = offset + 1; i <= termino ; i++){
			array.add(new Long(i));
		}
		return array.iterator();
	}

}
