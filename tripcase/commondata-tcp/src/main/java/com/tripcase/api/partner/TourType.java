//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.05.19 at 03:57:00 PM IST 
//


package com.tripcase.api.partner;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TourType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="TourType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="cruise"/>
 *     &lt;enumeration value="ferry"/>
 *     &lt;enumeration value="rail"/>
 *     &lt;enumeration value="ground_transportation"/>
 *     &lt;enumeration value="vehicle"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "TourType")
@XmlEnum
public enum TourType {

    @XmlEnumValue("cruise")
    CRUISE("cruise"),
    @XmlEnumValue("ferry")
    FERRY("ferry"),
    @XmlEnumValue("rail")
    RAIL("rail"),
    @XmlEnumValue("ground_transportation")
    GROUND_TRANSPORTATION("ground_transportation"),
    @XmlEnumValue("vehicle")
    VEHICLE("vehicle");
    private final String value;

    TourType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TourType fromValue(String v) {
        for (TourType c: TourType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
