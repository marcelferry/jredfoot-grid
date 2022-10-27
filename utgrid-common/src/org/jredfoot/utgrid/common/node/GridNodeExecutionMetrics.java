package org.jredfoot.utgrid.common.node;

import java.io.*;


public interface GridNodeExecutionMetrics extends Serializable {

    public int getProcessorsAvailable();

    public double getCurrentOverloadProcessor();

    public double getUserOverloadProcessor();

    public double getSystemOverloadProcessor();

    public double getIdleTimeProcessor();

    public double getCombinedOverloadProcessor();

    public double[] getUserOverloadProcessors();

    public double[] getSystemOverloadProcessors();

    public double[] getIdleTimeProcessors();

    public double[] getCombinedOverloadProcessors();

    public long getMemHeapInitialized();

    public long getMemHeapUsed();

    public long getMemHeapCommited();

    public long getMemHeapMax();

    public long getMemNoHeapInitialized();

    public long getMemNoHeapUsed();

    public long getMemNoHeapCommited();

    public long getMemNoHeapMax();

    public long getMemUsed();

    public long getMemFree();

    public long getMemTotal();

    public long getMemSwapUsed();

    public long getMemSwapFree();

    public long getMemSwapTotal();

    public long getUptime();

    public long getStartedTime();

    public int getThreadCount();

    public int getPeakThreadCount();

    public long getTotalStartedThreadCount();

    public int getDaemonThreadCount();
}
