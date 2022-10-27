package org.jredfoot.utgrid.utsgd.ws;

import java.util.List;

import javax.jws.WebService;

import org.jredfoot.utgrid.common.node.GridNode;

@WebService(
		name="GridNodePortType",
		targetNamespace="http://ws.utsgd.utgrid.jredfoot.org"
		)
public interface IGridNodeService {
	List<GridNode> listGridNodes();
	GridNode registerGridNode(GridNode gridNode);
	boolean updateGridNode(GridNode gridNode);
	boolean deregisterGridNode(GridNode gridNode);
}