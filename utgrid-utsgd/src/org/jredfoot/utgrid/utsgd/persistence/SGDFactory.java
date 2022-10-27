package org.jredfoot.utgrid.utsgd.persistence;

import javax.persistence.EntityManager;

import org.jredfoot.utgrid.utsgd.persistence.dao.GridNodeDAO;
import org.jredfoot.utgrid.utsgd.persistence.dao.GridTaskDAO;
import org.jredfoot.utgrid.utsgd.persistence.dao.jpaimpl.GridNodeDAOImpl;
import org.jredfoot.utgrid.utsgd.persistence.dao.jpaimpl.GridTaskDAOImpl;

public class SGDFactory {
	
	private static SGDFactory INSTANCE;
	

	private SGDFactory(){
		
	}
	
	public static SGDFactory getInstance() {
		if(INSTANCE == null){
			INSTANCE = new SGDFactory();
		}
		return INSTANCE;
	}
	
	public GridNodeDAO getGridNodeDAO(){
		EntityManager em = EntityManagerHelper.getEntityManager();
		return new GridNodeDAOImpl(em);
	}
	
	public GridTaskDAO getGridTaskDAO(){
		EntityManager em = EntityManagerHelper.getEntityManager();
		return new GridTaskDAOImpl(em);
	}


}
