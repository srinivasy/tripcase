package com.sabre.tripcase.tcp.gate;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.CorpusController;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.GateConstants;
import gate.util.Out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.sabre.tripcase.tcp.common.constants.Message;
import com.sabre.tripcase.tcp.common.dto.Airline;
import com.sabre.tripcase.tcp.process.ProcessRouteBuilder;


/**
 * 
 * @author Venkat Pedapudi
 *
 */
public class GateContentParser implements InitializingBean {

	private static Logger LOG = LoggerFactory.getLogger(GateContentParser.class);

	private CorpusController application;
	
	private Corpus corpus;
	
	private Set<String> flightAnnotationSet;
	
	private ProcessRouteBuilder processRouteBuilder;
	
	
	/**
	 * @param processRouteBuilder the processRouteBuilder to set
	 */
	public void setProcessRouteBuilder(ProcessRouteBuilder processRouteBuilder) {
		this.processRouteBuilder = processRouteBuilder;
	}

	public void setApplication(CorpusController application) {
		this.application = application;
	}

	public void afterPropertiesSet() throws Exception {
		init();
	}

	public void init() throws Exception {
		corpus = Factory.newCorpus("webapp corpus");
		application.setCorpus(corpus);
	}

	/**
	 * 
	 * @param exchange
	 * @throws Exception
	 */
	public void invokeHandler(Exchange exchange) throws Exception {
		corpus.clear();
		Document doc = null;
		List<Message> content = (List<Message>)exchange.getIn().getBody();
		try {
			for(Message message:content){
				if (message.getContent() != null)
				{
					FeatureMap params = Factory.newFeatureMap();
				    params.put("stringContent", message.getContent());
				    params.put("preserveOriginalContent", new Boolean(true));
				    params.put("collectRepositioningInfo", new Boolean(true));
					doc = (Document) Factory.createResource("gate.corpora.DocumentImpl",params);
					corpus.add(doc);
				}
			}
			LOG.debug("Creating app " + application);
			application.execute();
		} catch (Exception ex) {
			LOG.error("Exception while loading content in corpus"+ex);
		}
		getAnnotationResult(exchange);
	}
	
	/**
	 * 
	 * @param exchange
	 */
	private void getAnnotationResult(Exchange exchange){
		Iterator iter = corpus.iterator();
		List<Airline> annotationList = new ArrayList<Airline>();
		while (iter.hasNext()) 
		{
			Document doc1 = (Document) iter.next();
			AnnotationSet defaultAnnotSet = doc1.getAnnotations();
			Set<Annotation> AirAnnotations = new HashSet<Annotation>(defaultAnnotSet.get(flightAnnotationSet));
			FeatureMap features = doc1.getFeatures();
			String originalContent = (String) features.get(GateConstants.ORIGINAL_DOCUMENT_CONTENT_FEATURE_NAME);
			if (originalContent != null) {
				LOG.debug("OrigContent existing. Generate file...");
				Iterator it = AirAnnotations.iterator();
				Annotation currAnnot;
				SortedAnnotationList sortedAirAnnotations = new SortedAnnotationList();
				while (it.hasNext()) {
					currAnnot = (Annotation) it.next();
					sortedAirAnnotations.addSortedExclusive(currAnnot);
				} // while

				//Out.prln("Sorted annotations count: "+ sortedAirAnnotations.size());
				String AirStartToken = "";
				Airline AirObject = null;
				int airobjcount = 0;
				
				for (int i = 0; i < sortedAirAnnotations.size(); i++)
				{
					//needs to work a lot on this logic once we are clear on entities name and 
					// able to fetch entities other than air (Munish)
					currAnnot = (Annotation) sortedAirAnnotations.get(i);
					
					if (i == 0) 
					{
						AirStartToken = currAnnot.getType();	
						airobjcount=1;		
						//initialize the output object also
						AirObject = new Airline();
					} 
					else 
					{
						if (currAnnot.getType().equals(AirStartToken)) {							
							airobjcount++;
							if (AirObject != null)
								annotationList.add(AirObject);
							AirObject = new Airline();						
						}						
					}

					String token = originalContent.substring(currAnnot
							.getStartNode().getOffset().intValue(), currAnnot
							.getEndNode().getOffset().intValue());
					
					/* 
					 * needs to rework on approach once we reach the evaluation phase and have a clear visibility about 
					 * type of object to be passed on to evaluation module (Munish)
					 */
					if (currAnnot.getType().equals("FlightDeparture"))
						AirObject.setDepartLocation(token);			
					else if (currAnnot.getType().equals("FlightArrival"))
						AirObject.setArriveLocation(token);		
					else if (currAnnot.getType().equals("FlightName"))
						AirObject.setName(token);
					else if (currAnnot.getType().equals("FlightDepartureDate")){
						//Out.prln("FlightDepartureDate"+token);
						AirObject.setDepartDate(token);		
					}
					else if (currAnnot.getType().equals("FlightDepartureTime"))
						AirObject.setDepartTime(token);	
					else if (currAnnot.getType().equals("FlightArrivalDate"))
						AirObject.setArrivalDate(token);		
					else if (currAnnot.getType().equals("FlightArrivalTime"))
						AirObject.setArrivalTime(token);
					else if (currAnnot.getType().equals("FlightNumber"))
						AirObject.setFlightNumber(token);	
					
				} // for
				if (AirObject != null)
					annotationList.add(AirObject);				
			} else {
				Out.prln("Content : " + originalContent);
			} 
			Map<String,List<Airline>> map=new HashMap<String,List<Airline>>();
			map.put("Gate", annotationList);
			exchange.getIn().setBody(map);
			System.out.println("gate done"+map);
			processRouteBuilder.processfinalMessages(exchange);
		}


	}

	/**
	 * @param flightAnnotationSet the flightAnnotationSet to set
	 */
	public void setFlightAnnotationSet(Set<String> flightAnnotationSet) {
		this.flightAnnotationSet = flightAnnotationSet;
	}
	
	public static class SortedAnnotationList extends Vector {
		public SortedAnnotationList() {
			super();
		} // SortedAnnotationList

		public boolean addSortedExclusive(Annotation annot) {
			Annotation currAnot = null;

			// overlapping check
			for (int i = 0; i < size(); ++i) {
				currAnot = (Annotation) get(i);
				if (annot.overlaps(currAnot)) {
					return false;
				} // if
			} // for

			long annotStart = annot.getStartNode().getOffset().longValue();
			long currStart;
			// insert
			for (int i = 0; i < size(); ++i) {
				currAnot = (Annotation) get(i);
				currStart = currAnot.getStartNode().getOffset().longValue();
				if (annotStart < currStart) {
					insertElementAt(annot, i);
					/*
					 * Out.prln("Insert start: "+annotStart+" at position: "+i+
					 * " size="+size()); Out.prln("Current start: "+currStart);
					 */
					return true;
				} // if
			} // for

			int size = size();
			insertElementAt(annot, size);
			// Out.prln("Insert start: "+annotStart+" at size position: "+size);
			return true;
		} // addSorted
	} // SortedAnnotationList

}
