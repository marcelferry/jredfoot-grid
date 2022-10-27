
package org.jredfoot.utgrid.common.node;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;



public class GridNodeExecutionMetricsImpl implements GridNodeExecutionMetrics, Externalizable {

    private int availProcs = -1;

    private double load = -1;

    private double mediaTempoUsuario;

    private double mediaTempoSistema;

    private double mediaTempoOcioso;

    private double mediaTempoCombinado;

    private double[] tempoUsuario;

    private double[] tempoSistema;

    private double[] tempoOcioso;

    private double[] tempoCombinado;

    private long heapNoInicio = -1;

    private long heapUsada = -1;

    private long heapComitada = -1;

    private long heapMax = -1;

    private long naoHeapInicio = -1;

    private long naoHeapUsada = -1;

    private long naoHeapComitada = -1;

    private long nonHeapMax = -1;

    private long memoriaTotal = -1;

    private long memoriaUsada = -1;

    private long memoriaLivre = -1;

    private long memoriaSwapTotal = -1;

    private long memoriaSwapUsada = -1;
    
    private long memoriaSwapLivre = -1;

    private long tempoAtivo = -1;

    private long horarioInicio = -1;

    private int threadCnt = -1;

    private int peakThreadCnt = -1;

    private long startedThreadCnt = -1;

    private int daemonThreadCnt = -1;



    public GridNodeExecutionMetricsImpl() {
        // No-op.
    }

    
    public GridNodeExecutionMetricsImpl(
        int availProcs,
        double load,
        double mediaTempoUsuario,
        double mediaTempoSistema,
        double mediaTempoOcioso,
        double mediaTempoCombinado,
        double[] tempoUsuario,
        double[] tempoSistema,
        double[] tempoOcioso,
        double[] tempoCombinado,
        long heapInit,
        long heapUsed,
        long heapCommitted,
        long heapMax,
        long nonHeapInit,
        long nonHeapUsed,
        long nonHeapCommitted,
        long nonHeapMax,
        long memoriaTotal,
        long memoriaUsada,
        long memoriaLivre,
        long memoriaSwapTotal,
        long memoriaSwapUsada,
        long memoriaSwapLivre,
        long upTime,
        long startTime,
        int threadCnt,
        int peakThreadCnt,
        long startedThreadCnt,
        int daemonThreadCnt) {
        this.availProcs = availProcs;
        this.load = load;
        this.mediaTempoUsuario = mediaTempoUsuario;
        this.mediaTempoSistema = mediaTempoSistema;
        this.mediaTempoOcioso = mediaTempoOcioso;
        this.mediaTempoCombinado = mediaTempoCombinado;
        this.tempoUsuario = tempoUsuario;
        this.tempoSistema = tempoSistema;
        this.tempoOcioso = tempoOcioso;
        this.tempoCombinado = tempoCombinado;
        this.heapNoInicio = heapInit;
        this.heapUsada = heapUsed;
        this.heapComitada = heapCommitted;
        this.heapMax = heapMax;
        this.naoHeapInicio = nonHeapInit;
        this.naoHeapUsada = nonHeapUsed;
        this.naoHeapComitada = nonHeapCommitted;
        this.nonHeapMax = nonHeapMax;
        this.memoriaTotal = memoriaTotal;
        this.memoriaUsada = memoriaUsada;
        this.memoriaLivre = memoriaLivre;
        this.memoriaSwapTotal = memoriaSwapTotal;
        this.memoriaSwapUsada = memoriaSwapUsada;
        this.memoriaSwapLivre = memoriaSwapLivre;
        this.tempoAtivo = upTime;
        this.horarioInicio = startTime;
        this.threadCnt = threadCnt;
        this.peakThreadCnt = peakThreadCnt;
        this.startedThreadCnt = startedThreadCnt;
        this.daemonThreadCnt = daemonThreadCnt;
    }

     public GridNodeExecutionMetricsImpl(
        int availProcs,
        double load,
        long heapInit,
        long heapUsed,
        long heapCommitted,
        long heapMax,
        long nonHeapInit,
        long nonHeapUsed,
        long nonHeapCommitted,
        long nonHeapMax,
        long upTime,
        long startTime,
        int threadCnt,
        int peakThreadCnt,
        long startedThreadCnt,
        int daemonThreadCnt) {
        this.availProcs = availProcs;
        this.load = load;
        this.heapNoInicio = heapInit;
        this.heapUsada = heapUsed;
        this.heapComitada = heapCommitted;
        this.heapMax = heapMax;
        this.naoHeapInicio = nonHeapInit;
        this.naoHeapUsada = nonHeapUsed;
        this.naoHeapComitada = nonHeapCommitted;
        this.nonHeapMax = nonHeapMax;
        this.tempoAtivo = upTime;
        this.horarioInicio = startTime;
        this.threadCnt = threadCnt;
        this.peakThreadCnt = peakThreadCnt;
        this.startedThreadCnt = startedThreadCnt;
        this.daemonThreadCnt = daemonThreadCnt;
    }

