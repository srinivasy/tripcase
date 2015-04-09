package com.motivity.labs.parse;

import java.util.Map;

import com.motivity.labs.parse.html.parser.HtmlNestedTableParser;
import com.motivity.labs.parse.html.parser.HtmlParser;
import com.motivity.labs.parse.html.parser.HtmlTableParser;



public class TestHtmlParser {

	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		String fileSource="C:/Users/CB34388493/workspace/TripCase/parse-ml/src/test/java/com/motivity/labs/parse/travel.html";
		//String fileSource="D:/test/html/2254026.html";
		//String fileSource="D:/test/html/2254027.html";
		//String fileSource="D:/test/html/2254028.html";
		//String fileSource="D:/test/html/2254033.html";
		//String fileSource="D:/test/html/2254044.html";
		//String result=HtmlParser.parse("", fileSource, Boolean.TRUE);
		Map<String,String> resultMap =HtmlTableParser.parse("", fileSource, Boolean.TRUE,"|");
		//String result =HtmlParser.parseTables("", fileSource, Boolean.TRUE,"|");
		System.out.println(resultMap);
		//System.out.println(result);
		
		//HtmlNestedTableParser.parse("", fileSource, Boolean.TRUE,"|");
		

	}

}
