package com.sabre.tripcase.tcp.opennlp.utils;


import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities.EscapeMode;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import com.sabre.tripcase.tcp.opennlp.constants.Constants;


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
	
	   public static void convertHtmlToXml(String cleanedHtml){
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
	   }
	   
	public static Map<String, String> parse(String htmlString, String filePath,
			boolean isFileSource, String delimiter) throws Throwable {

		Map<String, Element> allTablesMap = new LinkedHashMap<String, Element>();
		Map<String, String> allTablesResultMap = new LinkedHashMap<String, String>();
		StringBuilder formatString = null;
		String patternMatches="DEPART(.*)AIRCRAFT(.*)ARRIVE(.*)CABIN(.*)TRAVEL TIME(.*)MEAL(.*)SEATS(.*)";

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
			htmlString=replaceAllHtmlCharacters(htmlString);
			
			htmlString = cleanTags(htmlString);
			
			Document document = Jsoup.parse(htmlString,"ISO-8859-9");
			
			document.outputSettings().escapeMode(EscapeMode.base); // default

			document.outputSettings().charset("UTF-8");
//			//document.outputSettings().charset("ASCII");
			//document.outputSettings().charset("ISO-8859-1");
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
			//document=removeTagsOnlyWithNbsp(document,"td");
			
			//convertHtmlToXml(document.html());
		
			//Elements allTables = document.select("table tbody tr td table tbody"); 
			Elements allTables = getTables(document,"table tbody");
			
			Set<String> hzArrangements=new LinkedHashSet<String>();
			hzArrangements.add("DEPART");
			hzArrangements.add("AIRCRAFT");
			hzArrangements.add("ARRIVE");
			hzArrangements.add("CABIN");
			hzArrangements.add("TRAVEL TIME");
			hzArrangements.add("MEAL");
			hzArrangements.add("SEATS");
			
			
			
			//allTablesMap=getTablesToParse(allTables);
			allTablesMap=getTablesUsingQueue(allTables);

			
		//	allTablesResultMap=removeDuplicateTables(allTablesResultMap);

			for (String tkeys : allTablesMap.keySet()) {
				// if(tkeys.equalsIgnoreCase("TABLE_MV_12"))
				// { //*testing scenario START */
				Element table = allTablesMap.get(tkeys);
				//table=removeEmptyTagsFromTable(table,"td");
				System.out.println("Table Data=====>"+table.html());
				
				//Element childTable=table.child(0).select("table").first();
				//System.out.println("Child Table Name="+childTable+",Key="+tkeys);

				formatString = new StringBuilder();

				Elements rows = table.children();
				for (Element row : rows) {
					
				//	row=removeEmptyTags(row,"td");
			
					Queue<Element> queueElements=null;
					queueElements=new ConcurrentLinkedQueue<Element>();
					
					for(Element child:row.children()){
						queueElements.add(child);
					}
					
									
					while(queueElements.size() >0){
						
						Element queueElement= queueElements.remove();
						//System.out.println("Text====>"+queueElement.text());
						
/*						if(queueElement.tagName().equalsIgnoreCase("td") || queueElement.tagName().equalsIgnoreCase("th"))
						{
							
								
								
								if(queueElement.ownText().isEmpty()){
									if(!queueElement.text().isEmpty()){
									formatString.append(replaceAmpSpaceNullEmpty(queueElement.text())+"|");
									}
								}
								else{
									formatString.append(replaceAmpSpaceNullEmpty(queueElement.ownText())+"|");
								}
								//formatString.append("|");
							
						}
						else{
							queueElements=refillQueue(queueElements,queueElement);
							//formatString.append("\n");
						}*/
											
						if(queueElement.tagName().equalsIgnoreCase("td"))
						{

							if(queueElement.children().size()>=1){
								
								queueElements=refillQueue(queueElements,queueElement);
								
							}
							else{
								if(!replaceAmpSpaceNullEmpty(queueElement.text()).isEmpty()){
									formatString.append(replaceAmpSpaceNullEmpty(queueElement.text()));
									formatString=addDelimiter(formatString,delimiter);
								}

								
								
							}

						}
						else{
							if(queueElement.children().size()==0){
								if(!replaceAmpSpaceNullEmpty(queueElement.text()).isEmpty()){
								formatString.append(replaceAmpSpaceNullEmpty(queueElement.text()));
								formatString=addDelimiter(formatString,delimiter);
								}
								
							}

							else{
								if(queueElement.tagName().equalsIgnoreCase("tr")){
									if(queueElement.child(0).tagName().equalsIgnoreCase("td")){
									formatString.append("\n");
									}
								}
								
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
		
		//allTablesResultMap=removeDuplicateTables(allTablesResultMap,delimiter);
		//changeArrangements(allTablesResultMap,patternMatches);
		
		
//		Set<String> keys=allTablesResultMap.keySet();
//		for(String key:keys){
//			String testTable=allTablesResultMap.get(key);
//		String[] v=testTable.split("#");
//		for(String str:v){
//			System.out.println(str);
//		}
//		}
		
		

		return allTablesResultMap;

	}

	private static String replaceAmpSpaceNullEmpty(String val) {

		if (null != val) {
			if (val.trim().isEmpty()) {
				return val.replace("\u00a0", "EMPTY");
			} else {
				return val.replace("\u00a0", "EMPTY");
				 
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
		for( Element element : document.select("a") )
		{
		    element.remove();
		}
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
	    		
//	    		if(tagName.equalsIgnoreCase("td")){
//	    			element.prependText("EMPTY");
//	    		}
//	    		else{
	    			element.remove();
	    		//}
	    		
	    	}
		   
		}
		
		return document;
	}
	
	private static Element removeEmptyTags(Element elem,String tagName){
		
		int siblingsCount=elem.siblingElements().size();
		System.out.println("Sibilings Count for the Given Row=["+elem+"] --->"+ siblingsCount);
		Elements allSiblings=elem.siblingElements();
		Map<String,Element> allRows=new LinkedHashMap<String,Element>();
		Map<String,Integer> tableStructureMap=new LinkedHashMap<String,Integer>();
		Map<String,Integer> columnIndexMap=new LinkedHashMap<String,Integer>();
		Map<String,ArrayList<Element>> emptyColumnMap=new HashMap<String,ArrayList<Element>>();
		
		
		allRows.put("1", elem);
		tableStructureMap.put("1", elem.children().size());
		for(int i=0;i<allSiblings.size();i++){
			allRows.put(String.valueOf((i+2)), allSiblings.get(i));
			tableStructureMap.put(String.valueOf((i+2)), allSiblings.get(i).children().size());
		}
		
		Set<Entry<String,Element>> rowEntries=allRows.entrySet();
		Iterator<Entry<String,Element>> rowEntriesItr =rowEntries.iterator();
		while(rowEntriesItr.hasNext()){
			Entry<String,Element> rowEntry=rowEntriesItr.next();
			Element row=rowEntry.getValue();
			
			int columnIndex=0;
			int columnCount=1;
			for( Element td : row.children())
			{
				
		    	if(replaceAmpSpaceNullEmpty(td.text()).isEmpty()){
		    		
		    				    		
		    		if(null!=columnIndexMap && columnIndexMap.containsKey(String.valueOf(columnIndex))){
		    			
		    				columnCount=columnIndexMap.get(String.valueOf(columnIndex));
		    				columnCount++;
		    				columnIndexMap.put(String.valueOf(columnIndex), columnCount);
		    				ArrayList<Element> emptyColumnList=emptyColumnMap.get(String.valueOf(columnIndex));
		    				emptyColumnList.add(td);
		    				emptyColumnMap.put(String.valueOf(columnIndex), emptyColumnList);
		    			
		    		}
		    		else{
		    			ArrayList<Element> emptyColumnList=new ArrayList<Element>();
		    			emptyColumnList.add(td);
		    			columnIndexMap.put(String.valueOf(columnIndex), 1);
		    			emptyColumnMap.put(String.valueOf(columnIndex), emptyColumnList);
		    		}
		    		
		      		
		    	}
		    	
		    	columnIndex++;
			   
			}
			
		}
		
		
		//System.out.println("Column Index Map="+columnIndexMap);
		
		/** Now Remove the Empty Columns which are not required */
		
		Set<Entry<String,Integer>> columnIndexEntries=columnIndexMap.entrySet();
		Iterator<Entry<String,Integer>> columnIndexEntriesItr=columnIndexEntries.iterator();
		while(columnIndexEntriesItr.hasNext()){
		Entry<String,Integer>	columnIndexEntry=columnIndexEntriesItr.next();
		if(columnIndexEntry.getValue() ==allRows.size()){
			ArrayList<Element> emptyColumns=emptyColumnMap.get(columnIndexEntry.getKey());
			for(Element emptyColumn:emptyColumns){
				System.out.println("Column ===>"+emptyColumn+" is removed successfully");
				emptyColumn.remove();
			}
		}
		}
		
		return elem;
	}
	
	private static Element removeEmptyTagsFromTable(Element elem,String tagName){
		
		int rowCount=elem.children().size();
	//	System.out.println("Rows Count for the Given Table=["+elem+"] --->"+ rowCount);
		Elements rowsFromTable=elem.children();
		Map<String,Element> allRows=new LinkedHashMap<String,Element>();
		Map<String,Integer> tableStructureMap=new LinkedHashMap<String,Integer>();
		Map<String,Integer> columnIndexMap=new LinkedHashMap<String,Integer>();
		Map<String,ArrayList<Element>> emptyColumnMap=new HashMap<String,ArrayList<Element>>();
		
		
		
		tableStructureMap.put("1", elem.children().size());
		for(int i=0;i<rowsFromTable.size();i++){
			allRows.put(String.valueOf((i+1)), rowsFromTable.get(i));
			tableStructureMap.put(String.valueOf((i+1)), rowsFromTable.get(i).children().size());
		}
		
		Set<Entry<String,Element>> rowEntries=allRows.entrySet();
		Iterator<Entry<String,Element>> rowEntriesItr =rowEntries.iterator();
		while(rowEntriesItr.hasNext()){
			Entry<String,Element> rowEntry=rowEntriesItr.next();
			Element row=rowEntry.getValue();
			
			int columnIndex=0;
			int columnCount=1;
			for( Element td : row.children())
			{
				
		    	if(replaceAmpSpaceNullEmpty(td.text()).isEmpty()){
		    		
		    				    		
		    		if(null!=columnIndexMap && columnIndexMap.containsKey(String.valueOf(columnIndex))){
		    			
		    				columnCount=columnIndexMap.get(String.valueOf(columnIndex));
		    				columnCount++;
		    				columnIndexMap.put(String.valueOf(columnIndex), columnCount);
		    				ArrayList<Element> emptyColumnList=emptyColumnMap.get(String.valueOf(columnIndex));
		    				emptyColumnList.add(td);
		    				emptyColumnMap.put(String.valueOf(columnIndex), emptyColumnList);
		    			
		    		}
		    		else{
		    			ArrayList<Element> emptyColumnList=new ArrayList<Element>();
		    			emptyColumnList.add(td);
		    			columnIndexMap.put(String.valueOf(columnIndex), 1);
		    			emptyColumnMap.put(String.valueOf(columnIndex), emptyColumnList);
		    		}
		    		
		      		
		    	}
		    	
		    	columnIndex++;
			   
			}
			
		}
		
		
		//System.out.println("Column Index Map="+columnIndexMap);
		
		/** Now Remove the Empty Columns which are not required */
		
		Set<Entry<String,Integer>> columnIndexEntries=columnIndexMap.entrySet();
		Iterator<Entry<String,Integer>> columnIndexEntriesItr=columnIndexEntries.iterator();
		while(columnIndexEntriesItr.hasNext()){
		Entry<String,Integer>	columnIndexEntry=columnIndexEntriesItr.next();
		if(columnIndexEntry.getValue() ==allRows.size()){
			ArrayList<Element> emptyColumns=emptyColumnMap.get(columnIndexEntry.getKey());
			for(Element emptyColumn:emptyColumns){
			//	System.out.println("Column ===>"+emptyColumn+" is removed successfully");
				emptyColumn.remove();
			}
		}
		}
		
		return elem;
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
	
	private static String replaceAllHtmlCharacters(String val) {

		if (null != val) {
			if (val.trim().isEmpty()) {
				return val.replaceAll("&nbsp;", "");
			} else {
				val=val.replaceAll("&gt;", "");
				val=val.replaceAll("&lt;", "");
				val=val.replaceAll("&amp;", " ");
				val=val.replaceAll("=09", "");
				val=val.replaceAll("=20", "");
				val=val.replaceAll("3D", "");
				
				return val.replaceAll("&nbsp;", " ");
			}
		} else {
			val = "";
		}

		return val;
	}
	
	private static String cleanTags(String htmlString) {

		htmlString = htmlString.replaceAll("<p>", "");
		htmlString = htmlString.replaceAll("</p>", "");
		htmlString = htmlString.replaceAll("<b>", "");
		htmlString = htmlString.replaceAll("</b>", "");
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
			sourceString.append(" ");
		}
		return sourceString;
	}
	
	private static Map<String,String> removeDuplicateTables(Map<String,String> allTableMaps,String delimiter){
		
		
		Set<String> matchedKeys=new HashSet<String>();
		
			for(String key:allTableMaps.keySet()){
				String value=allTableMaps.get(key);
				
				Set<Entry<String,String>> entries=allTableMaps.entrySet();
				Iterator<Entry<String,String>> entryItr=entries.iterator();
				while(entryItr.hasNext()){
					Entry<String,String> entry=entryItr.next();
					if(!entry.getKey().equals(key) && value.replace(delimiter, Constants.EMPTY).replace(Constants.NEWLINE, Constants.EMPTY).contains(entry.getValue().replace(delimiter, Constants.EMPTY).replace(Constants.NEWLINE, Constants.EMPTY))){
						matchedKeys.add(entry.getKey());
					}
					
				}
				
				
			}
			
			for(String matchedKey:matchedKeys){
				allTableMaps.remove(matchedKey);
			}
		
		
		
		
		return allTableMaps;
	}
	
	private static Map<String,String> removeDuplicateEntries(Map<String,String> allTableMaps,String delimiter){
		
		Set<String> entriesSet=new HashSet<String>();
		Set<String> allKeys=new HashSet<String>();
		entriesSet.addAll(allTableMaps.values());
	
		for(String value:entriesSet){
			
			
		}
		
		return allTableMaps;
	}
	
	private static Map<String,String> changeArrangements(Map<String,String> allTableMaps,String patternMatches){
		
		Set<String> matchedKeys=new HashSet<String>();
		
							
				Set<Entry<String,String>> entries=allTableMaps.entrySet();
				Iterator<Entry<String,String>> entryItr=entries.iterator();
				while(entryItr.hasNext()){
					Entry<String,String> entry=entryItr.next();
					if(entry.getValue().replace("\n", "").matches(patternMatches)){
						matchedKeys.add(entry.getKey());
						Pattern r = Pattern.compile(patternMatches);
						Matcher m = r.matcher(entry.getValue().replace("\n", ""));
						System.out.println(patternMatches.replace("(.*)", "\t"));
						if (m.find( )) {
							for (int i=1;i<=m.groupCount();i++){
					         System.out.print(m.group(i));
							}
					      } else {
					         System.out.println("NO MATCH");
					      }
					}
					
					
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
	
	private static Map<String, Element> getTablesUsingQueue(Elements tables){
		
		Queue<Element> producerQueues=new ConcurrentLinkedQueue<Element>();
		Queue<Element> consumerQueues=new ConcurrentLinkedQueue<Element>();
		Set<Element> uniqueTables=new LinkedHashSet<Element>();
		Map<String, Element> allTables = new LinkedHashMap<String, Element>();
		
		for(Element table:tables){
			
			producerQueues.add(table);
			producerQueues.add(tables.first());
				
			
		}
		
		while(producerQueues.size() >0){
			
			Element peekedTable=producerQueues.remove();
			if(peekedTable.children().select("table").size()>2){
				for(Element element:peekedTable.children()){
					if(element.tagName().equalsIgnoreCase("table")){
				consumerQueues.add(element);
					}
				
				}
			}
			else{
				consumerQueues.add(peekedTable);
			}
		}
		for(Element tableSelected:consumerQueues){
			uniqueTables.add(tableSelected);
			//System.out.println("Table----->"+tableSelected);
			
		}
		
		int i=0;
		for(Element e:uniqueTables){
			i++;
			allTables.put("TABLE_MV_" + i, e);
		}
		
		return allTables;
		
		
	}
	
	private static Map<String, Element> getTablesToParse(Elements tables){
		Map<String, Element> allTables = new LinkedHashMap<String, Element>();
		
		for (int i = 0; i < tables.size(); i++) {

			allTables.put("TABLE_MV_" + i, tables.get(i));
		
		}
		return allTables;
		
	}
	
	private static Elements getTables(Document htmlDocument,String criteria){
		
		Elements tables = htmlDocument.select(criteria);
		
		return tables;
		
	}
	
	
}
