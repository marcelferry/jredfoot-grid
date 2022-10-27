package java.io;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jppf.node.JPPFClassLoader;

public class UTGridClassLoader extends ClassLoader {

	/**
	 * Logger for this class.
	 */
	private static Log log = LogFactory.getLog(JPPFClassLoader.class);
	/**
	 * Determines whether the debug level is enabled in the log configuration,
	 * without the cost of a method call.
	 */
	private static boolean debugEnabled = log.isDebugEnabled();

	/**
	 * Find a class in this class loader's classpath.
	 * 
	 * @param name
	 *            binary name of the resource to find.
	 * @return a defined <code>Class</code> instance.
	 * @throws ClassNotFoundException
	 *             if the class could not be loaded.
	 * @see java.lang.ClassLoader#findClass(java.lang.String)
	 */
	public Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			//lock.lock();
			int i = name.lastIndexOf('.');
			if (i >= 0) {
				String pkgName = name.substring(0, i);
				Package pkg = getPackage(pkgName);
				if (pkg == null) {
					definePackage(pkgName, null, null, null, null, null, null,
							null);
				}
			}
			if (debugEnabled)
				log.debug("looking up definition for resource [" + name + "]");
			byte[] b = null;
			String resName = name.replace('.', '/') + ".class";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", resName);
			JPPFResourceWrapper resource = null; //loadResourceData(map, false);
			b = null;//resource.getDefinition();
			if ((b == null) || (b.length == 0)) {
				if (debugEnabled)
					log.debug("definition for resource [" + name
							+ "] not found");
				throw new ClassNotFoundException("Could not load class '"
						+ name + "'");
			}
			if (debugEnabled)
				log.debug("found definition for resource [" + name + "]");
			return defineClass(name, b, 0, b.length);
		} finally {
			//lock.unlock();
		}
	}

	/**
	 * Finds the resource with the specified name. The resource lookup order is
	 * the same as the one specified by {@link #getResourceAsStream(String)}
	 * 
	 * @param name
	 *            the name of the resource to find.
	 * @return the URL of the resource.
	 * @see java.lang.ClassLoader#findResource(java.lang.String)
	 */
	public URL findResource(String name) {
		URL url = null;
		if (debugEnabled)
			log.debug("resource [" + name
					+ "] not found locally, attempting remote lookup");
		try {
			Enumeration<URL> urlEnum = findResources(name);
			if ((urlEnum != null) && urlEnum.hasMoreElements())
				url = urlEnum.nextElement();
		} catch (IOException e) {
		} finally {
			// lock.unlock();
		}
		if (debugEnabled)
			log.debug("resource [" + name + "] " + (url == null ? "not " : "")
					+ "found remotely");
		return url;
	}

	/**
	 * Get a stream from a resource file accessible form this class loader. The
	 * lookup order is defined as follows:
	 * <ul>
	 * <li>locally, in the classpath for this class loader, such as specified by
	 * {@link java.lang.ClassLoader#getResourceAsStream(java.lang.String)
	 * ClassLoader.getResourceAsStream(String)}<br>
	 * <li>if the parent of this class loader is NOT an instance of
	 * {@link JPPFClassLoader}, in the classpath of the <i>JPPF driver</i>, such
	 * as specified by
	 * {@link org.jppf.classloader.ResourceProvider#getResourceAsBytes(java.lang.String, java.lang.ClassLoader)
	 * ResourceProvider.getResourceAsBytes(String, ClassLoader)}</li>
	 * (the search may eventually be sped up by looking up the driver's resource
	 * cache first)</li>
	 * <li>if the parent of this class loader IS an instance of
	 * {@link JPPFClassLoader}, in the <i>classpath of the JPPF client</i>, such
	 * as specified by
	 * {@link org.jppf.classloader.ResourceProvider#getResourceAsBytes(java.lang.String, java.lang.ClassLoader)
	 * ResourceProvider.getResourceAsBytes(String, ClassLoader)} (the search may
	 * eventually be sped up by looking up the driver's resource cache first)</li>
	 * </ul>
	 * 
	 * @param name
	 *            name of the resource to obtain a stream from.
	 * @return an <code>InputStream</code> instance, or null if the resource was
	 *         not found.
	 * @see java.lang.ClassLoader#getResourceAsStream(java.lang.String)
	 */
	public InputStream getResourceAsStream(String name) {
		InputStream is = getClass().getClassLoader().getResourceAsStream(name);
		// if ((is == null) && !loading.get())
		if (is == null) {
			if (debugEnabled)
				log.debug("resource [" + name
						+ "] not found locally, attempting remote lookup");
			try {
				//lock.lock();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", name);
				//JPPFResourceWrapper resource = loadResourceData(map, true);
				byte[] b = null;//resource.getDefinition();
				boolean found = (b != null) && (b.length > 0);
				if (debugEnabled)
					log.debug("resource [" + name + "] "
							+ (found ? "" : "not ") + "found remotely");
				if (!found)
					return null;
				is = new ByteArrayInputStream(b);
				throw new ClassNotFoundException();
			} catch (ClassNotFoundException e) {
				if (debugEnabled)
					log.debug("resource [" + name + "] not found remotely");
				return null;
			} finally {
				//lock.unlock();
			}
		}
		return is;
	}

	/**
	 * Find all resources with the specified name.
	 * 
	 * @param name
	 *            - name of the resources to find in the clas loader's
	 *            classpath.
	 * @return An enumeration of URLs pointing to the resources found.
	 * @throws IOException
	 *             if an error occurs.
	 * @see java.lang.ClassLoader#findResources(java.lang.String)
	 */
	public Enumeration<URL> findResources(String name) throws IOException {
		List<URL> urlList = null;
		// if (loading.get()) return null;
		if (debugEnabled)
			log.debug("resource [" + name
					+ "] not found locally, attempting remote lookup");
		try {
			//lock.lock();
			List<String> locationsList = null;//cache.getResourcesLocations(name);
			if (locationsList == null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", name);
				map.put("multiple", "true");
				JPPFResourceWrapper resource = null;//loadResourceData(map, true);
				List<byte[]> dataList = null;//(List<byte[]>) resource
						//.getData("resource_list");
				boolean found = (dataList != null) && !dataList.isEmpty();
				if (debugEnabled)
					log.debug("resource [" + name + "] "
							+ (found ? "" : "not ") + "found remotely");
				if (found) {
					//cache.registerResources(name, dataList);
					urlList = new ArrayList<URL>();
					locationsList = null;//cache.getResourcesLocations(name);
				}
			}
			if (locationsList != null) {
				for (String path : locationsList) {
					File file = new File(path);
					urlList.add(file.toURI().toURL());
				}
				if (debugEnabled)
					log.debug("found the following URLs for resource [" + name
							+ "] : " + urlList);
			}
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			if (debugEnabled)
				log.debug("resource [" + name + "] not found remotely");
		} finally {
			//lock.unlock();
		}
		return urlList == null ? null : null ; //new IteratorEnumeration(urlList
				//.iterator());
	}

	public URL getResource(String name) {
		URL url;
		if (null != null) {
			url = null;//parent.getResource(name);
		} else {
			url = null;//getBootstrapResource(name);
		}
		if (url == null) {
			url = findResource(name);
		}
		return url;
	}

}
