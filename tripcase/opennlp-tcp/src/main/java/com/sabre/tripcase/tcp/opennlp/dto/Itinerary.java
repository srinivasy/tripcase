/**
 * 
 */
package com.sabre.tripcase.tcp.opennlp.dto;


/**
 * @author Nalini Kanta
 *
 */
public class Itinerary {
	
	private static Itinerary itinerary=null;
	private static String transactionId=null;
	
	private static Airline airline=null;
	private static Hotel hotel=null;
	private static Car car=null;
	
	private Itinerary(){}

	/**
	 * @return the itinerary
	 */
	public static void init() {
		
		if(null==itinerary){
			itinerary=new Itinerary();
			airline=new Airline();
			hotel=new Hotel();
			car=new Car();
		}
		
	}
	
	public static void  destroy() {
		
		if(null!=itinerary){
			itinerary=null;
			airline=null;
			hotel=null;
			car=null;
		}
	
	}

	/**
	 * @return the airline
	 */
	public static Airline getAirline() {
		return airline;
	}

	/**
	 * @param airline the airline to set
	 */
	public static void setAirline(Airline airline) {
		Itinerary.airline = airline;
	}

	/**
	 * @return the hotel
	 */
	public static Hotel getHotel() {
		return hotel;
	}

	/**
	 * @param hotel the hotel to set
	 */
	public static void setHotel(Hotel hotel) {
		Itinerary.hotel = hotel;
	}

	/**
	 * @return the car
	 */
	public static Car getCar() {
		return car;
	}

	/**
	 * @param car the car to set
	 */
	public static void setCar(Car car) {
		Itinerary.car = car;
	}

	/**
	 * @return the transactionId
	 */
	public static String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public static void setTransactionId(String transactionId) {
		Itinerary.transactionId = transactionId;
	}


	

}
