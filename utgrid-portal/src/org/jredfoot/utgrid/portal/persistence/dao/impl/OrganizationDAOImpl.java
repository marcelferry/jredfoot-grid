package org.jredfoot.utgrid.portal.persistence.dao.impl;

import javax.persistence.EntityManager;

import org.jredfoot.utgrid.common.persistence.dao.impl.GenericDAOImpl;
import org.jredfoot.utgrid.portal.persistence.EntityManagerHelper;
import org.jredfoot.utgrid.portal.persistence.dao.OrganizationDAO;
import org.jredfoot.utgrid.portal.persistence.entities.OrganizationEntity;

@SuppressWarnings("serial")
public class OrganizationDAOImpl 
		extends GenericDAOImpl<OrganizationEntity, Long> 
		implements OrganizationDAO {

	Class<OrganizationEntity> domainClass = OrganizationEntity.class;
	
	
	
	@Override
	public Class<OrganizationEntity> getDomainClass() {
		return domainClass;
	}



	@Override
	public EntityManager createEntityManager() {
		return EntityManagerHelper.get().createEntityManager();
	}
    
}
