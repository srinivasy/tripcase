package com.motivity.ml;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class TesClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Declare hashmap for stroing records		
		Map<String,String> hashmap = new HashMap<String, String>();		
		
		String fileName = "C:\\sebre\\2019587.txt";
	    File file = new File(fileName);
	    StringBuffer sb = new StringBuffer("");
	    int ch;
	    try
	    {
	    	FileInputStream fileReader =new FileInputStream(file);
	    	
	    	try{
		    	while((ch=fileReader.read()) != -1){
		    		sb.append((char)ch);
		    	}
	    	}
	    	catch (IOException e) {
	            System.out.println("Exception :" + e.getMessage());
	        }
	    }
	    catch(FileNotFoundException ex){
	    	 System.out.println("Exception :" + ex.getMessage());    	
	    }
		
	    System.out.println("Storing values are : ");
	    
		//Match name		
		Pattern pName= Pattern.compile("N.*:\\s(.*)");
		
		
		Matcher mName = pName.matcher(sb);//.matches();
		if (mName.find()){
			System.out.println( mName.group());
			
			String group = mName.group();
			String matchedName = group.substring(group.lastIndexOf(":")+1, group.length());
			//String matchedName = sb.substring(mName.end(), mName.regionEnd());
			hashmap.put("Names", matchedName);
		}
		
		//Match airline
		Pattern pAirLine= Pattern.compile("T.*(\\s+)A.*:\\s(.*)");
		Matcher mAirline = pAirLine.matcher(sb);//.matches();
		if (mAirline.find()){
			System.out.println( mAirline.group());
			
			String group = mAirline.group();
			String matchedAirline = group.substring(group.lastIndexOf(":")+1, group.length());
			hashmap.put("AirLine", matchedAirline);
		}
		
		//ticket number
		Pattern pTicket= Pattern.compile("(Ti.*):\\s(.*)[0-9]");
		Matcher mTicket = pTicket.matcher(sb);//.matches();
		if (mTicket.find()){
			System.out.println( mTicket.group());
			
			String group = mTicket.group();
			String TicketNumber = group.substring(group.lastIndexOf(":")+1, group.length());
			hashmap.put("Ticket Number", TicketNumber);
		}
		
		//Match booking refernce
		
		Pattern pRef= Pattern.compile("B.*\\sR.*:\\s(.*)");
		//Matcher mRef = pRef.matcher(sb);//.matches();
		Matcher mRef = pRef.matcher("Booking  Ref :  UNRKCP");//.matches();
		if (mRef.find()){
			System.out.println(mRef.group());
			
			String group = mRef.group();
			String matchedRef = group.substring(group.lastIndexOf(":")+1, group.length());
			hashmap.put("Booking Ref", matchedRef);
		}			
	    
	  //iterate through map and display records.
	  		Set set = hashmap.entrySet();
	  		Iterator it = set.iterator();
	  		//while(it.hasNext())
	  			//System.out.println(it.next());
	  		
	  		System.out.println("\n\nResults are:");
	  		//get map values by key
	  		String refValue = hashmap.get("Booking Ref");
	  		System.out.println("Booking Ref :" + refValue);
	  		
	  		String nameValue = hashmap.get("Names");
	  		System.out.println("Names :" + nameValue);
	  		
	  		String airLineValue = hashmap.get("AirLine");
	  		System.out.println("AirLine :" + airLineValue);
	  		
	  		String ticketValue = hashmap.get("Ticket Number");
	  		System.out.println("Ticket Number :" + ticketValue);
	    
	}

}
