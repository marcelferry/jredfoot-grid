package org.jredfoot.utgrid.utsgd.persistence.dao.jpaimpl;

import javax.persistence.EntityManager;

import org.jredfoot.utgrid.common.persistence.dao.impl.GenericDAOImpl;
import org.jredfoot.utgrid.utsgd.persistence.EntityManagerHelper;
import org.jredfoot.utgrid.utsgd.persistence.dao.GridOrganizationDAO;
import org.jredfoot.utgrid.utsgd.persistence.entities.GridOrganizationEntity;

@SuppressWarnings("serial")
public class GridOrganizationDAOImpl extends GenericDAOImpl<GridOrganizationEntity, Long> implements GridOrganizationDAO {

	
	public GridOrganizationDAOImpl(EntityManager em) {
		this.entityManager = em;
	}	

	@Override
	public EntityManager createEntityManager() {
		return EntityManagerHelper.getEntityManager();
	}
}
