package org.jredfoot.utgrid.utsgd.persistence.dao.jpaimpl;

import javax.persistence.EntityManager;

import org.jredfoot.utgrid.common.persistence.dao.impl.GenericDAOImpl;
import org.jredfoot.utgrid.utsgd.persistence.EntityManagerHelper;
import org.jredfoot.utgrid.utsgd.persistence.dao.GridProjectDAO;
import org.jredfoot.utgrid.utsgd.persistence.entities.GridProjectEntity;

@SuppressWarnings("serial")
public class GridProjectDAOImpl extends GenericDAOImpl<GridProjectEntity, Long> implements GridProjectDAO {

	
	public GridProjectDAOImpl(EntityManager em) {
		this.entityManager = em;
	}	

	@Override
	public EntityManager createEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}
}
