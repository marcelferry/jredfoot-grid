

package org.jredfoot.utgrid.node.statistics;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jredfoot.utgrid.common.node.GridNodeExecutionMetrics;
import org.jredfoot.utgrid.common.node.GridNodeExecutionMetricsImpl;

public class SystemAnalytics  {

    /**
     * Logger for this class.
     */
    private static Log log = LogFactory.getLog(SystemAnalytics.class);
    /**
     * Determines whether the debug level is enabled in the log configuration, without the cost of a method call.
     */
    private boolean debugEnabled = log.isDebugEnabled();

    /** */
    private MemoryMXBean mem  = null;

    /** */
    private OperatingSystemMXBean os = null;

    /** */
    private RuntimeMXBean runtime = null;

    /** */
    private ThreadMXBean threads = null;

    /** */
    private volatile GridNodeExecutionMetrics metrics = null;

    /** */
    private Object sigar = null;

    /** */
    private Method sigarCpuPercMtd = null;

    private Method sigarMemMtd = null;

    private Method sigarSwapMtd = null;    
    /** */
    private Method sigarCpuCombinedMtd = null;

    private Method sigarCpuPercListMtd = null;

    private Method sigarCpuUserMtd = null;

    private Method sigarCpuSysMtd = null;

    private Method sigarCpuIdleMtd = null;

    private Method sigarCpuWaitMtd = null;
    
    private Method sigarCpuNiceMtd = null;

    private Method sigarMemUsedMtd = null;

    private Method sigarMemFreeMtd = null;

    private Method sigarMemTotalMtd = null;

    private Method sigarSwapUsedMtd = null;
    
    private Method sigarSwapFreeMtd = null;

    private Method sigarSwapTotalMtd = null;
    /** */
    private Method jdk6CpuLoadMtd = null;

    /** */
    private volatile boolean isPreferSigar = false;

    private volatile boolean isSigarPresent = true;




    /**
     * {@inheritDoc}
     */
    public boolean isPreferSigar() {
        return isPreferSigar;
    }

    public boolean isSigarPresent() {
        return isSigarPresent;
    }


    public SystemAnalytics()  {


        mem = ManagementFactory.getMemoryMXBean();
        os = ManagementFactory.getOperatingSystemMXBean();
        runtime = ManagementFactory.getRuntimeMXBean();
        threads = ManagementFactory.getThreadMXBean();


        initializeJdk16();

        initializeSigar();
        

        if (jdk6CpuLoadMtd != null) {
            if (log.isInfoEnabled() ) {
                log.info("Método JDK 1.6 'OperatingSystemMXBean.getSystemLoadAverage()' " +
                    "será usado para obter carregamento médio da CPU.");
            }
        }
        else if (sigar != null) {
            isPreferSigar = true;

            if (log.isInfoEnabled() ){
                log.info("Hyperic Sigar 'CpuPerc.getCombined()' method will be used to detect average CPU load. " +
                    "Note that Hyperic Sigar is licensed under GPL. For more " +
                    "information visit: http://support.hyperic.com/confluence/display/SIGAR/Home");
            }
        }
        else {
            log.warn("System CPU load cannot be detected (either upgrade to JDK 1.6 or higher or add " +
                "Hyperic Sigar to classpath). CPU load will be returned as -1.");
        }

        metrics = getMetrics();

    }

    private void initializeSigar() {
        // Detect if Sigar is available in classpath.
        try {
            Object sigar = Class.forName("org.hyperic.sigar.Sigar").newInstance();

            Method proxyMtd = Class.forName("org.hyperic.sigar.SigarProxyCache").getMethod("newInstance",
                sigar.getClass(), int.class);

            // Update CPU info every 2 seconds.
            this.sigar = proxyMtd.invoke(null, sigar, 2000);

            sigarCpuPercMtd = this.sigar.getClass().getMethod("getCpuPerc");

            sigarMemMtd = this.sigar.getClass().getMethod("getMem");

            sigarSwapMtd = this.sigar.getClass().getMethod("getSwap");

            sigarCpuPercListMtd = this.sigar.getClass().getMethod("getCpuPercList");

            sigarCpuCombinedMtd = sigarCpuPercMtd.getReturnType().getMethod("getCombined");

            sigarCpuUserMtd = sigarCpuPercMtd.getReturnType().getMethod("getUser");

            sigarCpuSysMtd = sigarCpuPercMtd.getReturnType().getMethod("getSys");

            sigarCpuIdleMtd = sigarCpuPercMtd.getReturnType().getMethod("getIdle");

            sigarCpuWaitMtd = sigarCpuPercMtd.getReturnType().getMethod("getWait");

            sigarCpuNiceMtd = sigarCpuPercMtd.getReturnType().getMethod("getNice");

            sigarMemUsedMtd = sigarMemMtd.getReturnType().getMethod("getUsed");

            sigarMemFreeMtd = sigarMemMtd.getReturnType().getMethod("getFree");

            sigarMemTotalMtd = sigarMemMtd.getReturnType().getMethod("getTotal");

            sigarSwapUsedMtd = sigarSwapMtd.getReturnType().getMethod("getUsed");

            sigarSwapFreeMtd = sigarSwapMtd.getReturnType().getMethod("getFree");

            sigarSwapTotalMtd = sigarSwapMtd.getReturnType().getMethod("getTotal");

        }
        // Purposely catch generic exception.
        catch (Exception e) {
            sigar = null;
            isSigarPresent = false;
            log.warn("Failed to find Hyperic Sigar in classpath: " + e.getMessage());
        }
    }

