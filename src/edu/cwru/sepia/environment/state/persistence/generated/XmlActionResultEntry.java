//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.13 at 09:38:30 PM EDT 
//


package edu.cwru.sepia.environment.state.persistence.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActionResultEntry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ActionResultEntry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="unitID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="actionResult" type="{}ActionResult"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActionResultEntry", propOrder = {
    "unitID",
    "actionResult"
})
public class XmlActionResultEntry {

    protected int unitID;
    @XmlElement(required = true)
    protected XmlActionResult actionResult;

    /**
     * Gets the value of the unitID property.
     * 
     */
    public int getUnitID() {
        return unitID;
    }

    /**
     * Sets the value of the unitID property.
     * 
     */
    public void setUnitID(int value) {
        this.unitID = value;
    }

    /**
     * Gets the value of the actionResult property.
     * 
     * @return
     *     possible object is
     *     {@link XmlActionResult }
     *     
     */
    public XmlActionResult getActionResult() {
        return actionResult;
    }

    /**
     * Sets the value of the actionResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlActionResult }
     *     
     */
    public void setActionResult(XmlActionResult value) {
        this.actionResult = value;
    }

}