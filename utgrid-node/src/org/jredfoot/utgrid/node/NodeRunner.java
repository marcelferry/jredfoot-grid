package org.jredfoot.utgrid.node;

import java.security.AccessController;
import java.security.PrivilegedAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jredfoot.utgrid.node.classloader.GridClassLoader;

/**
 * Bootstrap class for lauching a JPPF node. The node class is dynamically loaded from a remote server.
 * @author Laurent Cohen
 */
public class NodeRunner
{
	/**
	 * Logger for this class.
	 */
	private static Log log = LogFactory.getLog(NodeRunner.class);
	/**
	 * The ClassLoader used for loading the classes of the framework.
	 */
	private static GridClassLoader classLoader = null;
	
	public static void main(String...args)
	{
		NodeTray node;

		try {
			node = createNode();
			
			node.main(null);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static NodeTray createNode() throws Exception
	{
		try
		{
			Class clazz = getGridClassLoader().loadClass("org.jredfoot.utgrid.node.NodeTray");
			NodeTray node = (NodeTray) clazz.newInstance();
			return node;
		}
		catch(Exception e)
		{
			throw e;
		}
	}


	public static GridClassLoader getGridClassLoader()
	{
		if (classLoader == null)
		{
			classLoader = AccessController.doPrivileged(new PrivilegedAction<GridClassLoader>()
			{
				public GridClassLoader run()
				{
					GridClassLoader cl = new GridClassLoader(NodeRunner.class.getClassLoader());
					return cl;
				}
			});
			Thread.currentThread().setContextClassLoader(classLoader);
		}
		return classLoader;
	}

}
