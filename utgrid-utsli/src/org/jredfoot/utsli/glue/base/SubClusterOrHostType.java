//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.08.15 at 02:55:49 PM BRT 
//


package org.jredfoot.utsli.glue.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * 
 * 	     A subcluster contains at least one host, as well as having properties
 * 	     associated with it that represent the state of every host within the
 * 	     subcluster.
 * 	
 * 	     TODO: need some specification of uniqueness. It is reasonable for
 * 	     fairly simple type such as architecture to say that it MAY NOT appear
 * 	     on both a Host and its containing SubCluster, but for filesystems
 * 	     (for example) it might be desirable to put some filesystems on the
 * 	     SubCluster (eg. nfs or pvfs) and some on the 
 * 	     Hosts (eg. /scratch)
 * 	  
 * 
 * <p>Java class for SubClusterOrHostType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SubClusterOrHostType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Benchmark" type="{http://mds.globus.org/glue/ce/1.1}BenchmarkType" minOccurs="0"/>
 *         &lt;element name="Processor" type="{http://mds.globus.org/glue/ce/1.1}ProcessorType" minOccurs="0"/>
 *         &lt;element name="MainMemory" type="{http://mds.globus.org/glue/ce/1.1}MainMemoryType" minOccurs="0"/>
 *         &lt;element name="OperatingSystem" type="{http://mds.globus.org/glue/ce/1.1}OperatingSystemType" minOccurs="0"/>
 *         &lt;element name="StorageDevice" type="{http://mds.globus.org/glue/ce/1.1}StorageDeviceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Architecture" type="{http://mds.globus.org/glue/ce/1.1}ArchitectureType" minOccurs="0"/>
 *         &lt;element name="ApplicationSoftware" type="{http://mds.globus.org/glue/ce/1.1}ApplicationSoftwareType" minOccurs="0"/>
 *         &lt;element name="FileSystem" type="{http://mds.globus.org/glue/ce/1.1}FileSystemType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="NetworkAdapter" type="{http://mds.globus.org/glue/ce/1.1}NetworkAdapterType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;any namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://mds.globus.org/glue/ce/1.1}IdentifiableEntity"/>
 *       &lt;anyAttribute namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubClusterOrHostType", propOrder = {
    "benchmark",
    "processor",
    "mainMemory",
    "operatingSystem",
    "storageDevice",
    "architecture",
    "applicationSoftware",
    "fileSystem",
    "networkAdapter",
    "any"
})
@XmlSeeAlso({
    HostType.class,
    SubClusterType.class
})
public class SubClusterOrHostType {

    @XmlElement(name = "Benchmark")
    protected BenchmarkType benchmark;
    @XmlElement(name = "Processor")
    protected ProcessorType processor;
    @XmlElement(name = "MainMemory")
    protected MainMemoryType mainMemory;
    @XmlElement(name = "OperatingSystem")
    protected OperatingSystemType operatingSystem;
    @XmlElement(name = "StorageDevice")
    protected List<StorageDeviceType> storageDevice;
    @XmlElement(name = "Architecture")
    protected ArchitectureType architecture;
    @XmlElement(name = "ApplicationSoftware")
    protected ApplicationSoftwareType applicationSoftware;
    @XmlElement(name = "FileSystem")
    protected List<FileSystemType> fileSystem;
    @XmlElement(name = "NetworkAdapter")
    protected List<NetworkAdapterType> networkAdapter;
    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlAttribute(name = "Name", namespace = "http://mds.globus.org/glue/ce/1.1")
    protected String name;
    @XmlAttribute(name = "UniqueID", namespace = "http://mds.globus.org/glue/ce/1.1")
    protected String uniqueID;
    @XmlAttribute(name = "InformationServiceURL", namespace = "http://mds.globus.org/glue/ce/1.1")
    @XmlSchemaType(name = "anyURI")
    protected String informationServiceURL;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

    /**
     * Gets the value of the benchmark property.
     * 
     * @return
     *     possible object is
     *     {@link BenchmarkType }
     *     
     */
    public BenchmarkType getBenchmark() {
        return benchmark;
    }

    /**
     * Sets the value of the benchmark property.
     * 
     * @param value
     *     allowed object is
     *     {@link BenchmarkType }
     *     
     */
    public void setBenchmark(BenchmarkType value) {
        this.benchmark = value;
    }

