/**
 * 
 */
package com.sabre.tripcase.tcp.common.utils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nalini Kanta
 * Munish Gupta
 *
 */
public class CleanUpUtility {
	
	public static String removeBlankLines(String bodyText){
		Pattern p1 = Pattern.compile("^\\s*$\\n", Pattern.MULTILINE);
		//Pattern p2 = Pattern.compile("[\r\n][\r\n\t]*[\r\n]", Pattern.MULTILINE);
		Matcher m = p1.matcher(bodyText.trim());
		bodyText=m.replaceAll("").trim();
		bodyText=bodyText.replaceAll("\t", "").trim();
		bodyText=bodyText.replaceAll("\r", "").trim();
		//.replace("\n", ".").replace("(", "").replace(")", "");
		
		return bodyText;

	}
	
	public static String removeExtraWhiteSpaces(String line){
		return line.replaceAll(" +", " ");
	}
	

	
	public static String[] cleanText(String text){
		
		String[] tokens=text.split("\n");
		String[] newTokens=null;
		ArrayList<String> newLines=new ArrayList<String>();
		String newStr="";
		for(int i=0;i<tokens.length;i++){
		newStr=tokens[i];
		newStr=newStr.trim();
		newStr=newStr.replaceAll("[^a-z A-Z 0-9 : . / , ]", " ");
		newStr=newStr.replaceAll(", ", " ").replaceAll(" ,", " ");
		newStr=newStr.replaceAll(",", " ");
		newStr=removeExtraWhiteSpaces(newStr);
//		newStr=newStr.replaceAll("\\s+$", "").trim();
//		newStr=newStr.replaceAll(": ", ":").replaceAll(" :", ":").trim();
//		newStr=newStr.replaceAll("\\(","");
//		newStr=newStr.replaceAll("\\)","");
//		newStr=newStr.replaceAll("-", " ").replaceAll("/", " ");
//		newStr=newStr.replaceAll("#", " ");
//		newStr=newStr.replaceAll("\\'", "");
//		newStr=newStr.replaceAll("`", "");
//		newStr=newStr.replaceAll("\\*", "");
//		newStr=newStr.replaceAll("â†’", " ");
//		newStr=newStr.replaceAll("\\?", "");
//		newStr=newStr.replaceAll("\t", "");
//		newStr=newStr.replaceAll("PM", "<timetype>PM</timetype> ");
//		newStr=newStr.replaceAll(" pm ", " PM ");
//		newStr=newStr.replaceAll(" am ", " AM ");
//		newStr=newStr.replaceAll("\\s?(am)"," AM");
//		newStr=newStr.replaceAll("\\s?(pm)"," PM");
//		newStr=newStr.replaceAll("Not Available", "NA");
//		newStr=newStr.replaceAll("(Monday|MONDAY)", " ");
//		newStr=newStr.replaceAll("(Tuesday|TUESDAY)", " ");
//		newStr=newStr.replaceAll("(Wednesday|WEDNESDAY)", " ");
//		newStr=newStr.replaceAll("(Thursday|THURSDAY)", " ");
//		newStr=newStr.replaceAll("(Friday|FRIDAY)", " ");
//		newStr=newStr.replaceAll("(Saturday|SATURDAY)", " ");
//		newStr=newStr.replaceAll("(Sunday|SUNDAY)", " ");
//		newStr=newStr.replaceAll(" Wed ", " ");
//		newStr=newStr.replaceAll("Reserved", " ");
//		newStr=newStr.replaceAll("R/Economy", "Economy");
//		newStr=newStr.replaceAll("City", " ");
//		newStr=newStr.replaceAll("Flight Number", "Flight_Number");
//		newStr=newStr.replaceAll(" Fri ", " ");
//		newStr=newStr.replaceAll(" FRI ", " ");
		
		newStr=newStr.replaceAll("Date issued", " ");
		
		newStr=removeColonExceptTimeValue(newStr);
		//newStr=removeDotExceptMoneyValue(newStr);
		if(!newStr.isEmpty()){
			newLines.add(newStr);
			//System.out.println("Char Array:::"+newStr.toCharArray());
		}
		
		//System.out.println("Tokens:"+newStr);
		}
		newTokens=new String[newLines.size()];
		newTokens=newLines.toArray(newTokens);
		
		return newTokens;
	}
	
	public static String removeColonExceptTimeValue(String newStr){
		String[] internalTokens=newStr.split(":");
		if(null!=internalTokens && internalTokens.length >1){
		StringBuilder rejoin=new StringBuilder();
		for(int i=0; i<internalTokens.length-1 ;i++){
		if(isNumeric(internalTokens[i].substring(internalTokens[i].length()-1, internalTokens[i].length())) && 
				isNumeric(internalTokens[i+1].substring(0,1))){
				internalTokens[i+1]=":"+internalTokens[i+1];
		}
		else{
			internalTokens[i+1]=" "+internalTokens[i+1];
		}
		}
		for(String intStr:internalTokens){
			rejoin.append(intStr);
		}
		
		return rejoin.toString();
		}
		else{
			return newStr;
		}
		
	}
	
	public static String removeDotExceptMoneyValue(String newStr){
		String[] internalTokens=newStr.split("\\.");
		if(null!=internalTokens && internalTokens.length >1){
		StringBuilder rejoin=new StringBuilder();
		for(int i=0; i<internalTokens.length-1 ;i++){
		if(isNumeric(internalTokens[i].substring(internalTokens[i].length()-2, internalTokens[i].length())) && 
				isNumeric(internalTokens[i+1].substring(0,1))){
				internalTokens[i+1]="."+internalTokens[i+1];
		}
		else{
			internalTokens[i+1]=" "+internalTokens[i+1];
		}
		}
		for(String intStr:internalTokens){
			rejoin.append(intStr);
		}
		return rejoin.toString();
		}
		else{
			return newStr;
		}
		
		
		
	}
	
	public static boolean isNumeric(String str)
	{
	  return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
	public static boolean isColoned(String str)
	{
	  return str.matches("-?\\s+(\\:\\s+)?");  //match a string with optional '-' and decimal.
	}

}
