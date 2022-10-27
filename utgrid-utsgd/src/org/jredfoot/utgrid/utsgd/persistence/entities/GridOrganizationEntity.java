package org.jredfoot.utgrid.utsgd.persistence.entities;

import org.jredfoot.utgrid.common.persistence.entities.GridEntity;

public class GridOrganizationEntity extends GridEntity<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1687194903859586625L;
	private String name;

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
	
}
