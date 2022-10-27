package org.jredfoot.utgrid.portal.persistence.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.jredfoot.utgrid.common.persistence.entities.GridEntity;
import org.jredfoot.utgrid.common.vo.User;
import org.jredfoot.utgrid.common.vo.UserState;

@Entity
@Table(name="USR")
public class UserEntity extends GridEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6829898609906443430L;
	
	@Column(name="username")
	private String username;
	
	@Column(name="name")
	private String name;
	
	@Column(name="utsaa_id")
	private Long idUtsaa;
		
	@ManyToOne
	@JoinColumn(name="ogzu_id", referencedColumnName="id" )
	private OrganizationEntity organization;
	
	@ManyToOne
	@JoinColumn(name="grp_id", referencedColumnName="id" )
	private GroupEntity group;
	
	@Column(name="password")
	private String password;
	
	@Column(name="lastlogin")
	private Date lastLogin;
		
	@Column(name="status")
	private UserState status;	
	
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
	 * @return the idUtsaa
	 */
	public Long getIdUtsaa() {
		return idUtsaa;
	}

	/**
	 * @param idUtsaa the idUtsaa to set
	 */
	public void setIdUtsaa(Long idUtsaa) {
		this.idUtsaa = idUtsaa;
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
	 * @return the organization
	 */
	public OrganizationEntity getOrganization() {
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(OrganizationEntity organization) {
		this.organization = organization;
	}

	/**
	 * @return the group
	 */
	public GroupEntity getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(GroupEntity group) {
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
	public Date getLastLogin() {
		return lastLogin;
	}

	/**
	 * @param lastLogin the lastLogin to set
	 */
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
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
	
	public UserEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public UserEntity(User user) {
		User u = user;
		this.setId(u.getId());
		this.setUsername(u.getUsername());
		this.setName(u.getName());
		this.setGroup( new GroupEntity( u.getGroup() ) );
		this.setOrganization( new OrganizationEntity( u.getOrganization() ) ); 
		this.setStatus(u.getStatus());
	}

	public User getDTO() {
		User vo = new User();
		
		
		vo.setId(this.getId());
		vo.setUsername(this.getUsername());
		vo.setName(this.getName());
		vo.setGroup( this.getGroup().getDTO()  );
		vo.setOrganization( this.getOrganization().getDTO() ); 
		this.setStatus( this.getStatus() );
		
		return vo;
	}

	public static List<User> getDTOList(List<UserEntity> list) {
		List<User> retorno = new ArrayList<User>();
		for(UserEntity entity : list){
			retorno.add(entity.getDTO());
		}
		return retorno;
	}
}



