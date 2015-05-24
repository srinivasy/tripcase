/**
 **/
package com.sabre.tripcase.tcp.common.handler;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.sabre.tripcase.tcp.common.constants.Constants;
import com.sabre.tripcase.tcp.common.utils.HtmlTableParser;

public class HtmlHandler implements Handler {

	private static Logger log =Logger.getLogger(HtmlHandler.class);
	
	public String convertToText(String content)
	{
		log.info(" ************************ START ***************************");
		
		
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
				}								
			}
			catch(Throwable th){
				th.printStackTrace();
			}			
		}
		
		log.info(" ******************************END*************************************");
		return value;		
	}
}
