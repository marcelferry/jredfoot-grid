package org.jredfoot.utgrid.node.classloader;

import java.io.InputStream;
import java.net.URL;

public class MultiLoader extends JavaRunnerLoader {
	private static final String server = "support.xyz.com/";

	public MultiLoader(String url, ClassLoader parent) {
		super(url, parent);
	}

	protected Class findClass(String name) {
		URL codeURL;
		SecurityManager sm = System.getSecurityManager();
		if (sm != null) {
			int i = name.lastIndexOf('.');
			if (i >= 0)
				sm.checkPackageDefinition(name.substring(0, i));
		}
		try {
			String codeName = name.replace('.', '/') + ".class";
			if (name.startsWith("com.xyz.support"))
				codeURL = new URL("http://" + server + codeName);
			else
				codeURL = new URL(urlBase, codeName);
			if (printLoadMessages)
				System.out.println("Loading " + name);
			InputStream is = codeURL.openConnection().getInputStream();
			byte buf[] = getClassBytes(is);
			return defineClass(name, buf, 0, buf.length);
		} catch (Exception e) {
			return null;
		}
	}
}