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
 * <p>Java class for MainMemoryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MainMemoryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;any namespace='##other' maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="RAMSize" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="RAMAvailable" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="VirtualSize" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;attribute name="VirtualAvailable" type="{http://www.w3.org/2001/XMLSchema}long" />
 *       &lt;anyAttribute namespace='##other'/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MainMemoryType", propOrder = {
    "any"
})
public class MainMemoryType {

    @XmlAnyElement(lax = true)
    protected List<Object> any;
    @XmlAttribute(name = "RAMSize", namespace = "http://mds.globus.org/glue/ce/1.1")
    protected Long ramSize;
    @XmlAttribute(name = "RAMAvailable", namespace = "http://mds.globus.org/glue/ce/1.1")
    protected Long ramAvailable;
    @XmlAttribute(name = "VirtualSize", namespace = "http://mds.globus.org/glue/ce/1.1")
    protected Long virtualSize;
    @XmlAttribute(name = "VirtualAvailable", namespace = "http://mds.globus.org/glue/ce/1.1")
    protected Long virtualAvailable;
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
     * Gets the value of the ramSize property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRAMSize() {
        return ramSize;
    }

    /**
     * Sets the value of the ramSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRAMSize(Long value) {
        this.ramSize = value;
    }

    /**
     * Gets the value of the ramAvailable property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getRAMAvailable() {
        return ramAvailable;
    }

    /**
     * Sets the value of the ramAvailable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setRAMAvailable(Long value) {
        this.ramAvailable = value;
    }

    /**
     * Gets the value of the virtualSize property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVirtualSize() {
        return virtualSize;
    }

    /**
     * Sets the value of the virtualSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVirtualSize(Long value) {
        this.virtualSize = value;
    }

    /**
     * Gets the value of the virtualAvailable property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getVirtualAvailable() {
        return virtualAvailable;
    }

    /**
     * Sets the value of the virtualAvailable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setVirtualAvailable(Long value) {
        this.virtualAvailable = value;
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
