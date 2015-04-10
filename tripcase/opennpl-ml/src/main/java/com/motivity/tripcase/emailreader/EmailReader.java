package com.motivity.tripcase.emailreader;
//import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.motivity.ml.SentenceDetectClass;
import com.motivity.tripcase.pojo.EMail;
import com.motivity.tripcase.utils.HtmlFileReader;
import com.motivity.tripcase.utils.HtmlTableParser;

public class EmailReader {


	public EMail convertToString(MimeMessage mimeMessage)
	{
		EMail email = new EMail();
		try {
			InternetAddress[] toRecipients = (InternetAddress[]) mimeMessage.getRecipients(RecipientType.TO);

			if (toRecipients != null)
			{
				String displayTo = "";

				for (int i = 0; i < toRecipients.length; i++)
				{
					//System.out.println("toRecipients Address : " + toRecipients[i].getAddress());

					if (toRecipients[i].getPersonal() != null)
					{
						displayTo += toRecipients[i].getPersonal();
					}
					else
					{
						displayTo += toRecipients[i].getAddress();
					}
				}
				//System.out.println("All toRecipients " + displayTo);
				email.setToAddress(displayTo);
			}

			if (mimeMessage.getFrom() != null && mimeMessage.getFrom().length > 0)
			{
				InternetAddress from = (InternetAddress) mimeMessage.getFrom()[0];

				if (from.getPersonal() != null)
				{
					//System.out.println("From Personal : "+ from.getPersonal());
					email.setFromName(from.getPersonal());
				}
				if (from.getAddress() != null)
				{
					//System.out.println("From Address : " + from.getAddress());
					email.setFromAddress(from.getAddress());
				}

			}
			
			if (mimeMessage.getSubject() != null && mimeMessage.getSubject().length() > 0)
			{
				email.setSubject(mimeMessage.getSubject());
			}
			
			if (mimeMessage.getReceivedDate() != null && mimeMessage.getReceivedDate() != null )
			{
				email.setReceivedDate(mimeMessage.getReceivedDate().toLocaleString());
			}

			email = EmailReader.getBodyAsString(mimeMessage, email);
		
		} catch (MessagingException e) {
			System.err.println(e.getMessage());
		}

		return email;

	}

	private static EMail getBodyAsString(MimeMessage mimeMessage,EMail email)
	{
		
		List<BodyPart> allBodyParts = new ArrayList<BodyPart>();

		try {
			if (mimeMessage.getContent() instanceof Multipart)
			{
				getAllBodyParts((Multipart)mimeMessage.getContent(), allBodyParts);

				for (int i = 0; i < allBodyParts.size(); i++)
				{
					
					if (allBodyParts.get(i).isMimeType("text/plain") && allBodyParts.get(i).getDisposition() != null)
					{
						String content = (String) allBodyParts.get(i).getContent();
						byte[] buffer = content.getBytes("UTF-8"); //here you can use char set from allBodyParts.get(i)

					}
					else if (allBodyParts.get(i).isMimeType("text/plain"))
					{
						if(allBodyParts.get(i).getContent() instanceof MimeBodyPart)
						{
							MimeBodyPart mimeBodyPart = (MimeBodyPart)allBodyParts.get(i).getContent();
							
							//donwloadAttachments(mimeBodyPart);//attachment downloads.
							email.setBody( mimeBodyPart.getDescription());
						}
						else if (allBodyParts.get(i).getContent() instanceof String)
						{
							//System.out.println("Body getContent **************" + (String)allBodyParts.get(i).getContent());
							email.setBody( (String)allBodyParts.get(i).getContent());
						}
					}else if (allBodyParts.get(i).isMimeType("text/html"))
					{
						if(allBodyParts.get(i).getContent() instanceof MimeBodyPart)
						{
							MimeBodyPart mimeBodyPart = (MimeBodyPart)allBodyParts.get(i).getContent();
							
							//donwloadAttachments(mimeBodyPart);//attachment download
							String htmlText = mimeBodyPart.getDescription();
							email.setHtml(htmlText);
							//System.out.println("htmlText* :" + htmlText);

						}
						else if (allBodyParts.get(i).getContent() instanceof String)
						{
							String htmlText = (String)allBodyParts.get(i).getContent();							
							
							Map<String, String> htmlParserMap = HtmlTableParser.parse(htmlText, "", false, "|");
							String htmlTextAfterParse = getHtmlStringFromParserMap(htmlParserMap);
							//System.out.println("htmlTextAfterParse: " + htmlTextAfterParse);
							email.setHtml(htmlTextAfterParse);
						}
						
					}
				}
			}else{
				email.setBody(mimeMessage.getContent().toString());
			}
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}catch (Throwable t) {
			t.printStackTrace();
		}
		return email;

	}

