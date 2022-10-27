package org.jredfoot.utgrid.portal.persistence.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jredfoot.utgrid.common.persistence.entities.GridEntity;
import org.jredfoot.utgrid.common.vo.Group;

@Entity
@Table(name="GRP")
public class GroupEntity extends GridEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4256387645864242315L;
	
	
	@Column(name="name")
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	
	@Override
	public String toString() {
		return this.name;
	}
	
	public GroupEntity(){
		
	}
	
	public GroupEntity(Group g){
		this.id = g.getId();
		this.name = g.getName();
	}
	
	public Group getDTO(){
		Group g = new Group();
		
		g.setId(this.id);
		g.setName(this.name);
		
		return g;
	}
	
	public static List<Group> getDTOList(List<GroupEntity> list ){
		List<Group> lista = new ArrayList<Group>();
		for(GroupEntity vo : list){
			Group g = vo.getDTO();
			lista.add(g);
		}
		return lista;
	}
}
