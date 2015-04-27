/**
 * 
 */
package com.sabre.tripcase.tcp.opennlp.utils;

import java.util.Properties;

/**
 * @author Nalini Kanta
 *
 */
public class ControlProperties {
	
	private static Properties controlProps=null;
	private static String fileName="ALL";
	
	private ControlProperties(){
		controlProps=new Properties();
		controlProps.put("print_sentence", "NO");
		controlProps.put("print_tokens", "NO");
		controlProps.put("print_match", "YES");
		controlProps.put("print_origin_text", "NO");
		controlProps.put("print_clean_sentence", "YES");
		
	}
	
	public static Properties getControlProps(){
		if(null==controlProps){
			new ControlProperties();
		}
		return controlProps;
	}
	
	public static boolean printSentence(String fileName){
		boolean condition=Boolean.FALSE;
		if(null==controlProps){
			new ControlProperties();
		}
		else {
			String printSentence=(String) controlProps.get("print_sentence");
			if(printSentence.equalsIgnoreCase("YES") && fileName.equalsIgnoreCase(getFileName())){
				condition=Boolean.TRUE;
			}
		}
		return condition;
	}
	
	public static boolean printCleanSentence(String fileName){
		boolean condition=Boolean.FALSE;
		if(null==controlProps){
			new ControlProperties();
		}
		else {
			String printSentence=(String) controlProps.get("print_clean_sentence");
			if(printSentence.equalsIgnoreCase("YES") && fileName.equalsIgnoreCase(getFileName())){
				condition=Boolean.TRUE;
			}
		}
		return condition;
	}
	
	public static boolean printTokens(String fileName){
		boolean condition=Boolean.FALSE;
		if(null==controlProps){
			new ControlProperties();
		}
		else {
			String printTokens=(String) controlProps.get("print_tokens");
			if(printTokens.equalsIgnoreCase("YES") && fileName.equalsIgnoreCase(getFileName())){
				condition=Boolean.TRUE;
			}
		}
		return condition;
	}
	
	public static boolean printMatch(String fileName){
		boolean condition=Boolean.FALSE;
		if(null==controlProps){
			new ControlProperties();
		}
		else {
			String printMatch=(String) controlProps.get("print_match");
			if(printMatch.equalsIgnoreCase("YES") && fileName.equalsIgnoreCase(getFileName())){
				condition=Boolean.TRUE;
			}
		}
		return condition;
	}

	/**
	 * @return the fileName
	 */
	public static String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public static void setFileName(String fileName) {
		if(null==controlProps){
			new ControlProperties();
		}
		ControlProperties.fileName = fileName;
	}
	
	

}
