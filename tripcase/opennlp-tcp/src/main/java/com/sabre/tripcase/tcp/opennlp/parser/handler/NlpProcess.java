/**
 * 
 */
package com.sabre.tripcase.tcp.opennlp.parser.handler;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import com.sabre.tripcase.tcp.opennlp.constants.ModelTypes;
import com.sabre.tripcase.tcp.opennlp.dto.Itinerary;
import com.sabre.tripcase.tcp.opennlp.dto.Token;
import com.sabre.tripcase.tcp.opennlp.models.Trainer;
import com.sabre.tripcase.tcp.opennlp.models.nlp.NlpSentenceModel;
import com.sabre.tripcase.tcp.opennlp.models.nlp.NlpTokenModel;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.util.Span;

/**
 * @author Nalini Kanta
 *
 */
public class NlpProcess {
	
	private NlpSentenceModel nlpSentenceModel=null;
	private NlpTokenModel nlpTokenModel=null;
	private Trainer trainer;

	
	public String[] sentenceDetect(String content) {
		String sentences[] = null;
		sentences=nlpSentenceModel.getSentenceModel().sentDetect(content);
		return sentences;
	}
	
	public String[] tokenize(String sentence){
		String tokens[] = null;
		tokens = nlpTokenModel.getTokenModel().tokenize(sentence);
		return tokens;
	}
	
	public  void process(String value,String fileSource,String txId) throws Throwable{
		
		
		SentenceDetectorME sentenceME=nlpSentenceModel.getSentenceModel();
		
	
		String sentences[]= sentenceME.sentDetect(value);
		String tokens[];

		NlpHandler.printSentences(sentences,txId);
		sentences=NlpHandler.cleanSentences(value);
		
		
		NlpHandler.printCleanSentences(sentences,txId);
		String mapModel="";
	//	System.out.println("Matched ===>"+value);
	      for(String sentence:sentences){
	    	  
	    	 // mapModel=applyMatchedStringForModels(sentence);
	    	  //System.out.println("Matched String Map Model("+mapModel+"):::"+sentence);
	    	  
	    	  tokens=nlpTokenModel.getTokenModel().tokenize(sentence);
	    	  NlpHandler.printTokens(tokens,txId);
	    	  
	    	  if(null!=mapModel && !mapModel.isEmpty()){
	    	  Set<Token> filteredSet=processCustomTokens(mapModel,tokens);
	    	  fetchTokens(filteredSet,fileSource, txId);
	    	  }
	    	  else{
	    		  
//	 		     Set<Token> splitSet=processCustomTokens(ModelTypes.SPLIT_MODEL,tokens);
//			     fetchTokens(splitSet,fileSource, txId);
			     
//		     Set<Token> mixedSet=processCustomTokens(ModelTypes.MIXED_MODEL,tokens);
//		     fetchTokens(mixedSet,fileSource, txId);
//	    	  
	    	  Set<Token> departSet=processCustomTokens(ModelTypes.DEPART_MODEL,tokens);
	    	  fetchTokens(departSet,fileSource, txId);
	    	  
	    	  Set<Token> departTimeSet=processCustomTokens(ModelTypes.DEPART_TIME_MODEL,tokens);
	    	  fetchTokens(departTimeSet,fileSource, txId);
	    	  
	    	  Set<Token> departDateSet=processCustomTokens(ModelTypes.DEPART_DATE_MODEL,tokens);
	    	  fetchTokens(departDateSet,fileSource, txId);
	    	  
//	    	  Set<Token> bookingRefSet=processCustomTokens(ModelTypes.BOOKING_REF_MODEL,tokens);
//	    	  fetchTokens(bookingRefSet,fileSource, txId);
	    	  
	    	  Set<Token> arriveSet=processCustomTokens(ModelTypes.ARRIVE_MODEL,tokens);
	    	  fetchTokens(arriveSet,fileSource, txId);
		     
	    	  Set<Token> arriveDateSet=processCustomTokens(ModelTypes.ARRIVE_DATE_MODEL,tokens);
	    	  fetchTokens(arriveDateSet,fileSource, txId);
//	    	  
	    	  Set<Token> arrivalTimeSet=processCustomTokens(ModelTypes.ARRIVE_TIME_MODEL,tokens);
	    	  fetchTokens(arrivalTimeSet,fileSource, txId);
//	    	  
	    	  Set<Token> flightSet=processCustomTokens(ModelTypes.FLIGHT_MODEL,tokens);
	    	  fetchTokens(flightSet,fileSource, txId);
	    	  
//	    	  Set<Token> sentenceSet=processCustomTokens(ModelTypes.SENTENCE_MODEL,tokens);
//	    	  fetchTokens(sentenceSet,fileSource, txId);
	    	  
	    	  }
	    	
	      }
	      
			Itinerary.init();
			Itinerary.setTransactionId(txId);

		
	}
	
	public  Set<Token> processCustomTokens(String modelType,String[] tokens) throws IOException{
		NameFinderME finderME=trainer.loadTrainedModels(modelType);
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
	
	public  void fetchTokens(Set<Token> tokenSet,String fileSource,String txId){
  	  if(null!=tokenSet){
  	  Iterator<Token> itr=tokenSet.iterator();
  	  while(itr.hasNext()){
  		  Token token=itr.next();
  		NlpHandler.printMatchedTokens(token.getType(),token.getValue(), fileSource,txId);
  	  }
  	  }

	}

	/**
	 * @return the nlpSentenceModel
	 */
	public NlpSentenceModel getNlpSentenceModel() {
		return nlpSentenceModel;
	}

	/**
	 * @param nlpSentenceModel the nlpSentenceModel to set
	 */
	public void setNlpSentenceModel(NlpSentenceModel nlpSentenceModel) {
		this.nlpSentenceModel = nlpSentenceModel;
	}

	/**
	 * @return the nlpTokenModel
	 */
	public NlpTokenModel getNlpTokenModel() {
		return nlpTokenModel;
	}

	/**
	 * @param nlpTokenModel the nlpTokenModel to set
	 */
	public void setNlpTokenModel(NlpTokenModel nlpTokenModel) {
		this.nlpTokenModel = nlpTokenModel;
	}

	/**
	 * @return the trainer
	 */
	public Trainer getTrainer() {
		return trainer;
	}

	/**
	 * @param trainer the trainer to set
	 */
	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}
	
	

}