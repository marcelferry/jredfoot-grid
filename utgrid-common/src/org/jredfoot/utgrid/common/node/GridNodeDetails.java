package org.jredfoot.utgrid.common.node;

import java.io.Serializable;


/**
 * Classe que representa uma estacao de trabalho utilizada no ambiente do Grid.
 * 
 * @author marcelo
 *
 */
public class GridNodeDetails implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -7894383499711034943L;
	
	private OperatingSystem os;
    private Java java;
    private Hardware hardware;
    
    public OperatingSystem getOperatingSystem() {
		return os;
	}
    
    public void setOperatingSystem(OperatingSystem os) {
		this.os = os;
	}
    
    public Java getJava() {
		return java;
	}
    
    public void setJava(Java java) {
		this.java = java;
	}
    
    public Hardware getHardware() {
		return hardware;
	}
    
    public void setHardware(Hardware hardware) {
		this.hardware = hardware;
	}
    
    public static class OperatingSystem implements Serializable{
    	/**
		 * 
		 */
		private static final long serialVersionUID = 4693523228579539518L;
		
		/**
    	 * Descricao do SO
    	 */
    	private String description;
    	/** 
    	 * Nome do SO
    	 */
    	private String name;
    	/**
    	 * Arquitetura
    	 */
    	private String arch;
    	
    	private String machine;
    	/** 
    	 * Versao do sistema operacional
    	 */
    	private String version;
    	/**
    	 * Fornecedor do SO
    	 */
    	private String vendor;
    	/**
    	 * 
    	 */
    	private String dataModel;
		
    	public OperatingSystem() {
			// TODO Auto-generated constructor stub
		}
    	
    	/**
		 * @return the descricao
		 */
		public String getDescription() {
			return description;
		}
		/**
		 * @param description the descricao to set
		 */
		public void setDescription(String description) {
			this.description = description;
		}
		/**
		 * @return the nome
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the nome to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the arch
		 */
		public String getArch() {
			return arch;
		}
		/**
		 * @param arch the arch to set
		 */
		public void setArch(String arch) {
			this.arch = arch;
		}
		/**
		 * @return the maquina
		 */
		public String getMachine() {
			return machine;
		}
		/**
		 * @param machine the maquina to set
		 */
		public void setMachine(String machine) {
			this.machine = machine;
		}
		/**
		 * @return the versao
		 */
		public String getVersion() {
			return version;
		}
		/**
		 * @param version the versao to set
		 */
		public void setVersion(String version) {
			this.version = version;
		}
		/**
		 * @return the fornecedor
		 */
		public String getVendor() {
			return vendor;
		}
		/**
		 * @param vendor the fornecedor to set
		 */
		public void setVendor(String vendor) {
			this.vendor = vendor;
		}
		/**
		 * @return the dataModel
		 */
		public String getDataModel() {
			return dataModel;
		}
		/**
		 * @param dataModel the dataModel to set
		 */
		public void setDataModel(String dataModel) {
			this.dataModel = dataModel;
		}
    	
    	
    	
    }
    
    public static class Java implements Serializable {
    	
    	/**
		 * 
		 */
		private static final long serialVersionUID = 8609161094162144175L;
		
		private String version;
    	private String vendor;
    	private String javaHome;
    	
    	public Java() {
			// TODO Auto-generated constructor stub
		}
    	
    	public String getVersion() {
			return version;
		}
    	
    	public void setVersion(String version) {
			this.version = version;
		}
    	
    	public String getVendor() {
			return vendor;
		}
    	
    	public void setVendor(String vendor) {
			this.vendor = vendor;
		}
    	
    	public String getJavaHome() {
			return javaHome;
		}
    	
    	public void setJavaHome(String javaHome) {
			this.javaHome = javaHome;
		}
    	
    }
    
    public static class Hardware implements Serializable {
    	
    	/**
		 * 
		 */
		private static final long serialVersionUID = 4285352676845935679L;
		
		private String vendor;
        private String model;
        private int mhz;
        private int totalCores;
        private int totalSockets;
        private int coresPerSocket;
        private long cacheSize;
        private long memTotal;
        private long memSwap;
        
        public Hardware() {
			// TODO Auto-generated constructor stub
		}
        

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public void setMhz(int mhz) {
            this.mhz = mhz;
        }

        public void setTotalCPUs(int totalCores) {
            this.totalCores = totalCores;
        }

        public void setPhysicalCPUs(int totalSockets) {
            this.totalSockets = totalSockets;
        }

        public void setCoresPerCPU(int coresPerSocket) {
            this.coresPerSocket = coresPerSocket;
        }

        public void setCacheSize(long cacheSize) {
            this.cacheSize = cacheSize;
        }

        /**
         * @return the vendor
         */
        public String getVendor() {
            return vendor;
        }

        /**
         * @return the model
         */
        public String getModel() {
            return model;
        }

        /**
         * @return the mhz
         */
        public int getMhz() {
            return mhz;
        }

        /**
         * @return the totalCores
         */
        public int getTotalCores() {
            return totalCores;
        }

        /**
         * @return the totalSockets
         */
        public int getTotalSockets() {
            return totalSockets;
        }

        /**
         * @return the coresPerSocket
         */
        public int getCoresPerSocket() {
            return coresPerSocket;
        }

        /**
         * @return the cacheSize
         */
        public long getCacheSize() {
            return cacheSize;
        }
        
        public long getMemTotal() {
			return memTotal;
		}
        
        public void setMemSwap(long memSwap) {
			this.memSwap = memSwap;
		}
        
        public void setMemTotal(long memTotal) {
			this.memTotal = memTotal;
		}
        
        public long getMemSwap() {
			return memSwap;
		}
    }

}
