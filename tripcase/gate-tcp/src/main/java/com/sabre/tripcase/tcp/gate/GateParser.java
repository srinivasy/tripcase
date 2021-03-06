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

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.sabre.tripcase.tcp.common.constants.Message;

public class GateParser extends Parser implements InitializingBean, Runnable {
	
	private static Logger LOG = LoggerFactory.getLogger(GateParser.class);

	public void afterPropertiesSet() throws Exception {
		init();
	}

	public void init() throws Exception {
		corpus = Factory.newCorpus("GATE-Corpus");
		application.setCorpus(corpus);
	}
	
	public void setMessages(List<Message> messages) {
		this.messages = messages;
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
	
	public Thread execute()
	{
		Out.prln("Gate Parsing Started");
		Thread gatethread  = new Thread(this, "GATEParsing Thread");
		gatethread.start();
		return gatethread;
	}		
}