    /**
     * {@inheritDoc}
     */
    public int getProcessorsAvailable() {
        return availProcs;
    }

    /**
     * {@inheritDoc}
     */
    public double getCurrentOverloadProcessor() {
        return load;
    }

    public double getUserOverloadProcessor() {
        return mediaTempoUsuario;
    }

    public double getSystemOverloadProcessor() {
        return mediaTempoSistema;
    }

    public double getIdleTimeProcessor() {
        return mediaTempoOcioso;
    }

    public double getCombinedOverloadProcessor() {
        return mediaTempoCombinado;
    }

    public double[] getUserOverloadProcessors() {
        return tempoUsuario;
    }

    public double[] getSystemOverloadProcessors() {
        return tempoSistema;
    }

    public double[] getIdleTimeProcessors() {
        return tempoOcioso;
    }

    public double[] getCombinedOverloadProcessors() {
        return tempoCombinado;
    }


    /**
     * {@inheritDoc}
     */
    public long getMemHeapInitialized() {
        return heapNoInicio;
    }

    /**
     * {@inheritDoc}
     */
    public long getMemHeapUsed() {
        return heapUsada;
    }

    /**
     * {@inheritDoc}
     */
    public long getMemHeapCommited() {
        return heapComitada;
    }

    /**
     * {@inheritDoc}
     */
    public long getMemHeapMax() {
        return heapMax;
    }

    /**
     * {@inheritDoc}
     */
    public long getMemNoHeapInitialized() {
        return naoHeapInicio;
    }

    /**
     * {@inheritDoc}
     */
    public long getMemNoHeapUsed() {
        return naoHeapUsada;
    }

    /**
     * {@inheritDoc}
     */
    public long getMemNoHeapCommited() {
        return naoHeapComitada;
    }

    /**
     * {@inheritDoc}
     */
    public long getMemNoHeapMax() {
        return nonHeapMax;
    }

    public long getMemTotal() {
        return memoriaTotal;
    }

    public long getMemUsed() {
        return memoriaUsada;
    }

    public long getMemFree() {
        return memoriaLivre;    }

    public long getMemSwapTotal() {
        return memoriaSwapTotal;
    }

    public long getMemSwapUsed() {
        return memoriaSwapUsada;
    }

    public long getMemSwapFree() {
        return memoriaSwapLivre;
    }

    /**
     * {@inheritDoc}
     */
    public long getUptime() {
        return tempoAtivo;
    }

    /**
     * {@inheritDoc}
     */
    public long getStartedTime() {
        return horarioInicio;
    }

    /**
     * {@inheritDoc}
     */
    public int getThreadCount() {
        return threadCnt;
    }

    /**
     * {@inheritDoc}
     */
    public int getPeakThreadCount() {
        return peakThreadCnt;
    }

    /**
     * {@inheritDoc}
     */
    public long getTotalStartedThreadCount() {
        return startedThreadCnt;
    }

    /**
     * {@inheritDoc}
     */
    public int getDaemonThreadCount() {
        return daemonThreadCnt;
    }

    /**
     * Sets available processors.
     *
     * @param availProcs Available processors.
     */
    public void setAvailableProcessors(int availProcs) {
        this.availProcs = availProcs;
    }

    /**
     * Sets CPU load average over last minute.
     *
     * @param load CPU load average over last minute.
     */
    public void setCurrentCpuLoad(double load) {
        this.load = load;
    }

    /**
     * Sets heap initial memory.
     *
     * @param heapInit Heap initial memory.
     */
    public void setHeapMemoryInitialized(long heapInit) {
        this.heapNoInicio = heapInit;
    }

    /**
     * Sets used heap memory.
     *
     * @param heapUsed Used heap memory.
     */
    public void setHeapMemoryUsed(long heapUsed) {
        this.heapUsada = heapUsed;
    }

    /**
     * Sets committed heap memory.
     *
     * @param heapCommitted Committed heap memory.
     */
    public void setHeapMemoryCommitted(long heapCommitted) {
        this.heapComitada = heapCommitted;
    }

    /**
     * Sets maximum possible heap memory.
     *
     * @param heapMax Maximum possible heap memory.
     */
    public void setHeapMemoryMaximum(long heapMax) {
        this.heapMax = heapMax;
    }

