package com.motivity.labs.opennpl.backup;


import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import com.motivity.tripcase.utils.HtmlFileReader;

/**
 * HtmlTableParser is a helper class is designed to process the HTML file or String as an input and returns the text information contained in various tables in row column
 * @author Nalini Kanta
 *
 */
public class HtmlTableParser {

	/**
	 * parse
	 * @param htmlString
	 * @param filePath
	 * @param isFileSource
	 * @param delimiter
	 * @return Map<String, String>
	 * @throws Throwable
	 */
	
/*	   public static void convertHtmlToXml(String cleanedHtml){
		   try {
			   String fn = "C:\\Users\\CB34388493\\workspace\\HTMLParser\\test\\travel";
			   
			   FileWriter fw  = new FileWriter(fn + ".xml");
			   PrintWriter pw  = new PrintWriter(fw);
			   pw.print(HtmlToXmlConverter.Html2Xml(cleanedHtml));
			   pw.close();
		   } catch (FileNotFoundException e) {
				System.out.println("file not found");
		   } catch (IOException e) {
			   e.printStackTrace();
		   }
	   }*/
	   
	public static Map<String, String> parse(String htmlString, String filePath,
			boolean isFileSource, String delimiter) throws Throwable {

		Map<String, Element> allTablesMap = new LinkedHashMap<String, Element>();
		Map<String, String> allTablesResultMap = new LinkedHashMap<String, String>();
		StringBuilder formatString = null;

		if (Boolean.TRUE == isFileSource) {
			try {
				HtmlFileReader htmlFileReader = new HtmlFileReader();
				htmlString = htmlFileReader.htmlFileRead(filePath);
			} catch (Throwable t) {
				throw t;
			}
		}

		try {
			htmlString=Jsoup.clean(htmlString,  Whitelist.relaxed());
			htmlString=replaceAllAmpSpaceNullEmpty(htmlString);
			
			htmlString = cleanTags(htmlString);
			
			Document document = Jsoup.parse(htmlString);
			
			document=removeTags(document);
			document=removeAttributes(document,"td");
			document=removeAttributes(document,"table");
			
			document=removeEmptyTags(document,"head");
			document=removeEmptyTags(document,"body");
			document=removeEmptyTags(document,"table");
			document=removeEmptyTags(document,"div");
			document=removeEmptyTags(document,"br");
			document=removeEmptyTags(document,"tr");
			document=removeEmptyTags(document,"td");
			document=removeTagsOnlyWithNbsp(document,"td");
			
			//convertHtmlToXml(document.html());

			Elements allTables = document
					.select("table tbody tr td table tbody"); // select the
																// Flight
																// details
																// table.
			
	//	System.out.println(document.html());

			for (int i = 1; i < allTables.size(); i++) {
				
				allTablesMap.put("TABLE_MV_" + i, allTables.get(i));
				
			
			}

			for (String tkeys : allTablesMap.keySet()) {
				// if(tkeys.equalsIgnoreCase("TABLE_MV_9"))
				// { //*testing scenario START */
				Element table = allTablesMap.get(tkeys);

				formatString = new StringBuilder();

				Elements rows = table.children();
				for (Element row : rows) {
			
					Queue<Element> queueElements=null;
					queueElements=new ConcurrentLinkedQueue<Element>();
					
					for(Element child:row.children()){
						queueElements.add(child);
					}
					
									
					while(queueElements.size() >0){
						
						Element queueElement= queueElements.remove();

					
						if(queueElement.tagName().equalsIgnoreCase("td"))
						{
							//System.out.println("Text===>"+queueElement.text());
							if(queueElement.children().size()>=1){
								
								queueElements=refillQueue(queueElements,queueElement);
								
							}
							else{
								
							
								formatString.append(replaceAmpSpaceNullEmpty(queueElement.text()));
								formatString=addDelimiter(formatString,delimiter);
								
							}

						}
						else{
							if(queueElement.children().size()==0){
								formatString.append(replaceAmpSpaceNullEmpty(queueElement.text()));
								formatString=addDelimiter(formatString,delimiter);
							}
							else{
								
									queueElements=refillQueue(queueElements,queueElement);
								
							}
						}
					
					
					}

					formatString.append("\n");
				}
				allTablesResultMap.put(tkeys, formatString.toString());

				// } /**Testing scenario END */
			}

		} catch (Throwable t) {
			throw t;
		}
		
		//allTablesResultMap=removeDuplicateTables(allTablesResultMap);

		return allTablesResultMap;

	}