    private void initializeJdk16() {
        try {
            jdk6CpuLoadMtd = OperatingSystemMXBean.class.getMethod("getSystemLoadAverage");
        }
        catch (Exception e) {
            jdk6CpuLoadMtd = null;
            log.warn("Failed to find JDK 1.6 or higher CPU load metrics: " + e.getMessage());
        }
    }

    public GridNodeExecutionMetrics getMetrics() {
        MemoryUsage heap = mem.getHeapMemoryUsage();
        MemoryUsage nonHeap = null;

        try {
            nonHeap = mem.getNonHeapMemoryUsage();
        }
        catch(IllegalArgumentException e) {
            nonHeap = new MemoryUsage(0, 0, 0, 0);
        }

        if(sigar != null){

            metrics = new GridNodeExecutionMetricsImpl(
                os.getAvailableProcessors(),
                getCpuLoad(),
                getSigarUsuarioCpuLoad(),
                getSigarSistemaCpuLoad(),
                getSigarOciosoCpuLoad(),
                getSigarCpuLoad(),
                getSigarSobrecargas()[1],
                getSigarSobrecargas()[2],
                getSigarSobrecargas()[3],
                getSigarSobrecargas()[0],
                heap.getInit(),
                heap.getUsed(),
                heap.getCommitted(),
                heap.getMax(),
                nonHeap.getInit(),
                nonHeap.getUsed(),
                nonHeap.getCommitted(),
                nonHeap.getMax(),
                getSigarMemTotal(),
                getSigarMemUsada(),
                getSigarMemLivre(),
                getSigarSwapTotal(),
                getSigarSwapUsada(),
                getSigarSwapLivre(),
                runtime.getUptime(),
                runtime.getStartTime(),
                threads.getThreadCount(),
                threads.getPeakThreadCount(),
                threads.getTotalStartedThreadCount(),
                threads.getDaemonThreadCount()
            );
        } else {
            metrics = new GridNodeExecutionMetricsImpl(
                os.getAvailableProcessors(),
                getCpuLoad(),
                heap.getInit(),
                heap.getUsed(),
                heap.getCommitted(),
                heap.getMax(),
                nonHeap.getInit(),
                nonHeap.getUsed(),
                nonHeap.getCommitted(),
                nonHeap.getMax(),
                runtime.getUptime(),
                runtime.getStartTime(),
                threads.getThreadCount(),
                threads.getPeakThreadCount(),
                threads.getTotalStartedThreadCount(),
                threads.getDaemonThreadCount()
            );
        }

        return metrics;
    }

    /**
     *
     * @return CPU load.
     */
    @SuppressWarnings({"CatchGenericClass"})
    private double getCpuLoad() {
        double load = -1;

        if (isPreferSigar == false) {
            load = getJdk6CpuLoad();

            if (load < 0) {
                load = getSigarCpuLoad();

                if (load >= 0) {
                    log.warn("JDK 1.6 CPU load is not available (switching to Hyperic Sigar " +
                        "'CpuPerc.getCombined()' CPU metrics).");

                    isPreferSigar = true;
                }
            }
        }
        else {
            load = getSigarCpuLoad();

            if (load < 0) {
                load = getJdk6CpuLoad();

                if (load >= 0) {
                    log.warn("Hyperic Sigar CPU metrics not available (switching to JDK 1.6 " +
                        "'OperatingSystemMXBean.getSystemLoadAverage()' CPU metrics).");

                    isPreferSigar = false;
                }
            }
        }

        if (Double.isNaN(load) == true || Double.isInfinite(load) == true) {
            load = 0.5;
        }

        return load;
    }

