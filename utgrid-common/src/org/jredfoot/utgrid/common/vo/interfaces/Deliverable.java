package org.jredfoot.utgrid.common.vo.interfaces;

import org.jredfoot.utgrid.common.vo.Message;

/**
 * Interface responsavel pela troca e tratamentode mensagens entre os servi?os e aplicativos. 
 * Fornece as principais constantes que representam os tipos de mensagens esperados.
 * 
 * @author marcelo
 * @see {@link Message}
 *
 */
public interface Deliverable {
	
	/** Indicador de Mensagem de Sucesso */
	public final static int MENSAGEM_OK = 0;
	
	/** Indicador de Mensagem de Sucesso mas com ressalvas informativas */
	public final static int MENSAGEM_INFORMATIVA = 1;
	
	/** Indicador de Mensagem de Sucesso mas com alertas sobre a execu?‹o */
	public final static int MENSAGEM_ALERTA = 2;
	
	/** Indicador de Mensagem de Erro */
	public final static int MENSAGEM_ERRO = 3;
	
	
	void setMensagem(Message mensagem);
	
	Message getMensagem();
		
}
