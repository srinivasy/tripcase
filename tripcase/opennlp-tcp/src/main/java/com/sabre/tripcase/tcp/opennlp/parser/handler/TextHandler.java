/**
 * 
 */
package com.sabre.tripcase.tcp.opennlp.parser.handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sabre.tripcase.tcp.opennlp.constants.Constants;

/**
 * @author Nalini Kanta
 *
 */
public class TextHandler {
	
	private NlpProcess nlpProcess=null;
	private static Logger log =Logger.getLogger(TextHandler.class);
	
	public void processTextContent(String bodyText,String fileName,String actualFileName) throws IOException{
		List<String> allResponses=new ArrayList<String>();
		log.info(" ************************ TEXT Processor ***************************");
		log.info(" *******************************************************************");
		bodyText=NlpHandler.removeBlankLines(bodyText);
		bodyText=bodyText.toString().replaceAll("\\<.*?>","");
		try{
			nlpProcess.process(bodyText,Constants.TEXT,fileName,actualFileName,allResponses);
			}
		catch(Throwable th){
			th.printStackTrace();
			}
		}

	/**
	 * @return the nlpProcess
	 */
	public NlpProcess getNlpProcess() {
		return nlpProcess;
	}

	/**
	 * @param nlpProcess the nlpProcess to set
	 */
	public void setNlpProcess(NlpProcess nlpProcess) {
		this.nlpProcess = nlpProcess;
	}
	
	

}