    /**
     * Sets initial non-heap memory.
     *
     * @param nonHeapInit Initial non-heap memory.
     */
    public void setNonHeapMemoryInitialized(long nonHeapInit) {
        this.naoHeapInicio = nonHeapInit;
    }

    /**
     * Sets used non-heap memory.
     *
     * @param nonHeapUsed Used non-heap memory.
     */
    public void setNonHeapMemoryUsed(long nonHeapUsed) {
        this.naoHeapUsada = nonHeapUsed;
    }

    /**
     * Sets committed non-heap memory.
     *
     * @param nonHeapCommitted Committed non-heap memory.
     */
    public void setNonHeapMemoryCommitted(long nonHeapCommitted) {
        this.naoHeapComitada = nonHeapCommitted;
    }

    /**
     * Sets maximum possible non-heap memory.
     *
     * @param nonHeapMax Maximum possible non-heap memory.
     */
    public void setNonHeapMemoryMaximum(long nonHeapMax) {
        this.nonHeapMax = nonHeapMax;
    }

    /**
     * Sets VM up time.
     *
     * @param upTime VN up time.
     */
    public void setUpTime(long upTime) {
        this.tempoAtivo = upTime;
    }

    /**
     * Sets VM start time.
     *
     * @param startTime VM start time.
     */
    public void setStartTime(long startTime) {
        this.horarioInicio = startTime;
    }

    /**
     * Sets thread count.
     *
     * @param threadCnt Thread count.
     */
    public void setThreadCount(int threadCnt) {
        this.threadCnt = threadCnt;
    }

    /**
     * Sets peak thread count.
     *
     * @param peakThreadCnt Peak thread count.
     */
    public void setPeakThreadCount(int peakThreadCnt) {
        this.peakThreadCnt = peakThreadCnt;
    }

    /**
     * Sets started thread count.
     *
     * @param startedThreadCnt Started thread count.
     */
    public void setTotalStartedThreadCount(long startedThreadCnt) {
        this.startedThreadCnt = startedThreadCnt;
    }

    /**
     * Sets daemon thread count.
     *
     * @param daemonThreadCnt Daemon thread count.
     */
    public void setDaemonThreadCount(int daemonThreadCnt) {
        this.daemonThreadCnt = daemonThreadCnt;
    }

    /**
     * {@inheritDoc}
     */
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(availProcs);
        out.writeDouble(load);
        out.writeLong(heapNoInicio);
        out.writeLong(heapUsada);
        out.writeLong(heapComitada);
        out.writeLong(heapMax);
        out.writeLong(naoHeapInicio);
        out.writeLong(naoHeapUsada);
        out.writeLong(naoHeapComitada);
        out.writeLong(nonHeapMax);
        out.writeLong(tempoAtivo);
        out.writeLong(horarioInicio);
        out.writeInt(threadCnt);
        out.writeInt(peakThreadCnt);
        out.writeLong(startedThreadCnt);
        out.writeInt(daemonThreadCnt);
    }

    /**
     * {@inheritDoc}
     */
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        availProcs = in.readInt();
        load = in.readDouble();
        heapNoInicio = in.readLong();
        heapUsada = in.readLong();
        heapComitada = in.readLong();
        heapMax = in.readLong();
        naoHeapInicio = in.readLong();
        naoHeapUsada = in.readLong();
        naoHeapComitada = in.readLong();
        nonHeapMax = in.readLong();
        tempoAtivo = in.readLong();
        horarioInicio = in.readLong();
        threadCnt = in.readInt();
        peakThreadCnt = in.readInt();
        startedThreadCnt = in.readLong();
        daemonThreadCnt = in.readInt();
    }

    @Override
    public String toString() {
        Class<?> c1 = GridNodeExecutionMetricsImpl.class;
        StringBuffer retorno = new StringBuffer();
        boolean colocaVirgula = false;

        for(Method m : c1.getDeclaredMethods())
        {

            if(m.getName().startsWith("get")){
                if(colocaVirgula)
                {
                    retorno.append("\r\n");
                }
                try {
                    retorno.append( m.getName().substring(3) + ":" );
                    if(m.invoke(this) instanceof double[]  ){
                        double[] resposta = (double[])m.invoke(this);
                        retorno.append("[");
                        boolean inicio = true;
                        for(int j = 0; j < resposta.length; j++ ){
                            if(!inicio)
                                retorno.append(", ");
                            retorno.append(resposta[j]);
                            inicio = false;
                        }
                        retorno.append("]");
                    } else {
                        retorno.append(m.invoke(this));
                    }
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GridNodeExecutionMetricsImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(GridNodeExecutionMetricsImpl.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(GridNodeExecutionMetricsImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                colocaVirgula = true;
            }
        }
        retorno.append("\r\n------------------------------------\r\n");
        return retorno.toString();
    }



}
