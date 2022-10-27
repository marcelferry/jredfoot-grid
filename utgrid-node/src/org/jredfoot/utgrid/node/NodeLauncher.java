package org.jredfoot.utgrid.node;

import java.lang.reflect.Method;

import org.jredfoot.utgrid.node.classloader.GridClassLoader;


public class NodeLauncher
{
	/**
	 * Start this application, then the JPPF driver as a subprocess.
	 * @param args not used.
	 */
	public static void main(String...args)
	{
		try
		{
			GridClassLoader loader = new GridClassLoader(Thread.currentThread().getContextClassLoader());
			Class clazz = loader.loadClass("org.jredfoot.utgrid.node.NodeRunner");
			
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
		System.exit(0);
	}
}
