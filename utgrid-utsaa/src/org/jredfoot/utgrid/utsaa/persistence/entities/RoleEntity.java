package org.jredfoot.utgrid.utsaa.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jredfoot.utgrid.common.persistence.entities.GridEntity;

@Entity
@Table(name="role")
public class RoleEntity extends GridEntity<Long> {
	
	@Column(name="name")
	private String name;

	@Column(name="admin")
	private Boolean admin;

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
	 * @return the admin
	 */
	public Boolean getAdmin() {
		return admin;
	}

	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	
}