    private double getJdk6CpuLoad() {
        if (jdk6CpuLoadMtd != null) {
            try {
                return (Double)jdk6CpuLoadMtd.invoke(os) / 100;
            }
            // Purposely catch generic exception.
            catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Falha ao obter JDK 1.6 CPU load (irá retornar -1): " + e.getMessage());
                }
            }
        }

        return -1;
    }

   private double getSigarCpuLoad() {
        if (sigar != null) {
            try {
                return (Double)sigarCpuCombinedMtd.invoke(sigarCpuPercMtd.invoke(sigar));
            }
            // Purposely catch generic exception.
            catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Falha ao obter Hyperic Sigar CPU load (irá retornar -1): " + e.getMessage());
                }
            }
        }

        return -1;
    }

   private double getSigarSistemaCpuLoad() {
        if (sigar != null) {
            try {
                return (Double)sigarCpuSysMtd.invoke(sigarCpuPercMtd.invoke(sigar));
            }
            // Purposely catch generic exception.
            catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Falha ao obter Hyperic Sigar CPU load (irá retornar -1): " + e.getMessage());
                }
            }
        }

        return -1;
    }


      private double getSigarUsuarioCpuLoad() {
        if (sigar != null) {
            try {
                return (Double)sigarCpuUserMtd.invoke(sigarCpuPercMtd.invoke(sigar));
            }
            // Purposely catch generic exception.
            catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Falha ao obter Hyperic Sigar CPU load (irá retornar -1): " + e.getMessage());
                }
            }
        }

        return -1;
    }


    private double getSigarOciosoCpuLoad() {
        if (sigar != null) {
            try {
                return (Double)sigarCpuIdleMtd.invoke(sigarCpuPercMtd.invoke(sigar));
            }
            // Purposely catch generic exception.
            catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Falha ao obter Hyperic Sigar CPU load (irá retornar -1): " + e.getMessage());
                }
            }
        }

        return -1;
    }

    private long getSigarMemTotal() {
        if (sigar != null) {
            try {
                return (Long)sigarMemTotalMtd.invoke(sigarMemMtd.invoke(sigar));
            }
            // Purposely catch generic exception.
            catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Falha ao obter Hyperic Sigar CPU load (irá retornar -1): " + e.getMessage());
                }
            }
        }

        return -1;
    }

    private long getSigarMemUsada() {
        if (sigar != null) {
            try {
                return (Long)sigarMemUsedMtd.invoke(sigarMemMtd.invoke(sigar));
            }
            // Purposely catch generic exception.
            catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Falha ao obter Hyperic Sigar CPU load (irá retornar -1): " + e.getMessage());
                }
            }
        }

        return -1;
    }

    private long getSigarMemLivre() {
        if (sigar != null) {
            try {
                return (Long)sigarMemFreeMtd.invoke(sigarMemMtd.invoke(sigar));
            }
            // Purposely catch generic exception.
            catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Falha ao obter Hyperic Sigar CPU load (irá retornar -1): " + e.getMessage());
                }
            }
        }

        return -1;
    }

    private long getSigarSwapTotal() {
        if (sigar != null) {
            try {
                return (Long)sigarSwapTotalMtd.invoke(sigarSwapMtd.invoke(sigar));
            }
            // Purposely catch generic exception.
            catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Falha ao obter Hyperic Sigar CPU load (irá retornar -1): " + e.getMessage());
                }
            }
        }

        return -1;
    }

    private long getSigarSwapUsada() {
        if (sigar != null) {
            try {
                return (Long)sigarSwapUsedMtd.invoke(sigarSwapMtd.invoke(sigar));
            }
            // Purposely catch generic exception.
            catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Falha ao obter Hyperic Sigar CPU load (irá retornar -1): " + e.getMessage());
                }
            }
        }

        return -1;
    }

    private long getSigarSwapLivre() {
        if (sigar != null) {
            try {
                return (Long)sigarSwapFreeMtd.invoke(sigarSwapMtd.invoke(sigar));
            }
            // Purposely catch generic exception.
            catch (Exception e) {
                if (log.isDebugEnabled()) {
                    log.debug("Falha ao obter Hyperic Sigar CPU load (irá retornar -1): " + e.getMessage());
                }
            }
        }

        return -1;
    }

    public double[][] getSigarSobrecargas(){

        double[][] resposta = new double[6][os.getAvailableProcessors()];

        if(sigar != null) {
            try {
                Object[] retorno = (Object[]) sigarCpuPercListMtd.invoke(sigar);
                for (int i = 0; i < retorno.length; i++) {
                    resposta[0][i] = (Double) sigarCpuCombinedMtd.invoke(retorno[i]);
                    resposta[1][i] = (Double) sigarCpuUserMtd.invoke(retorno[i]);
                    resposta[2][i] = (Double) sigarCpuSysMtd.invoke(retorno[i]);
                    resposta[3][i] = (Double) sigarCpuIdleMtd.invoke(retorno[i]);
                    resposta[4][i] = (Double) sigarCpuWaitMtd.invoke(retorno[i]);
                    resposta[5][i] = (Double) sigarCpuNiceMtd.invoke(retorno[i]);
                }
            } catch (IllegalAccessException ex) {
                Logger.getLogger(SystemAnalytics.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(SystemAnalytics.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvocationTargetException ex) {
                Logger.getLogger(SystemAnalytics.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return resposta;
    }

    public static void main(String[] args) throws InterruptedException {
    	
        SystemAnalytics glm = new SystemAnalytics();

        GridNodeExecutionMetrics gm = glm.getMetrics();
        
        for(int i = 0; i < 1000; i++){
            gm = glm.getMetrics();

            System.out.println(gm);

            Thread.sleep(1000);
        }


    }

}
