package org.jredfoot.utgrid.utgar.ws;

import javax.jws.WebService;

import org.jredfoot.utgrid.common.classloader.ResourceHandler;

@WebService(
		name="RemoteClassLoaderPortType",
		targetNamespace="http://ws.utgar.utgrid.jredfoot.org"
		)
public interface IRemoteClassLoaderService {

	/**
	 * Recupera um jar completo para execu?‹o da aplica?‹o.
	 * 
	 * @param jar String com o nome do jar a ser carregado.
	 * 
	 * @return Array de bytes do arquivo fisico
	 */
	byte[] obterJarBytes(String jar);

	/**
	 * Recupera uma classe para execu?‹o da aplica?‹o.
	 * 
	 * @param classe String com o nome da classe a ser carregada.
	 * 
	 * @return Array de bytes com a classe carregada.
	 */
	byte[] obterClasseBytes(String classe);

	/**
	 * Recupera um recurso dispon’vel no classpath da aplica?‹o.
	 * 
	 * @param recurso Classe representando o recurso.
	 * 
	 * @return Objeto recurso.
	 */
	ResourceHandler getRemoteResource(ResourceHandler recurso);

}