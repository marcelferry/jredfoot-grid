package org.jredfoot.utgrid.utsgd.persistence.entities;

import java.util.List;

import org.jredfoot.utgrid.common.persistence.entities.GridEntity;
import org.jredfoot.utgrid.common.vo.Task;

public class GridTaskEntity extends GridEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5674626992178555982L;
	
	
	private String authorization;
	private String jobId;
	private Integer processCount;
	private Integer hostCount;
	private Long maxTime;
	private Double maxMemory;
	private Double minMemory;
	private GridProjectEntity project; 
	private String dir;
	/* Valid values are "single", "multiple", "condor", and "mpi". The default value is "multiple".  */
	private String jobType;
	private String stdin;
	private String stdout;
	private String stderr;
	private String libraryPath;
	private String env;
	private List<GridFileEntity> files;
	private String execute;
	
	public GridTaskEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public GridTaskEntity( Task task ) {
		// TODO Auto-generated constructor stub
	}

	
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
	public String getJobId() {
		return jobId;
	}
	/**
	 * @param jobId the jobId to set
	 */
	public void setJobId(String jobId) {
		this.jobId = jobId;
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
	public GridProjectEntity getProject() {
		return project;
	}
	/**
	 * @param project the project to set
	 */
	public void setProject(GridProjectEntity project) {
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
	public List<GridFileEntity> getFiles() {
		return files;
	}
	
	/**
	 * @param files the files to set
	 */
	public void setFiles(List<GridFileEntity> files) {
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
	public static List<Task> getDTOList(List<GridTaskEntity> lista) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Task getDTO(GridTaskEntity entity) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

}
