/**
 * 
 */
package com.motivity.tripcase.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * @author Nalini Kanta
 *
 */
public class ExtractMatchedSentense {
	private static Map<String,Set<String>> tokenMap=null;
	
	private ExtractMatchedSentense() {}
	
	public static Map<String,String> match(String parentString) throws Throwable{
		
		if(null==tokenMap){
			tokenMap=new LinkedHashMap<String,Set<String>>();
			Properties childTokens = new Properties();
			InputStream in=null;
			String propPath="C:/Users/CB34388493/Documents/tripcase_project/tripcase/common-ml/src/main/java/com/motivity/labs/common/props/airline_alias.properties";
						
			in=new FileInputStream(propPath);
			
			childTokens.load(in);
			Set<Entry<Object, Object>> entryTokensSet=childTokens.entrySet();
			Iterator<Entry<Object, Object>> entryItr=entryTokensSet.iterator();
				
			while(entryItr.hasNext()){
				Entry<Object, Object> mapRecord=entryItr.next();
				String tokens[]=String.valueOf(String.valueOf(mapRecord.getValue())).split("\\|");
				Set<String> tokenSets=new LinkedHashSet<String>();
				for(String token:tokens){
				tokenSets.add(token);
				}
				tokenMap.put(String.valueOf(mapRecord.getKey()), tokenSets);
				
			}
			return startSearch(parentString,tokenMap);
		}
		else{
			return startSearch(parentString,tokenMap);
		}
		
		
	//	System.out.println("Tokens:--->"+tokenSets);

	
		
		
	}
	
	private static boolean evaluate(String parentStr,String token){
		
		//System.out.println("String="+parentStr +" ["+token+"]");
		
		boolean matchFound=Boolean.FALSE;
	   
		 if(parentStr.trim().matches(token+" (.*)")){
		//	 System.out.println("match 1:"+parentStr.matches(token+"(.*)"));
		//	 System.out.println("1 Token Matched:"+token);
			// matchFound=verifyContent(parentStr, token);
			// System.out.println(" Match Found for:::"+parentStr+" , "+token);
			 matchFound=Boolean.TRUE;
	      }
	      
		 else if(!matchFound && parentStr.trim().matches("(.*) "+token+" (.*)")){
			
	     	     
		//	 System.out.println("match 3:"+parentStr.matches("(.*)"+token+"(.*)"));
		//	 System.out.println(" 3 Token Matched:"+token);
			// matchFound=verifyContent(parentStr, token);
			 matchFound= Boolean.TRUE;
	      }

		return matchFound;
		
		
	}
	
	private static boolean verifyContent(String parent, String token){
	//	System.out.println("Word Set Token:::"+token);
		
		String [] words=parent.split(" ");
		Set<String> wordsSet=new HashSet<String>();
		
		for (String str:words){
			wordsSet.add(str);
		//	System.out.println("Word Set String:::"+str);
		}
		if(wordsSet.contains(token)){
			
			return Boolean.TRUE;
		}
		else{
		return Boolean.FALSE;
		}
	}
	
	private static Map<String,String> startSearch(String parentString,Map<String,Set<String>> mTokenMap){
		
		boolean isMatched=Boolean.FALSE;
		Map<String,String> mRequiredLines=new HashMap<String,String>();
		
		for(String mapKey:mTokenMap.keySet()){
			
			Set<String> tokenSet=mTokenMap.get(mapKey);
			
			for(String token:tokenSet){
				if(evaluate(parentString,token)){
					isMatched= Boolean.TRUE;
					String tagging=parentString.replace(token, "<"+token+">");
//					String[] endTagging=tagging.split(" ");
//					for(int i=0;i<endTagging.length;i++){
//						if(endTagging[i].equals("<"+token+">")){
//							String endTag=endTagging[i+1]+"</"+token+">";
//							mRequiredLines.put(mapKey, tagging.replace(endTagging[i+1], endTag));
//							break;
//						}
//					}
					mRequiredLines.put(mapKey, tagging);
					break;
				}
				else{
					isMatched=Boolean.FALSE;
				}
			
		}
			
		}
		
		return mRequiredLines;
		
	}
	
	private static  String removeDays(String line){
		line=line.replaceAll("[^ mon MON monday MONDAY Monday]", " ");
		
		return line;
	}

}
