package org.jredfoot.utgrid.utsgd.persistence.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.jredfoot.utgrid.common.node.GridNode;
import org.jredfoot.utgrid.common.node.GridNodeDetails;
import org.jredfoot.utgrid.common.persistence.entities.GridEntity;

@Entity
@Table(name="GRID_NODES")
public class GridNodeEntity extends GridEntity<Long> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8885203524827538042L;
	
	@Column(name="name")
	private String nodeName;
	@Column(name="ip")
	private String nodeIP;
	@Column(name="mac")
	private String nodeAddress;
	@Column(name="type")
	private String nodeType;
	@Column(name="status")
	private String nodeStatus;

	// OS Information
	@Column(name="os_description")
	private String osDescription;
	@Column(name="os_name")
	private String osName;
	@Column(name="os_arch")
	private String osArch;
	@Column(name="os_machine")
	private String osMachine;
	@Column(name="os_version")
	private String osVersion;
	@Column(name="os_vendor")
	private String osVendor;
	@Column(name="os_data_model")
	private String osDataModel;
	
	// Java information
	@Column(name="jv_version")
	private String jvVersion;
	@Column(name="jv_vendor")
	private String jvVendor;
	@Column(name="jv_java_home")
	private String jvJavaHome;

	// Hardware information
	@Column(name="hw_vendor")
	private String hwVendor;
	@Column(name="hw_model")
    private String hwModel;
	@Column(name="hw_mhz")
    private int hwMhz;
	@Column(name="hw_total_cores")
    private int hwTotalCores;
	@Column(name="hw_total_sockets")
    private int hwTotalSockets;
	@Column(name="hw_cores_per_socket")
    private int hwCoresPerSocket;
	@Column(name="hw_cache_size")
    private long hwCacheSize;
	@Column(name="hw_mem_total")
    private long hwMemTotal;
	@Column(name="hw_mem_swap")
    private long hwMemSwap;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="register_date")
	private Date registerDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_date")
	private Date updateDate;
	@Column(name="register_status")
	private String status;

	@OneToMany(mappedBy="gridNode")
	@LazyCollection(LazyCollectionOption.FALSE) 
	private List<GridThreadEntity> threads;
	
	@OneToMany(mappedBy="gridNode")
	@LazyCollection(LazyCollectionOption.FALSE) 
	private List<GridStorageEntity> storages;

	public GridNodeEntity() {
		// TODO Auto-generated constructor stub
	}
	
	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public String getNodeIP() {
		return nodeIP;
	}

	public void setNodeIP(String nodeIP) {
		this.nodeIP = nodeIP;
	}
	
	public String getNodeAddress() {
		return nodeAddress;
	}
	
	public void setNodeAddress(String nodeAddress) {
		this.nodeAddress = nodeAddress;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getNodeStatus() {
		return nodeStatus;
	}

	public void setNodeStatus(String nodeStatus) {
		this.nodeStatus = nodeStatus;
	}

	public String getOsDescription() {
		return osDescription;
	}

	public void setOsDescription(String osDescription) {
		this.osDescription = osDescription;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getOsArch() {
		return osArch;
	}

	public void setOsArch(String osArch) {
		this.osArch = osArch;
	}

	public String getOsMachine() {
		return osMachine;
	}

	public void setOsMachine(String osMachine) {
		this.osMachine = osMachine;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getOsVendor() {
		return osVendor;
	}

	public void setOsVendor(String osVendor) {
		this.osVendor = osVendor;
	}

	public String getOsDataModel() {
		return osDataModel;
	}

	public void setOsDataModel(String osDataModel) {
		this.osDataModel = osDataModel;
	}

	public String getJvVersion() {
		return jvVersion;
	}

	public void setJvVersion(String jvVersion) {
		this.jvVersion = jvVersion;
	}

	public String getJvVendor() {
		return jvVendor;
	}

	public void setJvVendor(String jvVendor) {
		this.jvVendor = jvVendor;
	}

	public String getJvJavaHome() {
		return jvJavaHome;
	}

	public void setJvJavaHome(String jvJavaHome) {
		this.jvJavaHome = jvJavaHome;
	}

	public String getHwVendor() {
		return hwVendor;
	}

	public void setHwVendor(String hwVendor) {
		this.hwVendor = hwVendor;
	}

	public String getHwModel() {
		return hwModel;
	}

	public void setHwModel(String hwModel) {
		this.hwModel = hwModel;
	}

	public int getHwMhz() {
		return hwMhz;
	}

	public void setHwMhz(int hwMhz) {
		this.hwMhz = hwMhz;
	}

	public int getHwTotalCores() {
		return hwTotalCores;
	}

	public void setHwTotalCores(int hwTotalCores) {
		this.hwTotalCores = hwTotalCores;
	}

	public int getHwTotalSockets() {
		return hwTotalSockets;
	}

	public void setHwTotalSockets(int hwTotalSockets) {
		this.hwTotalSockets = hwTotalSockets;
	}

	public int getHwCoresPerSocket() {
		return hwCoresPerSocket;
	}

	public void setHwCoresPerSocket(int hwCoresPerSocket) {
		this.hwCoresPerSocket = hwCoresPerSocket;
	}

	public long getHwCacheSize() {
		return hwCacheSize;
	}

	public void setHwCacheSize(long hwCacheSize) {
		this.hwCacheSize = hwCacheSize;
	}

	public long getHwMemTotal() {
		return hwMemTotal;
	}

	public void setHwMemTotal(long hwMemTotal) {
		this.hwMemTotal = hwMemTotal;
	}

	public long getHwMemSwap() {
		return hwMemSwap;
	}

	public void setHwMemSwap(long hwMemSwap) {
		this.hwMemSwap = hwMemSwap;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<GridThreadEntity> getThreads() {
		return threads;
	}

	public void setThreads(List<GridThreadEntity> threads) {
		this.threads = threads;
	}

	public List<GridStorageEntity> getStorages() {
		return storages;
	}

	public void setStorages(List<GridStorageEntity> storages) {
		this.storages = storages;
	}
	
	public static GridNode getDTO(GridNodeEntity gridNodeEntity){
		
		GridNode g = new GridNode();
		GridNodeEntity i = gridNodeEntity;
		g.setNodeId(i.getId());
		g.setNodeName(i.getNodeName());
		g.setNodeIP(i.getNodeIP());
		g.setNodeAddress(i.getNodeAddress());
		g.setNodeStatus(i.getNodeStatus());
		g.setNodeType(i.getNodeType());
		GridNodeDetails d = new GridNodeDetails();
		// OS
		if(d.getOperatingSystem() != null){
			d.getOperatingSystem().setArch(i.getOsArch());
			d.getOperatingSystem().setDataModel(i.getOsDataModel());
			d.getOperatingSystem().setDescription(i.getOsDescription());
			d.getOperatingSystem().setMachine(i.getOsMachine());
			d.getOperatingSystem().setName(i.getOsName());
			d.getOperatingSystem().setVendor(i.getOsVendor());
			d.getOperatingSystem().setVersion(i.getOsVersion());
		}
		// Java
		if(d.getJava() != null){
			d.getJava().setJavaHome(i.getJvJavaHome());
			d.getJava().setVendor(i.getJvVendor());
			d.getJava().setVersion(i.getJvVersion());
		}
		// Hardware
		if(d.getOperatingSystem() != null){
			d.getHardware().setCacheSize(i.getHwCacheSize());
			d.getHardware().setCoresPerCPU(i.getHwCoresPerSocket());
			d.getHardware().setMemSwap(i.getHwMemSwap());
			d.getHardware().setMemTotal(i.getHwMemTotal());
			d.getHardware().setMhz(i.getHwMhz());
			d.getHardware().setModel(i.getHwModel());
			d.getHardware().setPhysicalCPUs(i.getHwTotalSockets());
			d.getHardware().setTotalCPUs(i.getHwTotalCores());
			d.getHardware().setVendor(i.getHwVendor());
		}
		
		if(i.getStorages() != null){
			for(GridStorageEntity gs : i.getStorages() ){
				g.getStorages().add(GridStorageEntity.getDTO(gs));
			}
		}

		if(i.getThreads() != null){
			for(GridThreadEntity gt : i.getThreads() ){
				g.getThreads().add(GridThreadEntity.getDTO(gt));
			}
		}
		return g;
	}
	
	public GridNodeEntity(GridNode node){

		
		this.setNodeName(node.getNodeName());
		this.setNodeIP(node.getNodeIP());
		this.setNodeAddress(node.getNodeAddress());
		this.setNodeStatus(node.getNodeStatus());
		this.setNodeType(node.getNodeType());
		// OS
		this.setOsArch( node.getDetails().getOperatingSystem().getArch() );
		this.setOsDataModel( node.getDetails().getOperatingSystem().getDataModel());
		this.setOsDescription( node.getDetails().getOperatingSystem().getDescription());
		this.setOsMachine( node.getDetails().getOperatingSystem().getMachine());
		this.setOsName( node.getDetails().getOperatingSystem().getName());
		this.setOsVendor( node.getDetails().getOperatingSystem().getVendor());
		this.setOsVersion( node.getDetails().getOperatingSystem().getVersion());
		
		// Java
		this.setJvJavaHome( node.getDetails().getJava().getJavaHome() );
		this.setJvVendor( node.getDetails().getJava().getVendor() );
		this.setJvVersion( node.getDetails().getJava().getVersion() );
		
		// Hardware
		this.setHwCacheSize( node.getDetails().getHardware().getCacheSize());
		this.setHwCoresPerSocket( node.getDetails().getHardware().getCoresPerSocket());
		this.setHwMemSwap( node.getDetails().getHardware().getMemSwap());
		this.setHwMemTotal( node.getDetails().getHardware().getMemTotal());
		this.setHwMhz( node.getDetails().getHardware().getMhz());
		this.setHwModel( node.getDetails().getHardware().getModel());
		this.setHwTotalSockets( node.getDetails().getHardware().getTotalSockets());
		this.setHwTotalCores( node.getDetails().getHardware().getTotalCores());
		this.setHwVendor( node.getDetails().getHardware().getVendor());
		
		//TODO: Fazer implementação do Threads e Storage - Preguiça mental
		
	}
	
	public static List<GridNode> getDTOList(List<GridNodeEntity> list){
		List<GridNode> retorno = new ArrayList<GridNode>();
		for(GridNodeEntity entity : list){
			retorno.add(getDTO(entity));
		}
		return retorno;
	}
}

