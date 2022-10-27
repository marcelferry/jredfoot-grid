package org.jredfoot.utgrid.node.facade;

import org.jredfoot.utgrid.common.node.GridNode;
import org.jredfoot.utgrid.utsgd.ws.client.GridNodeService;

public class GridNodeFacade {

	public void save(GridNode node) {
		GridNodeService gns = new GridNodeService();
		System.out.println("E ai?");
		gns.getGridNodeServicePort().registerGridNode(node);
		System.out.println("FIM");

	}
	
}
