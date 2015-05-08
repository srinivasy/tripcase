package com.sabre.tripcase.tcp.common.sourcetypes;

import com.sabre.tripcase.tcp.common.validation.util;

/**
 * @author naresh.k
 *
 */
public class Train {
	
	private String trainDataMatchStr;
	
	private String trainFileMatchStr;

	public String getTrainDataMatchStr() {
		return trainDataMatchStr;
	}

	public void setTrainDataMatchStr(String trainDataMatchStr) {
		this.trainDataMatchStr = trainDataMatchStr;
	}

	public String getTrainFileMatchStr() {
		return trainFileMatchStr;
	}

	public void setTrainFileMatchStr(String trainFileMatchStr) {
		this.trainFileMatchStr = trainFileMatchStr;
	}

	
	public boolean isTrain(String textStr){

		boolean bIsTrainFile	= false;
		boolean bIsTrainData = false;
		boolean bIsTrain 	= false;

		
		 String splitStr[] = null;
		 
		 // Split the sting of text by new line and match for the airlines source.
		 splitStr = textStr.split(util.NEWLINE);
		 
		 for(int i=0;i<splitStr.length;i++){
	            
	            //identifies airlines source files
	            if (splitStr[i].matches(trainFileMatchStr) && bIsTrainFile == false) {						
	            	bIsTrainFile = true;		    			
	    		}	
	            //identifies airlines source files have itinerary data or just keywords.
	            if (splitStr[i].matches(trainDataMatchStr) && bIsTrainData == false) {						
	            	bIsTrainData = true;		    			
	    		}
	            // It determines valid itinerary airlines data.
	            if(bIsTrainFile == true && bIsTrainData == true)
	            {
	            	bIsTrain = true;
	            	break;
	            }
		 }

		return bIsTrain;
	}

}
