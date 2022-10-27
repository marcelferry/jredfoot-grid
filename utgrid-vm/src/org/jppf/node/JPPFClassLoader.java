/*
 * JPPF.
 * Copyright (C) 2005-2010 JPPF Team.
 * http://www.jppf.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jppf.node;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.*;
import org.jppf.JPPFNodeReconnectionNotification;
import org.jppf.classloader.ResourceCache;
import org.jppf.comm.socket.*;
import org.jppf.data.transform.*;
import org.jppf.utils.*;

/**
 * This class is a custom class loader serving the purpose of dynamically loading the JPPF classes and the client
 * application classes, to avoid costly redeployment system-wide.
 * @author Laurent Cohen
 */
public class JPPFClassLoader extends URLClassLoader implements JPPFClassLoaderMBean
{
	/**
	 * Logger for this class.
	 */
	private static Log log = LogFactory.getLog(JPPFClassLoader.class);
	/**
	 * Determines whether the debug level is enabled in the log configuration, without the cost of a method call.
	 */
	private static boolean debugEnabled = log.isDebugEnabled();
	/**
	 * Wrapper for the underlying socket connection.
	 */
	private static SocketWrapper socketClient = null;
	/**
	 * Determines whether this class loader should handle dynamic class updating.
	 */
	private boolean dynamic = false;
	/**
	 * The unique identifier for the submitting application.
	 */
	private List<String> uuidPath = new ArrayList<String>();
	/**
	 * Used to synchronize access to the underlying socket from multiple threads.
	 */
	private static ReentrantLock lock = new ReentrantLock();
	/**
	 * Used to synchronize access to the underlying socket from multiple threads.
	 */
	private static SocketInitializer socketInitializer = new SocketInitializerImpl();
	/**
	 * Determines whether this class loader should handle dynamic class updating.
	 */
	private static AtomicBoolean initializing = new AtomicBoolean(false);
	/**
	 * Determines whether this class loader is currently loading resources from the network.
	 */
	private static AtomicBoolean loading = new AtomicBoolean(false);
	/**
	 * Uuid of the orignal task bundle that triggered a resource loading request. 
	 */
	private String requestUuid = null;
	/**
	 * The cache handling resources temporarily stored to file.
	 */
	private ResourceCache cache = new ResourceCache();

	/**
	 * Initialize this class loader with a parent class loader.
	 * @param parent a ClassLoader instance.
	 */
	public JPPFClassLoader(ClassLoader parent)
	{
		super(new URL[0], parent);
		if (parent instanceof JPPFClassLoader) dynamic = true;
		if (socketClient == null) init();
	}

	/**
	 * Initialize this class loader with a parent class loader.
	 * @param parent a ClassLoader instance.
	 * @param uuidPath unique identifier for the submitting application.
	 */
	public JPPFClassLoader(ClassLoader parent, List<String> uuidPath)
	{
		this(parent);
		this.uuidPath = uuidPath;
	}

	/**
	 * Initialize the underlying socket connection.
	 */
	private static void init()
	{
		if (!isInitializing())
		{
			try
			{
				lock.lock();
				if (debugEnabled) log.debug("initializing connection");
				setInitializing(true);
				System.out.println("JPPFClassLoader.init(): attempting connection to the class server");
				if (socketClient == null) initSocketClient();
				socketInitializer.initializeSocket(socketClient);
				if (!socketInitializer.isSuccessfull())
					throw new JPPFNodeReconnectionNotification("Could not reconnect to the driver");

				// we need to do this in order to dramatically simplify the state machine of ClassServer
				try
				{
					if (debugEnabled) log.debug("sending node initiation message");
					JPPFResourceWrapper resource = new JPPFResourceWrapper();
					resource.setState(JPPFResourceWrapper.State.NODE_INITIATION);
					ObjectSerializer serializer = socketClient.getSerializer();
					JPPFBuffer buf = serializer.serialize(resource);
					byte[] data = buf.getBuffer();
					data = JPPFDataTransformFactory.transform(true, data);
					socketClient.sendBytes(new JPPFBuffer(data, data.length));
					socketClient.flush();
					socketClient.receiveBytes(0);
					if (debugEnabled) log.debug("received node initiation response");
				}
				catch (IOException e)
				{
					throw new JPPFNodeReconnectionNotification("Could not reconnect to the driver", e);
				}
				catch (Exception e)
				{
					throw new RuntimeException(e);
				}
				System.out.println("JPPFClassLoader.init(): Reconnected to the class server");
			}
			finally
			{
				lock.unlock();
				setInitializing(false);
			}
		}
		else
		{
			if (debugEnabled) log.debug("waiting for end of connection initialization");
			// wait until initialization is over.
			try
			{
				lock.lock();
			}
			finally
			{
				lock.unlock();
			}
		}
	}
	
