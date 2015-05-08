package com.sabre.tripcase.tcp.gate;

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

import java.util.HashSet;
import java.util.Iterator;
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
	
	protected AirLinesEntitites[] airlinesentities;
	int airobjcount = 0;
	
	public void setMessages(String[] imessages) {
		this.messages = imessages;
	}	
	
	public void setApplication(CorpusController application) {
		this.application = application;
	}
	
	public AirLinesEntitites[] GetAirAnnotations()
	{
		return airlinesentities;
	}
	
	public boolean isAir()
	{
		if (airobjcount==0)
			return false;
		else
			return true;		
	}
	

	
	public void SetCorpus() throws Exception
	{	
		Document doc = null;

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
	
	public void GetAnnotations() throws Exception 
	{	
		//initialize the output object also
		airlinesentities = new AirLinesEntitites[corpus.size()];
		
		Iterator iter = corpus.iterator();
		
		while (iter.hasNext()) 
		{
			Document doc1 = (Document) iter.next();
			AnnotationSet defaultAnnotSet = doc1.getAnnotations();
			
			Set requiredAirAnnotations = new HashSet();
			
			//make it configurable later (Munish) - configuration should match exactly with POJO
			requiredAirAnnotations.add("Departure");			
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
				String AirStartToken;
				
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
						airlinesentities[airobjcount-1] = new AirLinesEntitites();
					} 
					else 
					{
						if (currAnnot.getType().equals("AirStartToken")) {
							airobjcount++;
							//initialize the output object also
							airlinesentities[airobjcount-1] = new AirLinesEntitites();
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
						airlinesentities[airobjcount-1].Departure = token;			
					else if (currAnnot.getType().equals("Arrival"))
						airlinesentities[airobjcount-1].Arrival = token;					
				} // for
				
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

