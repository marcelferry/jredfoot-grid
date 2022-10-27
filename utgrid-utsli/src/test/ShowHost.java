package test;

import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import org.jredfoot.utsli.glue.base.FileSystemType;
import org.jredfoot.utsli.glue.base.HostType;
import org.jredfoot.utsli.glue.base.NetworkAdapterType;
import org.jredfoot.utsli.glue.base.RunTimeEnvironmentType;
import org.jredfoot.utsli.glue.base.SubClusterOrHostType;
import org.jredfoot.utsli.glue.base.SubClusterType;
import org.w3c.dom.Element;





public class ShowHost
{
    public static void main(String args[]) throws Exception
    {
        GridServiceGridLocator gsl2 = new GridServiceGridLocator();
        GridServicePortType gsp2 = gsl2.getGridServicePort(new URL(args[0]));
        QName qname2 = new QName(args[1]); 
        ExtensibilityType queryResult2 = gsp2.findServiceData(QueryHelper.getNameQuery(qname2));
        System.out.println("==== As XML ===="); 
        Element elResult = AnyHelper.getAnyAsElement(queryResult2);
        String xmlDoc = DOM2Writer.nodeToString(elResult);
        System.out.println(xmlDoc);
        System.out.println("==== As Java objects ===="); 
        ServiceDataType sd = (ServiceDataType)AnyHelper.getAny(queryResult2);
        handleServiceDataType(sd);
    }

    static void handleServiceDataType(ServiceDataType sd)
    {
        System.out.println("Service data content is Java type: "+ AnyHelper.getAny(sd).getClass());
        Object[] content = (Object[]) AnyHelper.getAny(sd);
        for(int i=0;i<content.length;i++)
        {
            if(content[i]!=null)
            {
                System.out.println("Element "+i+" is a "+content[i].getClass());
                if(content[i] instanceof HostType)
                {
                    printHost((HostType)content[i]);
                } else if(content[i] instanceof SubClusterType)
		{
		    printSubCluster((SubClusterType)content[i]);
		} else if(content[i] instanceof ServiceDataType)
		{
		    handleServiceDataType((ServiceDataType)content[i]);
		}
		else
		{
		    System.out.println("I don't know how to handle that class.");
		}
            }
            else
                System.out.println("Element "+i+" is null");
        }
    }

    static void printSubCluster(SubClusterOrHostType h)
    {
        int i;
// subcluster information
        System.out.println("==== Subcluster Information ====");
        System.out.println("Subcluster name: "+h.getName());
        System.out.println("Subcluster Unique ID: "+h.getUniqueID());
        if(h instanceof SubClusterType){
        	System.out.println("downstream hosts: " + ((SubClusterType) h).getHost());
        }

        System.out.println("benchmark: "+h.getBenchmark());

        System.out.println("processor: ("+h.getProcessor()+")");
	if(h.getProcessor()!=null) {
            System.out.println("    Vendor: "+h.getProcessor().getVendor());
            System.out.println("    Model: "+h.getProcessor().getModel());
            System.out.println("    Version: "+h.getProcessor().getVersion());
            System.out.println("    ClockSpeed: "+h.getProcessor().getClockSpeed());
            System.out.println("    InstructionSet: "+h.getProcessor().getInstructionSet());
            System.out.println("    OtherProcessorDescription: "+h.getProcessor().getOtherProcessorDescription());
            System.out.println("    CacheL1: "+h.getProcessor().getCacheL1());
            System.out.println("    CacheL1I: "+h.getProcessor().getCacheL1I());
            System.out.println("    CacheL1D: "+h.getProcessor().getCacheL1D());
            System.out.println("    CacheL2: "+h.getProcessor().getCacheL2());
	}

        System.out.println("main memory: ("+h.getMainMemory()+")");
	if(h.getMainMemory()!=null) {
            System.out.println("    RAMAvail: "+h.getMainMemory().getRAMAvailable());
            System.out.println("    RAMSize: "+h.getMainMemory().getRAMSize());
            System.out.println("    VirSize: "+h.getMainMemory().getVirtualSize());
            System.out.println("    VirAvail: "+h.getMainMemory().getVirtualAvailable());
	}
        System.out.println("operating system: ("+h.getOperatingSystem()+")");
	if(h.getOperatingSystem()!=null) {
            System.out.println("    name: "+h.getOperatingSystem().getName());
            System.out.println("    release: "+h.getOperatingSystem().getRelease());
            System.out.println("    version: "+h.getOperatingSystem().getVersion());
	}

        System.out.println("storage devices: "+h.getStorageDevice());

        System.out.println("architecture: "+h.getArchitecture());

        System.out.println("application software: "+h.getApplicationSoftware());
	if(h.getApplicationSoftware()!=null)
	{
		List<RunTimeEnvironmentType> runTimeEnvironments=h.getApplicationSoftware().getRunTimeEnvironment();
	    for(i=0;i<runTimeEnvironments.size();i++)
	    {
                System.out.println("    runtime environment: "+runTimeEnvironments.get(i).getName());

	    }
	}

        System.out.println("file systems: "+h.getFileSystem());
	List<FileSystemType> fileSystems = h.getFileSystem();
        if(fileSystems!=null)
        {
            for(i=0;i<fileSystems.size();i++)
	    {
	        FileSystemType fileSystem = fileSystems.get(i);
		System.out.println("    Filesystem name: "+fileSystem.getName());
		System.out.println("        Root: "+fileSystem.getRoot());
		System.out.println("        Size: "+fileSystem.getSize());
		System.out.println("        Available Space: "+fileSystem.getAvailableSpace());
		System.out.println("        Read Only: "+fileSystem.isReadOnly());
		System.out.println("        Type: "+fileSystem.getType());

		/*
		  <attribute name="Name" type="string" />
		    <attribute name="Root" type="string" />
		      <attribute name="Size" type="long" />
		        <attribute name="AvailableSpace" type="long" />
			  <attribute name="ReadOnly" type="boolean" />
			    <attribute name="Type" type="string" />
			    */
	    }
	}

        System.out.println("network adapters: ("+h.getNetworkAdapter()+")");
        List<NetworkAdapterType> networkAdapters = h.getNetworkAdapter();
        if(networkAdapters!=null)
        {
            for(i=0;i<networkAdapters.size();i++)
	    {
	        NetworkAdapterType adapter = networkAdapters.get(i);
                System.out.println("    Adapter name: "+adapter.getName());
		System.out.println("        IP Address: "+adapter.getIPAddress());
		System.out.println("        MTU: "+adapter.getMTU());
		System.out.println("        outboundIP: "+adapter.isOutboundIP());
		System.out.println("        inboundIP: "+adapter.isInboundIP());
		
    }
        }
    }



    static void printHost(HostType h)
    {
        printSubCluster(h);
        System.out.println("==== Host Information ====");
// host information
        System.out.println("Processor load: "+h.getProcessorLoad());
	if(h.getProcessorLoad()!=null)
	{
            System.out.println("    1min: "+h.getProcessorLoad().getLast1Min());
            System.out.println("    5min: "+h.getProcessorLoad().getLast5Min());
            System.out.println("    15min: "+h.getProcessorLoad().getLast15Min());
	}
    }
}
