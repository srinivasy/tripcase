package com.sabre.tripcase.tcp.gate;

import com.sabre.tripcase.tcp.common.dto.Airline;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.CorpusController;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.GateConstants;
import gate.creole.ResourceInstantiationException;
import gate.util.Out;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;



public class Parser{	
	
	protected static final String NULL = null;
	protected CorpusController application;
	protected Corpus corpus;	
	protected String[] messages;		
	
	int airobjcount = 0;
	
	public void setMessages(String[] imessages) {
		this.messages = imessages;
	}	
	
	public void setApplication(CorpusController application) {
		this.application = application;
	}
	
	public boolean isAir()
	{
		if (airobjcount==0)
			return false;
		else
			return true;		
	}
		
	public void setCorpus() throws Exception
	{	
		Document doc = null;
		corpus.clear();

		for (String messagecontent : messages) { 
			if (messagecontent != NULL)
			{
				  FeatureMap params = Factory.newFeatureMap();
			      params.put("stringContent", messagecontent);
			      params.put("preserveOriginalContent", new Boolean(true));
			      params.put("collectRepositioningInfo", new Boolean(true));
	
				doc = (Document) Factory.createResource("gate.corpora.DocumentImpl",params);
				corpus.add(doc);				
			}
		}		
	}
	

	public void run() {	
		try 
		{
			setCorpus();			
			application.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getAnnotations(List<Airline> annotationList) throws Exception 
	{	
		
		Iterator iter = corpus.iterator();
		
		while (iter.hasNext()) 
		{
			Document doc1 = (Document) iter.next();
			AnnotationSet defaultAnnotSet = doc1.getAnnotations();
			
			Set requiredAirAnnotations = new HashSet();
			
			//make it configurable later (Munish) - configuration should match exactly with POJO
			requiredAirAnnotations.add("Departure");	
			requiredAirAnnotations.add("Arrival");
			
			Set<Annotation> AirAnnotations = new HashSet<Annotation>(defaultAnnotSet.get(requiredAirAnnotations));

			FeatureMap features = doc1.getFeatures();
			String originalContent = (String) features.get(GateConstants.ORIGINAL_DOCUMENT_CONTENT_FEATURE_NAME);
			if (originalContent != null) {
				Out.prln("OrigContent existing. Generate file...");

				Iterator it = AirAnnotations.iterator();
				Annotation currAnnot;
				SortedAnnotationList sortedAirAnnotations = new SortedAnnotationList();

				while (it.hasNext()) {
					currAnnot = (Annotation) it.next();
					sortedAirAnnotations.addSortedExclusive(currAnnot);
				} // while

				StringBuffer editableContent = new StringBuffer();
				Out.prln("Sorted annotations count: "+ sortedAirAnnotations.size());
				int flightcount = 0;
				String AirStartToken = "";
				Airline AirObject = null;
				
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
					if (currAnnot.getType().equals("Departure"))
						AirObject.setDepartLocation(token);			
					else if (currAnnot.getType().equals("Arrival"))
						AirObject.setArriveLocation(token);		
					else if (currAnnot.getType().equals("Airlines"))
						AirObject.setName(token);	
					
				} // for
				if (AirObject != null)
					annotationList.add(AirObject);				
			} else {
				Out.prln("Content : " + originalContent);
			}          
		} 			
	}
	
	// utility to sort the annotation
	public static class SortedAnnotationList extends Vector 
	{
		public SortedAnnotationList() {
			super();
		} 
	
		public boolean addSortedExclusive(Annotation annot) {
			Annotation currAnot = null;
	
			// overlapping check
			for (int i = 0; i < size(); ++i) {
				currAnot = (Annotation) get(i);
				if (annot.overlaps(currAnot)) {
					return false;
				} 
			} 
	
			long annotStart = annot.getStartNode().getOffset().longValue();
			long currStart;
			for (int i = 0; i < size(); ++i) {
				currAnot = (Annotation) get(i);
				currStart = currAnot.getStartNode().getOffset().longValue();
				if (annotStart < currStart) {
					insertElementAt(annot, i);
					return true;
				}
			} 
	
			int size = size();
			insertElementAt(annot, size);
			return true;
		} 
	} 
}

