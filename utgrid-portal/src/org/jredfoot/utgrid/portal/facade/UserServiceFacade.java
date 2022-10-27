package org.jredfoot.utgrid.portal.facade;

import org.jredfoot.utgrid.common.vo.User;
import org.jredfoot.utgrid.utsaa.ws.client.UserService;

public class UserServiceFacade {

	public User registerUser(User current) {
		UserService us = new UserService();
		User u = us.getUserServicePort().registerUser(current);		
		return u;
	}

	public User getUser(String username) {
		UserService us = new UserService();
		User u = us.getUserServicePort().getUser(username);		
		return u;
	}
	
}
