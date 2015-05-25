package com.sabre.tripcase.tcp.post.process;

import gate.util.Out;

import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sabre.tripcase.tcp.common.dto.Airline;

/**
 * 
 * @author Venkat Pedapudi
 *
 */
public class ContentFinalProcess {
	
	private static final Logger LOG = LoggerFactory.getLogger(ContentFinalProcess.class);
	
	/**
	 * 
	 * @param ex
	 */
	public void processFinalResults(Exchange ex){
		System.out.println(ex.getIn().getMessageId());
		Map<String,Object> headers=ex.getIn().getHeaders();
		System.out.println(headers.get("RequestID"));
		System.out.println(ex.getIn().getBody());
		List totalList=(List)ex.getIn().getBody();
		Map<String,List<Airline>> map=(Map<String, List<Airline>>) totalList.get(0);
		List<Airline> airlineList =map.get("Gate");
		
		for (int i = 0; i < airlineList.size(); i++) 
		{
			Out.prln("*****************ITINERARY******************************");
			Out.prln("AirLines Name : " + airlineList.get(i).getName());
			Out.prln("AirLines Number :" + airlineList.get(i).getFlightNumber());
			Out.prln("Departure :" + airlineList.get(i).getDepartLocation());	
			Out.prln("Arrival :" + airlineList.get(i).getArriveLocation());
			Out.prln("Departure Date : " + airlineList.get(i).getDepartDate());
			Out.prln("Departure Time : " + airlineList.get(i).getDepartTime());
			Out.prln("Arrival Date : " + airlineList.get(i).getArrivalDate());
			Out.prln("Arrival Time : " + airlineList.get(i).getArrivalTime());
		
		}
	}

}
