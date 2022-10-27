package org.jredfoot.utgrid.node.classloader;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.SecureClassLoader;

public class JavaRunnerLoader extends SecureClassLoader {
	protected URL urlBase;
	public boolean printLoadMessages = true;

	public JavaRunnerLoader(String base, ClassLoader parent) {
		super(parent);
		try {
			if (!(base.endsWith("/")))
				base = base + "/";
			urlBase = new URL(base);
		} catch (Exception e) {
			throw new IllegalArgumentException(base);
		}
	}

	byte[] getClassBytes(InputStream is) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		BufferedInputStream bis = new BufferedInputStream(is);
		boolean eof = false;
		while (!eof) {
			try {
				int i = bis.read();
				if (i == -1)
					eof = true;
				else
					baos.write(i);
			} catch (IOException e) {
				return null;
			}
		}
		return baos.toByteArray();
	}

	protected Class findClass(String name) {
		String urlName = name.replace('.', '/');
		byte buf[];
		Class cl;
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			int i = name.lastIndexOf('.');
			if (i >= 0)
				sm.checkPackageDefinition(name.substring(0, i));
		}
		try {
			URL url = new URL(urlBase, urlName + ".class");
			if (printLoadMessages)
				System.out.println("Loading " + url);
			InputStream is = url.openConnection().getInputStream();
			buf = getClassBytes(is);
			cl = defineClass(name, buf, 0, buf.length);
			return cl;
		} catch (Exception e) {
			System.out.println("Can't load " + name + ": " + e);
			return null;
		}
	}
}