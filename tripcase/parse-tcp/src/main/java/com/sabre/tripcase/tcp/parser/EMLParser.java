package com.sabre.tripcase.tcp.parser;

import java.io.File;
import java.io.IOException;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sabre.tripcase.tcp.gate.*;
import com.sabre.tripcase.tcp.common.constants.*;
import com.sabre.tripcase.tcp.common.preprocess.*;
import com.sabre.tripcase.tcp.common.validation.*;
import com.sabre.tripcase.tcp.common.utils.*;

/**
 * @author Nalini Kanta
 *
 */
public class EMLParser {
	
	@Autowired
	private MimeMessageReader mimeMessageReader=null;
	@Autowired
	private HtmlHandler htmlHandler=null;
	@Autowired
	private TextHandler textHandler=null;
	@Autowired
	private FileHandler fileHandler=null;
	@Autowired
	private ParsingEngine parsingengine=null;
	
	private static Logger log =Logger.getLogger(EMLParser.class);
	
	public static void main(String args[]) {
		try{
				@SuppressWarnings("resource")
				ApplicationContext context = new ClassPathXmlApplicationContext("parseconfig.xml");
				EMLParser emlparser =(EMLParser) context.getBean("emlParser");
				emlparser.startProcess();
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
		String fileName="";
		String[] messages = null;

		if(files != null){			
				for (File file : files) 
				{
					if (file.isFile()) 
					{
						log.info("************************* " + file.getName() + " ***********************");
						MimeMessage mimeMessage=mimeMessageReader.getMimeMessage(file);
						String bodyText=EmailReader.getBody(mimeMessage, false);
						ControlProperties.setFileName(fileHandler.getProcessFile());
						if(fileHandler.getProcessFile().equals(Constants.ALL_FILES)){
							fileName=Constants.ALL_FILES;
						}
						else{
							fileName=file.getName();
						}
						
						//will call validation fnction here 
						//filevalidator.validateeml(message);
						
						//it will return only the valid messages and a flag 
						
						
						
						//clean up code (we will push collection of messsages)
						
						
						
						if(bodyText.contains(Constants.HTML_TAG))
						{
							htmlHandler.processHTMLContent(bodyText,fileName);
						}
						else
						{						
							textHandler.processTextContent(bodyText,fileName);
						}
						messages[0]=bodyText;
					}				
				}
				parsingengine.ParseInput(messages);				
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
}
