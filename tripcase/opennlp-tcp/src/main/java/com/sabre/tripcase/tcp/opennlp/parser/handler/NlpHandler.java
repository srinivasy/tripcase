package com.sabre.tripcase.tcp.opennlp.parser.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sabre.tripcase.tcp.opennlp.dto.TokenType;
import com.sabre.tripcase.tcp.opennlp.utils.ControlProperties;
import com.sabre.tripcase.tcp.opennlp.utils.ExtractMatchedSentense;


public final class NlpHandler {
	
	public static void printSentences(String[] sentences,String fileName){
		if(ControlProperties.printSentence(fileName)){
		for(int i=0;i<sentences.length;i++){
			System.out.println("sentence::"+sentences[i].trim());
		 }
		}
	}
	

	
	public static void printTokens(String[] tokens,String fileName){
		if(ControlProperties.printTokens(fileName)){
		for(int i=0;i<tokens.length;i++){
			System.out.println("token::" + tokens[i]);
		 }
		}
	}
	
	public static void printCleanSentences(String[] clean,String fileName){
		if(ControlProperties.printCleanSentence(fileName)){
		for(int i=0;i<clean.length;i++){
			System.out.println("Clean Sentence::" + clean[i]);
		 }
		}
	}
	
	public static void printMatchedTokens(String type,String matchedTokens, String fileSource,String fileName){
		if(ControlProperties.printMatch(fileName)){
		System.out.println("Matched Token ("+fileSource+")::"+type+"-->"+matchedTokens);
		}
	}
	
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
	

	
	public static String[] cleanSentences(String line){
		
		String[] tokens=line.split("\n");
		String[] newTokens=null;
		ArrayList<String> newLines=new ArrayList<String>();
		String newStr="";
		for(int i=0;i<tokens.length;i++){
		newStr=tokens[i];
		newStr=newStr.trim();
		newStr=newStr.replaceAll("[^a-z A-Z 0-9 : . , @]", " ");
		newStr=removeExtraWhiteSpaces(newStr);
//		newStr=newStr.replaceAll("\\s+$", "").trim();
//		newStr=newStr.replaceAll(": ", ":").replaceAll(" :", ":").trim();
		newStr=newStr.replaceAll(", ", " ").replaceAll(" ,", " ");
		newStr=newStr.replaceAll(",", " ");
//		newStr=newStr.replaceAll("\\(","");
//		newStr=newStr.replaceAll("\\)","");
//		newStr=newStr.replaceAll("-", " ").replaceAll("/", " ");
//		newStr=newStr.replaceAll("#", " ");
//		newStr=newStr.replaceAll("\\'", "");
//		newStr=newStr.replaceAll("`", "");
//		newStr=newStr.replaceAll("\\*", "");
//		newStr=newStr.replaceAll("→", " ");
//		newStr=newStr.replaceAll("\\?", "");
//		newStr=newStr.replaceAll("\t", "");

//     newStr=newStr.replaceAll("PM", "<timetype>PM</timetype> ");
//		newStr=newStr.replaceAll(" pm ", " PM ");
//		newStr=newStr.replaceAll(" am ", " AM ");
////		newStr=newStr.replaceAll("\\s?(am)"," AM");
////		newStr=newStr.replaceAll("\\s?(pm)"," PM");
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
		newStr=newStr.replaceAll("Weather", " ");
//		newStr=newStr.replaceAll("at", " ");
		newStr=newStr.replaceAll("is", " ");
		newStr=newStr.replaceAll("of", " ");
		
		newStr=newStr.replaceAll("Est.", " ");
		
		
		

		
		
		
		
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
	
	public static String applyMatchedStringForModels(String sentences) throws Throwable{
		String mapValue="";
		String mapModel="";
		Map<String,String> matchedSenetenceMapLevel0=null;
		Map<String,String> matchedSenetenceMapLevel1=null;
		matchedSenetenceMapLevel0=ExtractMatchedSentense.match(sentences,0);
		Set<String> keySet=matchedSenetenceMapLevel0.keySet();
		
		List<TokenType> finalTagList=new LinkedList<TokenType>();
		
		for(String strKey:keySet){
				mapValue=matchedSenetenceMapLevel0.get(strKey);
				mapModel=strKey;
				System.out.println(" Model 0="+mapModel+" , "+mapValue);
				matchedSenetenceMapLevel1=ExtractMatchedSentense.match(mapValue,1);
			}
		keySet=matchedSenetenceMapLevel0.keySet();
		for(String strKey:keySet){
			mapValue=matchedSenetenceMapLevel1.get(strKey);
			mapModel=strKey;
			System.out.println(" Model 1="+mapModel+" , "+mapValue);
			String[] tokens=removeExtraWhiteSpaces(mapValue.trim()).split(" ");
			List<TokenType> tagList=new LinkedList<TokenType>();
			
			TokenType currentType=null;
			boolean isValueFirst=Boolean.FALSE;
			
			for(String token:tokens){
				
				if(token.contains("<") || token.contains(">")){
					//System.out.println("Tokens --->"+token);
					
					if(token.startsWith("</") && token.endsWith(">")){
						//System.out.println("Close Tag --->"+token);
						currentType.setEndTag(token);
						
					}
					else{
						if(isValueFirst){
							currentType.setFirstTag(token);
							isValueFirst=Boolean.FALSE;
						}
						else{
							currentType=new TokenType();
							currentType.setFirstTag(token);
						
						//System.out.println("Open Tag --->"+token);
						tagList.add(currentType);
						}
					}

				}
				else{
					//System.out.println("Value --->"+token);
					if(null!=currentType){
						currentType.setValue(token);
						
					}
					else{
						currentType=new TokenType();
						currentType.setValue(token);
						tagList.add(currentType);
						isValueFirst=Boolean.TRUE;
					}
					
					
				}
				
				
			}
			
			Iterator<TokenType> itrTokentype=tagList.iterator();
			
			while(itrTokentype.hasNext()){
				TokenType tokenType=itrTokentype.next();
				if(null== tokenType.getValue() && null==tokenType.getEndTag()){
					itrTokentype.remove();
				
				}
				else{
					if(null==tokenType.getEndTag()){
					TokenType newTokenType=new TokenType(tokenType.getFirstTag(),tokenType.getValue(),tokenType.getFirstTag().replace("<", "</"));
					finalTagList.add(newTokenType);
					//System.out.println("New Linked List:"+newTokenType.toStringXML());
					}
					else{
						finalTagList.add(tokenType);
					//System.out.println("Linked List:"+tokenType.toStringXML());
					}
				}
			}
			

			

			
		}
		
		printFinalList(finalTagList);
		
		
	
	      return mapModel;
	}
	
	

	public static void printFinalList(List<TokenType> finalList){
		
		Iterator<TokenType> itrList=finalList.iterator();
		while(itrList.hasNext()){
			System.out.println(itrList.next().toStringXML());
		}
		
	}
}
