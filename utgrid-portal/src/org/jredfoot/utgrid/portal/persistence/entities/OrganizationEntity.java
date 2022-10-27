package org.jredfoot.utgrid.portal.persistence.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jredfoot.utgrid.common.persistence.entities.GridEntity;
import org.jredfoot.utgrid.common.vo.Group;
import org.jredfoot.utgrid.common.vo.Organization;

@Entity
@Table(name="OGZU")
public class OrganizationEntity extends GridEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -379993349847433248L;
	
	@Column(name="name")
	private String name;
		
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public OrganizationEntity() {

	}
	
	public OrganizationEntity(Organization o){
		this.id = o.getId();
		this.name = o.getName();
	}
	
	public Organization getDTO() {
		Organization org = new Organization();
		org.setId(this.getId());
		org.setName(this.getName());
		return org;
	}
	
	public static List<Organization> getDTOList(List<OrganizationEntity> list) {
		List<Organization> lista = new ArrayList<Organization>();
		for(OrganizationEntity org : list ){
			lista.add(org.getDTO());
		}
		return lista;

	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
