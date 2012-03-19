//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.03.19 at 11:30:38 AM EDT 
//


package edu.cwru.SimpleRTS.environment.state.persistence.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;sequence maxOccurs="unbounded">
 *           &lt;element name="player" type="{}Player"/>
 *         &lt;/sequence>
 *         &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="resourceNode" type="{}ResourceNode"/>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="xExtent" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="yExtent" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="nextTargetID" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="nextTemplateID" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="revealedResources" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="fogOfWar" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "player",
    "resourceNode"
})
@XmlRootElement(name = "state")
public class XmlState {

    @XmlElement(required = true)
    protected List<XmlPlayer> player;
    protected List<XmlResourceNode> resourceNode;
    @XmlAttribute
    protected Integer xExtent;
    @XmlAttribute
    protected Integer yExtent;
    @XmlAttribute
    protected Integer nextTargetID;
    @XmlAttribute
    protected Integer nextTemplateID;
    @XmlAttribute
    protected Boolean revealedResources;
    @XmlAttribute
    protected Boolean fogOfWar;

    /**
     * Gets the value of the player property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the player property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPlayer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XmlPlayer }
     * 
     * 
     */
    public List<XmlPlayer> getPlayer() {
        if (player == null) {
            player = new ArrayList<XmlPlayer>();
        }
        return this.player;
    }

    /**
     * Gets the value of the resourceNode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resourceNode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResourceNode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link XmlResourceNode }
     * 
     * 
     */
    public List<XmlResourceNode> getResourceNode() {
        if (resourceNode == null) {
            resourceNode = new ArrayList<XmlResourceNode>();
        }
        return this.resourceNode;
    }

    /**
     * Gets the value of the xExtent property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getXExtent() {
        return xExtent;
    }

    /**
     * Sets the value of the xExtent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setXExtent(Integer value) {
        this.xExtent = value;
    }

    /**
     * Gets the value of the yExtent property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getYExtent() {
        return yExtent;
    }

    /**
     * Sets the value of the yExtent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setYExtent(Integer value) {
        this.yExtent = value;
    }

    /**
     * Gets the value of the nextTargetID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNextTargetID() {
        return nextTargetID;
    }

    /**
     * Sets the value of the nextTargetID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNextTargetID(Integer value) {
        this.nextTargetID = value;
    }

    /**
     * Gets the value of the nextTemplateID property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNextTemplateID() {
        return nextTemplateID;
    }

    /**
     * Sets the value of the nextTemplateID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNextTemplateID(Integer value) {
        this.nextTemplateID = value;
    }

    /**
     * Gets the value of the revealedResources property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRevealedResources() {
        return revealedResources;
    }

    /**
     * Sets the value of the revealedResources property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRevealedResources(Boolean value) {
        this.revealedResources = value;
    }

    /**
     * Gets the value of the fogOfWar property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFogOfWar() {
        return fogOfWar;
    }

    /**
     * Sets the value of the fogOfWar property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFogOfWar(Boolean value) {
        this.fogOfWar = value;
    }

}
