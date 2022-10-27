package org.jredfoot.utgrid.utsgd.persistence.dao.jpaimpl;

import javax.persistence.EntityManager;

import org.jredfoot.utgrid.common.persistence.dao.impl.GenericDAOImpl;
import org.jredfoot.utgrid.utsgd.persistence.EntityManagerHelper;
import org.jredfoot.utgrid.utsgd.persistence.dao.GridGroupDAO;
import org.jredfoot.utgrid.utsgd.persistence.entities.GridGroupEntity;

@SuppressWarnings("serial")
public class GridGroupDAOImpl extends GenericDAOImpl<GridGroupEntity, Long> implements GridGroupDAO {

	
	public GridGroupDAOImpl(EntityManager em) {
		this.entityManager = em;
	}	

	@Override
	public EntityManager createEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}
}
