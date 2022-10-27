package primos;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jredfoot.utgrid.tasks.DataProvider;
import org.jredfoot.utgrid.tasks.IDataProvider;


public class ArquivoEntradaDataProvider extends DataProvider<File, Long> {
	
	public static final long PAGE_SIZE = 1000;

	public ArquivoEntradaDataProvider(File in) {
		super(in, PAGE_SIZE);
	}
	
	public ArquivoEntradaDataProvider(String arquivo) {
		super(new File(arquivo), PAGE_SIZE);				
	}

	@Override
	public Iterator<Long> getPagedData(long offset, long pageSize) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(dataIn);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int i;
			while(( i = fis.read()) != -1 ){
				baos.write(i);
			}		
		
			// TODO Auto-generated method stub
			ArrayList<Long> array = new ArrayList<Long>();		
			StringReader sr = new StringReader(new String(baos.toByteArray()));
			BufferedReader br = new BufferedReader(sr);
			String linha;
			List<Long> lista = new ArrayList<Long>();		
	
			while((linha = br.readLine()) != null){
				//System.out.println( linha );
				lista.add(new Long(linha));
			}
				return lista.iterator();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		return null;
	}
}
