package com.sabre.tripcase.tcp.common.validation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class ExtractAttachmentContent {	

	// Maximum buffer size to read from the source file 
	private static final int MAXIMUM_BUFFER_SIZE = 10 * 1024 * 1024;
	
	private static Logger log =Logger.getLogger(ExtractAttachmentContent.class);

	public String  extractData(String attFileName)
	{	
		 String retStr = null;
		try {

			File file = new File(attFileName);
			 
			if (!file.exists()) {
				log.error("File does not exists");
				return retStr;
			}
			
			log.info("Source file to be extracted::" + file.getPath()); 

			InputStream input = new FileInputStream(file);
			Metadata metadata = new Metadata();
			BodyContentHandler handler = new BodyContentHandler(MAXIMUM_BUFFER_SIZE);
			AutoDetectParser parser = new AutoDetectParser(); 

			parser.parse(input, handler, metadata);
			retStr = handler.toString();

			//LOG.debug("Attachment content: " + retStr); 				
			
		}
		catch (IOException ex)
		{	   
			System.out.println("Failed to save the content in to File"); 
			ex.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TikaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retStr;
	}

	
}
