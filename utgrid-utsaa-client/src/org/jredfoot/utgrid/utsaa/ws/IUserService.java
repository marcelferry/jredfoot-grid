package org.jredfoot.utgrid.utsaa.ws;

import java.util.List;

import javax.jws.WebService;

import org.jredfoot.utgrid.common.vo.User;

@WebService(
		name="UserPortType",
		targetNamespace="http://ws.utsaa.utgrid.jredfoot.org"
		)
public interface IUserService {
	List<User> listUsers();
	User getUser(String username);
	User registerUser(User user);
	boolean updateUser(User user);
	boolean deregisterUser(User user);
}