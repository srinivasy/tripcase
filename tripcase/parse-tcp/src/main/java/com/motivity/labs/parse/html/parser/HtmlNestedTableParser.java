package com.motivity.labs.parse.html.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import com.motivity.labs.parse.file.reader.HtmlFileReader;

public class HtmlNestedTableParser {
	
	public static void parse(String htmlString, String filePath,
			boolean isFileSource, String delimiter) throws Throwable{
		if (Boolean.TRUE == isFileSource) {
			try {
				HtmlFileReader htmlFileReader = new HtmlFileReader();
				htmlString = htmlFileReader.htmlFileRead(filePath);
			} catch (Throwable t) {
				throw t;
			}
		}

		try {
		
		Document doc = Jsoup.parse(htmlString);

	    Elements tables = doc.select("table tr td:has(table)");
	    
		    for (Element table:tables) {

	        Elements trs = table.select("tr");

	        String[][] trtd = new String[trs.size()][];

	        for (int a = 0; a < trs.size(); a++) {

	            Elements tds = trs.get(a).select("td");

	            trtd[a] = new String[tds.size()];

	            for (int b = 0; b < tds.size(); b++) {

	                trtd[a][b] = tds.get(b).text(); 

	              System.out.print( trtd[a][b] +"    ");

	            }
	          System.out.println( );
	        }

	        // trtd now contains the desired array for this table
	    }
		}
		catch(Exception e){
			throw e;
	}
	}
	
	}


