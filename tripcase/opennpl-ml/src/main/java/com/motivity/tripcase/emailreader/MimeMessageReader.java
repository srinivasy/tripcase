package com.motivity.tripcase.emailreader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;


public class MimeMessageReader {
	private static MimeMessage mimeMessage;
	public static MimeMessage getMimeMessage(File file){
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
