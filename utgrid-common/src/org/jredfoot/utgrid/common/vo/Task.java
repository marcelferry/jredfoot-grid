package org.jredfoot.utgrid.common.vo;

import java.util.List;

import org.jredfoot.utgrid.common.persistence.entities.GridEntity;

public class Task {
	
	private String authorization;
	private String taskId;
	private Integer processCount;
	private Integer hostCount;
	private Long maxTime;
	private Double maxMemory;
	private Double minMemory;
	private Project project; 
	private String dir;
	private String jobType;
	private String stdin;
	private String stdout;
	private String stderr;
	private String libraryPath;
	private String env;
	private List<File> files;
	private String execute;
	
	/**
	 * @return the authorization
	 */
	public String getAuthorization() {
		return authorization;
	}
	/**
	 * @param authorization the authorization to set
	 */
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	/**
	 * @return the jobId
	 */
	public String getTaskId() {
		return taskId;
	}
	/**
	 * @param jobId the jobId to set
	 */
	public void setTaskId(String jobId) {
		this.taskId = jobId;
	}
	/**
	 * @return the processCount
	 */
	public Integer getProcessCount() {
		return processCount;
	}
	/**
	 * @param processCount the processCount to set
	 */
	public void setProcessCount(Integer processCount) {
		this.processCount = processCount;
	}
	/**
	 * @return the hostCount
	 */
	public Integer getHostCount() {
		return hostCount;
	}
	/**
	 * @param hostCount the hostCount to set
	 */
	public void setHostCount(Integer hostCount) {
		this.hostCount = hostCount;
	}
	/**
	 * @return the maxTime
	 */
	public Long getMaxTime() {
		return maxTime;
	}
	/**
	 * @param maxTime the maxTime to set
	 */
	public void setMaxTime(Long maxTime) {
		this.maxTime = maxTime;
	}
	/**
	 * @return the maxMemory
	 */
	public Double getMaxMemory() {
		return maxMemory;
	}
	/**
	 * @param maxMemory the maxMemory to set
	 */
	public void setMaxMemory(Double maxMemory) {
		this.maxMemory = maxMemory;
	}
	/**
	 * @return the minMemory
	 */
	public Double getMinMemory() {
		return minMemory;
	}
	/**
	 * @param minMemory the minMemory to set
	 */
	public void setMinMemory(Double minMemory) {
		this.minMemory = minMemory;
	}
	/**
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}
	/**
	 * @param project the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}
	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}
	/**
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}
	/**
	 * @return the jobType
	 */
	public String getJobType() {
		return jobType;
	}
	/**
	 * @param jobType the jobType to set
	 */
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	/**
	 * @return the stdin
	 */
	public String getStdin() {
		return stdin;
	}
	/**
	 * @param stdin the stdin to set
	 */
	public void setStdin(String stdin) {
		this.stdin = stdin;
	}
	/**
	 * @return the stdout
	 */
	public String getStdout() {
		return stdout;
	}
	/**
	 * @param stdout the stdout to set
	 */
	public void setStdout(String stdout) {
		this.stdout = stdout;
	}
	/**
	 * @return the stderr
	 */
	public String getStderr() {
		return stderr;
	}
	/**
	 * @param stderr the stderr to set
	 */
	public void setStderr(String stderr) {
		this.stderr = stderr;
	}
	
	/**
	 * @return the libraryPath
	 */
	public String getLibraryPath() {
		return libraryPath;
	}
	
	/**
	 * @param libraryPath the libraryPath to set
	 */
	public void setLibraryPath(String libraryPath) {
		this.libraryPath = libraryPath;
	}
	
	/**
	 * @return the env
	 */
	public String getEnv() {
		return env;
	}
	
	/**
	 * @param env the env to set
	 */
	public void setEnv(String env) {
		this.env = env;
	}
	
	/**
	 * @return the files
	 */
	public List<File> getFiles() {
		return files;
	}
	
	/**
	 * @param files the files to set
	 */
	public void setFiles(List<File> files) {
		this.files = files;
	}
	
	/**
	 * @return the execute
	 */
	public String getExecute() {
		return execute;
	}
	
	/**
	 * @param execute the execute to set
	 */
	public void setExecute(String execute) {
		this.execute = execute;
	}
	
	

}
