package org.jredfoot.utgrid.common.node;

import java.io.Serializable;


public class GridThread implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4336808360882395258L;
	
	private String threadID;
	private Long threadLoad;
	private Long threadPriority;
	
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
	
	
}