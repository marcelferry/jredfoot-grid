package org.jredfoot.utgrid.utsgd.persistence.dao.jpaimpl;

import javax.persistence.EntityManager;

import org.jredfoot.utgrid.common.persistence.dao.impl.GenericDAOImpl;
import org.jredfoot.utgrid.utsgd.persistence.EntityManagerHelper;
import org.jredfoot.utgrid.utsgd.persistence.dao.GridTaskDAO;
import org.jredfoot.utgrid.utsgd.persistence.entities.GridTaskEntity;

@SuppressWarnings("serial")
public class GridTaskDAOImpl extends GenericDAOImpl<GridTaskEntity, Long> implements GridTaskDAO {

	
	public GridTaskDAOImpl(EntityManager em) {
		this.entityManager = em;
	}	

	@Override
	public EntityManager createEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}
}
