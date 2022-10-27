package org.jredfoot.utgrid.portal.persistence.dao.impl;

import javax.persistence.EntityManager;

import org.jredfoot.utgrid.common.persistence.dao.impl.GenericDAOImpl;
import org.jredfoot.utgrid.portal.persistence.EntityManagerHelper;
import org.jredfoot.utgrid.portal.persistence.dao.UserDAO;
import org.jredfoot.utgrid.portal.persistence.entities.UserEntity;

@SuppressWarnings("serial")
public class UserDAOImpl 
		extends GenericDAOImpl<UserEntity, Long> 
		implements UserDAO {

	@Override
	public EntityManager createEntityManager() {
		return EntityManagerHelper.get().createEntityManager();
	}
    
}
