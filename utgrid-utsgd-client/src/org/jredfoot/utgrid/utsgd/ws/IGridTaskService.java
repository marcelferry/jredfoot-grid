package org.jredfoot.utgrid.utsgd.ws;

import java.util.List;

import javax.jws.WebService;

import org.jredfoot.utgrid.common.vo.Task;

@WebService(
		name="GridTaskPortType",
		targetNamespace="http://ws.utsgd.utgrid.jredfoot.org"
		)
public interface IGridTaskService {
	List<Task> listGridTasks();
	Task registerGridTask(Task gridNode);
	boolean updateGridTask(Task gridNode);
	boolean deregisterGridTask(Task gridNode);
	Task getTask(String taskId);
	
}