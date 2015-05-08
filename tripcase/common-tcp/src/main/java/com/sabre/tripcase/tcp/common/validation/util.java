package com.sabre.tripcase.tcp.common.validation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

/**
 * @author naresh.k
 *
 */
public class util {		
	
	private static Logger log =Logger.getLogger(util.class);
	
	/* Define constants here
	 * */	
	public final static String NEWLINE = "[\r\n]+";
	
	public final static String NBSP = "&nbsp;";
	
	public final static String SPACE_EMPTY = "";	
	
	
	
	final static String LANG_ENGLISH 	= "English";
	final static String LANG_DANISH 	= "Danish";
	final static String LANG_HUNGARIAN 	= "Hungarian";
	final static String LANG_NORWEGIAN 	= "Norwegian";
	final static String LANG_SWEDISH 	= "Swedish";
	final static String LANG_ESTONIAN 	= "Estonian";
	final static String LANG_FINNISH 	= "Finnish";
	final static String LANG_ITALIAN 	= "Italian";
	final static String LANG_PORTUGUESE = "Portuguese";
	final static String LANG_GREEK 		= "Greek";
	final static String LANG_FRENCH 	= "French"; 
	final static String LANG_DUTCH		= "Dutch";
	final static String LANG_RUSSIAN 	= "Russian";
	final static String LANG_GERMAN 	= "German";
	final static String LANG_SPANISH 	= "Spanish";
	final static String LANG_ICELANDIC 	= "Icelandic";
	final static String LANG_POLISH 	= "Polish";
	final static String LANG_THAI 		= "Thai";
	final static String LANG_OTHERS 	= "Unsupported";	
		
	/**
	 * @param ln
	 * @return
	 */
	public static String langFullName(String ln) {
		
		if (ln.compareToIgnoreCase("en") == 0) {
			return LANG_ENGLISH;
		} else if (ln.compareToIgnoreCase("da") == 0) {
			return LANG_DANISH;
		}else if (ln.compareToIgnoreCase("hu") == 0) {
			return LANG_HUNGARIAN;
		}else if (ln.compareToIgnoreCase("no") == 0){
			return LANG_NORWEGIAN;
		}else if (ln.compareToIgnoreCase("sv") == 0){
			return LANG_SWEDISH;
		}else if (ln.compareToIgnoreCase("et") == 0){
			return LANG_ESTONIAN;
		}else if (ln.compareToIgnoreCase("fi") == 0){
			return LANG_FINNISH;
		}else if (ln.compareToIgnoreCase("it") == 0){
			return LANG_ITALIAN;
		}else if (ln.compareToIgnoreCase("pt") == 0){
			return LANG_PORTUGUESE;
		}else if (ln.compareToIgnoreCase("el") == 0){
			return LANG_GREEK;
		}else if (ln.compareToIgnoreCase("fr") == 0){
			return LANG_FRENCH;
		}else if (ln.compareToIgnoreCase("nl") == 0){
			return LANG_DUTCH;
		}else if (ln.compareToIgnoreCase("ru") == 0){
			return LANG_RUSSIAN;
		}else if (ln.compareToIgnoreCase("de") == 0){
			return LANG_GERMAN;
		}else if (ln.compareToIgnoreCase("es") == 0){
			return LANG_SPANISH;
		}else if (ln.compareToIgnoreCase("is") == 0){
			return LANG_ICELANDIC;
		}else if (ln.compareToIgnoreCase("pl") == 0){
			return LANG_POLISH;
		}else if (ln.compareToIgnoreCase("th") == 0) {
			return LANG_THAI;
		}
		else {
			return LANG_OTHERS;
		}
	}
	
	
	/**
	 * @param htmlStr
	 * @return
	 * @Description Converts from HTML to plan text format.
	 */
	public static String convertHTML2Text(String htmlStr) {
		return htmlStr.replaceAll("<!--.*?-->", "").replaceAll("<[^>]+>", "");	
	}
	
	
	/**
	 * @param fileName
	 * @param strtext
	 */
	public static void writeToFile(String fileName , String strtext) {
		// Split the sting of text by new line and match for the airlines source.
				//String[] splitStr = strtext.split(util.NEWLINE);
		try {
			// Write the content of the source file in to destination file in .txt format
			log.info("Destination file:" + fileName); 
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
			// for(int i=0;i<splitStr.length;i++){
				 out.write(strtext);
		//	 }
			out.close();	
			log.info("Destination filstrtexte written successfully:"); 
		}
		catch (IOException ex)
		{	   
			log.error("Failed to save the content in to File"+ ex.getMessage()); 						
		}

	}
	
	
	/**
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	

	
} // end of class
