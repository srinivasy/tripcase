package com.motivity.labs.parse.html.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.util.Set;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.motivity.labs.parse.file.reader.HtmlFileReader;
/**
 * HtmlParser
 * @author Nalini Kanta
 *
 */
public class HtmlParser {
	
	static StringBuilder formatData=null;
	
	public static String parseTables(String htmlString,String filePath,boolean isFileSource,String delimiter) throws Throwable{
		StringBuilder formattedOutput = new StringBuilder();
		List< Map<String,String>> allTableList=new ArrayList<Map<String,String>>();
		if (Boolean.TRUE == isFileSource) {
			try {
				HtmlFileReader htmlFileReader = new HtmlFileReader();
				htmlString = htmlFileReader.htmlFileRead(filePath);
			} catch (Throwable t) {
				throw t;
			}
		}

		try {
			Document document = Jsoup.parse(htmlString);
			//Elements processAllTables = document.select("table"); //select the Passenger & travel cost details table.
		    Element processAllTables = document.select("table tbody tr td table").get(0); //select the first table.
		   // System.out.println("Tables ----->"+processAllTables);
		   	processAllTables(processAllTables);
			
		}
		catch(Throwable t){
			throw t;
		}
		
		
		return formattedOutput.toString();
	}
	

	/**
	 * travelCostParse
	 * @param htmlString
	 * @param filePath
	 * @param isFileSource
	 * @param delimiter
	 * @return String
	 * @throws Throwable
	 */
	public static String travelCostParse(String htmlString,String filePath,boolean isFileSource,String delimiter) throws Throwable{
		StringBuilder formattedOutput = new StringBuilder();
		List< Map<String,String>> travelCostList=new ArrayList<Map<String,String>>();
		if (Boolean.TRUE == isFileSource) {
			try {
				HtmlFileReader htmlFileReader = new HtmlFileReader();
				htmlString = htmlFileReader.htmlFileRead(filePath);
			} catch (Throwable t) {
				throw t;
			}
		}

		try {
			Document document = Jsoup.parse(htmlString);
			Elements passengerTables = document.select("table tbody tr td table tbody tr td table"); //select the Passenger & travel cost details table.
			travelCostList=processTravelCostTable(passengerTables,travelCostList);
			formattedOutput.append(format(travelCostList,delimiter));
		}
		catch(Throwable t){
			throw t;
		}
		
		
		return formattedOutput.toString();
	}
	
	/**
	 * passengerSummaryParse
	 * @param htmlString
	 * @param filePath
	 * @param isFileSource
	 * @param delimiter
	 * @return String
	 * @throws Throwable
	 */
	public static String passengerSummaryParse(String htmlString,String filePath,boolean isFileSource,String delimiter) throws Throwable{
		StringBuilder formattedOutput = new StringBuilder();
		List< Map<String,String>> passengerList=new ArrayList<Map<String,String>>();

		if (Boolean.TRUE == isFileSource) {
			try {
				HtmlFileReader htmlFileReader = new HtmlFileReader();
				htmlString = htmlFileReader.htmlFileRead(filePath);
			} catch (Throwable t) {
				throw t;
			}
		}

		try {
			Document document = Jsoup.parse(htmlString);
			Elements passengerTables = document.select("table tbody tr td table tbody tr td table"); //select the Passenger & travel cost details table.
			passengerList=processPassengerTable(passengerTables,passengerList);
			formattedOutput.append(format(passengerList,delimiter));
		}
		catch(Throwable t){
			throw t;
		}
		return formattedOutput.toString();
	}
	
	/**
	 * flightDetailsParse
	 * @param htmlString
	 * @param filePath
	 * @param isFileSource
	 * @param delimiter
	 * @return String
	 * @throws Throwable
	 */
	public static String flightDetailsParse(String htmlString,String filePath,boolean isFileSource,String delimiter) throws Throwable{
		StringBuilder formattedOutput = new StringBuilder();
		List< Map<String,String>> flightList=new ArrayList<Map<String,String>>();

		if (Boolean.TRUE == isFileSource) {
			try {
				HtmlFileReader htmlFileReader = new HtmlFileReader();
				htmlString = htmlFileReader.htmlFileRead(filePath);
			} catch (Throwable t) {
				throw t;
			}
		}

		try {
			Document document = Jsoup.parse(htmlString);
			Elements flightTables = document.select("table tbody tr td table tbody tr td table tbody tr td table"); //select the Flight details table.
			flightList=processFlightTable(flightTables,flightList);
			formattedOutput.append(format(flightList,delimiter));
		}
		catch(Throwable t){
			throw t;
		}
		return formattedOutput.toString();
	}
	
