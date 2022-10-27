package org.jredfoot.utgrid.node.controller;

import org.jredfoot.utgrid.common.node.GridNode;
import org.jredfoot.utgrid.node.facade.GridNodeFacade;
import org.jredfoot.utgrid.node.statistics.SigarNodeInfo;

public class GridNodeController {
	public void registerNode(){
		GridNodeFacade facade = new GridNodeFacade();
		SigarNodeInfo info = new SigarNodeInfo();
		try{
		GridNode node = info.getNodeInformationForRegister();
		facade.save(node);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
