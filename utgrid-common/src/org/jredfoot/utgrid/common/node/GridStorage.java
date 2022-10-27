package org.jredfoot.utgrid.common.node;

public class GridStorage{
	
	private Long storageId;
	private String storagePath;
	private Long storageUsed;
	private Long storageAvail;
	
	public Long getStorageId() {
		return storageId;
	}
	public void setStorageId(Long storageId) {
		this.storageId = storageId;
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
}