package org.jredfoot.utgrid.node.statistics;


import com.sun.management.OperatingSystemMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.cmd.SigarCommandBase;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jredfoot.utgrid.common.node.GridNode;
import org.jredfoot.utgrid.common.node.GridNodeDetails;

import sun.management.ManagementFactory;

/**
 * Display cpu information for each cpu found on the system.
 */
public class SigarCpuInfo extends SigarCommandBase {

    XYSeries userTime1 = new XYSeries("Tempo de Usu'ario");
    XYSeries sysTime1  = new XYSeries("Tempo de Sistema");
    XYSeries idleTime1 = new XYSeries("Tempo Ocioso");
    //XYSeries waitTime1 = new XYSeries("Tempo de Espera");
    //XYSeries niceTime1 = new XYSeries("Tempo de Sei lá");
    XYSeries combined1 = new XYSeries("M�dia Sigar");
    //XYSeries combined2 = new XYSeries("Média Sigar Total");
    //XYSeries mediajmx1 = new XYSeries("Média JDK");

    public static XYSeriesCollection collection1 = null;


    public static long contador = 100;

    private OperatingSystemMXBean os = null;

    private Method jdk6CpuLoadMtd = null;

    Date dataAtual = new Date();

    private final int cpuId;


    public SigarCpuInfo(int cpuId) {
        super();
        this.cpuId = cpuId;
        collection1 = new XYSeriesCollection();
        collection1.addSeries(userTime1);
        collection1.addSeries(sysTime1);
        collection1.addSeries(idleTime1);
        //collection1.addSeries(waitTime1);
        //collection1.addSeries(niceTime1);
        collection1.addSeries(combined1);
        //collection1.addSeries(combined2);
        //collection1.addSeries(mediajmx1);
        
        try {
            os = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
            jdk6CpuLoadMtd = OperatingSystemMXBean.class.getMethod("getSystemLoadAverage");

        } catch (NoSuchMethodException ex) {
            Logger.getLogger(SigarCpuInfo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(SigarCpuInfo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public SigarCpuInfo() {
        super();
        this.cpuId = 0;
    }
    
    public int getNumeroCpu(){
        try {
            org.hyperic.sigar.CpuInfo[] infos = this.sigar.getCpuInfoList();
            org.hyperic.sigar.CpuInfo info = infos[0];
            return info.getTotalCores();
        } catch (SigarException ex) {
            Logger.getLogger(SigarCpuInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
        
    }



    public void output(String[] args) throws SigarException {
        org.hyperic.sigar.CpuInfo[] infos =
            this.sigar.getCpuInfoList();

        CpuPerc[] cpus =
            this.sigar.getCpuPercList();

        org.hyperic.sigar.CpuInfo info = infos[0];
        long cacheSize = info.getCacheSize();

        GridNode node = new GridNode();
        node.setDetails(new GridNodeDetails());
        GridNodeDetails.Hardware hardware = new GridNodeDetails.Hardware();

        hardware.setVendor(info.getVendor());
        hardware.setModel(info.getModel());
        hardware.setMhz(info.getMhz());
        hardware.setTotalCPUs(info.getTotalCores());

        if ((info.getTotalCores() != info.getTotalSockets()) ||
            (info.getCoresPerSocket() > info.getTotalCores()))
        {
            hardware.setPhysicalCPUs( info.getTotalSockets());
            hardware.setCoresPerCPU( info.getCoresPerSocket());
        }

        if (cacheSize != Sigar.FIELD_NOTIMPL) {
            hardware.setCacheSize(cacheSize);
        }
        
        node.getDetails().setHardware(hardware);


    }

    public void atualizarCpus() throws SigarException {

        CpuPerc geral = this.sigar.getCpuPerc();
        CpuPerc[] cpus =
            this.sigar.getCpuPercList();

        Date data = new Date();

        long tempoAtual = (data.getTime() - dataAtual.getTime()) / 1000;


        CpuPerc cpu = cpus[this.cpuId];



        if(userTime1.getItemCount() == 100){
           contador = tempoAtual;
           userTime1.remove(0);
           sysTime1.remove(0);
           idleTime1.remove(0);
           //waitTime1.remove(0);
           //niceTime1.remove(0);
           combined1.remove(0);
           //mediajmx1.remove(0);
        }

        userTime1.add(tempoAtual, cpu.getUser() * 100);
        sysTime1.add( tempoAtual, cpu.getSys() * 100);
        idleTime1.add(tempoAtual, cpu.getIdle() * 100);
        //waitTime1.add(tempoAtual, cpu.getWait() * 100);
        //niceTime1.add(tempoAtual, cpu.getNice() * 100);
        combined1.add(tempoAtual, cpu.getCombined() * 100);
        //combined2.add(tempoAtual, geral.getCombined() * 100 );
        //mediajmx1.add(tempoAtual, getMediaCpu());
        
    }


    public Double getMediaCpu(){
        try {
            Double media = (Double) jdk6CpuLoadMtd.invoke(os) * 100;
            System.out.println(media);
            return media;
        } catch (IllegalAccessException ex) {
            Logger.getLogger(SigarCpuInfo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(SigarCpuInfo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(SigarCpuInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0.0;
    }

}
