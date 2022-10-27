package org.jredfoot.utgrid.node.statistics;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.cmd.SigarCommandBase;
import org.jredfoot.utgrid.common.node.GridNode;
import org.jredfoot.utgrid.common.node.GridNodeDetails;
import org.jredfoot.utgrid.common.utils.WindowsUtils;

public class SigarNodeInfo extends SigarCommandBase {

    public GridNode getNodeInformationForRegister() throws SigarException{
        org.hyperic.sigar.CpuInfo[] infos =
            this.sigar.getCpuInfoList();

        CpuPerc[] cpus = this.sigar.getCpuPercList();

        org.hyperic.sigar.CpuInfo cpuInfo = infos[0];
        long cacheSize = cpuInfo.getCacheSize();

        org.hyperic.sigar.NetInfo netInfo = this.sigar.getNetInfo();
        org.hyperic.sigar.NetInterfaceConfig netIntConfig = this.sigar.getNetInterfaceConfig();
        org.hyperic.sigar.Mem mem = this.sigar.getMem();
        org.hyperic.sigar.Swap swap = this.sigar.getSwap();
        
        GridNode node = new GridNode();
        node.setNodeHDSerial(WindowsUtils.getHDSerial("C"));
        node.setNodeId(-1L);
        node.setNodeIP(netIntConfig.getAddress());
        node.setNodeName(netInfo.getHostName());
        node.setNodeAddress(netIntConfig.getHwaddr());
        node.setNodeStatus(GridNode.IDLE);
        node.setNodeType(GridNode.EXECUTION);
        
        node.setDetails(new GridNodeDetails());
        GridNodeDetails.Hardware hardware = new GridNodeDetails.Hardware();

        hardware.setVendor(cpuInfo.getVendor());
        hardware.setModel(cpuInfo.getModel());
        hardware.setMhz(cpuInfo.getMhz());
        hardware.setTotalCPUs(cpuInfo.getTotalCores());
        hardware.setPhysicalCPUs(cpuInfo.getTotalSockets());
        hardware.setCoresPerCPU( cpuInfo.getCoresPerSocket() );
        
        if ((cpuInfo.getTotalCores() != cpuInfo.getTotalSockets()) ||
            (cpuInfo.getCoresPerSocket() > cpuInfo.getTotalCores()))
        {
            hardware.setPhysicalCPUs( cpuInfo.getTotalSockets());
            hardware.setCoresPerCPU( cpuInfo.getCoresPerSocket());
        }

        if (cacheSize != Sigar.FIELD_NOTIMPL) {
            hardware.setCacheSize(cacheSize);
        }
        hardware.setMemTotal(mem.getTotal());
        hardware.setMemSwap(swap.getTotal());
        
        GridNodeDetails.OperatingSystem os = new GridNodeDetails.OperatingSystem();
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        
        
        os.setArch(osBean.getArch());
        os.setName(osBean.getName());
        os.setVersion(osBean.getVersion());
        
        GridNodeDetails.Java jv = new GridNodeDetails.Java();
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        
        jv.setVersion(runtime.getVmVersion());
        jv.setVendor( runtime.getVmVendor() );
        jv.setJavaHome( System.getProperty("java.home") );
        
        node.getDetails().setHardware(hardware);
        node.getDetails().setOperatingSystem(os);
        node.getDetails().setJava(jv);
        
        return node;
        
    }
	
	@Override
	public void output(String[] arg0) throws SigarException {
		// TODO Auto-generated method stub
		
	}

}