	private static void getAllBodyParts(Multipart multipart, List<BodyPart> allBodyParts) throws MessagingException, IOException
	{
		for (int i = 0; i < multipart.getCount(); i++)
		{
			BodyPart bodyPart = multipart.getBodyPart(i);
			allBodyParts.add(bodyPart);

			if(bodyPart.getContent() instanceof Multipart)
			{
				getAllBodyParts((Multipart)bodyPart.getContent(), allBodyParts);
			}
		}
	}
	
    //  TODO : To be enhanced in later stages //http://www.independentsoft.com/jmsg/tutorial/convertmimetomsg.html
	private static void getBody(MimeMessage mimeMessage)
	{
		List<BodyPart> allBodyParts = new ArrayList<BodyPart>();

		try {
			if (mimeMessage.getContent() instanceof Multipart)
			{
				getAllBodyParts((Multipart)mimeMessage.getContent(), allBodyParts);

				for (int i = 0; i < allBodyParts.size(); i++)
				{
					if (allBodyParts.get(i).isMimeType("text/plain") && allBodyParts.get(i).getDisposition() != null)
					{
						String content = (String) allBodyParts.get(i).getContent();
						byte[] buffer = content.getBytes("UTF-8"); //here you can use char set from allBodyParts.get(i)
						//System.out.println("content* : " + content);
					}
					else if (allBodyParts.get(i).isMimeType("text/plain"))
					{
						if(allBodyParts.get(i).getContent() instanceof MimeBodyPart)
						{
							MimeBodyPart mimeBodyPart = (MimeBodyPart)allBodyParts.get(i).getContent();
							//System.out.println("getDescription* : " + mimeBodyPart.getDescription());
						}
						else if (allBodyParts.get(i).getContent() instanceof String)
						{
							//System.out.println("getContent* :" + (String)allBodyParts.get(i).getContent());
						}
					}
					else if (allBodyParts.get(i).isMimeType("text/html"))
					{
						if(allBodyParts.get(i).getContent() instanceof MimeBodyPart)
						{
							MimeBodyPart mimeBodyPart = (MimeBodyPart)allBodyParts.get(i).getContent();

							String htmlText = mimeBodyPart.getDescription();

							//System.out.println("htmlText* :" + htmlText);

						}
						else if (allBodyParts.get(i).getContent() instanceof String)
						{
							String htmlText = (String)allBodyParts.get(i).getContent();

							//System.out.println("htmlText@ :" + htmlText);
						}
					}
					else if (allBodyParts.get(i).getContent() instanceof InputStream)
					{
						InputStream is = (InputStream) allBodyParts.get(i).getContent();

						if(allBodyParts.get(i).getHeader("Content-Location") != null && allBodyParts.get(i).getHeader("Content-Location").length > 0)
						{
							String contentId = (String)allBodyParts.get(i).getHeader("Content-Location")[0];

						}
						else if(allBodyParts.get(i).getHeader("Content-ID") != null && allBodyParts.get(i).getHeader("Content-ID").length > 0)
						{
							String contentId = (String)allBodyParts.get(i).getHeader("Content-ID")[0];
							//System.out.println("contentId&" + contentId);

						}

					}

				}
			}
			else if (mimeMessage.getContent() instanceof String)
			{
				if(mimeMessage.isMimeType("text/html"))
				{            
					String htmlText = (String)mimeMessage.getContent();
					
					//passengerSummaryParse(htmlText,"|");
					Map<String, String> htmlParserMap = HtmlTableParser.parse(htmlText, "", false, "|");
					String htmlTextAfterParse = getHtmlStringFromParserMap(htmlParserMap);
					
					System.out.println("htmlTextAfterParse % :" + htmlTextAfterParse);
				}
				else   
				{ 
					String plainText = (String)mimeMessage.getContent();
					//System.out.println("plainText^ :" + plainText);
					
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}catch(Throwable t){
			t.printStackTrace();
		}

	}
	
public static void htmlTableParse(String htmlString){
		
	try{
		Document document = Jsoup.parse(htmlString);
		String format = "%-30s";
		StringBuilder formattedOutput=new StringBuilder();
		
		for (Element e : document.select("table tbody tr td")) {

			Elements childElements = e.children();
			for (Element element : childElements) {
				if (element.text().trim().equalsIgnoreCase("Passenger summary")) {

					Elements sibilings = element.parent().parent()
							.siblingElements();

					for (int i = 0; i < sibilings.size(); i++) {

						Elements children = sibilings.get(i).children();
						for (Element child : children) {

							if (child.text().replace("\u00a0", "").isEmpty()) {
		
								formattedOutput.append(String.format(format,child.text().replace("\u00a0", "")));

							} else {
								
								formattedOutput.append(String.format(format,child.text()));

							}

						}
						
						formattedOutput.append("\n");
						System.out.println("html format parse string: " + formattedOutput.toString());

					}
				}

			}
		}
		
		}
		catch(Exception t){
			t.printStackTrace();
		}
	}

	public static void donwloadAttachments(MimeBodyPart mimeBodyPart){

		//for attachments download
		try{
		//if (Part.ATTACHMENT.equalsIgnoreCase(mimeBodyPart.getDisposition())) {
			String attachFiles = "";
            String fileName = mimeBodyPart.getFileName();
            attachFiles += fileName + ", ";
            mimeBodyPart.saveFile("D:\\projects\\TripCase_Project\\Technical\\email_templates\\OpenNLP\\Airline\\emailattachment_downloads\\" + File.separator + fileName);
            System.out.println("email attachments download success ********: ");
		//}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*public static String passengerSummaryParse(String htmlString,String delimiter){
		StringBuilder formattedOutput = new StringBuilder();
		List< Map<String,String>> passengerList=new ArrayList<Map<String,String>>();
		
		delimiter = "|";


		try {
			Document document = Jsoup.parse(htmlString);
			Elements passengerTables = document.select("table tbody tr td table tbody tr td table"); //select the Passenger & travel cost details table.
			passengerList=processPassengerTable(passengerTables,passengerList);
			formattedOutput.append(format(passengerList,delimiter));
		}
		catch(Exception t){
				t.printStackTrace();
		}
		
		//System.out.println("FormattedOutput from passengerSummary: " + formattedOutput.toString());
		return formattedOutput.toString();
	}
	
	private static List<Map<String,String>> processPassengerTable(Elements nestedTables,List< Map<String,String>> passengerList) {
		ArrayList<Element> tableList = new ArrayList<Element>();
		   try{
		
		for(int i=0;i<nestedTables.size();i++){
			if(nestedTables.get(i).html().contains("Passenger summary"))
			tableList.add(nestedTables.get(i));
		}
		
		*//** Processing for Flight Details *//*
		for(Element tbody:tableList){
			
						
			if(tbody.html().contains("Passenger summary")){
				processPassengerDetails(tbody,passengerList);
				
			}

		}
		   }
		   catch(Exception t){
			   t.printStackTrace();
		   }
		return passengerList;
	}
	
	private static List< Map<String,String>>  processPassengerDetails(Element tbody,List< Map<String,String>> passengerList){
		
		if(tbody.html().contains("Passenger summary")){
			
		Elements rows=tbody.select("tr");
		for (int i=2;i<rows.size();i++){
			Map<String,String>  passengerDetailsMap=new LinkedHashMap<String,String>();
			passengerDetailsMap.put(replaceAmpSpaceNullEmpty(rows.get(1).child(0).text()), replaceAmpSpaceNullEmpty(rows.get(i).child(0).text()));
			passengerDetailsMap.put(replaceAmpSpaceNullEmpty(rows.get(1).child(1).text()), replaceAmpSpaceNullEmpty(rows.get(i).child(1).text()));
			passengerDetailsMap.put(replaceAmpSpaceNullEmpty(rows.get(1).child(2).text()), replaceAmpSpaceNullEmpty(rows.get(i).child(2).text()));
			passengerDetailsMap.put(replaceAmpSpaceNullEmpty(rows.get(1).child(3).text()), replaceAmpSpaceNullEmpty(rows.get(i).child(3).text()));
			passengerList.add(passengerDetailsMap);
			
		}
		}

		return passengerList;
		
	}

	private static String replaceAmpSpaceNullEmpty(String val){	
		if(null!=val){
			if(val.trim().isEmpty()){
				return val.replace("\u00a0", "");
			}
			else{
				return val.replace("\u00a0", "");
			}
		}
		else{
			val="";
		}
	
		return val;
	}
	
	private static String format(List< Map<String,String>> listMap,String delimiter){
		
		StringBuilder localString=new StringBuilder();
		
		if(!listMap.isEmpty() && listMap.size()>0){
			
			Map<String,String> passengerHeaderMap=listMap.get(0);
			Set<String> headers=passengerHeaderMap.keySet();
			for(String key:headers){
				localString.append(key);
				localString.append(delimiter);
			}
			
			for(Map<String,String> passengerMap:listMap){
				Collection<String> values=passengerMap.values();
				localString.append("\n");
				for(String value:values){
					localString.append(value);
					localString.append(delimiter);
				}
			}
			
		}
		localString.append("\n");
		return localString.toString();
		
	}*/
	
	
	public static String getHtmlStringFromParserMap(Map<String, String> htmlTableMap){
		
		String tableHeaderAndValues = null;
		String htmlString = null;
		StringBuffer htmlStringFinaloutput = new StringBuffer();
		
		for (String tkeys : htmlTableMap.keySet()) {				
			if(htmlTableMap.get(tkeys)!=null){
				 tableHeaderAndValues = htmlTableMap.get(tkeys);
				 System.out.println("tableHeaderAndValues: " + tableHeaderAndValues);
				 if(tableHeaderAndValues != "|||||"){
					 htmlString =  parseOutputFromHtmlParser(tableHeaderAndValues);
					 htmlStringFinaloutput.append(htmlString);
				 }else{
					 
				 }
			}
			//System.out.println("htmlString  : " + htmlString);
		}
		return htmlStringFinaloutput.toString();		
		
	}
	
	public static String parseOutputFromHtmlParser(String tableHeaderAndValues){
		String line = null;
		StringBuffer parseOutputFromHtmlParser = new StringBuffer();
		
		
		/*String emailInput = "Passenger summary| \n" + 
					"Passenger name|Frequent flyer # (Airline)|Ticket number|Special needs| \n" +
					"Justin Horchem||03773939189193|| \n";*/
		
		//below lines of code for testing purpose
		 //tableHeaderAndValues  = "Meal:|Snack| \n" + 
			//					"Aircraft:|BOEING 737-800 JET|";
		
		String[] emailInputArray = tableHeaderAndValues.split("\\n");
		
		
		HashMap<String, String> arrayMap = new HashMap<String, String>();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("D:\\projects\\TripCase_Project\\Technical\\email_templates\\OpenNLP\\Airline\\airlinemapping.txt"));
			while ((line = br.readLine()) != null) {

				
				if(!arrayMap.containsKey(line)){
					arrayMap.put(line, "");
				}
					
			} 
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, String> keyAndValues = new LinkedHashMap<String, String>(); //to holds the key and value format like : dep:xxxx
		float count=0;
		int noOfColumns=0;
		boolean headerFound = false;
		
		//HashSet<String> headerSet = new HashSet<String>();
		ArrayList<String> headerList = new ArrayList<String>() ;
		ArrayList<String> valueList = new ArrayList<String>() ;
		
		for(int i=0;i<emailInputArray.length;i++){
			Pattern p = Pattern.compile("^[ |]+$");
			Matcher m = p.matcher(emailInputArray[i]);
		       // get a matcher object
		       if(!m.matches())
		       {
		    	   String[] emailInputArrays = emailInputArray[i].split("\\|");
		    	   if(noOfColumns != emailInputArrays.length){
						headerList.clear();
						headerFound = false;
		    	   }
		
		    	   for(int j=0;j<emailInputArrays.length;j++){
		    		   if(headerFound)
		    		   {
		    			   System.out.println("headerList -  Key: " + headerList.get(j) + "  value : " + valueList.get(j));
		    			   
		    			   //parseOutputFromHtmlParser.append(headerList.get(j) + ": " + emailInputArrays[j].toString());
		    			   parseOutputFromHtmlParser.append(headerList.get(j) + ": " + valueList.get(j));
		    		   }
		    		   else
		    		   {
		    			   		    			   
		    			   if(arrayMap.containsKey(emailInputArrays[j])){
		    				   headerList.add(emailInputArrays[j].toString());
		    			   }
		    			   else if(!arrayMap.containsKey(emailInputArrays[j])){		    				   
		    				   valueList.add(emailInputArrays[j].toString());		    				  
		    			   }
					
		    			   if(arrayMap.containsKey(emailInputArrays[j])){
		    				   count = count+1;
					
		    			   }
				
		    		   }
		    	   }
			
		    	   if(count/emailInputArrays.length>0.5){
		    		   headerFound = true;
		    		   noOfColumns = emailInputArrays.length;
		    	   }
		       }
		}
		
		
		return parseOutputFromHtmlParser.toString();
			
	}
	
}


