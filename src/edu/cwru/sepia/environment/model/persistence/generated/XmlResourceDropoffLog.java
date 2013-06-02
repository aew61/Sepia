//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.05.07 at 12:46:41 AM EDT 
//


package edu.cwru.sepia.environment.model.persistence.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import edu.cwru.sepia.environment.model.state.ResourceType;


/**
 * <p>Java class for ResourceDropoffLog complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResourceDropoffLog">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="depositAmount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="gathererID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="depotID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="controller" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="resourceType" type="{}ResourceType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResourceDropoffLog", propOrder = {
    "depositAmount",
    "gathererID",
    "depotID",
    "controller",
    "resourceType"
})
public class XmlResourceDropoffLog {

    protected int depositAmount;
    protected int gathererID;
    protected int depotID;
    protected int controller;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(Adapter3 .class)
    protected ResourceType resourceType;

    /**
     * Gets the value of the depositAmount property.
     * 
     */
    public int getDepositAmount() {
        return depositAmount;
    }

    /**
     * Sets the value of the depositAmount property.
     * 
     */
    public void setDepositAmount(int value) {
        this.depositAmount = value;
    }

    /**
     * Gets the value of the gathererID property.
     * 
     */
    public int getGathererID() {
        return gathererID;
    }

    /**
     * Sets the value of the gathererID property.
     * 
     */
    public void setGathererID(int value) {
        this.gathererID = value;
    }

    /**
     * Gets the value of the depotID property.
     * 
     */
    public int getDepotID() {
        return depotID;
    }

    /**
     * Sets the value of the depotID property.
     * 
     */
    public void setDepotID(int value) {
        this.depotID = value;
    }

    /**
     * Gets the value of the controller property.
     * 
     */
    public int getController() {
        return controller;
    }

    /**
     * Sets the value of the controller property.
     * 
     */
    public void setController(int value) {
        this.controller = value;
    }

    /**
     * Gets the value of the resourceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public ResourceType getResourceType() {
        return resourceType;
    }

    /**
     * Sets the value of the resourceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourceType(ResourceType value) {
        this.resourceType = value;
    }

}