	/**
	 * parse
	 * @param htmlString
	 * @param filePath
	 * @param isFileSource
	 * @param delimiter
	 * @return String
	 * @throws Throwable
	 */
	public static Map<String,String>  parse(String htmlString,String filePath,boolean isFileSource,String delimiter) throws Throwable{

		Map<String,String> containerParentMap=new LinkedHashMap<String,String>();
		List< Map<String,String>> flightList=new ArrayList<Map<String,String>>();
		List< Map<String,String>> passengerList=new ArrayList<Map<String,String>>();
		List< Map<String,String>> travelCostList=new ArrayList<Map<String,String>>();

		if (Boolean.TRUE == isFileSource) {
			try {
				HtmlFileReader htmlFileReader = new HtmlFileReader();
				htmlString = htmlFileReader.htmlFileRead(filePath);
			} catch (Throwable t) {
				throw t;
			}
		}

		try {
			Document document = Jsoup.parse(htmlString);
			/** Passenger Summary ,  path: table tbody tr td table ) , Data Structure Horizontal**/
			/** Total Travel Cost ,  path: table tbody tr td table ) , Data Structure Vertical **/
			/** Flight Details ,  path: table tbody tr td table ) , Data Structure Vertical **/

			Elements flightTables = document.select("table tbody tr td table tbody tr td table tbody tr td table"); //select the Flight details table.
			flightList=processFlightTable(flightTables,flightList);
				
			Elements passengerTables = document.select("table tbody tr td table tbody tr td table"); //select the Passenger & travel cost details table.
			passengerList=processPassengerTable(passengerTables,passengerList);
			
			travelCostList=processTravelCostTable(passengerTables,travelCostList);
			
			containerParentMap.put("Table_Passenger",format(passengerList,delimiter));
			containerParentMap.put("Table_Flight",format(flightList,delimiter));
			containerParentMap.put("Table_TravelCost",format(travelCostList,delimiter));
			
			
		    

		} catch (Throwable t) {
			throw t;
		}

		return containerParentMap;
	
	}
	
/**
 * processFlightTable
 * @param nestedTables
 * @param flightList
 * @return List<Map<String,String>>
 * @throws Throwable
 */
	private static List<Map<String,String>> processFlightTable(Elements nestedTables,List<Map<String,String>> flightList) throws Throwable{
		ArrayList<Element> tableList = new ArrayList<Element>();
		Map<String,String> flightDetailsMap=null;
		   
		try{
		for(int i=0;i<nestedTables.size();i++){
			//System.out.println("Table Index="+i+"--->Tag Name--->"+tables.get(i).tagName()+"--->"+tables.get(i).html());
			if(nestedTables.get(i).html().contains("FLIGHT#") || nestedTables.get(i).html().contains("DEPART"))
			tableList.add(nestedTables.get(i));
		}
		
		//System.out.println("ArrayList--->"+tableList.toString());
		/** Processing for Flight Details */
		for(Element tbody:tableList){
			
						
			if(tbody.html().contains("FLIGHT#")){
				flightDetailsMap=new LinkedHashMap<String,String>();
				flightDetailsMap=processFlightDetails(tbody,flightDetailsMap);
				
			}
			else if(tbody.html().contains("DEPART")){
				flightDetailsMap=processFlightDetails(tbody,flightDetailsMap);
				flightList.add(flightDetailsMap);
			}
		}
		}
		catch(Throwable t){
			throw t;
		}
		return flightList;
	}
	
	/**
	 * processPassengerTable
	 * @param nestedTables
	 * @param passengerList
	 * @return List<Map<String,String>> processPassengerTable
	 * @throws Throwable
	 */
	private static List<Map<String,String>> processPassengerTable(Elements nestedTables,List< Map<String,String>> passengerList) throws Throwable{
		ArrayList<Element> tableList = new ArrayList<Element>();
		   try{
		
		for(int i=0;i<nestedTables.size();i++){
			if(nestedTables.get(i).html().contains("Passenger summary"))
			tableList.add(nestedTables.get(i));
		}
		
		/** Processing for Flight Details */
		for(Element tbody:tableList){
			
						
			if(tbody.html().contains("Passenger summary")){
				processPassengerDetails(tbody,passengerList);
				
			}

		}
		   }
		   catch(Throwable t){
			   throw t;
		   }
		return passengerList;
	}
	
