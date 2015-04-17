/**
 * 
 */
package com.motivity.labs.opennpl.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.mail.internet.MimeMessage;

import com.motivity.labs.opennpl.models.ModelCreation;
import com.motivity.labs.opennpl.models.ModelTypes;
import com.motivity.labs.opennpl.models.NLPHelper;
import com.motivity.labs.opennpl.models.NLPProcess;
import com.motivity.tripcase.emailreader.EmailReader;
import com.motivity.tripcase.emailreader.MimeMessageReader;
import com.motivity.tripcase.utils.ControlProperties;
import com.motivity.tripcase.utils.HtmlTableParser;

/**
 * @author Nalini Kanta
 *
 */
public class NLPParser {

	static String filePath="C:/Users/CB34388493/opennlp/emails/";
	static String processFile="ALL";
	
	public static void main(String args[]) throws IOException{
		startProcess();
	}
	
	public static void prepareTrainingModels() throws IOException{
		ModelCreation.createModel(ModelTypes.DEPART_MODEL,ModelTypes.DEPART_MODEL_FILE);
		ModelCreation.createModel(ModelTypes.BOOKING_REF_MODEL,ModelTypes.BOOKING_REF_MODEL_FILE);
		
	}
	public static void processTextContent(String bodyText,String fileName) throws IOException{
		System.out.println(" ************************ TEXT Processor ***************************");
		System.out.println(" *******************************************************************");
		bodyText=bodyText.toString().replaceAll("\\<.*?>","");
		try{
			NLPProcess.process(bodyText,"TEXT",fileName);
			}
		catch(Throwable th){
			th.printStackTrace();
			}
		}
	
	public static void processHTMLContent(String bodyText,String fileName) throws IOException{
		System.out.println(" ************************ HTML Processor ***************************");
		System.out.println(" *******************************************************************");
		ArrayList<String> preserveOnlySentences=new ArrayList<String>();
		ArrayList<String> nextProcessing=new ArrayList<String>();
		if(bodyText.contains("<html>") ){
			try{
				Map<String,String> htmlParsedMap=HtmlTableParser.parse(bodyText, null, false, " ");
				Set<Entry<String,String>> entrySet=htmlParsedMap.entrySet();
				Iterator<Entry<String,String>> iterator=entrySet.iterator();
				while(iterator.hasNext()){
					Entry<String,String> keyEntry=iterator.next();
					String key=keyEntry.getKey();
					String value=keyEntry.getValue().replace("-", " ");
					//System.out.println("Key="+key);
					//System.out.println("value="+value);
					NLPProcess.process(value,"HTML",fileName);

				}
			}
			catch(Throwable th){
				th.printStackTrace();
			}
		}
		
	}
	
	public static void startProcess() throws IOException{
		String fileName="ALL";
		File[] files = new File(filePath).listFiles();
		prepareTrainingModels();
		if(files != null){			
				for (File file : files) {
				if (file.isFile()) {
					System.out.println("************************* " + file.getName() + " ***********************");
					
					MimeMessage mimeMessage=MimeMessageReader.getMimeMessage(file);
					String bodyText=EmailReader.getBody(mimeMessage, false);
					//System.out.println("Body Text="+bodyText);
					ControlProperties.setFileName(processFile);
					if(processFile.equals("ALL")){
						fileName="ALL";
					}
					else{
						fileName=file.getName();
					}
					if(bodyText.contains("<html>")){
						
					bodyText=NLPHelper.removeBlankLines(bodyText);
					processHTMLContent(bodyText,fileName);
					}
					else{
						//bodyText=NLPHelper.removeBlankLines(bodyText);
						processTextContent(bodyText,fileName);
					}
					
				}
			}
		}
	
		
	}
	

}
