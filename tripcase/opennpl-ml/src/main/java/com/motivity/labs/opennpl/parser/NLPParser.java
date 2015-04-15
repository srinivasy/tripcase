/**
 * 
 */
package com.motivity.labs.opennpl.parser;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.MimeMessage;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.util.Span;

import com.motivity.labs.opennpl.models.ModelCreation;
import com.motivity.labs.opennpl.models.NLPHelper;
import com.motivity.labs.opennpl.models.NLPModels;
import com.motivity.ml.SentenceDetectClass;
import com.motivity.tripcase.emailreader.EmailReader;
import com.motivity.tripcase.emailreader.MimeMessageReader;
import com.motivity.tripcase.pojo.Parser;
import com.motivity.labs.open.npl.backup.HtmlTableParser;
import com.motivity.tripcase.utils.ParserBuilder;



/**
 * @author Nalini Kanta
 *
 */
public class NLPParser {
	String parseData="";
	
	public static void main(String args[]) throws IOException{
		File[] files = new File("C:/Users/CB34388493/opennlp/emails/").listFiles();
		ModelCreation.createDepartModel();
		SentenceDetectorME sentenceME=NLPModels.getSentenceDetectorME();
		NameFinderME departME=NLPModels.getDepartModel();
		
		if(files != null){			
			ParserBuilder utils = new ParserBuilder();
			for (File file : files) {
				if (file.isFile()) {
					System.out.println("************************* " + file.getName() + " ***********************");
					
					MimeMessage mimeMessage=MimeMessageReader.getMimeMessage(file);
					String bodyText=EmailReader.getBody(mimeMessage, false);
					System.out.println("Body Text="+bodyText);
					if(bodyText.contains("<html>") || bodyText.contains("<div>")){
						try{
							Map<String,String> htmlParsedMap=HtmlTableParser.parse(bodyText, null, false, " ");
							Set<Entry<String,String>> entrySet=htmlParsedMap.entrySet();
							Iterator<Entry<String,String>> iterator=entrySet.iterator();
							while(iterator.hasNext()){
								Entry<String,String> keyEntry=iterator.next();
								String key=keyEntry.getKey();
								String value=keyEntry.getValue().replace("-", " ");
//								if (!key.contains("TABLE_MV_2"))
//									continue;
								//String value=htmlParsedMap.get("TABLE_MV_2");
								
								//System.out.println("Key="+key);
								//System.out.println("value="+value);
								
								String sentences[]= sentenceME.sentDetect(value);
							    NLPHelper.printSentences(sentences);
							    //  System.out.println("Length of sentences:"+sentences.length);
							      for(String sentence:sentences){
							     
							      String tokens[]=NLPModels.getTokenizer().tokenize(sentence);
							      									
									Span[] departs=departME.find(tokens);
									
								      for (Span depart:departs){
									    	System.out.println("Department="+depart);
									    	System.out.println("Covered text is: "+tokens[depart.getStart()]);
									    	}
								   
							      }
							      
//							      NameFinderME nameTimeME=NLPModels.getNameFinderMoneyME();
//							      Span times[]=nameTimeME.find(tokens);
//							      
//							      for (Span time:times){
//								    	System.out.println("time="+time);
//								    	System.out.println("Covered text is: "+tokens[time.getStart()] + " " + tokens[time.getEnd()]);
//								    	}
								
//								if(value.contains("Booking Reference")){
//									value=value.replace("Booking Reference", "<Booking_Reference>");
//									System.out.println("New Value="+value);
//								}

							}
						}
						catch(Throwable th){
							th.printStackTrace();
						}
					} /** End of html If */
					else{
						String sentences[]= sentenceME.sentDetect(bodyText);
					    NLPHelper.printSentences(sentences);
					    //  System.out.println("Length of sentences:"+sentences.length);
					      for(String sentence:sentences){
					     
					      String tokens[]=NLPModels.getTokenizer().tokenize(sentence);
					      									
							Span[] departs=departME.find(tokens);
							
						      for (Span depart:departs){
							    	System.out.println("Department="+depart);
							    	System.out.println("Covered text is: "+tokens[depart.getStart()]);
							    	}
						   
					      }
					}
					

					
					//System.out.println("Body Text="+bodyText);
					//readStringFromParseData(parser.getEmail().getBody().toString());
					//readStringFromParseData(parser.getEmail().getHtml().toString());
					
				}
			}
		}
	}
	
	@SuppressWarnings("static-access")
	public static void readStringFromParseData(String stringFromParseData){
		String sourceString = "";
		String REGEX = "[\t ]+";
		String REGEX2 = "[><]+";
		String REPLACE = " ";
		
		String[] sourceStringArray;
		
		try{		
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
	       SentenceDetectClass.SentenceDetect(sourceString);
	    /*  String sentences[]= NLPModels.getSentenceDetectorME().sentDetect(sourceString);
	      NLPHelper.printSentences(sentences);
	      
	      String tokens[]=NLPModels.getTokenizer().tokenize(sentences[0]);
	   
	      NLPHelper.printTokens(tokens);
	      NameFinderME nameME=NLPModels.getNameFinderPersonME();
	      nameME.clearAdaptiveData();
	      Span[] names= nameME.find(tokens);
	      for (Span name:names){
	    	System.out.println("Name="+name);
	    	System.out.println("Covered text is: "+tokens[name.getStart()] + " " + tokens[name.getEnd()]);
	    	}
	      
	      NameFinderME nameLocationME=NLPModels.getNameFinderLocationME();
	      Span locations[]=nameLocationME.find(tokens);
	      
	      for (Span location:locations){
		    	System.out.println("location="+location);
		    	System.out.println("Covered text is: "+tokens[location.getStart()] + " " + tokens[location.getEnd()]);
		    	}
	      NameFinderME nameTimeME=NLPModels.getNameFinderTimeME();
	      Span times[]=nameTimeME.find(tokens);
	      
	      for (Span time:times){
		    	System.out.println("time="+time);
		    	System.out.println("Covered text is: "+tokens[time.getStart()] + " " + tokens[time.getEnd()]);
		    	}*/
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
