/**
 * 
 */
package com.sabre.tripcase.tcp.common.preprocess;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.sabre.tripcase.tcp.common.constants.Constants;
import com.sabre.tripcase.tcp.common.utils.HtmlTableParser;

public class HtmlHandler {

	private static Logger log =Logger.getLogger(HtmlHandler.class);
	
	public  void processHTMLContent(String bodyText,String fileName) throws IOException
	{
		log.info(" ************************ HTML Processor ***************************");
		log.info(" *******************************************************************");
		
		bodyText=NlpHandler.removeBlankLines(bodyText);

		if(bodyText.contains(Constants.HTML_TAG) )
		{
			try
			{
				Map<String,String> htmlParsedMap=HtmlTableParser.parse(bodyText, null, false, Constants.SPACE_DELIMITER);
				Set<Entry<String,String>> entrySet=htmlParsedMap.entrySet();
				Iterator<Entry<String,String>> iterator=entrySet.iterator();
				String value="";
				
				while(iterator.hasNext())
				{
					Entry<String,String> keyEntry=iterator.next();					
					value += keyEntry.getValue().replace("-", " ");	
					//nlpProcess.process(value,Constants.HTML,fileName);
				}
				bodyText = value;				
			}
			catch(Throwable th){
				th.printStackTrace();
			}
		}
		
	}
}
