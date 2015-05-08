package com.sabre.tripcase.tcp.gate;

import java.util.HashSet;
import java.util.Iterator;
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

/**
 * 
 * move the common code of gate parser and nlp parser to a common parser class later
 */
public class ParsingEngine {

	private static Logger LOG = LoggerFactory.getLogger(ParsingEngine.class);
	
	private GateParser gateparser;
	private NLPParser  nlpparser;
	
	public AirLinesEntitites[] airlinesentities;
	
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
			
			Out.prln("Gate Parsing Started");
			Thread gatethread  = new Thread(gateparser, "GATE Thread");
			gatethread.start();
						
			Out.prln("NLP Parsing Started");
			Thread nlpthread  = new Thread(nlpparser, "NLP Thread");
			nlpthread.start();	
			
			gatethread.join(waitinms);
			nlpthread.join(waitinms);
			
			//return back the annotations
			
			if (gateparser.isAir())
			{
				airlinesentities = gateparser.GetAirAnnotations();
				for (AirLinesEntitites objcontent : airlinesentities)
				{
					
					//Out.prln("****GATE****" + objcontent.Airlines);	
					//Out.prln("****GATE****" + objcontent.Arrival);	
					Out.prln("****GATE**Departure** " + objcontent.Departure);	
					//Out.prln("****GATE****" + objcontent.Flight);	
				}			
			}
			else
			{
				Out.prln("No Air Annotations returned by Gate Parser");
			}
			
			Out.prln("***********");
			
			//return back the annotations
			if (nlpparser.isAir())
			{
				airlinesentities = nlpparser.GetAirAnnotations();
				for (AirLinesEntitites objcontent : airlinesentities)
				{
					//Out.prln("****NLP****" + objcontent.Airlines);	
					//Out.prln("****NLP****" + objcontent.Arrival);	
					Out.prln("****NLP** Departure** " + objcontent.Departure);	
					//Out.prln("****NLP****" + objcontent.Flight);					
				}			
			}
			else
			{
				Out.prln("No Air Annotations returned by NLP Parser");
			}	
		
			Out.prln("Parsing Finished");								
		} 
}	 
