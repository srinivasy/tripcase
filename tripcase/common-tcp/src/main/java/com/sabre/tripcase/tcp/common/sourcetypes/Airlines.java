package com.sabre.tripcase.tcp.common.sourcetypes;

import com.sabre.tripcase.tcp.common.validation.util;

public class Airlines {

	private String airlinesDataMatchStr;

	private String airlinesFileMatchStr;

	public String getAirlinesDataMatchStr() {
		return airlinesDataMatchStr;
	}

	public void setAirlinesDataMatchStr(String airlinesDataMatchStr) {
		this.airlinesDataMatchStr = airlinesDataMatchStr;
	}

	public String getAirlinesFileMatchStr() {
		return airlinesFileMatchStr;
	}

	public void setAirlinesFileMatchStr(String airlinesFileMatchStr) {
		this.airlinesFileMatchStr = airlinesFileMatchStr;
	}

	/**
	 * @param textStr
	 * @return
	 */
	public boolean isAirlines(String textStr){

		boolean bIsAirLinesFile	= false;
		boolean bIsAirLinesData = false;
		boolean bIsAirLines 	= false;

		
		 String splitStr[] = null;
		 
		 // Split the sting of text by new line and match for the airlines source.
		 splitStr = textStr.split(util.NEWLINE);
		 
		 for(int i=0;i<splitStr.length;i++){
	            
	            //identifies airlines source files
	            if (splitStr[i].matches(airlinesFileMatchStr) && bIsAirLinesFile == false) {						
	            	bIsAirLinesFile = true;		    			
	    		}	
	            //identifies airlines source files have itinerary data or just keywords.
	            if (splitStr[i].matches(airlinesDataMatchStr) && bIsAirLinesData == false) {						
	            	bIsAirLinesData = true;		    			
	    		}
	            // It determines valid itinerary airlines data.
	            if(bIsAirLinesFile == true && bIsAirLinesData == true)
	            {
	            	bIsAirLines = true;
	            	break;
	            }
		 }

		return bIsAirLines;
	}

}
