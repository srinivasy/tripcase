package com.motivity.tripcase.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.motivity.ml.SentenceDetectClass;
import com.motivity.tripcase.pojo.Parser;
import com.motivity.tripcase.utils.Config;
import com.motivity.tripcase.utils.ParserBuilder;

public class MainParser {
	public static void main(String[] args) {
		parseAirlineEMails();
	}

	static void parseAirlineEMails(){

		parseAirline();
	}

	
	
	private static void parseAirline() {
		File[] files = new File(Config.getEMailPath("airline")).listFiles();
		
		//readFile();
		
		//If this pathname does not denote a directory, then listFiles() returns null.
		if(files != null){			
			ParserBuilder utils = new ParserBuilder();
			for (File file : files) {
				if (file.isFile()) {
					System.out.println("************************* " + file.getName() + " ***********************");
					Parser parser = utils.buildParser(file);
					
					System.out.println("Parse: " + parser.getEmail().getHtml().toString());
					//readStringFromParseData(parser.getEmail().getBody().toString());
					readStringFromParseData(parser.getEmail().getHtml().toString());
					
				}
			}
		}
	}

	/*@SuppressWarnings("static-access")
	public static String readFile(){
		String sourceString = "";
		String REGEX = "[\t ]+";
		String REGEX2 = "^[>]+";
		String REPLACE = " ";
		
		try{
		//BufferedReader br = new BufferedReader(new FileReader("D:\\projects\\TripCase_Project\\Technical\\OpenNLPProject\\models\\2019587.txt"));
		BufferedReader br = new BufferedReader(new FileReader("D:\\projects\\TripCase_Project\\Technical\\email_templates\\OpenNLP\\Airline\\test\\2361966.txt"));
		String line;
		SentenceDetectClass SentenceDetector = new SentenceDetectClass();
		while ((line = br.readLine()) != null) {
			if(line != null && line!= ""){
				Pattern p = Pattern.compile(REGEX2);
			       // get a matcher object
			       Matcher m = p.matcher(line); 
			       line = m.replaceAll(REPLACE);
			sourceString += line + " ";
			//System.out.println("File output: " + sourceString);			
			//	SentenceDetector.SentenceDetect(parser.getEmail().getBody().toString());
			//SentenceDetector.SentenceDetect(sourceString);
			}
		}
		Pattern p = Pattern.compile(REGEX);
	       // get a matcher object
	       Matcher m = p.matcher(sourceString); 
	       sourceString = m.replaceAll(REPLACE);
		SentenceDetector.SentenceDetect(sourceString);
		br.close();		
		
		}catch(FileNotFoundException  fne){
			fne.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sourceString;
	}*/
	
	@SuppressWarnings("static-access")
	public static void readStringFromParseData(String stringFromParseData){
		String sourceString = "";
		String REGEX = "[\t ]+";
		String REGEX2 = "[><]+";
		String REPLACE = " ";
		
		String[] sourceStringArray;
		
		try{		
		SentenceDetectClass SentenceDetector = new SentenceDetectClass();
		//while ((line = br.readLine()) != null) {
			if(stringFromParseData != null && stringFromParseData!= ""){
				Pattern p = Pattern.compile(REGEX2);
			       // get a matcher object
			       Matcher m = p.matcher(stringFromParseData); 
			       stringFromParseData = m.replaceAll(REPLACE);
			       
			       sourceStringArray = stringFromParseData.split("\n");
			       for(int i=0;i<sourceStringArray.length;i++){
			    	   sourceString += sourceStringArray[i] + " ";
			    	   
			       }		            
			
			}
		//}
		Pattern p = Pattern.compile(REGEX);
	       // get a matcher object
	       Matcher m = p.matcher(sourceString); 
	       sourceString = m.replaceAll(REPLACE);
	    //System.out.println("sourceString: " + sourceString);
		SentenceDetector.SentenceDetect(sourceString);
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
