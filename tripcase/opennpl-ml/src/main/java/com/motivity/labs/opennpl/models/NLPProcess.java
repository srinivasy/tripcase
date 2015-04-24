/**
 * 
 */
package com.motivity.labs.opennpl.models;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.motivity.labs.tripcase.travelinfo.Itinerary;
import com.motivity.labs.tripcase.travelinfo.Token;
import com.motivity.tripcase.utils.ControlProperties;
import com.motivity.tripcase.utils.ExtractMatchedSentense;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.util.Span;

/**
 * @author Nalini Kanta
 *
 */
public class NLPProcess {
	
	public static void process(String value,String fileSource,String txId) throws Throwable{
		
		
		SentenceDetectorME sentenceME=NLPModels.getSentenceDetectorME();
		
	
		String sentences[]= sentenceME.sentDetect(value);
		String tokens[];

		NLPHelper.printSentences(sentences,txId);
		sentences=NLPHelper.cleanSentences(value);
		
		
		NLPHelper.printCleanSentences(sentences,txId);
		String mapModel="";
	//	System.out.println("Matched ===>"+value);
	      for(String sentence:sentences){
	    	  
	    	 // mapModel=applyMatchedStringForModels(sentence);
	    	  //System.out.println("Matched String Map Model("+mapModel+"):::"+sentence);
	    	  
	    	  tokens=NLPModels.getTokenizer().tokenize(sentence);
	    	  NLPHelper.printTokens(tokens,txId);
	    	  
	    	  if(null!=mapModel && !mapModel.isEmpty()){
	    	  Set<Token> filteredSet=processCustomTokens(mapModel,tokens);
	    	  fetchTokens(filteredSet,fileSource, txId);
	    	  }
	    	  else{
	    		  
//	 		     Set<Token> splitSet=processCustomTokens(ModelTypes.SPLIT_MODEL,tokens);
//			     fetchTokens(splitSet,fileSource, txId);
			     
		     Set<Token> mixedSet=processCustomTokens(ModelTypes.MIXED_MODEL,tokens);
		     fetchTokens(mixedSet,fileSource, txId);
//	    	  
//	    	  Set<Token> departSet=processCustomTokens(ModelTypes.DEPART_MODEL,tokens);
//	    	  fetchTokens(departSet,fileSource, txId);
	    	  
//	    	  Set<Token> departTimeSet=processCustomTokens(ModelTypes.DEPART_TIME_MODEL,tokens);
//	    	  fetchTokens(departTimeSet,fileSource, txId);
//	    	  
//	    	  Set<Token> departDateSet=processCustomTokens(ModelTypes.DEPART_DATE_MODEL,tokens);
//	    	  fetchTokens(departDateSet,fileSource, txId);
	    	  
//	    	  Set<Token> bookingRefSet=processCustomTokens(ModelTypes.BOOKING_REF_MODEL,tokens);
//	    	  fetchTokens(bookingRefSet,fileSource, txId);
	    	  
//	    	  Set<Token> arriveSet=processCustomTokens(ModelTypes.ARRIVE_MODEL,tokens);
//	    	  fetchTokens(arriveSet,fileSource, txId);
//	    	  
//	    	  Set<Token> arrivalTimeSet=processCustomTokens(ModelTypes.ARRIVE_TIME_MODEL,tokens);
//	    	  fetchTokens(arrivalTimeSet,fileSource, txId);
//	    	  
//	    	  Set<Token> flightSet=processCustomTokens(ModelTypes.FLIGHT_MODEL,tokens);
//	    	  fetchTokens(flightSet,fileSource, txId);
	    	  
//	    	  Set<Token> sentenceSet=processCustomTokens(ModelTypes.SENTENCE_MODEL,tokens);
//	    	  fetchTokens(sentenceSet,fileSource, txId);
	    	  
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
				    	sentenceBuild.append(tokens[ti]);
				    	sentenceBuild.append(" ");
			    	  }
			    	  resultSet.add(new Token(result.getType(),sentenceBuild.toString()));
				   }
	  	  }
	  	  else{
	  		resultSet=new LinkedHashSet<Token>();
	  	  }
		}
	  	  return resultSet;
		}
	
	public static void fetchTokens(Set<Token> tokenSet,String fileSource,String txId){
  	  if(null!=tokenSet){
  	  Iterator<Token> itr=tokenSet.iterator();
  	  while(itr.hasNext()){
  		  Token token=itr.next();
  		  NLPHelper.printMatchedTokens(token.getType(),token.getValue(), fileSource,txId);
  	  }
  	  }

	}
	
	public static String applyMatchedStringForModels(String sentences) throws Throwable{
		String mapValue="";
		String mapModel="";
		Map<String,String> matchedSenetenceMap=null;
	  	matchedSenetenceMap=ExtractMatchedSentense.match(sentences);
		Set<String> keySet=matchedSenetenceMap.keySet();
		for(String strKey:keySet){
				mapValue=matchedSenetenceMap.get(strKey);
				mapModel=strKey;
				System.out.println(" Model="+mapModel+" , "+mapValue);
			}
	
	      return mapModel;
	}
}
