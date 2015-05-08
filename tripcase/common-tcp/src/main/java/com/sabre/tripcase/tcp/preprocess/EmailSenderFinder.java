package com.sabre.tripcase.tcp.preprocess;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.*;
import javax.mail.internet.*;

/**
 * Finds the orignal and most recent senders of an email.
 * @author Sriram
 *
 */
public class EmailSenderFinder {

    private static final String ORIGINAL_SENDER_REGEX_2 = "(?:From:|De:|Da:)\\s*[\"']?(.*?)[\"']?\\s*(?:\\[mailto:|<|&lt;)(.*?)(?:\\]|>|&gt;)";
    private static final String ORIGINAL_SENDER_REGEX = "(?:From:|De:|Da:)(.*?)([_A-Za-z0-9-\\+]+(?:\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(?:\\.[A-Za-z0-9]+)*(?:\\.[A-Za-z]{2,}))";
    private static final String RECENT_SENDER_REGEX = "(.*?)([_A-Za-z0-9-\\+]+(?:\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(?:\\.[A-Za-z0-9]+)*(?:\\.[A-Za-z]{2,}))";


    /**
     *
     * @param args the command line arguments
     */
    public static void main(String args[]){
        findAndDisplayEmail(new File("."));
    }

    /**
     * @param directory the lookup directory for fetching email files
     */
    private static void findAndDisplayEmail(File directory){
        File[] files = directory.listFiles();
        String fileName;
        for (File file : files) {
            try{
            if (file.isDirectory()) {
                System.out.println("Directory Name: " + file.getCanonicalPath());
                findAndDisplayEmail(file);
            } else {
                fileName = file.getCanonicalPath();
                if (fileName.endsWith(".eml") || fileName.endsWith(".EML")) {
                    System.out.println("FileName: " + fileName);
                    displayEmailAddress(new File(fileName));
                }
            }
          }catch(IOException ioException){
              System.out.println("Replace with a logger:" + ioException);
          }
        }
    }

    /**
     *
     * @param emlFile the email file
     */
    public static void displayEmailAddress(File emlFile){
        Pattern originalSenderPattern = Pattern.compile(ORIGINAL_SENDER_REGEX, Pattern.MULTILINE);
        Pattern senderPattern = Pattern.compile(RECENT_SENDER_REGEX, Pattern.MULTILINE);
        Pattern originalSenderPattern2 = Pattern.compile(ORIGINAL_SENDER_REGEX_2, Pattern.MULTILINE);

        Matcher originalSenderMatcher;
        Matcher senderMatcher;
        Matcher originalSenderMatcher2;

        Properties props = System.getProperties();

        props.put("mail.transport.protocol", "smtp");

        Session mailSession = Session.getDefaultInstance(props, null);
        try{
        InputStream source = new FileInputStream(emlFile);
        MimeMessage message = new MimeMessage(mailSession, source);
        System.out.println("From:" + message.getFrom()[0]);
        String finalContent = "";

        if (message.getContent() instanceof Multipart) {
            Multipart mp = (Multipart)message.getContent();
            BodyPart bp = mp.getBodyPart(0);
            if (bp.getContent() instanceof MimeMultipart) {
                finalContent = getFinalContent(bp);
            } else {
                finalContent = (bp.getContent()).toString();
            }
        } else if (message.getContent() instanceof String) {
            finalContent = (String)message.getContent();
        }
        originalSenderMatcher = originalSenderPattern.matcher(finalContent);
        senderMatcher = senderPattern.matcher((message.getFrom()[0]).toString());

        String originalSender = "";
        while (originalSenderMatcher.find()) {
            originalSender = originalSenderMatcher.group(2);
            System.out.println("Previous Sender:" + originalSender);
        }
        if ("".equals(originalSender)) {
            originalSenderMatcher2 = originalSenderPattern2.matcher(finalContent);
            while (originalSenderMatcher2.find()) {
                originalSender = originalSenderMatcher2.group(2);
                System.out.println("Previous Sender:" + originalSender);
            }
        }

        System.out.println("Original Sender:" + originalSender);
        if (senderMatcher.find()) {
            System.out.println("Most recent Sender:" + senderMatcher.group(2));
        } else {
            System.out.println("Most recent Sender:" + (message.getFrom()[0]).toString());
        }
        System.out.println("-----");
        }catch(IOException ioException){
            System.out.println("Replace with a logger:" + ioException.getMessage());
        }catch(MessagingException me){
            System.out.println("Replace with a logger:" + me.getMessage());
        }
    }

    /**
     * @param p the mail message part
     * @return the contents of the mail message
     */
    private static String getFinalContent(Part p){

        String finalContents = "";
        try{
            if (p.getContent() instanceof String) {
                finalContents = (String)p.getContent();
            } else{
                Multipart mp = (Multipart)p.getContent();
                if (mp.getCount() > 0){
                    Part bp = mp.getBodyPart(0);
                    finalContents = dumpPart(bp);
                }
            }
        }catch(IOException ioException){
            System.out.println(ioException.getMessage());
        }catch(MessagingException me){
            System.out.println(me.getMessage());
        }
        return finalContents.trim();
    }


    /**
     * @param p the message part
     * @return the buffered input stream from the message part
     */
    private static String dumpPart(Part p){
        InputStream is = null;
        try{
            is = p.getInputStream();
        }catch(IOException ioException){
            System.out.println(ioException.getMessage());
        }catch(MessagingException me){
            System.out.println("Replace this message with a logger:" + me.getMessage());
        }
            // If "is" is not already buffered, wrap a BufferedInputStream
            // around it.
        if (!(is instanceof BufferedInputStream)) {
            is = new BufferedInputStream(is);
        }
        return getStringFromInputStream(is);
    }

    /**
     * @param is the message input stream
     * @return the string retrieved from the input stream
     */
    private static String getStringFromInputStream(InputStream is){

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        }catch (IOException e){
            System.out.println("Replace this message with a logger:" + e.getMessage());
        }finally {
            if (br != null) {
                try {
                    br.close();
                }catch (IOException e){
                    System.out.println("Replace this message with a logger:" + e.getMessage());
                }
            }
        }
        return sb.toString();
    }

}