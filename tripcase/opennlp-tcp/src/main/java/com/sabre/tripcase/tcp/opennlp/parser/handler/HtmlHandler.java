/**
 * 
 */
package com.sabre.tripcase.tcp.opennlp.parser.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import opennlp.tools.sentdetect.SentenceDetectorME;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;

import com.sabre.tripcase.tcp.opennlp.constants.Constants;
import com.sabre.tripcase.tcp.opennlp.constants.ModelTypes;
import com.sabre.tripcase.tcp.opennlp.dto.Itinerary;
import com.sabre.tripcase.tcp.opennlp.dto.Token;
import com.sabre.tripcase.tcp.opennlp.dto.TokenType;
import com.sabre.tripcase.tcp.opennlp.utils.ExtractMatchedSentense;
import com.sabre.tripcase.tcp.opennlp.utils.HtmlTableParser;

/**
 * @author Nalini Kanta
 *
 */
public class HtmlHandler {
	@Autowired
	private static Logger log =Logger.getLogger(HtmlHandler.class);
	private static Map<String,Set<String>> mapHeader;
	
	public List<Object> processHTMLContent(String bodyText,String fileName) throws IOException{
		return processHTMLContent(bodyText,fileName,fileName);
		
	}
	
	public String getHtmlToText(String bodyText) throws Throwable{
		
		String htmlToText="";
		
		return htmlToText;
	}
	
	public  List<Object> processHTMLContent(String bodyText,String fileName,String responseFileName) throws IOException{
		log.info(" ************************ HTML Processor ***************************");
		log.info(" *******************************************************************");
		
		bodyText=NlpHandler.removeBlankLines(bodyText);
//		System.out.println("===================== START fileName ("+fileName+") =====================");
//		System.out.println("HTML--->"+bodyText);
//		System.out.println("===================== END =====================");

		if(bodyText.contains(Constants.HTML_TAG) ){
			try{
				List<String> allResponses=new ArrayList<String>();
				Map<String,String> htmlParsedMap=HtmlTableParser.parse(bodyText, null, false, Constants.PIPE_DELIMITER);
				
				Set<Entry<String,String>> entrySet=htmlParsedMap.entrySet();
				Iterator<Entry<String,String>> iterator=entrySet.iterator();
				while(iterator.hasNext()){
					Entry<String,String> keyEntry=iterator.next();
					String key=keyEntry.getKey();
					String value=keyEntry.getValue().replace("-", " ");
					System.out.println("Key="+key);
					System.out.println("Value="+value);
					value=switchToKeyValueFormat(value);
					htmlParsedMap.put(key, value);
					System.out.println("Returned value from TABLE FORMAT====>"+value);
					allResponses=process(value,fileName,responseFileName,allResponses);
					copyResponseToFile(allResponses, responseFileName);

				}
				
//				String value="Day, Date|Sat, 12APR14|Flight|UA6508|Class|S|Departure City and Time|AUSTIN, TX (AUS) 8:00 AM|Arrival City and Time|SAN FRANCISCO, CA (SFO) 10:00 AM|Aircraft|CRJ 700|Meal|Purchase";
//				
//									
//									nlpProcess.process(value,Constants.HTML,fileName);
			}
			catch(Throwable th){
				th.printStackTrace();
			}
		}
		
		return null;
		
	}

	
	public List<String> process(String sentenceValue,String transactionId,String actualFileName,List<String> allResponses) throws Throwable{
		String sentences[]= sentenceValue.split("\n");
		String mapModel="";
	
	      for(String sentence:sentences){
 
		    	 mapModel=NlpHandler.applyMatchedStringForModels(sentence);
		    	 allResponses.add(mapModel);
		    	//System.out.println("Matched String Map Model("+mapModel+"):::"+sentence);
	      }
	      
	      return allResponses;
		
	}
	