	/**
	 * Initialize the underlying socket connection.
	 */
	private static void initSocketClient()
	{
		if (debugEnabled) log.debug("initializing socket connection");
		TypedProperties props = JPPFConfiguration.getProperties();
		String host = props.getString("jppf.server.host", "localhost");
		int port = props.getInt("class.server.port", 11111);
		socketClient = new BootstrapSocketClient();
		socketClient.setHost(host);
		socketClient.setPort(port);
	}
	
	/**
	 * @param name the binary name of the class
	 * @return the resulting <tt>Class</tt> object
	 * @throws ClassNotFoundException if the class could not be found
	 */
	public synchronized Class<?> loadJPPFClass(String name) throws ClassNotFoundException
	{
		if (debugEnabled) log.debug("looking up resource [" + name + "]");
		Class<?> c = findLoadedClass(name);
		if (c == null)
		{
			ClassLoader parent = getParent();
			if (parent instanceof JPPFClassLoader) c = ((JPPFClassLoader) parent).findLoadedClass(name);
		}
		if (c == null)
		{
			if (debugEnabled) log.debug("resource [" + name + "] not already loaded");
			c = findClass(name);
		}
		if (debugEnabled) log.debug("definition for resource [" + name + "] : " + c);
		return c;
	}

	/**
	 * Find a class in this class loader's classpath.
	 * @param name binary name of the resource to find.
	 * @return a defined <code>Class</code> instance.
	 * @throws ClassNotFoundException if the class could not be loaded.
	 * @see java.lang.ClassLoader#findClass(java.lang.String)
	 */
	public Class<?> findClass(String name) throws ClassNotFoundException
	{
		try
		{
			lock.lock();
			int i = name.lastIndexOf('.');
			if (i >= 0)
			{
				String pkgName = name.substring(0, i);
				Package pkg = getPackage(pkgName);
				if (pkg == null)
				{
					definePackage(pkgName, null, null, null, null, null, null, null);
				}
			}
			if (debugEnabled) log.debug("looking up definition for resource [" + name + "]");
			byte[] b = null;
			String resName = name.replace('.', '/') + ".class";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", resName);
			JPPFResourceWrapper resource = loadResourceData(map, false);
			b = resource.getDefinition();
			if ((b == null) || (b.length == 0))
			{
				if (debugEnabled) log.debug("definition for resource [" + name + "] not found");
				throw new ClassNotFoundException("Could not load class '" + name + "'");
			}
			if (debugEnabled) log.debug("found definition for resource [" + name + "]");
			return defineClass(name, b, 0, b.length);
		}
		finally
		{
			lock.unlock();
		}
	}

	/**
	 * Load the specified class from a socket conenction.
	 * @param map - contains the necessary resource request data.
	 * @param asResource true if the resource is loaded using getResource(), false otherwise. 
	 * @return a <code>JPPFResourceWrapper</code> containing the resource content.
	 * @throws ClassNotFoundException if the class could not be loaded from the remote server.
	 */
	private JPPFResourceWrapper loadResourceData(Map<String, Object> map, boolean asResource) throws ClassNotFoundException
	{
		JPPFResourceWrapper resource = null;
		try
		{
			if (debugEnabled) log.debug("loading remote definition for resource [" + map.get("name") + "]");
			resource = loadResourceData0(map, asResource);
		}
		catch(IOException e)
		{
			if (debugEnabled) log.debug("connection with class server ended, re-initializing");
			init();
			try
			{
				resource = loadResourceData0(map, asResource);
			}
			catch(ClassNotFoundException ex)
			{
				throw ex;
			}
			catch(Exception ex)
			{
			}
		}
		catch(ClassNotFoundException e)
		{
			throw e;
		}
		catch(Exception e)
		{
		}
		return resource;
	}

	/**
	 * Load the specified class from a socket connection.
	 * @param map - contains the necessary resource request data.
	 * @param asResource true if the resource is loaded using getResource(), false otherwise. 
	 * @return a <code>JPPFResourceWrapper</code> containing the resource content.
	 * @throws Exception if the connection was lost and could not be reestablished.
	 */
	private JPPFResourceWrapper loadResourceData0(Map<String, Object> map, boolean asResource) throws Exception
	{
		if (debugEnabled) log.debug("loading remote definition for resource [" + map.get("name") + "], requestUuid = " + requestUuid);
		JPPFResourceWrapper resource = loadRemoteData(map, false);
		if (debugEnabled) log.debug("remote definition for resource [" + map.get("name") + "] "+ (resource.getDefinition()==null ? "not " : "") + "found");
		return resource;
	}

