//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.06.12 at 06:59:58 PM EDT 
//


package edu.cwru.SimpleRTS.util.config.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Player complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Player">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AgentClass">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ClassName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                     &lt;element name="Argument" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;/sequence>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;choice>
 *           &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *             &lt;element name="Parameter" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;/sequence>
 *           &lt;element name="PropertyFile" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Player", propOrder = {
    "agentClass",
    "parameter",
    "propertyFile"
})
public class XmlPlayer {

    @XmlElement(name = "AgentClass", required = true)
    protected XmlPlayer.AgentClass agentClass;
    @XmlElement(name = "Parameter")
    protected List<String> parameter;
    @XmlElement(name = "PropertyFile")
    protected String propertyFile;
    @XmlAttribute(name = "Id")
    protected Integer id;

    /**
     * Gets the value of the agentClass property.
     * 
     * @return
     *     possible object is
     *     {@link XmlPlayer.AgentClass }
     *     
     */
    public XmlPlayer.AgentClass getAgentClass() {
        return agentClass;
    }

    /**
     * Sets the value of the agentClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link XmlPlayer.AgentClass }
     *     
     */
    public void setAgentClass(XmlPlayer.AgentClass value) {
        this.agentClass = value;
    }

    /**
     * Gets the value of the parameter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getParameter() {
        if (parameter == null) {
            parameter = new ArrayList<String>();
        }
        return this.parameter;
    }

    /**
     * Gets the value of the propertyFile property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropertyFile() {
        return propertyFile;
    }

    /**
     * Sets the value of the propertyFile property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropertyFile(String value) {
        this.propertyFile = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }


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
     *         &lt;element name="ClassName" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;sequence maxOccurs="unbounded" minOccurs="0">
     *           &lt;element name="Argument" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;/sequence>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "className",
        "argument"
    })
    public static class AgentClass {

        @XmlElement(name = "ClassName", required = true)
        protected String className;
        @XmlElement(name = "Argument")
        protected List<String> argument;

        /**
         * Gets the value of the className property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getClassName() {
            return className;
        }

        /**
         * Sets the value of the className property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setClassName(String value) {
            this.className = value;
        }

        /**
         * Gets the value of the argument property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the argument property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getArgument().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getArgument() {
            if (argument == null) {
                argument = new ArrayList<String>();
            }
            return this.argument;
        }

    }

}