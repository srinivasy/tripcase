/**
 * 
 */
package com.motivity.labs.opennpl.models;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.motivity.labs.tripcase.travelinfo.Itinerary;
import com.motivity.labs.tripcase.travelinfo.Token;
import com.motivity.tripcase.utils.ControlProperties;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.util.Span;

/**
 * @author Nalini Kanta
 *
 */
public class NLPProcess {
	
	public static void process(String value,String fileSource,String txId) throws IOException{
		SentenceDetectorME sentenceME=NLPModels.getSentenceDetectorME();
		String sentences[]= sentenceME.sentDetect(value);
		String tokens[];

		NLPHelper.printSentences(sentences,txId);
		sentences=NLPHelper.cleanSentences(value);
		NLPHelper.printCleanSentences(sentences,txId);
		
	      for(String sentence:sentences){
	    	  tokens=NLPModels.getTokenizer().tokenize(sentence);
	    	  NLPHelper.printTokens(tokens,txId);
	    	  Set<Token> departSet=processCustomTokens(ModelTypes.DEPART_MODEL,tokens);
	    	  if(null!=departSet){
	    	  Iterator<Token> itr=departSet.iterator();
	    	  while(itr.hasNext()){
	    		  Token token=itr.next();
	    		  NLPHelper.printMatchedTokens( token.getType(),token.getValue(), fileSource,txId);
	    	  }
	    	  }
	    	  
	    	  Set<Token> bookingRefSet=processCustomTokens(ModelTypes.BOOKING_REF_MODEL,tokens);
	    	  
	    	  if(null!=bookingRefSet){
	    	  Iterator<Token> itr=bookingRefSet.iterator();
	    	  while(itr.hasNext()){
	    		  Token token=itr.next();
	    		  NLPHelper.printMatchedTokens( token.getType(),token.getValue(), fileSource,txId);
	    	  }
	    	  }
	    	  
	    	
	      }
	      
			Itinerary.init();
			Itinerary.setTransactionId(txId);

		
	}
	
	public static Set<Token> processCustomTokens(String modelType,String[] tokens) throws IOException{
		NameFinderME finderME=NLPHelper.loadTrainedModels(modelType);
		Set<Token> resultSet=null;
		if(null!=finderME){
	  	  Span[] results=finderME.find(tokens);
	  	  
	  	  if(results.length >0){
			      for (Span result:results){
			    	  StringBuilder sentenceBuild=new StringBuilder();
			    	  resultSet=new LinkedHashSet<Token>();
			    	  for (int ti=result.getStart(); ti < result.getEnd(); ti++){
				    	//System.out.println("Department="+depart);
				    	
				    	sentenceBuild.append(tokens[ti]);
				    	sentenceBuild.append(" ");
			    	  }
			    	  resultSet.add(new Token(result.getType(),sentenceBuild.toString()));
			    	 // System.out.println("Covered text From HTML is: "+sentenceBuild.toString());
				    	
				   }
			     // System.out.println("Originonal Sentence is: "+sentence);  
	  	  }
	  	  else{
	  		  //System.out.println("No match found for sentence:"+sentence);
	  	  }
		}
	  	  return resultSet;
		}

	
	

}
