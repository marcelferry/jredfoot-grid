/*
 * @(#)FileInputStream.java	1.68 06/04/07
 *
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package java.io;

public class FileInputStream extends InputStream {
	
	private FileDescriptor fd;

	protected byte buf[];

	protected int pos;

	protected int mark = 0;

	protected int count;

	public FileInputStream(String name) throws FileNotFoundException {
		this(name != null ? new File(name) : null);
	}

	public FileInputStream(File file) throws FileNotFoundException {
		String name = (file != null ? file.getPath() : null);
		if (name == null) {
			throw new NullPointerException();
		}
		fd = new FileDescriptor();
		
		this.buf = open(name);
        this.pos = 0;
        this.count = buf.length;
	}

	public FileInputStream(FileDescriptor fdObj) {
		SecurityManager security = System.getSecurityManager();
		if (fdObj == null) {
			throw new NullPointerException();
		}
		if (security != null) {
			security.checkRead(fdObj);
		}
		fd = fdObj;		
	}

	private byte[] open(String name) throws FileNotFoundException{
		//getClass().getClassLoader().find
		return null;
	}

	public synchronized int read() {
		return (pos < count) ? (buf[pos++] & 0xff) : -1;
	}

	public synchronized int read(byte b[], int off, int len) {
		if (b == null) {
			throw new NullPointerException();
		} else if (off < 0 || len < 0 || len > b.length - off) {
			throw new IndexOutOfBoundsException();
		}
		if (pos >= count) {
			return -1;
		}
		if (pos + len > count) {
			len = count - pos;
		}
		if (len <= 0) {
			return 0;
		}
		System.arraycopy(buf, pos, b, off, len);
		pos += len;
		return len;
	}

	public synchronized long skip(long n) {
		if (pos + n > count) {
			n = count - pos;
		}
		if (n < 0) {
			return 0;
		}
		pos += n;
		return n;
	}

	public synchronized int available() {
		return count - pos;
	}

	public void close() throws IOException {
	}

	public final FileDescriptor getFD() throws IOException {
		if (fd != null)
			return fd;
		throw new IOException();
	}

	protected void finalize() throws IOException {
		if (fd != null) {
			if (fd != fd.in) {
				close();
			}
		}
	}
}
