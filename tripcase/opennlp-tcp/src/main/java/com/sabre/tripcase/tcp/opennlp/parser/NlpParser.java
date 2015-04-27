package com.sabre.tripcase.tcp.opennlp.parser;

import java.io.File;
import java.io.IOException;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sabre.tripcase.tcp.opennlp.constants.Constants;
import com.sabre.tripcase.tcp.opennlp.email.EmailReader;
import com.sabre.tripcase.tcp.opennlp.email.MimeMessageReader;
import com.sabre.tripcase.tcp.opennlp.models.Trainer;
import com.sabre.tripcase.tcp.opennlp.parser.handler.FileHandler;
import com.sabre.tripcase.tcp.opennlp.parser.handler.HtmlHandler;
import com.sabre.tripcase.tcp.opennlp.parser.handler.TextHandler;
import com.sabre.tripcase.tcp.opennlp.utils.ControlProperties;

/**
 * @author Nalini Kanta
 *
 */
public class NlpParser {
	
	@Autowired
	private MimeMessageReader mimeMessageReader=null;
	@Autowired
	private HtmlHandler htmlHandler=null;
	@Autowired
	private TextHandler textHandler=null;
	@Autowired
	private FileHandler fileHandler=null;
	@Autowired
	private Trainer trainer=null;
	
	public static void main(String args[]) {
	
		try{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("opennlp-beans.xml");
		NlpParser nlpParser =(NlpParser) context.getBean("nlpParser");
		nlpParser.startTraining();
		nlpParser.startProcess();
		}
		catch(Throwable th){
			th.printStackTrace();
		}
	}
	
	public void startTraining() throws IOException{
		trainer.train();
	}
	
	public  void startProcess() throws Throwable{
		
		File[] files = fileHandler.getFilesList();
		String fileName="";

		if(files != null){			
				for (File file : files) {
				if (file.isFile()) {
					System.out.println("************************* " + file.getName() + " ***********************");
					
					MimeMessage mimeMessage=mimeMessageReader.getMimeMessage(file);
					String bodyText=EmailReader.getBody(mimeMessage, false);
					ControlProperties.setFileName(fileHandler.getProcessFile());
					if(fileHandler.getProcessFile().equals(Constants.ALL_FILES)){
						fileName=Constants.ALL_FILES;
					}
					else{
						fileName=file.getName();
					}
					if(bodyText.contains(Constants.HTML_TAG)){
					//htmlHandler.processHTMLContent(bodyText,fileName);
					}
					else{
						
						textHandler.processTextContent(bodyText,fileName);
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

	/**
	 * @return the trainer
	 */
	public Trainer getTrainer() {
		return trainer;
	}

	/**
	 * @param trainer the trainer to set
	 */
	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}



}
