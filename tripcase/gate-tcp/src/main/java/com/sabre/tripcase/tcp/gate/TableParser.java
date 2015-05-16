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

public class TableParser extends Parser implements Runnable{
	private static Logger LOG = LoggerFactory.getLogger(TableParser.class);

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	public void run() 
	{	
		return;
	}
	
	public Thread execute()
	{
		Out.prln("Table Parsing Started");
		Thread tablethread  = new Thread(this, "TableParsing Thread");
		tablethread.start();
		return tablethread;
	}	
}
