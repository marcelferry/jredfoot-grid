package org.jredfoot.utgrid.common.vo;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
	
	/** Identificador do usuario. */
	Long id;
	/** Nome de usuario, unico, que permite o login na aplicacao web. */
	String username;
	/** Nome completo do usuario */
	String name;
	/** Endereco de e-mail do usuario. */
	String email;
	/** Objeto que define a qual organizacao o usuario pertence. */
	Organization organization;
	/** Objeto que define a qual organizacao o usuario pertence. */
	Group group;	
	/** Senha do usuario criptografada. */
	String password;
	/** Data do ultimo login por extenso. */
	String lastLogin;
	/** Lista de alcadas aos quais o usuario pertence. */
	List<Role> roles;
	/** Estado atual do usuario */
	UserState status;
	/** Resultado da autenticacao do usuario. Repassar a mensagem atraves do objeto mensagem geral. */
	AuthState auth;
	/** Chave publica do usuario. Enviada apenas no cadastro ou alteracao. */
	String certificate;
	

	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the user
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param user the user to set
	 */
	public void setUsername(String user) {
		this.username = user;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the organization
	 */
	public Organization getOrganization() {
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	/**
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the lastLogin
	 */
	public String getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param lastLogin the lastLogin to set
	 */
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * @return the roles
	 */
	public List<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<Role> roles) {
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
	 * @return the auth
	 */
	public AuthState getAuth() {
		return auth;
	}

	/**
	 * @param auth the auth to set
	 */
	public void setAuth(AuthState auth) {
		this.auth = auth;
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
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
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
	
	
	
}



