package com.sabre.tripcase.tcp.opennlp.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Airline {

	private String name;
	private String bookingReference;
	private String ticketNumber;
	private String ticketAirline;
	private List<Flight> flights;
	private Map<String, List<String>> data = new HashMap<String, List<String>>();
	


	public List<Flight> getFlights() {
		return flights;
	}
	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
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
}
