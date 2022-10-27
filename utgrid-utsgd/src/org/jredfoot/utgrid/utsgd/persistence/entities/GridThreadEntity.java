package org.jredfoot.utgrid.utsgd.persistence.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.jredfoot.utgrid.common.node.GridThread;
import org.jredfoot.utgrid.common.persistence.entities.GridEntity;

@Entity
@Table(name="GRID_THREADS")
public class GridThreadEntity extends GridEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3331045207131104347L;
	
	@ManyToOne
	@JoinColumn(name="node_id", referencedColumnName="id")
	private GridNodeEntity gridNode;
	@Column(name="thread_id")
	private String threadID;
	@Column(name="thread_load")
	private Long threadLoad;
	@Column(name="thread_priority")
	private Long threadPriority;
	
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
	public String getThreadID() {
		return threadID;
	}
	public void setThreadID(String threadID) {
		this.threadID = threadID;
	}
	public Long getThreadLoad() {
		return threadLoad;
	}
	public void setThreadLoad(Long threadLoad) {
		this.threadLoad = threadLoad;
	}
	public Long getThreadPriority() {
		return threadPriority;
	}
	public void setThreadPriority(Long threadPriority) {
		this.threadPriority = threadPriority;
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
	
	public static GridThread getDTO(GridThreadEntity entity){
		GridThread thread = new GridThread();
		thread.setThreadID(entity.getThreadID());
		thread.setThreadLoad(entity.getThreadLoad());
		thread.setThreadPriority(entity.getThreadPriority());
		return thread;
	}
}