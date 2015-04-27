package com.sabre.tripcase.tcp.opennlp.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;


public class MimeMessageReader {

	public  MimeMessage getMimeMessage(File file){
		MimeMessage mimeMessage=null;
			try{
			Properties p = System.getProperties();
			Session session = Session.getInstance(p);
			mimeMessage = new MimeMessage(session, new FileInputStream(file.getPath()));
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return mimeMessage;
	}
	
	


}
