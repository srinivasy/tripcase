package com.sabre.tripcase.tcp.common.validation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import com.sabre.tripcase.tcp.common.sourcetypes.*;
 
public class FileValidator 
{
	private LangIdentifier langIdentifier;
	private ExtractAttachmentContent extractAttContent;
	private Airlines airlines;

	private String fileLocation;
	private String downloadfileLocation;


	private static Logger log =Logger.getLogger(FileValidator.class);


	/**
	 * @param langIdentifier
	 */
	public void setLangIdentifier(LangIdentifier langIdentifier) {
		this.langIdentifier = langIdentifier;
	}

	/**
	 * @param extractAttContent
	 */
	public void setExtractAttContent(ExtractAttachmentContent extractAttContent) {
		this.extractAttContent = extractAttContent;
	}

	/**
	 * @param airlines
	 */
	public void setAirlines(Airlines airlines) {
		this.airlines = airlines;
	}
	

	public String getDownloadfileLocation() {
		return downloadfileLocation;
	}

	public void setDownloadfileLocation(String downloadfileLocation) {
		this.downloadfileLocation = downloadfileLocation;
	}		

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}


	/**
	 * @param none
	 * @return none
	 * @throws MessagingException
	 * @throws IOException
	 * @Description Reads the .eml file content and search for attachments if any. If found any attachments, 
	 * it will extract content from the attachment and identifies its language.
	 */

	public void ValidateEML() throws MessagingException, IOException
	{
		log.debug("START: ValidateEML");
		MimeMessage message = null;

/*
		if (fileLocation.isEmpty()){
			LOG.debug("Source location doesnot have any files to process.");
			return;
		}			

		File folder = new File(fileLocation);
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isFile()) {					
			
				InputStream source = new FileInputStream(new File(folder.getAbsolutePath()+
						File.separator + fileEntry.getName()));
				MimeMessage message = new MimeMessage(null, source);
*/
		try{
					ArrayList<String> lContentEnAirlines = processMessage(message);	

					//Logging content of the airlines data which is in English.
					//$$$ only for debug purpose. MAKE SURE TO DELETE THIS
					describe(lContentEnAirlines);			

				}catch(Exception e){
					System.out.println("Exception thrown" + e.getMessage());
				}			
		log.debug("END: ValidateEML");			
	}


	/**
	 * @param mMsg
	 * @throws MessagingException
	 * @throws IOException
	 */
	public ArrayList<String> processMessage(MimeMessage mMsg) throws MessagingException, IOException {
		log.debug(" Process MIME message for attachments and/or body content");

		// store attachment file name, separated by comma
		String attachFiles = "";
		ArrayList<String> lContent = new ArrayList<String>();
		ArrayList<String> lContentEnglish = new ArrayList<String>();
		ArrayList<String> lContentEnAirlines = new ArrayList<String>();
		
		String contentType = mMsg.getContentType();

		if (contentType.contains("multipart")) {
			log.info("Eml contains multipart body");

			// content may contain attachments
			MimeMultipart multiPart = (MimeMultipart) mMsg.getContent();
			int numberOfParts = multiPart.getCount();
			log.info("Number of parts found in the eml : " + numberOfParts);

			for (int partCount = 0; partCount < numberOfParts; partCount++) {
				MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
				log.debug("Reading MIME body parts.");

				// Email body contains in either of the Parts or sometimes in both the Parts.
				String sEmailBody = getBodyContent(part);				 

				if (sEmailBody!= null)	{					
					lContent.add(sEmailBody);
					log.debug("Eml body content length: " + sEmailBody.length()); 					
				}				

				if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
					// this part is attachment
					log.info("Eml contains attachment(s)");
					
					String attachmentName = null;					
					String attContent =  getAttachmentContent(part, attachmentName);
					attachFiles += attachmentName + ", ";	
					
					if (attContent != null) {
						lContent.add(attContent);
					}					
					log.info("Eml attachment data is in: " + langIdentifier.identifyLanguage(attContent) + " language");
				} 				
			}

			if (attachFiles.length() > 1) {
				attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
				log.info("List if eml attachment files are:  " + attachFiles);
			}
			
			
		}
		else if (contentType.contains("text/plain")
				|| contentType.contains("text/html")) {
			Object content = mMsg.getContent();
			if (content != null) {
				lContent.add(content.toString());
			}	
		}
		else if (contentType.contains("application/*")) {
			log.info("Eml has different content types other than multipart");			
		}
		
		
		// identify language of the content.
		languageDetection(lContent, lContentEnglish);
					
		// identify source of the content.
		sourceDetection(lContentEnglish, lContentEnAirlines);
		return lContentEnAirlines;
	}
	
	/**
	 * @param alContents
	 */
	public void languageDetection(ArrayList<String> alContentsIn, ArrayList<String> alContentsOut){
		// identify language of the content.
		
		String sContent = null;		
		
		for(int index = 0; index < alContentsIn.size(); index++) {			
			
			sContent = alContentsIn.get(index);
			if( (langIdentifier.identifyLanguage(sContent)).compareToIgnoreCase("en") == 0 ) {
				log.debug(" Eml content" + index +" language identified as English.");
				alContentsOut.add(sContent);
			}else {
				log.debug(" Eml content" + index +" language identified as Non-English.");
			}
		}		
	}
		
	
	/**
	 * @param alContEn
	 * @param alContEnAir
	 */
	public void sourceDetection(ArrayList<String> alContEn, ArrayList<String> alContEnAir) {
		// identify source of the content.

		String sContent = null;	
		for(int index = 0; index < alContEn.size(); index++) {
			sContent = alContEn.get(index);
			if( airlines.isAirlines(sContent) ) {
				log.info(" Eml content belongs to Airlines data.");
				alContEnAir.add(sContent);
			} else {
				log.info(" Eml content belongs to other sources.");
			}
		}		
	}
		
	
	/**
	 * @param mPart
	 * @param attFileName
	 * @return
	 * @throws IOException
	 * @throws MessagingException
	 */
	public String getAttachmentContent(MimeBodyPart mPart, String attFileName) throws IOException, MessagingException {
		// Extract data from attachment
		
		attFileName = mPart.getFileName();
		log.info("Attachment file name " + attFileName);	
		
		// validate attachments by extension.

		downloadfileLocation = downloadfileLocation + File.separator + attFileName;
		//Save the attachment at temporary location. It need to be deleted after processing it
		log.debug("Attachment file storing in to a temporary location : " + downloadfileLocation);
		mPart.saveFile(downloadfileLocation);

		/*  
		 * Extract data from the downloaded attachment. Accuracy of finding language type is more by extracting text
		 *  from attachment than performing it on document. 
		 *  
		 * */
		String attData = extractAttContent.extractData(downloadfileLocation);
		//LOG.debug("Attachment content: "+ attContent);
		return attData;		
	}
	
	
	/**
	 * @param alString
	 */
	public void describe(ArrayList<String> alString) {
		// Displays content of eml body and attachments which are in English language and belongs to Airlines.
		log.debug("########################################################################################");
		log.debug("Describe::::::");
		for(int index = 0; index < alString.size(); index++) {	
			log.debug(alString.get(index));
			util.writeToFile("E:\\Temp\\sample"+util.getRandInt(1,10000)+".txt", (alString.get(index)).replace(util.NBSP, util.SPACE_EMPTY) );
		}
		log.debug("########################################################################################");
	}
	

	/**
	 * @param part. MimeBodyPart implements public interface Part. 
	 * @return String: eml body content in plain text format.
	 * @throws MessagingException
	 * @throws IOException
	 * @Description Logic of extracting body content from the .eml file. It performs based on the content type. If it is HTML text it will remove the tags. 
	 * So that the file format will be retained.	
	 */

	public String getBodyContent(Part part) throws MessagingException, IOException {
		log.debug(" Get body content of the eml ");

		if (part.isMimeType("text/*")) {
			String sContent = (String)part.getContent();
			boolean textIsHtml = part.isMimeType("text/html");
			if (textIsHtml)
			{	
				log.info(" Body contains HTML text.");
				sContent= util.convertHTML2Text(sContent);   
				log.info(" Removed HTML tags from the body content.");
			}
			return sContent;
		}

		if (part.isMimeType("multipart/alternative")) {
			log.info(" Body contains multipart/alternative content.");
			// prefer html text over plain text
			Multipart mpart = (Multipart)part.getContent();
			String text = null;

			for (int i = 0; i < mpart.getCount(); i++) {
				Part bodypart = mpart.getBodyPart(i);

				if (bodypart.isMimeType("text/plain")) {
					log.info(" Body contains - text/plain content.");
					if (text == null) {
						text = getBodyContent(bodypart); 
					}
					continue;
				} else if (bodypart.isMimeType("text/html")) {
					log.info(" Body contains - text/html content.");
					String sBody = getBodyContent(bodypart);

					if (sBody != null){
						sBody= util.convertHTML2Text(sBody); 
						return sBody;
					}
				} else {
					return getBodyContent(bodypart);
				}
			}
			return text;
		} else if (part.isMimeType("multipart/*")) { 
			log.info(" Body contains - multipart/* content.");
			Multipart mpart = (Multipart)part.getContent();
			for (int i = 0; i < mpart.getCount(); i++) {
				String sBody = getBodyContent(mpart.getBodyPart(i));
				if (sBody != null) {
					return sBody;
				}
			}
		}
		return null;
	}



}


