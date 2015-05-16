package com.sabre.tripcase.tcp.parser;

import gate.util.Out;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sabre.tripcase.tcp.gate.ParsingEngine;
import com.sabre.tripcase.tcp.common.constants.*;
import com.sabre.tripcase.tcp.common.preprocess.*;
import com.sabre.tripcase.tcp.common.validation.*;
import com.sabre.tripcase.tcp.common.utils.*;
import com.sabre.tripcase.tcp.common.dto.*;

public class EMLParser 
{	
	@Autowired
	private MimeMessageReader mimeMessageReader=null;
	@Autowired
	private HtmlHandler htmlHandler=null;
	@Autowired
	private TextHandler textHandler=null;
	@Autowired
	private FileHandler fileHandler=null;
	@Autowired
	private ParsingEngine parsingEngine=null;
	@Autowired
	private FileValidator fileValidator=null;
	@Autowired
	private CleanText cleanText=null;
	
	
	private static Logger log =Logger.getLogger(EMLParser.class);
	protected List<Airline> gateAirAnnotations;
	protected List<Airline> nlpAirAnnotations;
	
	public static void main(String args[]) {
		try{
				@SuppressWarnings("resource")
				ApplicationContext context = new ClassPathXmlApplicationContext("parseconfig.xml");
				EMLParser emlParser =(EMLParser) context.getBean("EMLParser");
				emlParser.startProcess();
				log.info("File has been successfully processed");
		}
		catch(Throwable th){
			log.error("Error inside EMLParser:", th);
			th.printStackTrace();
		}
	}	

	public  void startProcess() throws Throwable
	{
		File[] files = fileHandler.getFilesList();
		
		//initialize the variables
		gateAirAnnotations = new ArrayList<Airline>();
		nlpAirAnnotations = new ArrayList<Airline>();
		
		List<Message> rawMessagesSupported = new ArrayList<Message>();
		List<Message> rawMessagesLangNotSupported = new ArrayList<Message>();
		List<Message> rawMessagesTypeNotSupported = new ArrayList<Message>();
		List<Message> processedMessages = new ArrayList<Message>();

		if(files != null){			
				for (File file : files) 
				{
					if (file.isFile()) 
					{
						log.info("************************* " + file.getName() + " ***********************");
						System.out.println("************************* " + file.getName() + " ***********************");
						MimeMessage mimeMessage=mimeMessageReader.getMimeMessage(file);	
						processedMessages.clear();
												
						//will call validation function here 			
						if (fileValidator.validateMessage(mimeMessage, rawMessagesSupported,rawMessagesLangNotSupported,rawMessagesTypeNotSupported))
						{
							//verify the size of unsupported message later to send partial success later
							//even though it has some supported messages
							
							//clean up code (we will push collection of messages)
											
							for (int i = 0; i < rawMessagesSupported.size(); i++) 
							{
								Message message = rawMessagesSupported.get(i);
								message.setContent(cleanText.seggregateSectionWise(message.getContent()));
								if(message.getContent().contains(Constants.HTML_TAG))
								{
									message.setContent(htmlHandler.processHTMLContent(message.getContent()));
								}
								else
								{
									message.setContent(textHandler.processTextContent(message.getContent()));
								}
								
								processedMessages.add(message);								
							}
							
							//Call the Parsing Engine
							
							parsingEngine.ParseInput(rawMessagesSupported, processedMessages);	
							
							//Get the POJO to pass it to evaluation Engine
							parsingEngine.getNLPAnnotations(nlpAirAnnotations);
							parsingEngine.getGateAnnotations(gateAirAnnotations);
									
							for (int i = 0; i < gateAirAnnotations.size(); i++) 
							{
								Out.prln("****GATE**Departure** " + gateAirAnnotations.get(i).getDepartLocation());	
								Out.prln("****GATE**Arrival** " + gateAirAnnotations.get(i).getArriveLocation());
								Out.prln("****GATE**AirLines** " + gateAirAnnotations.get(i).getName());
							}	
							
							for (int i = 0; i < nlpAirAnnotations.size(); i++) 
							{
								Out.prln("****NLP**Departure** " + nlpAirAnnotations.get(i).getDepartLocation());	
								Out.prln("****NLP**Arrival** " + nlpAirAnnotations.get(i).getArriveLocation());
								Out.prln("****NLP**AirLines** " + nlpAirAnnotations.get(i).getName());
							}
						}
						else
						{
							System.out.println("EML file does not support any message to process" + file.getName() );							
						}					
					}				
				}
								
			}	
		}	

	/**
	 * @return the mimeMessageReader
	 */
	public MimeMessageReader getMimeMessageReader() {
		return mimeMessageReader;
	}

	/**
	 * @param mimeMessageReader the mimeMessageReader to set
	 */
	public void setMimeMessageReader(MimeMessageReader mimeMessageReader) {
		this.mimeMessageReader = mimeMessageReader;
	}

	/**
	 * @return the htmlHandler
	 */
	public HtmlHandler getHtmlHandler() {
		return htmlHandler;
	}

	/**
	 * @param htmlHandler the htmlHandler to set
	 */
	public void setHtmlHandler(HtmlHandler htmlHandler) {
		this.htmlHandler = htmlHandler;
	}

	/**
	 * @return the textHandler
	 */
	public TextHandler getTextHandler() {
		return textHandler;
	}

	/**
	 * @param textHandler the textHandler to set
	 */
	public void setTextHandler(TextHandler textHandler) {
		this.textHandler = textHandler;
	}

	/**
	 * @return the fileHandler
	 */
	public FileHandler getFileHandler() {
		return fileHandler;
	}

	/**
	 * @param fileHandler the fileHandler to set
	 */
	public void setFileHandler(FileHandler fileHandler) {
		this.fileHandler = fileHandler;
	}
	
	public ParsingEngine getParsingEngine() {
		return parsingEngine;
	}	

	public void setParsingEngine(ParsingEngine parsingengine) {
		this.parsingEngine = parsingengine;
	}
 	
	
	public FileValidator getFileValidator() {
		return fileValidator;
	}

	/**
	 * @param fileHandler the fileHandler to set
	 */
	public void setFileValidator(FileValidator fileValidator) {
		this.fileValidator = fileValidator;
	}
	
	public CleanText getCleanText() {
		return cleanText;
	}	

	public void setCleanText(CleanText cleanText) {
		this.cleanText = cleanText;
	}
 	
}
