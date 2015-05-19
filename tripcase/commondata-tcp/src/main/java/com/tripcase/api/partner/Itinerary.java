//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.05.19 at 03:57:00 PM IST 
//


package com.tripcase.api.partner;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Itinerary complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Itinerary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Air" type="{http://tripcase.com/api/partner}Air" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="LocationEvent" type="{http://tripcase.com/api/partner}LocationEvent" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="TourEvent" type="{http://tripcase.com/api/partner}TourEvent" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ItineraryReference" type="{http://tripcase.com/api/partner}ItineraryReference" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Reference" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ReferenceTerm" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Host" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Pcc" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="DkNumber" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ClientId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="TravellerLastName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="CreateDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="GTSiteName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="GTSubsiteName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Itinerary", propOrder = {
    "air",
    "locationEvent",
    "tourEvent",
    "itineraryReference"
})
public class Itinerary {

    @XmlElement(name = "Air")
    protected List<Air> air;
    @XmlElement(name = "LocationEvent")
    protected List<LocationEvent> locationEvent;
    @XmlElement(name = "TourEvent")
    protected List<TourEvent> tourEvent;
    @XmlElement(name = "ItineraryReference")
    protected List<ItineraryReference> itineraryReference;
    @XmlAttribute(name = "Name")
    protected String name;
    @XmlAttribute(name = "Reference", required = true)
    protected String reference;
    @XmlAttribute(name = "ReferenceTerm", required = true)
    protected String referenceTerm;
    @XmlAttribute(name = "Host")
    protected String host;
    @XmlAttribute(name = "Pcc")
    protected String pcc;
    @XmlAttribute(name = "DkNumber")
    protected String dkNumber;
    @XmlAttribute(name = "ClientId")
    protected String clientId;
    @XmlAttribute(name = "TravellerLastName")
    protected String travellerLastName;
    @XmlAttribute(name = "CreateDateTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createDateTime;
    @XmlAttribute(name = "GTSiteName")
    protected String gtSiteName;
    @XmlAttribute(name = "GTSubsiteName")
    protected String gtSubsiteName;

    /**
     * Gets the value of the air property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the air property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAir().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Air }
     * 
     * 
     */
    public List<Air> getAir() {
        if (air == null) {
            air = new ArrayList<Air>();
        }
        return this.air;
    }

    /**
     * Gets the value of the locationEvent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the locationEvent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocationEvent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LocationEvent }
     * 
     * 
     */
    public List<LocationEvent> getLocationEvent() {
        if (locationEvent == null) {
            locationEvent = new ArrayList<LocationEvent>();
        }
        return this.locationEvent;
    }

    /**
     * Gets the value of the tourEvent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tourEvent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTourEvent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TourEvent }
     * 
     * 
     */
    public List<TourEvent> getTourEvent() {
        if (tourEvent == null) {
            tourEvent = new ArrayList<TourEvent>();
        }
        return this.tourEvent;
    }

    /**
     * Gets the value of the itineraryReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itineraryReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItineraryReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItineraryReference }
     * 
     * 
     */
    public List<ItineraryReference> getItineraryReference() {
        if (itineraryReference == null) {
            itineraryReference = new ArrayList<ItineraryReference>();
        }
        return this.itineraryReference;
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
     * Gets the value of the reference property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReference() {
        return reference;
    }

    /**
     * Sets the value of the reference property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReference(String value) {
        this.reference = value;
    }

    /**
     * Gets the value of the referenceTerm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceTerm() {
        return referenceTerm;
    }

    /**
     * Sets the value of the referenceTerm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceTerm(String value) {
        this.referenceTerm = value;
    }

    /**
     * Gets the value of the host property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHost() {
        return host;
    }

    /**
     * Sets the value of the host property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHost(String value) {
        this.host = value;
    }

    /**
     * Gets the value of the pcc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPcc() {
        return pcc;
    }

    /**
     * Sets the value of the pcc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPcc(String value) {
        this.pcc = value;
    }

    /**
     * Gets the value of the dkNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDkNumber() {
        return dkNumber;
    }

    /**
     * Sets the value of the dkNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDkNumber(String value) {
        this.dkNumber = value;
    }

    /**
     * Gets the value of the clientId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Sets the value of the clientId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientId(String value) {
        this.clientId = value;
    }

    /**
     * Gets the value of the travellerLastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTravellerLastName() {
        return travellerLastName;
    }

    /**
     * Sets the value of the travellerLastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTravellerLastName(String value) {
        this.travellerLastName = value;
    }

    /**
     * Gets the value of the createDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreateDateTime() {
        return createDateTime;
    }

    /**
     * Sets the value of the createDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreateDateTime(XMLGregorianCalendar value) {
        this.createDateTime = value;
    }

    /**
     * Gets the value of the gtSiteName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGTSiteName() {
        return gtSiteName;
    }

    /**
     * Sets the value of the gtSiteName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGTSiteName(String value) {
        this.gtSiteName = value;
    }

    /**
     * Gets the value of the gtSubsiteName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGTSubsiteName() {
        return gtSubsiteName;
    }

    /**
     * Sets the value of the gtSubsiteName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGTSubsiteName(String value) {
        this.gtSubsiteName = value;
    }

}
