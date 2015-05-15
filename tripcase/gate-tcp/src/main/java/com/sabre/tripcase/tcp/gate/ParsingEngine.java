package com.sabre.tripcase.tcp.gate;

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
import com.sabre.tripcase.tcp.common.dto.Airline;

/**
 * 
 * move the common code of gate parser and nlp parser to a common parser class later
 */
public class ParsingEngine {

	private static Logger LOG = LoggerFactory.getLogger(ParsingEngine.class);
	
	private GateParser gateparser;
	private NLPParser nlpparser;
	
	public Airline[] airlinesentities;
	
	public void setgateParser(GateParser parsingobj) {
		this.gateparser = parsingobj;
	}
	
	public void setnlpParser(NLPParser parsingobj) {
		this.nlpparser = parsingobj;
	}

	public void ParseInput(String[] messages) throws Exception 
	{			
			Out.prln("Parsing Started");
			
			long waitinms = 30000; //change it configuration later
		
			gateparser.setMessages(messages);
			nlpparser.setMessages(messages); 
			//tablepareser.semssgs(oringlmsg);
			
			Out.prln("Gate Parsing Started");
			Thread gatethread  = new Thread(gateparser, "GATE Thread");
			gatethread.start();
						
			Out.prln("NLP Parsing Started");
			Thread nlpthread  = new Thread(nlpparser, "NLP Thread");
			nlpthread.start();	
			
			gatethread.join(waitinms);
			nlpthread.join(waitinms);
			
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