	public String switchToKeyValueFormat(String text) throws IOException{
		StringBuilder sb=new StringBuilder();
		List<String> headerList=new ArrayList<String>();
		List<String> valueList=new ArrayList<String>();
		Map<String,String> matchedHeaderMap=new LinkedHashMap<String,String>();
		int headerCount=0;
		int matchFoundCount=0;
		Map<Integer,Integer> matchFoundMap=new HashMap<Integer,Integer>();
		if(null==mapHeader){
		mapHeader=ExtractMatchedSentense.loadMap("src/main/java/airline_alias.properties");
		}
		String[] sentences=text.split("\n");
		
		/** Removing empty lines from table */
		sentences=removeEmptyLinesFromTable(sentences);
		
			for (int i=0;i<sentences.length;i++){
						for(String headerKey:mapHeader.keySet()){
						Set<String> headerSet=mapHeader.get(headerKey);
						for(String header:headerSet){
						sentences[i]=insertSpaceInMiddle(sentences[i]);
						if(ExtractMatchedSentense.evaluateTable(sentences[i],header)){
						matchedHeaderMap.put("<S"+headerCount+">", header);
						sentences[i]=sentences[i].replace(header, "<S"+headerCount+">");
						headerCount++;
						if(matchFoundMap.containsKey(i))
							matchFoundCount=matchFoundMap.get(i)+1;
							matchFoundMap.put(i, matchFoundCount);
						}
						else{
							matchFoundMap.put(i, 1);
						}
					}
					}
			}
	
	/** Let's remove or eliminate the unmatched lines , stop these from processing */
			sentences=eliminateUnmatchedString(sentences,matchFoundMap);
	/** Remove sentence with more stop words , stop these from processing */
			sentences=checkCountStopWords(sentences);
	
	/** Setting the Original Header Token back to the String sentence */
		for(int i=0;i<sentences.length;i++){
			String line=sentences[i];
			
			for(String headKey:matchedHeaderMap.keySet()){
			if(line.contains(headKey)){
			line=line.replace(headKey, "<"+matchedHeaderMap.get(headKey)+">");
			}
			sentences[i]=line;
		}
	}
		
		
	/** Swapping the Horizontal format to Key:value format */	
		for (int i=0;i<sentences.length;i++){
			if(sentences[i].contains(">|<") || sentences[i].contains("><") || sentences[i].contains("> | <")){
				headerList.clear();
				String headers[]=sentences[i].split("\\|");
				for(String header:headers){
					//if(header.startsWith("<") && header.endsWith(">")){
					headerList.add(header);
					//}
				}
				System.out.println("Header List="+headerList);
			}
			else {
				String values[]=sentences[i].split("\\|");
				for(String value:values){
					valueList.add(value);
				}
			}
			
		
			
			if(matchingPercent(valueList.size(),headerList.size())){
				for(int h=0;h<headerList.size();h++){
					try{
						if(valueList.size()==0){
							break;
						}
						sb.append(headerList.get(h)+"|"+valueList.get(h)+"|");
					}
					catch(Exception e){
						sb.append(headerList.get(h)+"|"+"EMPTY"+"|");
					}
				}
				
			}
			else{
				Iterator<String> valueItr=valueList.iterator();
				while(valueItr.hasNext()){
				sb.append(valueItr.next()+"|");
				}
				
			}

			sb.append("\n");
			valueList.clear();
		}
		
	//return sb.toString();
	return sb.toString().replaceAll("<"," ").replaceAll(">"," ");
		
	}
	
	private boolean matchingPercent(int valSize,int headerSize){
		
		boolean isMatch=Boolean.FALSE;
		float percent=0.0f;
		float val=valSize;
		float head=headerSize;
		if(headerSize>0){
		percent=(val/head)*100;
		if(percent>80){
			isMatch=Boolean.TRUE;
		}
		}
		
		return isMatch;
	}
	
	private String[] eliminateUnmatchedString(String[] sentenceArray,Map<Integer,Integer> matchFoundMap){
		List<String> newLines=new ArrayList<String>();
		int index=0;
		if(null!=sentenceArray && sentenceArray.length>0){
			for(String sentence:sentenceArray){
				int matchCount=matchFoundMap.get(index);
				if(matchCount>0){
					newLines.add(sentence);
				}
				else{
					System.out.println("Unmatched Lines=====>"+sentence);
				}
					
				}
			}
		
		return newLines.toArray(new String[0]);
		
	}

	private void verifySentenceLengthForUnusalMatch(String value){
		
	}
	
	
	private void caseCheckForAirlinesAndFlight(String value,int matchFound){
		
		if(matchFound>0){
			
		}
		
	}
	
	private void preProcessTable(String value){
		
	}
	
