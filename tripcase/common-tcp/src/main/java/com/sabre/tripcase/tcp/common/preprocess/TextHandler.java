/**
 * 
 */
package com.sabre.tripcase.tcp.common.preprocess;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.sabre.tripcase.tcp.common.constants.Constants;

public class TextHandler 
{
	private static Logger log =Logger.getLogger(TextHandler.class);	
	public String processTextContent(String content) throws IOException
	{
		
		log.info(" ************************ TEXT Processor ***************************");
		log.info(" *******************************************************************");
		return content.toString().replaceAll("\\<.*?>","");
	}
}
