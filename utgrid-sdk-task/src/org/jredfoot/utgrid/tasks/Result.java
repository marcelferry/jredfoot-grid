package org.jredfoot.utgrid.tasks;

import org.jredfoot.utgrid.common.exception.GridException;

public class Result {

	public final static int SUCCESS = 0;
	public final static int ERROR = 1;
	public final static int STANDBY = 2;
	public final static int RUNNING = 3;
	
	private String tid;
	private String message;
	private GridException exception;
	private Object value;
	private byte[] content;
	private int status;
	
	public Result() {
		// TODO Auto-generated constructor stub
	}
	
	public Result(String tid, GridException exception) {
		this.tid = tid;
		this.exception = exception;
	}
	
	public Result(String tid, Object value, byte[] content, String message, int status) {
		this.tid = tid;
		this.value = value;
		this.content = content;
		this.message = message;
		this.status = status;
	}
	
	public Result(String tid, Object value, String message, int status) {
		this.tid = tid;
		this.value = value;
		this.message = message;
		this.status = status;
	}
	
	public Result(String tid, String message, int status) {
		this.tid = tid;
		this.message = message;
		this.status = status;
	}

	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public GridException getException() {
		return exception;
	}
	public void setException(GridException exception) {
		this.exception = exception;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
}