	private String[] checkCountStopWords(String[] sentenceArray){
		String stopWords="is|of|a|an|the|to|at|other|and|from|by|which|be|your|hereby|.com|has|or|any|on|thank|via|for|please|are|Subject";
		List<String> newLines=new ArrayList<String>();
		String[] stops=stopWords.split("\\|");
		String tempSentence="";
		int stopCount=0;
		
		if(null!=sentenceArray && sentenceArray.length>0){
			for(String sentence:sentenceArray){
				tempSentence=sentence.replace("|", " ").trim();
				for(String stop:stops){
					if(tempSentence.matches("(.*) "+stop+" (.*)")){
						stopCount++;
				      }
				}
				if(stopCount <1 ){
					newLines.add(sentence);
				}
				else{
					System.out.println("Stop words=====>"+sentence);
				}
				stopCount=0;
		}
		
		
	}
		return newLines.toArray(new String[0]);
	}
	
	private String[] removeEmptyLinesFromTable(String[] sentenceArray){
		
		List<String> newLines=new ArrayList<String>();
		
		if(null!=sentenceArray && sentenceArray.length>0){
			for(String sentence:sentenceArray){
				if(!sentence.replace("|", " ").trim().isEmpty()){
					newLines.add(sentence);
				}
			
			}
		}
		return newLines.toArray(new String[0]);
		
	}
	
	private String[] wrongStatusRemoval(String[] sentenceArray){
		String statusWords="Confirmed|OK";
		List<String> newLines=new ArrayList<String>();
		String[] statuses=statusWords.split("\\|");
		String tempSentence="";
		int statusCount=0;
		
		if(null!=sentenceArray && sentenceArray.length>0){
			for(String sentence:sentenceArray){
				tempSentence=sentence.replace("|", " ").trim();
				for(String status:statuses){
					if(tempSentence.toLowerCase().contains("status")){
					if(tempSentence.toLowerCase().matches("(.*) "+status.toLowerCase()+" (.*)")){
						statusCount++;
				      }
					}
				}
				if(statusCount >1 ){
					newLines.add(sentence);
				}
				else{
					System.out.println("statusCount words=====>"+sentence);
				}
				statusCount=0;
		}
		
		
	}
		return newLines.toArray(new String[0]);
	}
	
	private String insertSpaceInMiddle(String sentence){
		
		StringBuilder result = new StringBuilder();
		for(int i=0 ; i <sentence.length()-2 ; i++) {
		    char c1 = sentence.charAt(i);
		    char c2 = sentence.charAt(i+1);
		    result.append(c1);
		    if(i!=0 && Character.isLowerCase(c1) && Character.isUpperCase(c2)) {
		        result.append(' ');
		    }
		    else if(i!=0 && c1==')' && (Character.isLowerCase(c2) || Character.isUpperCase(c2))) {
		        result.append(' ');
		    }
		    
		    else if(i!=0 && c1==')' && (Character.isLowerCase(c2) || Character.isUpperCase(c2))) {
		        result.append(' ');
		    }
		    
		}
		
		return result.toString();
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
		
		//printFinalList(finalTagList);
	     // return mapModel;
		return printFinalList(finalTagList);
	}
	
	public static String printFinalList(List<TokenType> finalList){
		StringBuilder output=new StringBuilder();
		Iterator<TokenType> itrList=finalList.iterator();
		String result="";
		while(itrList.hasNext()){
			result=itrList.next().toStringXML();
			System.out.println(result);
			result=result.replace("|", "");
			if(!result.contains("><")){
			output.append(result);
			}
			output.append("\n");
		}
		
		return output.toString();
		
	}
	public static String removeExtraWhiteSpaces(String line){
		return line.replaceAll(" +", " ");
	}
	
	public  void copyResponseToFile(List<String> responses,String fileName){
		FileOutputStream fop=null;
   	 try{
   	 File responseFile=new File(ModelTypes.RESPONSE_FILE_BASE_LOCATION+"/"+fileName.replace("eml", "txt"));
   	 fop=new FileOutputStream(responseFile);
   	 for(String response:responses){
   		 byte[] contentInBytes = response.getBytes();
   	   	 fop.write(contentInBytes);

   	   	 }
   	 }
   	   	 catch(Exception ex){
   	   		 System.out.println("Exception while copying response to File:");
   	   		 ex.printStackTrace();
   	   	 }
   	 finally{
   		 try{
   			 if(null!=fop){
   				 fop.flush();
   				 fop.close(); 
   			 }

   		 }
   		 catch(Exception ex){
   			 System.out.println("Exception while closing filestream:"+ex.getMessage());
   		 }
   	 }
   	 
   	
	}

}