	private static void processAllTables(Element allTables) throws Throwable{
		Map<String,String> tableMaps= new LinkedHashMap<String,String>();
		Elements tbodys=null;
		Elements rows=null;
		Elements cols=null;
		Elements texts=null;
		int tableCount=0;
		
		   
		try{

			if(allTables.tagName().equalsIgnoreCase("table")){
			tbodys = allTables.children();
			
			for(int t=0;t<tbodys.size();t++){
				
				if(tbodys.get(t).tagName().equalsIgnoreCase("tbody")){
					
					processAllTables(tbodys.get(t));
					
					
				}
			}
			}
			//System.out.println("tbodys size--->"+tbodys.size());
			
			if(allTables.tagName().equalsIgnoreCase("tbody")){
				rows=allTables.children();
				
				for(int t=0;t<rows.size();t++){
					if(rows.get(t).tagName().equalsIgnoreCase("tr")){
						
						processAllTables(rows.get(t));
						
					}
				}
			}
			
			if(allTables.tagName().equalsIgnoreCase("tr")){
				cols=allTables.children();
				
				for(int t=0;t<cols.size();t++){
					if(cols.get(t).tagName().equalsIgnoreCase("td")){
						processAllTables(cols.get(t));
						System.out.println("Column Data--->"+formatData);
					}
				}
			}
			
			if(allTables.tagName().equalsIgnoreCase("td")){
				
				Elements colChildren=allTables.children();
				for(int t=0;t<colChildren.size();t++){
					if(colChildren.get(t).tagName().equalsIgnoreCase("table")){
						formatData=new StringBuilder();
						processAllTables(colChildren.get(t));
						
					}
					else if(colChildren.get(t).tagName().equalsIgnoreCase("tbody")){
						
						processAllTables(colChildren.get(t));
					}
					else {
						//process Column Data
						formatData.append(replaceAmpSpaceNullEmpty(allTables.text()));
						formatData.append("|");
					}
				}
				
			}
			
			
			
//			for(String key:tableMaps.keySet()){
//				System.out.println(key+"\n");
//				System.out.println(tableMaps.get(key)+"\n");
//			}
			
		}
		

		
		catch(Throwable t){
			throw t;
		}
		
	}
	/**
	 * processTravelCostTable
	 * @param nestedTables
	 * @param travelCostList
	 * @return List<Map<String,String>>
	 * @throws Throwable
	 */
	private static List<Map<String,String>> processTravelCostTable(Elements nestedTables,List< Map<String,String>> travelCostList) throws Throwable{
		ArrayList<Element> tableList = new ArrayList<Element>();
		   
		try{
		for(int i=0;i<nestedTables.size();i++){
			if(nestedTables.get(i).html().contains("Total travel cost"))
			tableList.add(nestedTables.get(i));
		}
		
		/** Processing for Travel Cost Details */
		for(Element tbody:tableList){
			
						
			if(tbody.html().contains("Total travel cost")){
				travelCostList=processTravelCostDetails(tbody,travelCostList);
				break;
				
			}

		}
		}
		catch(Throwable t){
			throw t;
		}
		return travelCostList;
	}
	
	/**
	 * processFlightDetails
	 * @param tbody
	 * @param flightDetailsMap
	 * @return Map<String,String>
	 */
	private static Map<String,String> processFlightDetails(Element tbody,Map<String,String> flightDetailsMap) {
		
		if(tbody.html().contains("FLIGHT#")){
			
		Elements rows=tbody.select("tr");
		for (int i=0;i<rows.size();i++){
			Element row=rows.get(0);
			Elements cols=row.select("td");
			flightDetailsMap.put("FLIGHT#", replaceAmpSpaceNullEmpty(cols.get(0).text()));
			flightDetailsMap.put("OPERATOR", replaceAmpSpaceNullEmpty(cols.get(3).text()));
			
		}
		}
		else if(tbody.html().contains("DEPART")){
				Elements rows=tbody.select("tr");
			
				Element row=rows.get(0);
				Elements cols=row.select("td");
				flightDetailsMap.put(replaceAmpSpaceNullEmpty(cols.get(0).text()), replaceAmpSpaceNullEmpty(cols.get(2).text()));
				flightDetailsMap.put("Terminal", replaceAmpSpaceNullEmpty(cols.get(3).text()));
				flightDetailsMap.put(replaceAmpSpaceNullEmpty(cols.get(4).text()), replaceAmpSpaceNullEmpty(cols.get(6).text()));
				
				row=rows.get(1);
				cols=row.select("td");
				flightDetailsMap.put(replaceAmpSpaceNullEmpty(cols.get(0).text()), replaceAmpSpaceNullEmpty(cols.get(2).text()));
				flightDetailsMap.put(replaceAmpSpaceNullEmpty(cols.get(4).text()), replaceAmpSpaceNullEmpty(cols.get(6).text()));
				
				row=rows.get(2);
				cols=row.select("td");
				flightDetailsMap.put(replaceAmpSpaceNullEmpty(cols.get(0).text()), replaceAmpSpaceNullEmpty(cols.get(2).text()));
				flightDetailsMap.put(replaceAmpSpaceNullEmpty(cols.get(4).text()), replaceAmpSpaceNullEmpty(cols.get(6).text()));
				
				row=rows.get(3);
				cols=row.select("td");
				flightDetailsMap.put(replaceAmpSpaceNullEmpty(cols.get(4).text()), "");
			
		}

		return flightDetailsMap;
		
	}
	
