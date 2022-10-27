package org.jredfoot.utgrid.common.persistence.entities;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class GridEntity<T> implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4922074832344327701L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected T id;
	
	public T getId(){
		return id;
	}
	
	public void setId(T id){
		this.id = id;
	}
}
