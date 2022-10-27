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
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;


/**
 * <p>Java class for InfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;any namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="LRMSType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="LRMSVersion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="GRAMVersion" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="HostName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="GatekeeperPort" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="TotalCPUs" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;anyAttribute namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoType", propOrder = {
    "any"
})
public class InfoType {

    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlAttribute(name = "LRMSType", namespace = "http://mds.globus.org/glue/ce/1.1")
    protected String lrmsType;
    @XmlAttribute(name = "LRMSVersion", namespace = "http://mds.globus.org/glue/ce/1.1")
    protected String lrmsVersion;
    @XmlAttribute(name = "GRAMVersion", namespace = "http://mds.globus.org/glue/ce/1.1")
    protected String gramVersion;
    @XmlAttribute(name = "HostName", namespace = "http://mds.globus.org/glue/ce/1.1")
    protected String hostName;
    @XmlAttribute(name = "GatekeeperPort", namespace = "http://mds.globus.org/glue/ce/1.1")
    protected String gatekeeperPort;
    @XmlAttribute(name = "TotalCPUs", namespace = "http://mds.globus.org/glue/ce/1.1")
    protected String totalCPUs;
    @XmlAnyAttribute
    private Map<QName, String> otherAttributes = new HashMap<QName, String>();

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
     * Gets the value of the lrmsType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLRMSType() {
        return lrmsType;
    }

    /**
     * Sets the value of the lrmsType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLRMSType(String value) {
        this.lrmsType = value;
    }

    /**
     * Gets the value of the lrmsVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLRMSVersion() {
        return lrmsVersion;
    }

    /**
     * Sets the value of the lrmsVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLRMSVersion(String value) {
        this.lrmsVersion = value;
    }

    /**
     * Gets the value of the gramVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGRAMVersion() {
        return gramVersion;
    }

    /**
     * Sets the value of the gramVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGRAMVersion(String value) {
        this.gramVersion = value;
    }

    /**
     * Gets the value of the hostName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Sets the value of the hostName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostName(String value) {
        this.hostName = value;
    }

    /**
     * Gets the value of the gatekeeperPort property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGatekeeperPort() {
        return gatekeeperPort;
    }

    /**
     * Sets the value of the gatekeeperPort property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGatekeeperPort(String value) {
        this.gatekeeperPort = value;
    }

    /**
     * Gets the value of the totalCPUs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalCPUs() {
        return totalCPUs;
    }

    /**
     * Sets the value of the totalCPUs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalCPUs(String value) {
        this.totalCPUs = value;
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