	/**
	 * processPassengerDetails
	 * @param tbody
	 * @param passengerList
	 * @return List< Map<String,String>>
	 */
	private static List< Map<String,String>>  processPassengerDetails(Element tbody,List< Map<String,String>> passengerList){
		
		if(tbody.html().contains("Passenger summary")){
			
		Elements rows=tbody.select("tr");
		for (int i=2;i<rows.size();i++){
			Map<String,String>  passengerDetailsMap=new LinkedHashMap<String,String>();
			passengerDetailsMap.put(replaceAmpSpaceNullEmpty(rows.get(1).child(0).text()), replaceAmpSpaceNullEmpty(rows.get(i).child(0).text()));
			passengerDetailsMap.put(replaceAmpSpaceNullEmpty(rows.get(1).child(1).text()), replaceAmpSpaceNullEmpty(rows.get(i).child(1).text()));
			passengerDetailsMap.put(replaceAmpSpaceNullEmpty(rows.get(1).child(2).text()), replaceAmpSpaceNullEmpty(rows.get(i).child(2).text()));
			passengerDetailsMap.put(replaceAmpSpaceNullEmpty(rows.get(1).child(3).text()), replaceAmpSpaceNullEmpty(rows.get(i).child(3).text()));
			passengerList.add(passengerDetailsMap);
			
		}
		}

		return passengerList;
		
	}
	
	/**
	 * processTravelCostDetails
	 * @param tbody
	 * @param travelCostList
	 * @return List< Map<String,String>>
	 */
	private static List< Map<String,String>>  processTravelCostDetails(Element tbody,List< Map<String,String>> travelCostList){
		
		if(tbody.html().contains("Total travel cost")){
			
			Elements rows=tbody.select("tr");
			
			Element row=rows.get(2);
			Elements cols=row.select("td");
			Map<String,String> travelCostDetailsMap=new LinkedHashMap<String,String>();
			travelCostDetailsMap.put(replaceAmpSpaceNullEmpty(cols.get(0).text()), replaceAmpSpaceNullEmpty(cols.get(1).text()));
			
			row=rows.get(3);
			cols=row.select("td");
			travelCostDetailsMap.put(replaceAmpSpaceNullEmpty(cols.get(0).text()), replaceAmpSpaceNullEmpty(cols.get(1).text()));
			
			
			row=rows.get(5);
			cols=row.select("td");
			travelCostDetailsMap.put(replaceAmpSpaceNullEmpty(cols.get(0).text()), replaceAmpSpaceNullEmpty(cols.get(1).text()));
			
			
			row=rows.get(8);
			cols=row.select("td");
			travelCostDetailsMap.put(replaceAmpSpaceNullEmpty(cols.get(0).text()), replaceAmpSpaceNullEmpty(cols.get(1).text()));
			
			travelCostList.add(travelCostDetailsMap);
		}

		return travelCostList;
		
	}
	
	
private static String replaceAmpSpaceNullEmpty(String val){
	
	if(null!=val){
		if(val.trim().isEmpty()){
			return val.replace("\u00a0", "");
		}
		else{
			return val.replace("\u00a0", "");
		}
	}
	else{
		val="";
	}
	
	return val;
}

/**
 * format
 * @param listMap
 * @param delimiter
 * @return String
 */
private static String format(List< Map<String,String>> listMap,String delimiter){
	
	StringBuilder localString=new StringBuilder();
	
	if(!listMap.isEmpty() && listMap.size()>0){
		
		Map<String,String> passengerHeaderMap=listMap.get(0);
		Set<String> headers=passengerHeaderMap.keySet();
		for(String key:headers){
			localString.append(key);
			localString.append(delimiter);
		}
		
		for(Map<String,String> passengerMap:listMap){
			Collection<String> values=passengerMap.values();
			localString.append("\n");
			for(String value:values){
				localString.append(value);
				localString.append(delimiter);
			}
		}
		
	}
	localString.append("\n");
	return localString.toString();
	
}
	
	

}
