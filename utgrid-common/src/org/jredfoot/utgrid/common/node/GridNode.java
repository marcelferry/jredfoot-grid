package org.jredfoot.utgrid.common.node;

import java.util.List;

public class GridNode{
	public static final String IDLE = "idle";
	public static final String DOWN = "down";
	public static final String RUNNING = "run";
	
	public static final String EXECUTION = "exec";
	public static final String STORAGE = "store";
	
	private Long nodeId;
	private String nodeName;
	private String nodeIP;
	private String nodeAddress;
	private String nodeType;
	private String nodeStatus;
	private String nodeHDSerial;
	private List<GridThread> threads;
	private List<GridStorage> storages;
	private List<GridNodeExecutionMetricsImpl> metrics;
	private GridNodeDetails details;
	 
	
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeIP() {
		return nodeIP;
	}
	public void setNodeIP(String nodeIP) {
		this.nodeIP = nodeIP;
	}
	public String getNodeAddress() {
		return nodeAddress;
	}
	public void setNodeAddress(String nodeAddress) {
		this.nodeAddress = nodeAddress;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public String getNodeStatus() {
		return nodeStatus;
	}
	public void setNodeStatus(String nodeStatus) {
		this.nodeStatus = nodeStatus;
	}
	public String getNodeHDSerial() {
		return nodeHDSerial;
	}
	public void setNodeHDSerial(String nodeHDSerial) {
		this.nodeHDSerial = nodeHDSerial;
	}
	public List<GridThread> getThreads() {
		return threads;
	}
	public void setThreads(List<GridThread> threads) {
		this.threads = threads;
	}
	public List<GridStorage> getStorages() {
		return storages;
	}
	public void setStorages(List<GridStorage> storages) {
		this.storages = storages;
	}
	public List<GridNodeExecutionMetricsImpl> getMetrics() {
		return metrics;
	}
	public void setMetrics(List<GridNodeExecutionMetricsImpl> metrics) {
		this.metrics = metrics;
	}
	public GridNodeDetails getDetails() {
		return details;
	}
	public void setDetails(GridNodeDetails details) {
		this.details = details;
	}
}
