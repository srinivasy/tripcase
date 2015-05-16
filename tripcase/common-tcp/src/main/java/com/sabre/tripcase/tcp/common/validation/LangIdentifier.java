package com.sabre.tripcase.tcp.common.validation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LangIdentifier {
	
	private final static String NEWLINE = "[\r\n]+";
	private static int nValidEnLines = 0;
	/* Function overloaded to identifies the language of given text */
	public String identifyLanguage(String messageContent) 
	{

		String lang = getLanguage(messageContent);
		String sLine = null;
		
		try{
			if (!lang.equalsIgnoreCase("en")) {
				String strLine[] = null;
				
				messageContent = convertHTML2Text(messageContent);	
				messageContent = removeBlankLines(messageContent);				
				strLine = messageContent.split(NEWLINE);
				
				/*Read File Line By Line till it finds some English texts. 
				This is heavy operation, so once it finds minimum of five English lines coming out of the operation.
				*/
				for(int i=1;i<strLine.length;i++){					
					sLine= removeBlankLines(strLine[i]);					
					if (sLine.length() == 0 || sLine == null){
						continue;
					}else {
						lang= getLanguage(strLine[i]);					
						if (lang.equalsIgnoreCase("en") ) {
							nValidEnLines++;
							if(nValidEnLines > 4)					
								break;
						}
					}
				}

			}	
		}catch(Exception ex) {
			System.out.println("Exception: identifyLanguage"); 
		}
		return lang;
	}

	/* Function overloaded to identifies the language of given File */
	public String identifyLanguage(File file)
	{
		//Parser method parameters
		Parser parser = new AutoDetectParser();
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		FileInputStream content;
		try {
			content = new FileInputStream(file);

			//Parsing the given document
			parser.parse(content, handler, metadata, new ParseContext());
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TikaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LanguageIdentifier object = new LanguageIdentifier(handler.toString());
		return object.getLanguage();
	}
	
	public static String getLanguage(String str) {

		LanguageIdentifier identifier = new LanguageIdentifier(str);
		String language = identifier.getLanguage();
		return language;
	}
	
	/**
	 * Return the Email body text content of the message.
	 */
	public static String convertHTML2Text(String htmlStr) {
		return htmlStr.replaceAll("<!--.*?-->", "").replaceAll("<[^>]+>", "");	
	}

	public static String removeBlankLines(String bodyText){
		//Pattern p1 = Pattern.compile("^\\s*$\\n", Pattern.MULTILINE); ^\s*$
		
		bodyText=bodyText.replaceAll("\t", " ").trim();
		bodyText=bodyText.replaceAll("&nbsp;", "\n").trim();
		bodyText=bodyText.trim();
		
		Pattern p1 = Pattern.compile("^\\s*$", Pattern.MULTILINE);
		Matcher m = p1.matcher(bodyText);
		bodyText=m.replaceAll("");
		// replace any special chars at starting of line
		return bodyText;

	}


}
