/**
 * 
 */
package com.sabre.tripcase.tcp.opennlp.utils;

import java.util.Comparator;

/**
 * @author Nalini Kanta
 *
 */
class HeaderOrder implements Comparator<String>{
	 
    @Override
    public int compare(String str1, String str2) {
    	

    	 if(str2.length() >= str1.length()){
    		return 1;
    	}
    	else{
    		return -1;
    	}
       // return str1.compareTo(str2);
    }
     
}