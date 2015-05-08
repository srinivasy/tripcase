/**
 * 
 */
package com.sabre.tripcase.tcp.opennlp.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
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
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Nalini Kanta
 *
 */
public class ExtractMatchedSentense {
	private static Map<String,Set<String>> tokenMapLevel0=null;
	private static Map<String,Set<String>> tokenMapLevel1=null;
	
	private static String dayMatch="(Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday)";
	//private static String dateMatch="(1|2|3|4|5|6|7|8|9|01|02|03|04|05|06|07|08|09|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31)";
	private static String dateMatch="(0?[1-9]|[12][0-9]|3[01])";
	private static String monthMatch="(January|February|March|April|May|June|July|August|September|October|November|December|JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)";
	private static String yearMatch="((20)\\d\\d)";
	private static String timeMatch24="([01]?[0-9]|2[0-3]):[0-5][0-9]";
	
	public static Map<String,String> match(String parentString,int level) throws Throwable{
	
		Map<String,String> resultMap=null;
		if(level==0){
			if(null==tokenMapLevel0){
			tokenMapLevel0=loadMap("src/main/java/airline_alias.properties");
			resultMap= startSearch(parentString,tokenMapLevel0,level);
			}
			else{
				resultMap= startSearch(parentString,tokenMapLevel0,level);
			}
		}
		else if (level==1){
			if(null==tokenMapLevel1){
			tokenMapLevel1=loadMap("src/main/java/annotate_level_1.properties");
			resultMap=startSearch(parentString,tokenMapLevel1,level);
			}
			else{
				resultMap=startSearch(parentString,tokenMapLevel1,level);
			}
		}
		
			return resultMap;

		
	}
	
	public static boolean evaluate(String parentStr,String token,int level){
		
		//System.out.println("String="+parentStr +" ["+token+"]");
		
		boolean matchFound=Boolean.FALSE;
		
		if(level==0){
	   
		 if(parentStr.trim().matches(token+" (.*)")){
			 matchFound=Boolean.TRUE;
	      }
	      
		 else if(!matchFound && parentStr.trim().matches("(.*) "+token+" (.*)")){
			 matchFound= Boolean.TRUE;
	      }
		 
		 else if(!matchFound && parentStr.trim().matches("(.*) "+token)){
			 matchFound= Boolean.TRUE;
	      }
	}
		else if(level==1){
			return evaluatelevel1(parentStr,token);
		}


		return matchFound;
		
		
	}
	
	public static boolean evaluateTable(String parentStr,String token){
		
		//System.out.println("String="+parentStr +" ["+token+"]");
		
		boolean matchFound=Boolean.FALSE;
		
		if(token.equals("Departure City and Time")){
			System.out.println("Here is the Token="+token);
		}
	   
		 if(parentStr.trim().matches(token+"(.*)")){
			 matchFound=Boolean.TRUE;
	      }
	      
		 else if(!matchFound && parentStr.trim().matches("(.*)"+token+"(.*)")){
			 matchFound= Boolean.TRUE;
	      }
		 
		 else if(!matchFound && parentStr.trim().matches("(.*)"+token)){
			 matchFound= Boolean.TRUE;
	      }
	

		return matchFound;
		
		
	}
	
