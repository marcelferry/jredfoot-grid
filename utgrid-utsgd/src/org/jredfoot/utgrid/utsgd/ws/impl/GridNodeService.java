package org.jredfoot.utgrid.utsgd.ws.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;
import javax.xml.ws.soap.Addressing;

import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;
import org.jredfoot.utgrid.common.node.GridNode;
import org.jredfoot.utgrid.utsgd.persistence.SGDFactory;
import org.jredfoot.utgrid.utsgd.persistence.dao.GridNodeDAO;
import org.jredfoot.utgrid.utsgd.persistence.entities.GridNodeEntity;
import org.jredfoot.utgrid.utsgd.ws.IGridNodeService;

@WebService(
		serviceName="GridNodeService",
		portName="GridNodeServicePort",
		endpointInterface="org.jredfoot.utgrid.utsgd.ws.IGridNodeService",
		targetNamespace="http://ws.utsgd.utgrid.jredfoot.org"
	)
@Addressing(enabled=true, required=false)
@InInterceptors(interceptors = { "org.jredfoot.utgrid.common.ws.cfx.WSSecurityInterceptor","org.apache.cxf.interceptor.LoggingInInterceptor"})
@OutInterceptors(interceptors = { "org.apache.cxf.interceptor.LoggingOutInterceptor"})
public class GridNodeService implements IGridNodeService {

	public List<GridNode> listGridNodes(){
		GridNodeDAO dao = SGDFactory.getInstance().getGridNodeDAO();
		List<GridNodeEntity> lista = dao.list();
		List<GridNode> retorno = GridNodeEntity.getDTOList(lista);
		if(retorno.size() == 0){
			retorno = new ArrayList<GridNode>();
			//retorno.add(new GridNode());
		}
		return retorno;
	}
	
	public GridNode registerGridNode(GridNode gridNode){
		GridNodeDAO dao = SGDFactory.getInstance().getGridNodeDAO();
		GridNodeEntity entity = new GridNodeEntity( gridNode );
		dao.persist( entity );
		return GridNodeEntity.getDTO(entity);
	}

	public boolean updateGridNode(GridNode gridNode){
		GridNodeDAO dao = SGDFactory.getInstance().getGridNodeDAO();
		dao.persist( new GridNodeEntity( gridNode ));
		return true;
	}

	public boolean deregisterGridNode(GridNode gridNode){
		GridNodeDAO dao = SGDFactory.getInstance().getGridNodeDAO();
		dao.remove( new GridNodeEntity( gridNode ));
		return true;
	}
}
