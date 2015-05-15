package com.sabre.tripcase.tcp.common.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Airline {

	private String name;
	private String bookingReference;
	private String ticketNumber;
	private String ticketAirline;
	private String flightNumber;
	private String departDate;
	private String departLocation;
	private String departTime;
	private String arriveLocation;
	private String status;
	private String clazz;
	private String meal;
	private String airfare;
	private String seat;

	private Map<String, List<String>> data = new HashMap<String, List<String>>();

	public Map<String, List<String>> getData() {
		return data;
	}
	public void setData(Map<String, List<String>> data) {
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	private String to;

	public String getTicketAirline() {
		return ticketAirline;
	}
	public void setTicketAirline(String ticketAirline) {
		this.ticketAirline = ticketAirline;
	}
	public String getBookingReference() {
		return bookingReference;
	}
	public void setBookingReference(String bookingReference) {
		this.bookingReference = bookingReference;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	/**
	 * @return the flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * @return the departDate
	 */
	public String getDepartDate() {
		return departDate;
	}
	/**
	 * @param departDate the departDate to set
	 */
	public void setDepartDate(String departDate) {
		this.departDate = departDate;
	}
	/**
	 * @return the departLocation
	 */
	public String getDepartLocation() {
		return departLocation;
	}
	/**
	 * @param departLocation the departLocation to set
	 */
	public void setDepartLocation(String departLocation) {
		this.departLocation = departLocation;
	}
	/**
	 * @return the departTime
	 */
	public String getDepartTime() {
		return departTime;
	}
	/**
	 * @param departTime the departTime to set
	 */
	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}
	/**
	 * @return the arriveLocation
	 */
	public String getArriveLocation() {
		return arriveLocation;
	}
	/**
	 * @param arriveLocation the arriveLocation to set
	 */
	public void setArriveLocation(String arriveLocation) {
		this.arriveLocation = arriveLocation;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the clazz
	 */
	public String getClazz() {
		return clazz;
	}
	/**
	 * @param clazz the clazz to set
	 */
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
	/**
	 * @return the meal
	 */
	public String getMeal() {
		return meal;
	}
	/**
	 * @param meal the meal to set
	 */
	public void setMeal(String meal) {
		this.meal = meal;
	}
	/**
	 * @return the airfare
	 */
	public String getAirfare() {
		return airfare;
	}
	/**
	 * @param airfare the airfare to set
	 */
	public void setAirfare(String airfare) {
		this.airfare = airfare;
	}
	/**
	 * @return the seat
	 */
	public String getSeat() {
		return seat;
	}
	/**
	 * @param seat the seat to set
	 */
	public void setSeat(String seat) {
		this.seat = seat;
	}
}
