package org.jredfoot.utgrid.tasks;

import org.jredfoot.utgrid.common.exception.GridExpectedException;

public abstract class Task implements ITask {

	/** Task ID */
	protected String tid;
	/** Tempo de vida a partir do createTime */
	protected long lifetime;
	/** Data/Hora de criação */
	protected long createTime;
	/** Resultado */
	protected Result result;
	/** Exceção esperada */
	protected GridExpectedException exception;
	/** Conjunto de dados utilizado pela Task */
	protected LocalDataPackage data;

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public long getLifetime() {
		return lifetime;
	}

	public void setLifetime(long lifetime) {
		this.lifetime = lifetime;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public GridExpectedException getException() {
		return exception;
	}

	public void setException(GridExpectedException exception) {
		this.exception = exception;
	}
}
