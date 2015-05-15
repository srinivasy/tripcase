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
	
	public String processHTMLContent(String content) throws IOException
	{
		log.info(" ************************ HTML Processor ***************************");
		log.info(" *******************************************************************");
		
		content=NlpHandler.removeBlankLines(content);
		String value="";

		if(content.contains(Constants.HTML_TAG) )
		{
			try
			{
				Map<String,String> htmlParsedMap=HtmlTableParser.parse(content, null, false, Constants.SPACE_DELIMITER);
				Set<Entry<String,String>> entrySet=htmlParsedMap.entrySet();
				Iterator<Entry<String,String>> iterator=entrySet.iterator();
								
				while(iterator.hasNext())
				{
					Entry<String,String> keyEntry=iterator.next();					
					value += keyEntry.getValue().replace("-", " ");	
					//nlpProcess.process(value,Constants.HTML,fileName);
				}								
			}
			catch(Throwable th){
				th.printStackTrace();
			}			
		}
		return value;		
	}
}
