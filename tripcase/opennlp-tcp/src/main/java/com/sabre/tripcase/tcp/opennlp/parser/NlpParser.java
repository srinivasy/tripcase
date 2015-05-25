package com.sabre.tripcase.tcp.opennlp.parser;

import java.io.File;
import java.io.IOException;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
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
	
	private static Logger log =Logger.getLogger(NlpParser.class);
	
	public static void main(String args[]) {
		try{
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("opennlp-beans.xml");
		NlpParser nlpParser =(NlpParser) context.getBean("nlpParser");
		//nlpParser.startTraining();
		log.info("Training models are successfully done");
		nlpParser.startProcess();
		log.info("NLP processing of Models and Source Files are successfully done :::");
		}
		catch(Throwable th){
			log.error("Error inside NlpParser:", th);
			th.printStackTrace();
		}
	}
	
	public void startTraining() throws IOException{
		trainer.train();
	}
	
	public  void startProcess() throws Throwable{
		
		File[] files = fileHandler.getFilesList();
		//String fileName="2362060_horizontal.eml";
		String fileName="";

		if(files != null){			
				for (File file : files) {
				if (file.isFile()) {
					log.info("************************* " + file.getName() + " ***********************");
					//System.out.println("************************* " + file.getName() + " ***********************");
					MimeMessage mimeMessage=mimeMessageReader.getMimeMessage(file);
					String bodyText=EmailReader.getBody(mimeMessage, false);
					//System.out.println(bodyText);
					ControlProperties.setFileName(fileHandler.getProcessFile());
					if(fileHandler.getProcessFile().equals(Constants.ALL_FILES)){
						fileName=Constants.ALL_FILES;
					}
					else{
						fileName=file.getName();
					}
					if(bodyText.contains(Constants.HTML_TAG)){
					htmlHandler.processHTMLContent(bodyText,fileName,file.getName());
					}
					else if(bodyText.contains(Constants.HTML_TABLE)){
						htmlHandler.processHTMLContent("<html> "+bodyText+" </html>",fileName,file.getName());
					}
					else if(bodyText.contains(Constants.HTML_DIV)){
						htmlHandler.processHTMLContent("<html> "+bodyText+" </html>",fileName,file.getName());
					}
					else{
						
					//	textHandler.processTextContent(bodyText,fileName);
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
