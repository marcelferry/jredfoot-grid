package org.jredfoot.utgrid.common.classloader;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlInlineBinaryData;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.jredfoot.utgrid.common.utils.TraversalList;
import org.jredfoot.utgrid.common.ws.cfx.StringObjectMapAdapter;

/**
 * A inst‰ncia dessa classe fornece as informa?›es necess‡rias para o ClassLoader por WebService,
 * tanto no envio de defini?›es da classe requisitada bem como no recebimento de informa?›es de classes.
 * 
 * @author Marcelo Ferreira da Silva
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class ResourceHandler implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3235617417279375635L;

	/**
	 * Determines whether the class should be loaded through the network classloader.
	 */
	private boolean dynamic = false;
	/**
	 * The uuid sent by a node when it first contacts a resource provider.
	 */
	private String providerUuid = null;
	/**
	 * Determines whether the resource is to be loaded using <code>ClassLoader.getResource()</code>.
	 */
	private boolean asResource = false;
	/**
	 * Uuid of the orignal task bundle that triggered this resource request. 
	 */
	private String requestUuid = null;
	/**
	 * Contains data about th kind of lookup that is to be done.
	 */
    @XmlJavaTypeAdapter(StringObjectMapAdapter.class)
	private Map<String, Object> data = new HashMap<String, Object>();

	/**
	 * Get the name of the class whose definition is requested.
	 * @return the class name as a string.
	 */
	public String getName()
	{
		return (String) getData("name");
	}

	/**
	 * Set the name of the class whose definition is requested.
	 * @param name - the class name as a string.
	 */
	public void setName(String name)
	{
		setData("name", name);
	}

	/**
	 * Get the actual definition of the requested class.
	 * @return the class definition as an array of bytes.
	 */
	@XmlInlineBinaryData
	public byte[] getDefinition()
	{
		return (byte[]) getData("definition");
	}

	/**
	 * Set the actual definition of the requested class.
	 * @param definition - the class definition as an array of bytes.
	 */
	public void setDefinition(byte[] definition)
	{
		setData("definition", definition);
	}

	/**
	 * Determine whether the class should be loaded through the network classloader.
	 * @return true if the class should be loaded via the network classloader, false otherwise.
	 */
	public boolean isDynamic()
	{
		return dynamic;
	}

	/**
	 * Set whether the class should be loaded through the network classloader.
	 * @param dynamic - true if the class should be loaded via the network classloader, false otherwise.
	 */
	public void setDynamic(boolean dynamic)
	{
		this.dynamic = dynamic;
	}

	/**
	 * Get the uuid sent by a node when it first contacts a resource provider.
	 * @return the uuid as a string.
	 */
	public String getProviderUuid()
	{
		return providerUuid;
	}

	/**
	 * Set the uuid sent by a node when it first contacts a resource provider.
	 * @param providerUuid - the uuid as a string.
	 */
	public void setProviderUuid(String providerUuid)
	{
		this.providerUuid = providerUuid;
	}

	/**
	 * Determine whether the resource is to be loaded using <code>ClassLoader.getResource()</code>.
	 * @return true if the resource is loaded using getResource(), false otherwise.
	 */
	public boolean isAsResource()
	{
		return asResource;
	}

	/**
	 * Set whether the resource is to be loaded using <code>ClassLoader.getResource()</code>.
	 * @param asResource - true if the resource is loaded using getResource(), false otherwise. 
	 */
	public void setAsResource(boolean asResource)
	{
		this.asResource = asResource;
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
	 * Get the serialized callback to execute code on the client side.
	 * @return a <code>byte[]</code> instance.
	 */
	@XmlInlineBinaryData
	public byte[] getCallable()
	{
		return (byte[]) getData("callable");
	}

	/**
	 * Set the serialized callback to execute code on the client side.
	 * @param callable - a <code>byte[]</code> instance.
	 */
	public void setCallable(byte[] callable)
	{
		setData("callable", callable);
	}

	/**
	 * Get the metadata corresponding to the specified key.
	 * @param key - the string identifying the metadata.
	 * @return an object value or null if the metadata could not be found.
	 */
	public Object getData(String key)
	{
		return data.get(key);
	}

	/**
	 * Get the metadata corresponding to the specified key.
	 * @param key - the string identifying the metadata.
	 * @param value - the value of the metadata.
	 */
	public void setData(String key, Object value)
	{
		data.put(key, value);
	}

}
