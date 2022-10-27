package org.jredfoot.utgrid.utgar.classloader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jredfoot.utgrid.common.utils.FileUtils;


/**
 * Instances of this class are dedicated to reading resource files form the JVM's classpath and converting them into
 * arrays of bytes.
 * @author Laurent Cohen
 * @author Domingos Creado
 */
public class ResourceProvider
{
	/**
	 * Log para esta classe
	 */	
	private static Log log = LogFactory.getLog(ResourceProvider.class);
		
	/**
	 * Determina o nível do Log sem o custo de uma chamada ao método.
	 */
	private static boolean debugEnabled = log.isDebugEnabled();	
	

	/**
	 * Default constructor.
	 */
	public ResourceProvider()
	{
	}

	/**
	 * Carrega um recurso (imagens / properties / classes / etc.) de seu classpath em um array de bytes.
	 * @param nomeRes nome do recurso a ser carregado.
	 * @return um array de bytes ou nulo se não for encontrado.
	 */
	public byte[] getResourceAsBytes(String nomeRes)
	{
		return getResourceAsBytes(nomeRes, null);
	}

	/**
	 * Carrega um recurso (imagens / properties / classes / etc.) de seu classpath em um array de bytes.
	 * @param nomeRes nome do recurso a ser carregado .
	 * @param cl O classloader que irá processar a requisi?ão.
	 * @return um array de bytes ou nulo se não for encontrado.
	 */
	public byte[] getResourceAsBytes(String nomeRes, ClassLoader cl)
	{
		try
		{
			if (cl == null) cl = Thread.currentThread().getContextClassLoader();
			if (cl == null) cl = getClass().getClassLoader();
			InputStream is = cl.getResourceAsStream(nomeRes);
			if (is == null)
			{
				File file = new File(nomeRes);
				if (file.exists()) is = new BufferedInputStream(new FileInputStream(file));
			}
			if (is != null)
			{
				if (debugEnabled) log.debug("recurso [" + nomeRes + "] localizado.");
				return FileUtils.getInputStreamAsByte(is);
			}
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
		}
		if (debugEnabled) log.debug("recurso [" + nomeRes + "] não encontrado.");
		return null;
	}

	/**
	 * Get a resource as an array of byte using a call to <b>ClassLoader#getResource()</b>.
	 * This method simply calls {@link #getResource(java.lang.String, java.lang.ClassLoader) getResource(String, ClassLoader)}
	 * with a null class loader.
	 * @param resName  the name of the resource to find.
	 * @return the content of the resource as an array of bytes.
	 */
	public byte[] getResource(String resName)
	{
		return getResource(resName, null);
	}

	/**
	 * Get a resource as an array of byte using a call to <b>ClassLoader#getResource()</b>.
	 * @param resName  the name of the resource to find.
	 * @param cl the class loader to use to load the request resource.
	 * @return the content of the resource as an array of bytes.
	 */
	public byte[] getResource(String resName, ClassLoader cl)
	{
		if (cl == null) cl = Thread.currentThread().getContextClassLoader();
		if (cl == null) cl = getClass().getClassLoader();
		URL url = cl.getResource(resName);
		if (url != null)
		{
			try
			{
				if (debugEnabled) log.debug("resource [" + resName + "] found, url = " + url);
				return FileUtils.getInputStreamAsByte(url.openStream());
			}
			catch (Exception e)
			{
				log.error(e.getMessage(), e);
			}
		}
		else
		{
			InputStream is = null;
			try
			{
				File file = new File(resName);
				if (file.exists()) is = new BufferedInputStream(new FileInputStream(file));
				if (is != null)
				{
					if (debugEnabled) log.debug("resource [" + resName + "] found");
					return FileUtils.getInputStreamAsByte(is);
				}
			}
			catch (Exception e)
			{
				log.error(e.getMessage(), e);
			}
		}
		if (debugEnabled) log.debug("resource [" + resName + "] not found");
		return null;
	}
}
