package org.jredfoot.utgrid.utsaa.persistence.dao.jpaimpl;

import javax.persistence.EntityManager;

import org.jredfoot.utgrid.common.persistence.dao.impl.GenericDAOImpl;
import org.jredfoot.utgrid.utsaa.persistence.dao.UserDAO;
import org.jredfoot.utgrid.utsaa.persistence.entities.UserEntity;
import org.jredfoot.utgrid.utsaa.utils.EntityManagerHelper;

@SuppressWarnings("serial")
public class UserDAOImpl 
		extends GenericDAOImpl<UserEntity, Long> 
		implements UserDAO {
	
	@Override
	public EntityManager createEntityManager() {
		return EntityManagerHelper.get().createEntityManager();
	}
    
}
