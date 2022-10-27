package org.jredfoot.utgrid.common.vo;

public class File {
	
	private String path;
	private char type;
	private char writeable; 
	private byte[] content;
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the type
	 */
	public char getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(char type) {
		this.type = type;
	}
	/**
	 * @return the writeable
	 */
	public char getWriteable() {
		return writeable;
	}
	/**
	 * @param writeable the writeable to set
	 */
	public void setWriteable(char writeable) {
		this.writeable = writeable;
	}
	/**
	 * @return the content
	 */
	public byte[] getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(byte[] content) {
		this.content = content;
	}
	
	
	
}