    /**
     * Gets the value of the processor property.
     * 
     * @return
     *     possible object is
     *     {@link ProcessorType }
     *     
     */
    public ProcessorType getProcessor() {
        return processor;
    }

    /**
     * Sets the value of the processor property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProcessorType }
     *     
     */
    public void setProcessor(ProcessorType value) {
        this.processor = value;
    }

    /**
     * Gets the value of the mainMemory property.
     * 
     * @return
     *     possible object is
     *     {@link MainMemoryType }
     *     
     */
    public MainMemoryType getMainMemory() {
        return mainMemory;
    }

    /**
     * Sets the value of the mainMemory property.
     * 
     * @param value
     *     allowed object is
     *     {@link MainMemoryType }
     *     
     */
    public void setMainMemory(MainMemoryType value) {
        this.mainMemory = value;
    }

    /**
     * Gets the value of the operatingSystem property.
     * 
     * @return
     *     possible object is
     *     {@link OperatingSystemType }
     *     
     */
    public OperatingSystemType getOperatingSystem() {
        return operatingSystem;
    }

    /**
     * Sets the value of the operatingSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperatingSystemType }
     *     
     */
    public void setOperatingSystem(OperatingSystemType value) {
        this.operatingSystem = value;
    }

    /**
     * Gets the value of the storageDevice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the storageDevice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStorageDevice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StorageDeviceType }
     * 
     * 
     */
    public List<StorageDeviceType> getStorageDevice() {
        if (storageDevice == null) {
            storageDevice = new ArrayList<StorageDeviceType>();
        }
        return this.storageDevice;
    }

    /**
     * Gets the value of the architecture property.
     * 
     * @return
     *     possible object is
     *     {@link ArchitectureType }
     *     
     */
    public ArchitectureType getArchitecture() {
        return architecture;
    }

    /**
     * Sets the value of the architecture property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArchitectureType }
     *     
     */
    public void setArchitecture(ArchitectureType value) {
        this.architecture = value;
    }

    /**
     * Gets the value of the applicationSoftware property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationSoftwareType }
     *     
     */
    public ApplicationSoftwareType getApplicationSoftware() {
        return applicationSoftware;
    }

    /**
     * Sets the value of the applicationSoftware property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationSoftwareType }
     *     
     */
    public void setApplicationSoftware(ApplicationSoftwareType value) {
        this.applicationSoftware = value;
    }

    /**
     * Gets the value of the fileSystem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fileSystem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFileSystem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FileSystemType }
     * 
     * 
     */
    public List<FileSystemType> getFileSystem() {
        if (fileSystem == null) {
            fileSystem = new ArrayList<FileSystemType>();
        }
        return this.fileSystem;
    }

    /**
     * Gets the value of the networkAdapter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the networkAdapter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNetworkAdapter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NetworkAdapterType }
     * 
     * 
     */
    public List<NetworkAdapterType> getNetworkAdapter() {
        if (networkAdapter == null) {
            networkAdapter = new ArrayList<NetworkAdapterType>();
        }
        return this.networkAdapter;
    }

    /**
     * Gets the value of the any property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the any property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAny().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getAny() {
        if (any == null) {
            any = new ArrayList<Object>();
        }
        return this.any;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the uniqueID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueID() {
        return uniqueID;
    }

    /**
     * Sets the value of the uniqueID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueID(String value) {
        this.uniqueID = value;
    }

    /**
     * Gets the value of the informationServiceURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInformationServiceURL() {
        return informationServiceURL;
    }

    /**
     * Sets the value of the informationServiceURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInformationServiceURL(String value) {
        this.informationServiceURL = value;
    }

    /**
     * Gets a map that contains attributes that aren't bound to any typed property on this class.
     * 
     * <p>
     * the map is keyed by the name of the attribute and 
     * the value is the string value of the attribute.
     * 
     * the map returned by this method is live, and you can add new attribute
     * by updating the map directly. Because of this design, there's no setter.
     * 
     * 
     * @return
     *     always non-null
     */
    public Map<QName, String> getOtherAttributes() {
        return otherAttributes;
    }

}
