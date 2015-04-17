package com.motivity.labs.opennpl.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.motivity.tripcase.utils.ControlProperties;

import opennlp.tools.namefind.NameFinderME;

public final class NLPHelper {
	
	public static String[] sentenceDetect(String content) {
		String sentences[] = null;
		sentences=NLPModels.getSentenceDetectorME().sentDetect(content);
		return sentences;
	}
	
	public static void printSentences(String[] sentences,String fileName){
		if(ControlProperties.printSentence(fileName)){
		for(int i=0;i<sentences.length;i++){
			System.out.println("sentence::"+sentences[i].trim());
		 }
		}
	}
	
	public static String[] Tokenize(String sentence){
		String tokens[] = null;
		tokens = NLPModels.getTokenizer().tokenize(sentence);
		return tokens;
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
		return line.replaceAll("\\s+$", "");
	}
	
	public static String[] cleanSentences(String line){
		
		String[] tokens=line.split("\n");
		String[] newTokens=null;
		ArrayList<String> newLines=new ArrayList<String>();
		String newStr="";
		for(int i=0;i<tokens.length;i++){
		newStr=tokens[i];
		newStr=newStr.replaceAll("\\s+$", "").trim();
		newStr=newStr.replaceAll(": ", ":").replaceAll(" :", ":").trim();
		newStr=newStr.replaceAll(", ", " ").replaceAll(" ,", " ");
		newStr=newStr.replaceAll("\\(","");
		newStr=newStr.replaceAll("\\)","");
		newStr=newStr.replaceAll("-", " ").replaceAll("/", " ");
		newStr=newStr.replaceAll("#", " ");
		newStr=newStr.replaceAll("\\'", "");
		newStr=newStr.replaceAll("`", "");
		newStr=newStr.replaceAll("\\*", "");
		newStr=newStr.replaceAll("→", " ");
		newStr=newStr.replaceAll("\\?", "");
		
		newStr=removeColonExceptTimeValue(newStr);
		//newStr=removeDotExceptMoneyValue(newStr);
		if(!newStr.trim().isEmpty()){
			newLines.add(newStr.trim());
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
			//System.out.println("Tokens with dot--->"+internalTokens[i]);
		if(isNumeric(internalTokens[i].substring(internalTokens[i].length()-2, internalTokens[i].length())) && 
				isNumeric(internalTokens[i+1].substring(0,1))){
				internalTokens[i+1]="."+internalTokens[i+1];
				//System.out.println("Value with dot="+internalTokens[i+1]);
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
	
	public static NameFinderME loadTrainedModels(String modelName)throws IOException{
		NameFinderME modelME=null;
		
		if(ModelTypes.DEPART_MODEL.equals(modelName)){
			modelME=NLPModels.getCustomTrainedModel(ModelTypes.DEPART_MODEL,ModelTypes.DEPART_MODEL_FILE);
		}
		if(ModelTypes.BOOKING_REF_MODEL.equals(modelName)){
			modelME=NLPModels.getCustomTrainedModel(ModelTypes.BOOKING_REF_MODEL,ModelTypes.BOOKING_REF_MODEL_FILE);
		}
		return modelME;
		
	}
	

}
