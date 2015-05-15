package com.sabre.tripcase.tcp.common.sourcetypes;

import com.sabre.tripcase.tcp.common.validation.util;

public class Airlines {
	
	private boolean airLines 			= false;
	private boolean ticket_Flag 		= false;
	private  boolean terminal_Flag 	= false;
	private  boolean departure_Flag 	= false;
	private  boolean arrival_Flag 		= false;
	private  boolean takeoff_Flag 		= false;
	private  boolean landing_Flag 		= false;
	private  boolean airclass_Flag 	= false;
	private  boolean seat_Flag 		= false;
	private  boolean airbus_Flag 		= false;
	private  boolean operated_by_Flag 	= false;
	private  boolean itinerary_Flag 	= false;
	private  boolean confirmation_Flag = false;
	
	private  int nSourceFiles = 0;
	private  int nDesteFiles = 0;
	private  int nValidFileds = 0;


	private  final String airlineFile = "(?i).*airline.*|.*flight.*|.*airway.*|.*aircraft.*|.*FLT.*";	

	private  final String ticket		= "(?i).*ticket.*";
	private  final String terminal		= "(?i).*terminal.*";
	private  final String departure 	= "(?i).*depart.*";
	private  final String arrival 		= "(?i).*arriv[ea].*";
	private  final String takeoff 		= "(?i).*Take-off.*";
	private  final String landing 		= "(?i).*landing.*";
	private  final String airclass 	= "(?i).*class.*";
	private  final String seat 		= "(?i).*seat.*";
	private  final String airbus 		= "(?i).*airbus.*|.*aircraft.*";
	private  final String operated_by 	= "(?i).*operated.*";
	private  final String itinerary	= "(?i).*air(\\s)itinerary.*";
	private  final String confirmation = "(?i).*air\\s(?i)confirmation.*";	
	
	private final  String NEWLINE = "\\r?\\n";	

	/**
	 * @param textStr
	 * @return
	 */
	public boolean isAirlines(String textStr)
	{
		boolean isAirlines = false;

		nSourceFiles++;	
		resetFlags();

		String strLine[] = null;
		strLine = textStr.split(NEWLINE);

		//Read File Line By Line
		for(int i=0;i<strLine.length;i++)
		{								
			if (strLine[i].matches(airlineFile) && airLines == false) {	
				airLines  = true;							
			}

			if ( strLine[i].matches(ticket) && ticket_Flag == false ) {	
				System.out.println("Attribute considared as valid air travel :" + ticket);
				nValidFileds++;		
				ticket_Flag =  true;
			}
			if ( strLine[i].matches(terminal) && terminal_Flag == false ) {
				System.out.println("Attribute considared as valid air travel :" + terminal);
				nValidFileds++;	
				terminal_Flag = true;
			}
			if ( strLine[i].matches(departure) && departure_Flag == false ) {
				System.out.println("Attribute considared as valid air travel :" + departure);
				nValidFileds++;	
				departure_Flag = true;
			}
			if ( strLine[i].matches(arrival) && arrival_Flag == false ) {
				System.out.println("Attribute considared as valid air travel :" + arrival);
				nValidFileds++;	
				arrival_Flag = true;
			}
			if ( strLine[i].matches(takeoff) && takeoff_Flag == false ) {
				System.out.println("Attribute considared as valid air travel :" + takeoff);
				nValidFileds++;	
				takeoff_Flag = true;
			}
			if ( strLine[i].matches(landing) && landing_Flag == false ) {
				System.out.println("Attribute considared as valid air travel :" + landing);
				nValidFileds++;	
				landing_Flag = true;
			}
			if ( strLine[i].matches(airclass) && airclass_Flag == false ) {	
				System.out.println("Filed considared as valid air travel :" + airclass);
				nValidFileds++;
				airclass_Flag = true;
			}
			if ( strLine[i].matches(seat) && seat_Flag == false ) {
				System.out.println("Attribute considared as valid air travel :" + seat);
				nValidFileds++;	
				seat_Flag = true;
			}
			if ( strLine[i].matches(airbus) && airbus_Flag == false ) {	
				System.out.println("Attribute considared as valid air travel :" + airbus);
				nValidFileds++;
				airbus_Flag = true;
			}
			if (strLine[i].matches(operated_by) && operated_by_Flag == false ) {	
				System.out.println("Attribute considared as valid air travel :" + operated_by);
				nValidFileds++;	
				operated_by_Flag = true;
			}
			if ( strLine[i].matches(confirmation) && itinerary_Flag == false ) {
				System.out.println("Attribute considared as valid air travel :" + confirmation);
				nValidFileds++;	
				itinerary_Flag = true;
			}	
			if ( strLine[i].matches(itinerary) && confirmation_Flag == false ) {
				System.out.println("Attribute considared as valid air travel :" + itinerary);
				nValidFileds++;	
				confirmation_Flag = true;
			}						

			if (nValidFileds > 3 && airLines == true) {	
				System.out.println("Number of Valid Fileds identified are :: " + nValidFileds);
				isAirlines = true;
				nDesteFiles++;
				nValidFileds = 0;			
				break;
			}							
		}
		return isAirlines;
	}
	
	private void resetFlags() {
		ticket_Flag= false; 
		terminal_Flag= false; 
		departure_Flag= false; 
		arrival_Flag= false; 
		takeoff_Flag= false; 
		landing_Flag= false; 
		airclass_Flag= false; 
		seat_Flag= false; 
		airbus_Flag= false; 
		operated_by_Flag= false; 
		itinerary_Flag= false; 
		confirmation_Flag= false;	

		airLines = false;

		nValidFileds = 0;
	}
}


