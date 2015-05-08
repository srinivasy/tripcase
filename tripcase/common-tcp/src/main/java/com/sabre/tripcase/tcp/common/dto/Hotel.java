package com.sabre.tripcase.tcp.common.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hotel {

	private Map<String, List<String>> data = new HashMap<String, List<String>>();
	private String confirmationNumber;
	private String arrivalDate;
	private String departureDate;
	private String numberOfNights;
	private String roomTypeRequested;
	private String nightlyRate;
	private String checkInTime;
	private String checkOutTime;
	private String hotelPolicy;
	private String cancellationPolicy;
	
	
	public Map<String, List<String>> getData() {
		return data;
	}
	public void setData(Map<String, List<String>> data) {
		this.data = data;
	}
	public String getConfirmationNumber() {
		return confirmationNumber;
	}
	public void setConfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}
	public String getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}
	public String getNumberOfNights() {
		return numberOfNights;
	}
	public void setNumberOfNights(String numberOfNights) {
		this.numberOfNights = numberOfNights;
	}
	public String getRoomTypeRequested() {
		return roomTypeRequested;
	}
	public void setRoomTypeRequested(String roomTypeRequested) {
		this.roomTypeRequested = roomTypeRequested;
	}
	public String getNightlyRate() {
		return nightlyRate;
	}
	public void setNightlyRate(String nightlyRate) {
		this.nightlyRate = nightlyRate;
	}
	public String getCheckInTime() {
		return checkInTime;
	}
	public void setCheckInTime(String checkInTime) {
		this.checkInTime = checkInTime;
	}
	public String getCheckOutTime() {
		return checkOutTime;
	}
	public void setCheckOutTime(String checkOutTime) {
		this.checkOutTime = checkOutTime;
	}
	public String getHotelPolicy() {
		return hotelPolicy;
	}
	public void setHotelPolicy(String hotelPolicy) {
		this.hotelPolicy = hotelPolicy;
	}
	public String getCancellationPolicy() {
		return cancellationPolicy;
	}
	public void setCancellationPolicy(String cancellationPolicy) {
		this.cancellationPolicy = cancellationPolicy;
	}
	
}
