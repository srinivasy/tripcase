package com.sabre.tripcase.tcp.common.preprocess;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CleanText {
	
	/* Make this entire block configurable (Munish) */	
	/* **********************************************************************************************************/
	private final static String AIR_HEADER = "(?i).*\\bAir\\b.*|.*flight.*|.*FLT.*|.*Airway.*|.*airline.*|.*((CONFIRMATION)+(\\s)+(NUMBERS)).*|"
			+ ".*((people)+(\\s+)+(Traveling)).*";	
	
	private final String HOTEL_HEADER	= "^[Hh]OTEL$|^HOTEL$|(?i).*HOTEL.*|(?i).*Lodging.*|(?i).*Room.*";
	private final String CAR_HEADER	= "(?i).*\\bCAR\\b.*|CAR|.*((Car)+(\\s+)+(Type)).*";
	private final String GENERAL_HEADERS		= ".*ITINERARY.*|(?i).*Itinerary$|(?i)^Reservations$";
	private final String MISC_HEADERS = ".*((Air)+(\\s+)+(conditioning)).*|.*((Automatic)+(\\s+)+(Air)).*";
	private final String AIR_FOOTER	= ".*Operated by|.*(\"Seats Requested\").*|.*Cabin.*";
	/* **********************************************************************************************************/
	
	private final static String NEWLINE = "\\r?\\n";
	private final static String NEWLINE1 = "\n";
	
	
	public String seggregateSectionWise(String input) {

		String strLine[] = null;
		strLine = input.split(NEWLINE);
		String sLine = null;
		String output="";

		boolean validData = true;
		
		try 
		{
			for(int i=0;i<strLine.length;i++)
			{
				sLine= textCleanUp(strLine[i]);	
				sLine = convertHTML2Text(sLine);
				sLine= sLine.trim();
				
				if (sLine.length() == 0 || sLine.equals("") || sLine.isEmpty()) {					
					continue;
				}					
					
				if (sLine.matches(HOTEL_HEADER) ) {	
					validData  = false;		
				}
				if (sLine.matches(CAR_HEADER) ) {	
					validData  = false;	
				}
				
				if (sLine.matches(AIR_HEADER) || sLine.matches(GENERAL_HEADERS) ) {	
					validData  = true;	
				}
				
				if (sLine.matches(MISC_HEADERS) ) {
					validData  = false;	
			}
						
				if (validData == true) {
					output = output.concat(NEWLINE1);
					output = output.concat(sLine);
				}
			}
		}
		catch (Exception ex){
			System.out.println("Failed to save the content in to File" + ex.getMessage()); 
		}
		return output;
	}
	
	private String textCleanUp(String bodyText){
		Pattern p1 = Pattern.compile("^\\s*$", Pattern.MULTILINE);
		Matcher m = p1.matcher(bodyText);
		bodyText=m.replaceAll("");
		bodyText=bodyText.replaceAll("&nbsp;", " ").trim();	
		return bodyText;
	}
	
	private String convertHTML2Text(String htmlStr) {
		return htmlStr.replaceAll("<!--.*?-->", "").replaceAll("<[^>]+>", "");	
	}

}
