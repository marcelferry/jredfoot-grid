package org.jredfoot.utgrid.tasks;

import java.io.Serializable;
import java.util.Date;

import org.jredfoot.utgrid.common.exception.GridException;

public interface ITask extends Runnable, Serializable {
	/** Obtem id da tarefa formado pelo bid + num_seq */
	String getTid();
	/** Obtem o resultado da execu��o */
	Result getResult();
	/** Obtem a excecao gerada, tamb�m dispon�vel no resultado*/
	GridException getException();
	/** Obtem a data limite para execucao da tarefa */
	Date getTimeout();
}
