package org.jredfoot.utgrid.common.vo;

public class Message {
	
	int codigo;
	String tipo;
	Object valor;
	String mensagem;
	Exception excecao;
	
	
	/**
	 * @return the codigo
	 */
	public int getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the valor
	 */
	public Object getValor() {
		return valor;
	}
	/**
	 * @param valor the valor to set
	 */
	public void setValor(Object valor) {
		this.valor = valor;
	}
	/**
	 * @return the mensagem
	 */
	public String getMensagem() {
		return mensagem;
	}
	/**
	 * @param mensagem the mensagem to set
	 */
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	/**
	 * @return the excecao
	 */
	public Exception getExcecao() {
		return excecao;
	}
	/**
	 * @param excecao the excecao to set
	 */
	public void setExcecao(Exception excecao) {
		this.excecao = excecao;
	}

	
	
}