	private static boolean evaluatelevel1(String parentStr,String token){
		
		//System.out.println("String="+parentStr +" ["+token+"]");
		
		boolean matchFound=Boolean.FALSE;
	   
		 if(parentStr.trim().matches(token+"(.*)")){
			 matchFound=Boolean.TRUE;
	      }
	      
		 else if(!matchFound && parentStr.trim().matches("(.*)"+token+"(.*)")){
			 matchFound= Boolean.TRUE;
	      }
		 
		 else if(!matchFound && parentStr.trim().matches("(.*)"+token)){
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
	
	private static Map<String,String> startSearch(String parentString,Map<String,Set<String>> mTokenMap,int level){
		
		boolean isMatched=Boolean.FALSE;
		boolean isDayMatch=Boolean.FALSE;
		boolean isDateMatch=Boolean.FALSE;
		boolean isMonthMatch=Boolean.FALSE;
		Map<String,String> mRequiredLines=new HashMap<String,String>();
		
		int rowNumber=0;
		String row="ROW"+rowNumber;
		String tagging="";
		for(String mapKey:mTokenMap.keySet()){
			
			Set<String> tokenSet=mTokenMap.get(mapKey);
			rowNumber=rowNumber+1;
			for(String token:tokenSet){
				
				if(evaluate(parentString,token,level)){
					isMatched= Boolean.TRUE;
					if(level==0){
						//tagging=parentString.replace(token, "<"+token+">");
						tagging=parentString.replace(token, " <"+mapKey+"> ");
					}
					else{
						
						tagging=parentString.replace(token, " <"+mapKey+"> "+token+" </"+mapKey+"> ");
					}
					parentString=tagging;
					//parentString=dayMatch(parentString,dayMatch,isDayMatch);
					//parentString=monthMatch(parentString,monthMatch,isMonthMatch);
					if(!parentString.contains("<time>")){
					parentString=timeMatch24(parentString,timeMatch24, Boolean.FALSE);
					}
					
					if(parentString.contains("<month>")){
					//parentString=dateMatch(parentString,dateMatch, Boolean.FALSE);
					}
					
					mRequiredLines.put(row, parentString);
					//break;
				}
				else{
					if(level==1){
						mRequiredLines.put(row, parentString);
					}
					isMatched=Boolean.FALSE;
				}
			
		}
			
		}
		
		return mRequiredLines;
		
	}
	
	private static String patternMatch(String regex,String sentence){
		String match="";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(sentence);
	
		
		
		while (matcher.find()) {
//		      System.out.print("Start index: " + matcher.start());
//		      System.out.print(" End index: " + matcher.end() + " ");
//		      System.out.println(matcher.group());
		      match=matcher.group();
		
		    }
		
		return match;
		
	}
	
	private static String patternDayMatch(String regex,String sentence){
		String match="";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(sentence);
		
		while (matcher.find()) {
//		      System.out.print("Start index: " + matcher.start());
//		      System.out.print(" End index: " + matcher.end() + " ");
//		      System.out.println(matcher.group());
		      match+=matcher.group();
		      if(match.length()==2){
		      sentence=sentence.replace(match, " <date> "+match+" </date> ");
		      match="";
		     }
		      

		
		    }
		
		return sentence;
		
	}
	
	private static String patternTimeMatch(String regex,String sentence){
		String match="";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(sentence);
		
		
		while (matcher.find()) {
		      match=matcher.group();
		      sentence=sentence.replace(match, " <time> "+match+" </time> ");
				
	
		    }
		
		return sentence;
		
	}
	
	private static String dayMatch(String parentString,String regEx, boolean isDayMatch){
	
		if(evaluatelevel1(parentString,regEx) && !isDayMatch){
			String customTagging=parentString.replace(patternMatch(regEx,parentString), " <day> "+patternMatch(regEx,parentString)+" </day> ");
			parentString=customTagging;
			isDayMatch=Boolean.TRUE;

		}
		
		return parentString;
		
	}
	
	private static String dateMatch(String parentString,String regEx, boolean isDateMatch){
		
		
		
	//	if(evaluate(parentString,regEx,0) && !isDateMatch){
			
			String customTagging=patternDayMatch(regEx,parentString);
			parentString=customTagging;
			isDateMatch=Boolean.TRUE;
	//	}
		
		return parentString;
		
	}
	
	private static String  monthMatch(String parentString,String regEx, boolean isMonthMatch){
		
		if(evaluatelevel1(parentString,regEx) && !isMonthMatch){
			String customTagging=parentString.replace(patternMatch(regEx,parentString), " <month> "+patternMatch(regEx,parentString)+" </month> ");
			parentString=customTagging;
			isMonthMatch=Boolean.TRUE;
		}
		
		if(isMonthMatch){
			parentString=yearMatch(parentString,yearMatch, Boolean.FALSE);
			
			parentString=dateMatch(parentString,dateMatch,Boolean.FALSE);
		}
		return parentString;
		
	}
	
	private static String yearMatch(String parentString,String regEx, boolean isYearMatch){
		
		if(evaluatelevel1(parentString,regEx) && !isYearMatch){
			String customTagging=parentString.replace(patternMatch(regEx,parentString), " <year> "+patternMatch(regEx,parentString)+" </year> ");
			parentString=customTagging;
			isYearMatch=Boolean.TRUE;
		}
		return parentString;
		
	}
	
	private static String timeMatch24(String parentString,String regEx, boolean isTimeMatch24){
		if(evaluate(parentString,regEx,1) && !isTimeMatch24){
			String customTagging=patternTimeMatch(regEx,parentString);
			parentString=customTagging;
			isTimeMatch24=Boolean.TRUE;
		}
		return parentString;
	}
	
	public static Map<String,Set<String>> loadMap(String path) throws IOException{
		Properties childTokens = new Properties();
		InputStream in=null;
								
		in=new FileInputStream(path);
		
		childTokens.load(in);
		Set<Entry<Object, Object>> entryTokensSet=childTokens.entrySet();
		Iterator<Entry<Object, Object>> entryItr=entryTokensSet.iterator();
		Map<String,Set<String>> tokenMap=new LinkedHashMap<String,Set<String>>();
		while(entryItr.hasNext()){
			Entry<Object, Object> mapRecord=entryItr.next();
			String tokens[]=String.valueOf(String.valueOf(mapRecord.getValue())).split("\\|");
			Set<String> tokenSets=new TreeSet<String>(new HeaderOrder());
			for(String token:tokens){
			tokenSets.add(token);
			}
			tokenMap.put(String.valueOf(mapRecord.getKey()), tokenSets);
			
		}
		
		return tokenMap;
	}

	
	

}
