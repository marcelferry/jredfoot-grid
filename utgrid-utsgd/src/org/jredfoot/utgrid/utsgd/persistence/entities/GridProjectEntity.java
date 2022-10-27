package org.jredfoot.utgrid.utsgd.persistence.entities;

import org.jredfoot.utgrid.common.persistence.entities.GridEntity;

public class GridProjectEntity extends GridEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6575579667111242740L;
	
	private String name;
	
	private GridOrganizationEntity organization;
	
	private GridGroupEntity group;

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
	public GridOrganizationEntity getOrganization() {
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(GridOrganizationEntity organization) {
		this.organization = organization;
	}

	/**
	 * @return the group
	 */
	public GridGroupEntity getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(GridGroupEntity group) {
		this.group = group;
	}
	
	

}
