package org.jredfoot.utgrid.portal.facade;

import java.util.List;

import org.jredfoot.utgrid.common.vo.Task;
import org.jredfoot.utgrid.utsgd.ws.client.GridTaskService;

public class GridTaskServiceFacade {

	public Task persist(Task current) {
		GridTaskService gts = new GridTaskService();
		Task u = gts.getGridTaskServicePort().registerGridTask(current);
		return u;
	}
	
	public boolean remove(Task current) {
		GridTaskService gts = new GridTaskService();
		Boolean b = gts.getGridTaskServicePort().deregisterGridTask( current );
		return b;
	}
	
	public boolean update(Task current) {
		GridTaskService gts = new GridTaskService();
		Boolean b = gts.getGridTaskServicePort().updateGridTask(current);
		return b;
	}

	public Task getById(String taskId) {
		GridTaskService gts = new GridTaskService();
		Task u = gts.getGridTaskServicePort().getTask(taskId);
		return u;
	}

	public List<Task> findRange(int[] is) {
		GridTaskService gts = new GridTaskService();
		List<Task> col = gts.getGridTaskServicePort().listGridTasks();
		return col;
	}

	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