	/**
	 * Request the remote computation of a <code>JPPFCallable</code> on the client.
	 * @param callable - the serialized callable to execute remotely.
	 * @return an array of bytes containing the result of the callable's execution.
	 * @throws Exception if the connection was lost and could not be reestablished.
	 */
	public byte[] computeRemoteData(byte[] callable) throws Exception
	{
		try
		{
			lock.lock();
			if (debugEnabled) log.debug("requesting remote computation, requestUuid = " + requestUuid);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("callable", callable);
			byte[] b = loadRemoteData(map, false).getCallable();
			if (debugEnabled) log.debug("remote definition for collable resource "+ (b==null ? "not " : "") + "found");
			return b;
		}
		finally
		{
			lock.unlock();
		}
	}

	/**
	 * Load the specified class from a socket connection.
	 * @param map - contains the necessary resource request data.
	 * @param asResource - true if the resource is loaded using getResource(), false otherwise. 
	 * @return a <code>JPPFResourceWrapper</code> containing the resource content.
	 * @throws Exception if the connection was lost and could not be reestablished.
	 */
	private JPPFResourceWrapper loadRemoteData(Map<String, Object> map, boolean asResource) throws Exception
	{
		try
		{
			loading.set(true);
			JPPFResourceWrapper resource = new JPPFResourceWrapper();
			resource.setState(JPPFResourceWrapper.State.NODE_REQUEST);
			resource.setDynamic(dynamic);
			TraversalList<String> list = new TraversalList<String>(uuidPath);
			resource.setUuidPath(list);
			if (list.size() > 0) list.setPosition(uuidPath.size()-1);
			for (Map.Entry<String, Object> entry: map.entrySet()) resource.setData(entry.getKey(), entry.getValue());
			resource.setAsResource(asResource);
			resource.setRequestUuid(requestUuid);
	
			JPPFDataTransform transform = JPPFDataTransformFactory.getInstance();
			ObjectSerializer serializer = socketClient.getSerializer();
			JPPFBuffer buf = serializer.serialize(resource);
			byte[] data = buf.getBuffer();
			if (transform != null) data = JPPFDataTransformFactory.transform(transform, true, data);
			socketClient.sendBytes(new JPPFBuffer(data, data.length));
			socketClient.flush();
			buf = socketClient.receiveBytes(0);
			data = buf.getBuffer();
			if (transform != null) data = JPPFDataTransformFactory.transform(transform, false, data);
			resource = (JPPFResourceWrapper) serializer.deserialize(data);
			return resource;
		}
		finally
		{
			loading.set(false);
		}
	}

	/**
	 * Finds the resource with the specified name.
	 * The resource lookup order is the same as the one specified by {@link #getResourceAsStream(String)} 
	 * @param name the name of the resource to find.
	 * @return the URL of the resource.
	 * @see java.lang.ClassLoader#findResource(java.lang.String)
	 */
	public URL findResource(String name)
	{
		URL url = null;
		if (debugEnabled) log.debug("resource [" + name + "] not found locally, attempting remote lookup");
		try
		{
			Enumeration<URL> urlEnum = findResources(name);
			if ((urlEnum != null) && urlEnum.hasMoreElements()) url = urlEnum.nextElement();
		}
		catch(IOException e)
		{
		}
		finally
		{
			//lock.unlock();
		}
		if (debugEnabled) log.debug("resource [" + name + "] " + (url == null ? "not " : "") + "found remotely");
		return url;
	}

