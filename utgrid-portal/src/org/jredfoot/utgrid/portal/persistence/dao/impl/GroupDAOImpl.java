package org.jredfoot.utgrid.portal.persistence.dao.impl;

import javax.persistence.EntityManager;

import org.jredfoot.utgrid.common.persistence.dao.impl.GenericDAOImpl;
import org.jredfoot.utgrid.portal.persistence.EntityManagerHelper;
import org.jredfoot.utgrid.portal.persistence.dao.GroupDAO;
import org.jredfoot.utgrid.portal.persistence.entities.GroupEntity;

@SuppressWarnings("serial")
public class GroupDAOImpl 
		extends GenericDAOImpl<GroupEntity, Long> 
		implements GroupDAO {

	Class<GroupEntity> domainClass = GroupEntity.class;
	
	
	
	@Override
	public Class<GroupEntity> getDomainClass() {
		return domainClass;
	}



	@Override
	public EntityManager createEntityManager() {
		return EntityManagerHelper.get().createEntityManager();
	}
    
}
