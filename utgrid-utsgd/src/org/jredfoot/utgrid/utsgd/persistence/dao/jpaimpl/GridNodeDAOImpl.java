package org.jredfoot.utgrid.utsgd.persistence.dao.jpaimpl;

import javax.persistence.EntityManager;

import org.jredfoot.utgrid.common.persistence.dao.impl.GenericDAOImpl;
import org.jredfoot.utgrid.utsgd.persistence.EntityManagerHelper;
import org.jredfoot.utgrid.utsgd.persistence.dao.GridNodeDAO;
import org.jredfoot.utgrid.utsgd.persistence.entities.GridNodeEntity;

@SuppressWarnings("serial")
public class GridNodeDAOImpl extends GenericDAOImpl<GridNodeEntity, Long> implements GridNodeDAO {

	
	public GridNodeDAOImpl(EntityManager em) {
		this.entityManager = em;
	}	

	@Override
	public EntityManager createEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}
}
