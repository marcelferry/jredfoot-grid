package org.jredfoot.utgrid.utsaa.persistence;

import org.jredfoot.utgrid.utsaa.persistence.dao.UserDAO;
import org.jredfoot.utgrid.utsaa.persistence.dao.jpaimpl.UserDAOImpl;

public class SAAFactory {
	
	private static SAAFactory INSTANCE;
	

	private SAAFactory(){
		
	}
	
	public static SAAFactory getInstance() {
		if(INSTANCE == null){
			INSTANCE = new SAAFactory();
		}
		return INSTANCE;
	}
	
	public UserDAO getUserDAO(){
		return new UserDAOImpl();
	}

}