	/**
	 * Get a stream from a resource file accessible form this class loader.
	 * The lookup order is defined as follows:
	 * <ul>
	 * <li>locally, in the classpath for this class loader, such as specified by {@link java.lang.ClassLoader#getResourceAsStream(java.lang.String) ClassLoader.getResourceAsStream(String)}<br>
	 * <li>if the parent of this class loader is NOT an instance of {@link JPPFClassLoader},
	 * in the classpath of the <i>JPPF driver</i>, such as specified by {@link org.jppf.classloader.ResourceProvider#getResourceAsBytes(java.lang.String, java.lang.ClassLoader) ResourceProvider.getResourceAsBytes(String, ClassLoader)}</li>
	 * (the search may eventually be sped up by looking up the driver's resource cache first)</li>
	 * <li>if the parent of this class loader IS an instance of {@link JPPFClassLoader},
	 * in the <i>classpath of the JPPF client</i>, such as specified by {@link org.jppf.classloader.ResourceProvider#getResourceAsBytes(java.lang.String, java.lang.ClassLoader) ResourceProvider.getResourceAsBytes(String, ClassLoader)}
	 * (the search may eventually be sped up by looking up the driver's resource cache first)</li>
	 * </ul>
	 * @param name name of the resource to obtain a stream from. 
	 * @return an <code>InputStream</code> instance, or null if the resource was not found.
	 * @see java.lang.ClassLoader#getResourceAsStream(java.lang.String)
	 */
	public InputStream getResourceAsStream(String name)
	{
		InputStream is = getClass().getClassLoader().getResourceAsStream(name);
		//if ((is == null) && !loading.get())
		if (is == null)
		{
			if (debugEnabled) log.debug("resource [" + name + "] not found locally, attempting remote lookup");
			try
			{
				lock.lock();
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", name);
				JPPFResourceWrapper resource = loadResourceData(map, true);
				byte[] b = resource.getDefinition();
				boolean found = (b != null) && (b.length > 0);
				if (debugEnabled) log.debug("resource [" + name + "] " + (found ? "" : "not ") + "found remotely");
				if (!found) return null;
				is = new ByteArrayInputStream(b);
			}
			catch(ClassNotFoundException e)
			{
				if (debugEnabled) log.debug("resource [" + name + "] not found remotely");
				return null;
			}
			finally
			{
				lock.unlock();
			}
		}
		return is;
	}

	/**
	 * Determine whether the socket client is being initialized.
	 * @return true if the socket client is being initialized, false otherwise.
	 */
	private static boolean isInitializing()
	{
		return initializing.get();
	}

	/**
	 * Set the socket client initialization status.
	 * @param initFlag true if the socket client is being initialized, false otherwise.
	 */
	private static void setInitializing(boolean initFlag)
	{
		initializing.set(initFlag);
	}

	/**
	 * Get the uuid for the orignal task bundle that triggered this resource request. 
	 * @return the uuid as a string.
	 */
	public String getRequestUuid()
	{
		return requestUuid;
	}

	/**
	 * Set the uuid for the orignal task bundle that triggered this resource request. 
	 * @param requestUuid the uuid as a string.
	 */
	public void setRequestUuid(String requestUuid)
	{
		this.requestUuid = requestUuid;
	}

	/**
	 * Terminate this classloader and clean the resources it uses.
	 */
	public void close()
	{
		lock.lock();
		try
		{
			if (socketInitializer != null) socketInitializer.close();
			if (socketClient != null)
			{
				try
				{
					socketClient.close();
				}
				catch(Exception e)
				{
					if (debugEnabled) log.debug(e.getMessage(), e);
				}
				socketClient = null;
			}
		}
		finally
		{
			lock.unlock();
		}
	}

	/**
	 * Find all resources with the specified name.
	 * @param name - name of the resources to find in the clas loader's classpath. 
	 * @return An enumeration of URLs pointing to the resources found.
	 * @throws IOException if an error occurs.
	 * @see java.lang.ClassLoader#findResources(java.lang.String)
	 */
	public Enumeration<URL> findResources(String name) throws IOException
	{
		List<URL> urlList = null;
		//if (loading.get()) return null;
		if (debugEnabled) log.debug("resource [" + name + "] not found locally, attempting remote lookup");
		try
		{
			lock.lock();
			List<String> locationsList = cache.getResourcesLocations(name);
			if (locationsList == null)
			{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("name", name);
				map.put("multiple", "true");
				JPPFResourceWrapper resource = loadResourceData(map, true);
				List<byte[]> dataList = (List<byte[]>) resource.getData("resource_list");
				boolean found = (dataList != null) && !dataList.isEmpty();
				if (debugEnabled) log.debug("resource [" + name + "] " + (found ? "" : "not ") + "found remotely");
				if (found)
				{
					cache.registerResources(name, dataList);
					urlList = new ArrayList<URL>();
					locationsList = cache.getResourcesLocations(name);
				}
			}
			if (locationsList != null)
			{
				for (String path: locationsList)
				{
					File file = new File(path);
					urlList.add(file.toURI().toURL());
				}
				if (debugEnabled) log.debug("found the following URLs for resource [" + name + "] : " + urlList);
			}
		}
		catch(IOException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			if (debugEnabled) log.debug("resource [" + name + "] not found remotely");
		}
		finally
		{
			lock.unlock();
		}
		return urlList == null ? null : new IteratorEnumeration(urlList.iterator());
	}
}
