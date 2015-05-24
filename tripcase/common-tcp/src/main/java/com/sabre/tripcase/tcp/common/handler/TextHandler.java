/**
 * */
package com.sabre.tripcase.tcp.common.handler;

import java.io.IOException;
import org.apache.log4j.Logger;
import com.sabre.tripcase.tcp.common.constants.Constants;

public class TextHandler implements Handler
{
	private static Logger log =Logger.getLogger(TextHandler.class);	
	public String convertToText(String content)
	{		
		log.info("************************ START ***************************");
		
		return content.toString().replaceAll("\\<.*?>","");
	}
}
