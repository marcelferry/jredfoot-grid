package org.jredfoot.utgrid.utsgd.persistence.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.jredfoot.utgrid.common.node.GridStorage;
import org.jredfoot.utgrid.common.persistence.entities.GridEntity;

@Entity
@Table(name="GRID_STORAGES")
public class GridStorageEntity extends GridEntity<Long> {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7787938057234774734L;
	
	@ManyToOne
	@JoinColumn(name="node_id", referencedColumnName="id")
	private GridNodeEntity gridNode;
	@Column(name="storage_path")
	private String storagePath;
	
	@Column(name="storage_used")
	private Long storageUsed;
	@Column(name="storage_avail" )
	private Long storageAvail;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="register_date")
	private Date registerDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_date")
	private Date updateDate;
	@Column(name="status")
	private String status;

	public GridNodeEntity getGridNode() {
		return gridNode;
	}
	public void setGridNode(GridNodeEntity gridNode) {
		this.gridNode = gridNode;
	}
	public String getStoragePath() {
		return storagePath;
	}
	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}
	public Long getStorageUsed() {
		return storageUsed;
	}
	public void setStorageUsed(Long storageUsed) {
		this.storageUsed = storageUsed;
	}
	public Long getStorageAvail() {
		return storageAvail;
	}
	public void setStorageAvail(Long storageAvail) {
		this.storageAvail = storageAvail;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public static GridStorage getDTO(GridStorageEntity entity){
		GridStorage gs = new GridStorage();
		gs.setStorageAvail(entity.getStorageAvail());
		gs.setStoragePath(entity.getStoragePath());
		gs.setStorageUsed(entity.getStorageUsed());
		return gs;
	}

	
}