package com.sabre.tripcase.tcp.common.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/*import javax.mail.*;
 import javax.mail.internet.*;*/

public class AttachmentConverter {
   // public static final String ATTACHMENT_DIR = "C:\\Users\\Sriram\\Downloads\\EmailAttachments-1";
    public static final String ATTACHMENT_DIR = "C:\\Users\\Sriram\\Downloads\\EmailAttachments-1";
    public static final String EMAIL_DIR = "C:\\Users\\Sriram\\Downloads\\AIRLINES\\AIRLINES\\Email Body + Other Attach";
    public static final String EXTRACT_DIR = "C:\\Users\\Sriram\\Downloads\\EXTRACT_DIR";

    public static final String ERROR_DIR = "C:\\Users\\Sriram\\Downloads\\ConversionIssues";

    public static final int BUFFER_SIZE = 4096;

    private AttachmentConverter() {
    }

    public static void main(String[] args) {
        findAndSaveAttachment(new File(EMAIL_DIR), ".eml");
      //  findPdfAndConvertToHtml(new File(ATTACHMENT_DIR));
        
    }

    public static void findAndSaveAttachment(File directory, String extension) {
        File[] files = directory.listFiles();
        String path;
        String emailFileName;
        String directoryName;

        for (File file : files) {
            try {
                if (file.isDirectory()) {
                    System.out.println("Current Directory: " + file.getCanonicalPath());
                    findAndSaveAttachment(file, extension);
                } else {
                    path = file.getCanonicalPath();
                    directoryName = directory.getName();
                    emailFileName = file.getName();
                    if (path.toLowerCase().endsWith(extension)) {
                        System.out.println("---");
                        System.out.println("Current File: " + path);
                        Properties props = System.getProperties();
                        props.put("mail.transport.protocol", "smtp");
                        Session mailSession = Session.getDefaultInstance(props, null);
                        InputStream source = new FileInputStream(new File(path));
                        MimeMessage message = new MimeMessage(mailSession, source);
                        String contentType = message.getContentType();
                        String messageContent = "";
                        String attachFiles = "";
                        Address[] fromAddress = message.getFrom();
                        String from = fromAddress[0].toString();
                        String subject = message.getSubject();
                        String sentDate = message.getSentDate().toString();
                        String attachmentName = "";
                        if (contentType.contains("multipart")) {
                            Multipart multiPart = (Multipart)message.getContent();
                            int numberOfParts = multiPart.getCount();
                            for (int partCount = 0; partCount < numberOfParts; partCount++) {
                                MimeBodyPart part = (MimeBodyPart)multiPart.getBodyPart(partCount);
                                if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                                    // this part is attachment
                                    System.out.println("Part ContentType: " + part.getContentType());
                                    String fileName = part.getFileName();
                                    attachFiles += fileName + ", ";
                                    attachmentName = ATTACHMENT_DIR + File.separator + directoryName + "-" + emailFileName + "-" + fileName;
                                    System.out.println(attachmentName);
                                   /* if (attachmentName.toLowerCase().endsWith(".zip")) {
                                        extractZippedFile(part.getFileName());
                                    } else {*/
                                        part.saveFile(attachmentName);
                                   // }
                                } else {
                                    parseMessage(message, directoryName, emailFileName);
                                }
                            }
                            // if(isMultiPart && !isAnAttachment){
                            // parseMessage(message,directoryName,emailFileName);
                            // }
                            if (attachFiles.length() > 1) {
                                attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
                            }
                        } else if (contentType.contains("text/plain") || contentType.contains("text/html")) {
                            Object content = message.getContent();
                            if (content != null) {
                                messageContent = content.toString();
                            }
                        }
                        System.out.println("\t From: " + from);
                        System.out.println("\t Subject: " + subject);
                        System.out.println("\t Sent Date: " + sentDate);
                        System.out.println("\t Attachments: " + attachFiles);
                    }
                }
            } catch (IOException ioException) {
                System.out.println("Replace with a logger:" + ioException);
            } catch (MessagingException me) {
                System.out.println(me.getMessage());
            }
        }
    }

    private static void parseMessage(Message message, String dirName, String emailFileName) throws MessagingException, IOException {
        System.out.println("<" + message.getFrom()[0] + "> " + message.getSubject());
        Multipart multipart = (Multipart)message.getContent();
        System.out.println("     > Message has " + multipart.getCount() + " multipart elements");
        for (int j = 0; j < multipart.getCount(); j++) {
            BodyPart bodyPart = multipart.getBodyPart(j);
            if (!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition())) {
                if (bodyPart.getContent().getClass().equals(MimeMultipart.class)) {
                    MimeMultipart mimemultipart = (MimeMultipart)bodyPart.getContent();
                    System.out.println("Number of embedded multiparts " + mimemultipart.getCount());
                    for (int k = 0; k < mimemultipart.getCount(); k++) {
                        if (mimemultipart.getBodyPart(k).getFileName() != null) {
                            System.out.println("     > Creating file with name : " + mimemultipart.getBodyPart(k).getFileName());
                            savefile(mimemultipart.getBodyPart(k).getFileName(), mimemultipart.getBodyPart(k).getInputStream(), dirName, emailFileName);
                        }
                    }
                }
                continue;
            }
            System.out.println("     > Creating file with name : " + bodyPart.getFileName());
            savefile(bodyPart.getFileName(), bodyPart.getInputStream(), dirName, emailFileName);
        }
    }

    private static void savefile(String fileName, InputStream is, String directoryName, String emailFileName) throws IOException {
        String attachmentName = ATTACHMENT_DIR + File.separator + directoryName + "-" + emailFileName + "-" + fileName;
       /* if (fileName.toLowerCase().endsWith(".zip")) {
            extractZippedFile(fileName);
        } else {*/
            File f = new File(attachmentName);
            FileOutputStream fos = new FileOutputStream(f);
            byte[] buf = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = is.read(buf)) != -1) {
                fos.write(buf, 0, bytesRead);
            }
            fos.close();
       // }
    }

    static void extractZippedFile(File attachmentDirectory, String extractDir) {
        String fileName;
        for (File file : attachmentDirectory.listFiles()) {
            try {
                if (file.isDirectory()) {
                    System.out.println("Directory Name: " + file.getCanonicalPath());
                    extractZippedFile(file, extractDir);
                } else {
                    fileName = file.getCanonicalPath();
                    if (fileName.toLowerCase().endsWith(".zip")) {
                        System.out.println("Zipped File: " + fileName);
                        ZipFile zipFile = new ZipFile(fileName);
                        zipFile.extractAll(extractDir);
                    }
                }
            } catch (IOException ioException) {
                System.out.println("Exception is: " + ioException);
            } catch(ZipException ze){
                System.out.println("Exception is: " + ze);
            }
        }
    }

    public static void findPdfAndConvertToHtml(File attachmentDir) {
        String fileName;
        for (File file : attachmentDir.listFiles()) {
            try {
                if (file.isDirectory()) {
                    System.out.println("Directory Name: " + file.getCanonicalPath());
                    findPdfAndConvertToHtml(file);
                } else {
                    fileName = file.getCanonicalPath();
                    if (fileName.toLowerCase().endsWith(".pdf")) {
                        System.out.println("FileName: " + fileName);
                        convertPdfToHtml(fileName);
                    }
                }
            } catch (IOException ioException) {
                System.out.println("Replace with a logger:" + ioException);
            }
        }
    }

    private static void convertPdfToHtml(String fileName) {

        ProcessBuilder pb = new ProcessBuilder("java", "-cp", "lib/PDFToHTML.jar", "org.fit.pdfdom.PDFToHTML", fileName);
        // File commands = new
        // File("C:\\Users\\Sriram\\Downloads\\BASSETT.pdf");
        // File dirOut = new
        // File("C:\\Users\\Sriram\\Downloads\\BASSETT-2.html");
        File dirErr = new File(ERROR_DIR + File.separator + "Error.txt");
        pb.redirectError(dirErr);
        try {
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
