package org.jredfoot.utgrid.utsaa.persistence.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.jredfoot.utgrid.common.persistence.entities.GridEntity;
import org.jredfoot.utgrid.common.vo.Role;
import org.jredfoot.utgrid.common.vo.User;
import org.jredfoot.utgrid.common.vo.UserState;

@Entity
@Table(name="USR")
public class UserEntity extends GridEntity<Long> {
		
	@Column(name="username")
	private String username;
		
	@Column(name="email")
	private String email;
					
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="USR_ROLES", 
    joinColumns=@JoinColumn(name="USER_ID"),
    inverseJoinColumns=@JoinColumn(name="ROLE_ID")) 
	private List<RoleEntity> roles;
	
	@Column(name="status")
	private UserState status;
	
	@Column(name="certificate")
	String certificate;
	

	
	public UserEntity() {
		// TODO Auto-generated constructor stub
	}
	
	


	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the roles
	 */
	public List<RoleEntity> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<RoleEntity> roles) {
		this.roles = roles;
	}

	/**
	 * @return the status
	 */
	public UserState getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(UserState status) {
		this.status = status;
	}

	/**
	 * @return the certificate
	 */
	public String getCertificate() {
		return certificate;
	}

	/**
	 * @param certificate the certificate to set
	 */
	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UserEntity))
			return false;
		UserEntity other = (UserEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	
	public UserEntity(User user) {
		User u = user;
		this.setId(u.getId());
		this.setUsername(u.getUsername());
		this.setEmail(u.getEmail());
		this.setCertificate(u.getCertificate());
		this.setStatus(u.getStatus());
		if(u.getRoles() != null && u.getRoles().size() > 0){
			this.setRoles(new ArrayList<RoleEntity>());
			for(Role role : u.getRoles()){
				RoleEntity rol = new RoleEntity();
				rol.setId(role.getId());
				rol.setName(role.getName());
				rol.setAdmin(role.getAdmin());
				this.getRoles().add(rol);
			}
		}
	}
	
	public static List<User> getDTOList(List<UserEntity> lista) {
		List<User> retorno = new ArrayList<User>();
		for(UserEntity entity : lista){
			retorno.add(getDTO(entity));
		}
		return retorno;
	}

	public static User getDTO(UserEntity entity) {
		User user = new User();
		UserEntity u = entity;
		user.setId(u.getId());
		user.setUsername(u.getUsername());
		user.setEmail(u.getEmail());
		user.setCertificate(u.getCertificate());
		user.setStatus(u.getStatus());
		if(u.getRoles() != null && u.getRoles().size() > 0){
			user.setRoles(new ArrayList<Role>());
			for(RoleEntity role : u.getRoles()){
				Role rol = new Role();
				rol.setId(role.getId());
				rol.setName(role.getName());
				rol.setAdmin(role.getAdmin());
				user.getRoles().add(rol);
			}
		}
		
		return user;
	}
	
}



