package org.jredfoot.utgrid.utgar.ws.impl;

import java.io.FileInputStream;

import javax.jws.WebService;
import javax.xml.ws.soap.Addressing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;
import org.jredfoot.utgrid.common.classloader.ResourceHandler;
import org.jredfoot.utgrid.common.utils.CompressionUtils;
import org.jredfoot.utgrid.utgar.classloader.ResourceProvider;
import org.jredfoot.utgrid.utgar.ws.IRemoteClassLoaderService;

@WebService(
		serviceName="RemoteClassLoaderService",
		portName="RemoteClassLoaderServicePort",
		endpointInterface="org.jredfoot.utgrid.utgar.ws.IRemoteClassLoaderService",
		targetNamespace="http://ws.utgar.utgrid.jredfoot.org"
	)
@Addressing(enabled=true, required=false)
@InInterceptors(interceptors = { "org.jredfoot.utgrid.common.ws.cfx.WSSecurityInterceptor","org.apache.cxf.interceptor.LoggingInInterceptor"})
@OutInterceptors(interceptors = { "org.apache.cxf.interceptor.LoggingOutInterceptor"})
public class RemoteClassLoaderService implements IRemoteClassLoaderService {
	
	/**
	 * Log para esta classe
	 */
	private static Log log = LogFactory.getLog(RemoteClassLoaderService.class);
	
	/**
	 * Determina o nível do Log sem o custo de uma chamada ao método.
	 */
	private static boolean debugEnabled = log.isDebugEnabled();	
	
	
	public byte[] obterJarBytes(String jar) {
		
		byte[] jarBytes = null;

		try {
			FileInputStream fi = new FileInputStream("/Projetos/Exemplo.jar");
			jarBytes = new byte[fi.available()];
			fi.read(jarBytes);
			fi.close();
		} catch (Exception e) {
			//
		}

		return jarBytes;
	}
	
	public byte[] obterClasseBytes(String classe) { return null; }
	
	public ResourceHandler getRemoteResource(ResourceHandler resource){
		
		try {
			
			ResourceProvider provedor = new ResourceProvider();		
			boolean encontrado = true;
			String nome = resource.getName();
			if  (debugEnabled) 
				log.debug("["+this.getClass().getName()+"] recurso requisitado: " + nome);
			
			byte[] b = null;
			
			if (resource.isAsResource())
			{ 
				b = provedor.getResource(nome);
			} else { 
				b = provedor.getResourceAsBytes(nome);
			}
			
			if (b == null)
			{ 
				encontrado = false;
			}
			if (b != null){ 
				resource.setDefinition(CompressionUtils.zip(b, 0, b.length));
			} else { 
				resource.setDefinition(null);
			}
			
			if  (debugEnabled)
			{
				if (encontrado) 
					log.debug("["+ this.getClass().getName()+"] enviando recurso: " + nome + " (" + b.length + " bytes)");
				else 
					log.debug("["+ this.getClass().getName()+"] recurso não encontrado: " + nome);
			}
			return resource;			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