	private static String replaceAmpSpaceNullEmpty(String val) {

		if (null != val) {
			if (val.trim().isEmpty()) {
				return val.replace("\u00a0", "");
			} else {
				return val.replace("\u00a0", "");
			}
		} else {
			val = "EMPTY";
		}

		return val;
	}
	
	private static boolean checkOnlyNbsp(String val) {

		boolean nbsp=Boolean.FALSE;
		if (null != val) {
			if (val.trim().equalsIgnoreCase("\u00a0")) {
				nbsp= Boolean.TRUE;
			} 
			else{
				nbsp= Boolean.FALSE;
			}
		} 
		return nbsp;
		
	}
	
	private static boolean checkOnlyEmpty(String val) {

		boolean empty=Boolean.FALSE;
		if (null != val) {
			if (val.trim().equalsIgnoreCase("\u00a0")) {
				empty= Boolean.FALSE;
			} 
			else if(val.trim().isEmpty()){
				empty= Boolean.TRUE;
			}
		} 
		return empty;
		
	}


	
	private static Document removeTags(Document document){
//		for( Element element : document.select("a") )
//		{
//		    element.remove();
//		}
		for( Element element : document.select("img") )
		{
		    element.remove();
		}
		
		
		return document;
	}
	
	private static Document removeAttributes(Document document,String tagName){
		
		for( Element element : document.select(tagName) )
		{
	    	Attributes tdAttributes=element.attributes();
		    	for(Attribute attribute:tdAttributes){
		    		element.removeAttr(attribute.getKey());
		    	}
		   
		}
		
		return document;
	}
	
	private static Document removeEmptyTags(Document document,String tagName){
		
		for( Element element : document.select(tagName) )
		{
	    	if(replaceAmpSpaceNullEmpty(element.text()).isEmpty()){
	    		element.remove();
	    	}
		   
		}
		
		return document;
	}
	
	private static Document removeTagsOnlyWithNbsp(Document document,String tagName){
		
		for( Element element : document.select(tagName) )
		{
			boolean checkNbspOnly=checkOnlyNbsp(element.text());
			if(checkNbspOnly){
	    		    		element.remove();
			}
	    	
		}
		
		return document;
	}
	
	private static String replaceAllAmpSpaceNullEmpty(String val) {

		if (null != val) {
			if (val.trim().isEmpty()) {
				return val.replaceAll("&nbsp;", "");
			} else {
				return val.replaceAll("&nbsp;", "");
			}
		} else {
			val = "";
		}

		return val;
	}
	
	private static String cleanTags(String htmlString) {

		htmlString = htmlString.replaceAll("<p>", "");
		htmlString = htmlString.replaceAll("</p>", "");
		htmlString = htmlString.replaceAll("<tbody>", "");
		htmlString = htmlString.replaceAll("</tbody>", "");
		htmlString = htmlString.replaceAll("<thead>", "");
		htmlString = htmlString.replaceAll("</thead>", "");
		
		return htmlString;
	}
	
	private static StringBuilder addDelimiter(StringBuilder sourceString,String delimiter){
		if (null != delimiter && !delimiter.isEmpty()) {
			sourceString.append(delimiter);
		} else {
			sourceString.append("\t");
		}
		return sourceString;
	}
	
	private static Map<String,String> removeDuplicateTables(Map<String,String> allTableMaps){
		
		Set<String> matchedKeys=new HashSet<String>();
		
			for(String key:allTableMaps.keySet()){
				String value=allTableMaps.get(key);
				
				Set<Entry<String,String>> entries=allTableMaps.entrySet();
				Iterator<Entry<String,String>> entryItr=entries.iterator();
				while(entryItr.hasNext()){
					Entry<String,String> entry=entryItr.next();
					if(!entry.getKey().equals(key) && value.contains(entry.getValue())){
						matchedKeys.add(entry.getKey());
					}
					
				}
				
				
			}
			
			for(String matchedKey:matchedKeys){
				allTableMaps.remove(matchedKey);
			}
		
		
		
		
		return allTableMaps;
	}
	
	private static Queue<Element> refillQueue(Queue<Element> queueElements,Element element){
		
		Queue<Element> tempQueueElements=new ConcurrentLinkedQueue<Element>();
		tempQueueElements.addAll(queueElements);
		queueElements.removeAll(queueElements);
		for(Element child:element.children()){
			queueElements.add(child);
		}
		queueElements.addAll(tempQueueElements);
		
		return queueElements;
		
	}
	
	
}
