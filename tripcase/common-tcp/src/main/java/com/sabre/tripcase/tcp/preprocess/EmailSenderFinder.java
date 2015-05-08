package com.sabre.tripcase.tcp.preprocess;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSenderFinder {
    private static final String originalSenderRegEx2 = "(?:From:|De:|Da:)\\s*[\"']?(.*?)[\"']?\\s*(?:\\[mailto:|<|&lt;)(.*?)(?:\\]|>|&gt;)";
  //  private static final String originalSenderRegEx3 = "(?:From:|Da:|De:)(.*?)(?:\\[mailto:|<|&lt;)(.*?)(?:\\]|>|&gt;)";
  //  private static final String latestPattern = "\\s+[\"']?(.*?)[\"']?\\s*(?:\\[mailto:|<)(.*?)(?:[\\]>])";
  //  private static final String latestPattern2 = "(.*?)(?:\\[mailto:|<)(.*?)(?:[\\]>])";
    private static final String originalSenderRegEx = 	"(?:From:|De:|Da:)(.*?)([_A-Za-z0-9-\\+]+(?:\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(?:\\.[A-Za-z0-9]+)*(?:\\.[A-Za-z]{2,}))";
	private static final String senderRegEx = 	"(.*?)([_A-Za-z0-9-\\+]+(?:\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(?:\\.[A-Za-z0-9]+)*(?:\\.[A-Za-z]{2,}))";
	//private static final String originalSenderRegEx3="(?:From:|De:|Da:)(.*?)&lt;([_A-Za-z0-9-\\+]+(?:\\.[_A-Za-z0-9-]+)*@"
		//+ "[A-Za-z0-9-]+(?:\\.[A-Za-z0-9]+)*(?:\\.[A-Za-z]{2,}))&gt;"
	//private static final String originalSenderRegExMailTo = "[From:|De:](.*?)(?:\\[mailto:|<)(.*?)(?:[\\]>])";
		
	
   
    public static void main(String args[]) throws Exception{		
	    findAndDisplayEmail(new File("."));
	}
	
	private static void findAndDisplayEmail(File directory) throws Exception{
		File[] files = directory.listFiles();
		String fileName;
		for(File file : files){
			if (file.isDirectory()){
				System.out.println("Directory Name: " + file.getCanonicalPath());
				findAndDisplayEmail(file);
			}else {
				fileName = file.getCanonicalPath();				
				if (fileName.endsWith(".eml") || fileName.endsWith(".EML")){
					System.out.println("FileName: " + fileName);
					displayEmailAddress(new File(fileName));
				}
			}
		}
	}
	 
  /*for (int i = 0; i < listOfFiles.length; i++) 
  {
 
   if (listOfFiles[i].isFile()) 
   {
	 fileName = listOfFiles[i].getCanonicalPath();
       if (fileName.endsWith(".eml") || fileName.endsWith(".EML"))
       {
         // System.out.println(fileName);
		 // display(new File(fileName));
        }
     }
  }
       displayEmailAddress(new File("C:\\Users\\Sriram\\Downloads\\AIRLINES\\AIRLINES\\Email Body\\2253570.eml"));
	 displayEmailAddress(new File("C:\\Users\\Sriram\\Downloads\\AIRLINES\\AIRLINES\\Email Body\\2501790.eml"));
	 displayEmailAddress(new File("C:\\Users\\Sriram\\Downloads\\AIRLINES\\AIRLINES\\Email Body\\2253990.eml"));

   }*/

   public static void displayEmailAddress(File emlFile) throws Exception{
	    Pattern originalSenderPattern = Pattern.compile(originalSenderRegEx, Pattern.MULTILINE);
		Pattern senderPattern = Pattern.compile(senderRegEx, Pattern.MULTILINE);
		Pattern originalSenderPattern2 = Pattern.compile(originalSenderRegEx2, Pattern.MULTILINE);
		
		
		Matcher originalSenderMatcher;
		Matcher senderMatcher;
		Matcher originalSenderMatcher2;
		
		
		
        Properties props = System.getProperties();
      //  props.put("mail.host", "smtp.office365.com");
        props.put("mail.transport.protocol", "smtp");

        Session mailSession = Session.getDefaultInstance(props, null);
        InputStream source = new FileInputStream(emlFile);
        MimeMessage message = new MimeMessage(mailSession, source);   
        System.out.println("From:" + message.getFrom()[0]);	
		String finalContent = "";
		
	    if (message.getContent() instanceof Multipart){
		    Multipart mp = (Multipart) message.getContent();
			BodyPart bp = mp.getBodyPart(0);
			if(bp.getContent() instanceof MimeMultipart){
				 finalContent = getFinalContent(bp);
			} else {
				finalContent = (bp.getContent()).toString();
			}
	    } else if(message.getContent() instanceof String){		  
		   finalContent = (String) message.getContent();
	    }
		originalSenderMatcher = originalSenderPattern.matcher(finalContent);		
		senderMatcher = senderPattern.matcher((message.getFrom()[0]).toString());
		
		String originalSender = "";
		while(originalSenderMatcher.find()){
			originalSender = originalSenderMatcher.group(2);
			System.out.println("Previous Sender:" + originalSender );			
		}
		if(originalSender.equals("")){
			originalSenderMatcher2 = originalSenderPattern2.matcher(finalContent);
			while(originalSenderMatcher2.find()){
			originalSender = originalSenderMatcher2.group(2);
			System.out.println("Previous Sender:" + originalSender );			
		   }	
		}
			
		System.out.println("Original Sender:" + originalSender);
		if(senderMatcher.find()){			
			System.out.println("Most recent Sender:" + senderMatcher.group(2) );			
		} else{
			System.out.println("Most recent Sender:" + (message.getFrom()[0]).toString());
		}
		System.out.println("-----");
		   /*Multipart mp = (Multipart) message.getContent();
            BodyPart bp = mp.getBodyPart(0);
            System.out.println("SENT DATE:" + message.getSentDate());
            System.out.println("SUBJECT:" + message.getSubject());
            System.out.println("CONTENT:" + bp.getContent());*/
		
		
	//	for(javax.mail.Address fromAddress: message.getFrom()){
			
		//	System.out.println(" In Loop From Address: " + fromAddress);
			
		//}
	   
    }
	
	private static String getFinalContent(Part p) throws MessagingException,
            IOException {

        String finalContents = "";
        if (p.getContent() instanceof String) {
            finalContents = (String) p.getContent();
        } else {
            Multipart mp = (Multipart) p.getContent();
            if (mp.getCount() > 0) {
                Part bp = mp.getBodyPart(0);
                try {
                    finalContents = dumpPart(bp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return finalContents.trim();
    }

private static String dumpPart(Part p) throws Exception {

        InputStream is = p.getInputStream();
        // If "is" is not already buffered, wrap a BufferedInputStream
        // around it.
        if (!(is instanceof BufferedInputStream)) {
            is = new BufferedInputStream(is);
        }
        return getStringFromInputStream(is);
    }

private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
	
	
}