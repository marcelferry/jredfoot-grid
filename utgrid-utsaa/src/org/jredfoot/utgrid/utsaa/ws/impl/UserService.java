package org.jredfoot.utgrid.utsaa.ws.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.xml.ws.soap.Addressing;

import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;
import org.jredfoot.utgrid.common.vo.User;
import org.jredfoot.utgrid.utsaa.persistence.SAAFactory;
import org.jredfoot.utgrid.utsaa.persistence.dao.UserDAO;
import org.jredfoot.utgrid.utsaa.persistence.entities.UserEntity;
import org.jredfoot.utgrid.utsaa.ws.IUserService;


@WebService(
		serviceName="UserService",
		portName="UserServicePort",
		endpointInterface="org.jredfoot.utgrid.utsaa.ws.IUserService",
		targetNamespace="http://ws.utsaa.utgrid.jredfoot.org"
	)
@Addressing(enabled=true, required=false)
@InInterceptors(interceptors = { "org.jredfoot.utgrid.common.ws.cfx.WSSecurityInterceptor","org.apache.cxf.interceptor.LoggingInInterceptor"})
@OutInterceptors(interceptors = { "org.apache.cxf.interceptor.LoggingOutInterceptor"})
public class UserService implements IUserService {

	public List<User> listUsers(){
		UserDAO dao = SAAFactory.getInstance().getUserDAO();
		List<UserEntity> lista = dao.list();
		List<User> retorno = UserEntity.getDTOList(lista);
		if(retorno.size() == 0){
			retorno = new ArrayList<User>();
		}
		return retorno;
	}
	
	public User getUser(String username){
		UserDAO dao = SAAFactory.getInstance().getUserDAO();
		List<UserEntity> lista = dao.findByProperty("username", username);
		List<User> retorno = UserEntity.getDTOList(lista);
		if(retorno.size() == 0){
			retorno = new ArrayList<User>();
		}
		return retorno.get(0);
	}

	
	public User registerUser(User user){
		UserDAO dao = SAAFactory.getInstance().getUserDAO();
		UserEntity entity = new UserEntity( user );
		dao.persist( entity );
		return UserEntity.getDTO(entity);
	}

	public boolean updateUser(User user){
		UserDAO dao = SAAFactory.getInstance().getUserDAO();
		dao.persist( new UserEntity( user ));
		return true;
	}

	public boolean deregisterUser(User user){
		UserDAO dao = SAAFactory.getInstance().getUserDAO();
		dao.remove( new UserEntity( user ));
		return true;
	}
}
