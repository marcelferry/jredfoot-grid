package org.jredfoot.utgrid.utsgd.ws.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.xml.ws.soap.Addressing;

import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;
import org.jredfoot.utgrid.common.vo.Task;
import org.jredfoot.utgrid.utsgd.persistence.SGDFactory;
import org.jredfoot.utgrid.utsgd.persistence.dao.GridTaskDAO;
import org.jredfoot.utgrid.utsgd.persistence.entities.GridTaskEntity;
import org.jredfoot.utgrid.utsgd.ws.IGridTaskService;

@WebService(serviceName = "GridTaskService", portName = "GridTaskServicePort", endpointInterface = "org.jredfoot.utgrid.utsgd.ws.IGridTaskService", targetNamespace = "http://ws.utsgd.utgrid.jredfoot.org")
@Addressing(enabled = true, required = false)
@InInterceptors(interceptors = {
		"org.jredfoot.utgrid.common.ws.cfx.WSSecurityInterceptor",
		"org.apache.cxf.interceptor.LoggingInInterceptor" })
@OutInterceptors(interceptors = { "org.apache.cxf.interceptor.LoggingOutInterceptor" })
public class GridTaskService implements IGridTaskService {
	
	@Override
	public List<Task> listGridTasks() {
		GridTaskDAO dao = SGDFactory.getInstance().getGridTaskDAO();
		List<GridTaskEntity> lista = dao.list();
		List<Task> retorno = GridTaskEntity.getDTOList(lista);
		if (retorno.size() == 0) {
			retorno = new ArrayList<Task>();
			// retorno.add(new GridTask());
		}
		return retorno;
	}

	@Override
	public Task registerGridTask(Task gridNode) {
		GridTaskDAO dao = SGDFactory.getInstance().getGridTaskDAO();
		GridTaskEntity entity = new GridTaskEntity(gridNode);
		dao.persist(entity);
		return GridTaskEntity.getDTO(entity);
	}

	@Override
	public boolean updateGridTask(Task gridNode) {
		GridTaskDAO dao = SGDFactory.getInstance().getGridTaskDAO();
		dao.persist(new GridTaskEntity(gridNode));
		return true;
	}

	@Override
	public boolean deregisterGridTask(Task gridNode) {
		GridTaskDAO dao = SGDFactory.getInstance().getGridTaskDAO();
		dao.remove(new GridTaskEntity(gridNode));
		return true;
	}

	@Override
	public Task getTask(String taskId) {
		// TODO Auto-generated method stub
		return null;
	}
}
