package org.jredfoot.utgrid.portal.facade;

import java.util.Collection;
import java.util.List;

import org.jredfoot.utgrid.common.node.GridNode;
import org.jredfoot.utgrid.utsgd.ws.client.GridNodeService;

public class GridNodeFacade {

	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<GridNode> findRange(int[] is) {
		GridNodeService gns = new GridNodeService();
		System.out.println("E ai?");
		List<GridNode> col = gns.getGridNodeServicePort().listGridNodes();
		System.out.println("fim");
		return col;
	}

	public void persist(GridNode current) {
		// TODO Auto-generated method stub
		
	}

	public void remove(GridNode current) {
		// TODO Auto-generated method stub
		
	}

	public List<GridNode> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getById(Long key) {
		// TODO Auto-generated method stub
		return null;
	}

}
