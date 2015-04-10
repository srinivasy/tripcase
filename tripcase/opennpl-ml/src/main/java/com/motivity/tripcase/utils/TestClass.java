package com.motivity.tripcase.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.StringTokenizer;

public class TestClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		parseOutputFromHtmlParser();
	}

	public static String parseOutputFromHtmlParser(){
		String line = null;
		//String emailInput =  "Passenger name" ; 
		// "Passenger name|Frequent flyer # (Airline)|Ticket number|Special needs|";
		String emailInput = "Passenger name| |Ticket number|Special needs| | |";
		
		
		try {
			
			
			
			/*String s = "a:b";
	        String res[] = s.split(":");
	        System.out.println(res.length);
	        for (int i = 0; i < res.length; i++)
	            System.out.println(res[i]);
	    	*/
			
			StringTokenizer st2 = new StringTokenizer(emailInput, "\\|");
			
			//String[] emailInputArray = emailInput.split("|");
			
			Map<String,String>  passengerDetailsMap=new LinkedHashMap<String,String>();
			
			while (st2.hasMoreElements()) {
				System.out.println(st2.nextElement());
			}
			
			HashSet<String> headerSet = new HashSet<String>();
			
			
			BufferedReader br = new BufferedReader(new FileReader("D:\\projects\\TripCase_Project\\Technical\\email_templates\\OpenNLP\\Airline\\airlinemapping.txt"));
		/*	while ((line = br.readLine()) != null) {
				if(line != null && line!= ""){
					for(int i=0;i<emailInputArray.length;i++){
					if(line.trim().contains(emailInputArray[i])){
						//passengerDetailsMap.put(emailInputArray[i], "");	
						headerSet.add(emailInputArray[i]);
						System.out.println("Header Identified");						
					}else{
						//passengerDetailsMap.put(headerSet, "");
					}
						
					}
				
				}
			}*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return line;
			
	}
}
