//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2012.01.14 at 11:11:32 AM EST 
//


package edu.cwru.SimpleRTS.environment.state.persistence.generated;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import edu.cwru.SimpleRTS.model.resource.ResourceNode.Type;

public class Adapter3
    extends XmlAdapter<String, Type>
{


    public Type unmarshal(String value) {
        return (Type.valueOf(value));
    }

    public String marshal(Type value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

}
