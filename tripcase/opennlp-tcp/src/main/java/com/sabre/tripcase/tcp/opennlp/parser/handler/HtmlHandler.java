/**
 * 
 */
package com.sabre.tripcase.tcp.opennlp.parser.handler;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;

import com.sabre.tripcase.tcp.opennlp.constants.Constants;
import com.sabre.tripcase.tcp.opennlp.utils.HtmlTableParser;

/**
 * @author Nalini Kanta
 *
 */
public class HtmlHandler {
	@Autowired
	private NlpProcess nlpProcess=null;
	
	public  void processHTMLContent(String bodyText,String fileName) throws IOException{
		System.out.println(" ************************ HTML Processor ***************************");
		System.out.println(" *******************************************************************");
		
		bodyText=NlpHandler.removeBlankLines(bodyText);

		if(bodyText.contains(Constants.HTML_TAG) ){
			try{
				Map<String,String> htmlParsedMap=HtmlTableParser.parse(bodyText, null, false, Constants.SPACE_DELIMITER);
				Set<Entry<String,String>> entrySet=htmlParsedMap.entrySet();
				Iterator<Entry<String,String>> iterator=entrySet.iterator();
				while(iterator.hasNext()){
					Entry<String,String> keyEntry=iterator.next();
				//	String key=keyEntry.getKey();
					String value=keyEntry.getValue().replace("-", " ");
					
					nlpProcess.process(value,Constants.HTML,fileName);

				}
			}
			catch(Throwable th){
				th.printStackTrace();
			}
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
