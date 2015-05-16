package com.sabre.tripcase.tcp.gate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import gate.Annotation;
import gate.AnnotationSet;
import gate.CorpusController;
import gate.Document;
import gate.FeatureMap;
import gate.GateConstants;
import gate.util.Out;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sabre.tripcase.tcp.common.constants.Message;
import com.sabre.tripcase.tcp.common.dto.Airline;

/**
 * 
 * move the common code of gate parser and nlp parser to a common parser class later
 */
public class ParsingEngine {

	private static Logger LOG = LoggerFactory.getLogger(ParsingEngine.class);
	
	private GateParser gateparser;
	private NLPParser nlpparser;	
	private TableParser tableparser;
	
	public Airline[] airlinesentities;
	
	public void setgateParser(GateParser parsingobj) {
		this.gateparser = parsingobj;
	}
	
	public void setnlpParser(NLPParser parsingobj) {
		this.nlpparser = parsingobj;
	}
	
	public void setTableParser(TableParser parsingobj) {
		this.tableparser = parsingobj;
	}
	
	public void ParseInput(List<Message> rawMessages, List<Message> processedMessages) throws Exception 
	{			
		Out.prln("Parsing Started");
		List<Thread> parseThreadList = new ArrayList<Thread>();
		long waitinms = 30000; //change it configuration later
	
		gateparser.setMessages(processedMessages);
		nlpparser.setMessages(processedMessages); 
		tableparser.setMessages(rawMessages);
		
		Thread threadRef = gateparser.execute();
		parseThreadList.add(threadRef);
		
		threadRef = nlpparser.execute();
		parseThreadList.add(threadRef);
		
		threadRef = tableparser.execute();
		parseThreadList.add(threadRef);
		
		for (Thread threadId : parseThreadList) 
			threadId.join(waitinms);			
				
		Out.prln("Parsing finished");
	}
			
			//return back the annotations
	public void getGateAnnotations(List<Airline> annotationList)
	{
		//in future, expand the function to take some argument and based on that, identify it as air or any other type (Munish)
		try{
			annotationList.clear();
			gateparser.getAnnotations(annotationList);	
		}
		catch(Exception ex)
		{
			
		}		
	}
	
	public void getNLPAnnotations(List<Airline> annotationList)
	{
		try{
			annotationList.clear();
			nlpparser.getAnnotations(annotationList);	
		}
		catch(Exception ex)
		{
			
		}
	}	
}	 
