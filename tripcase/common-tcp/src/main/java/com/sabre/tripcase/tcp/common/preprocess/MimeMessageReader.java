package com.sabre.tripcase.tcp.common.preprocess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;


public class MimeMessageReader {

	private static Logger log =Logger.getLogger(MimeMessageReader.class);
	public  MimeMessage getMimeMessage(File file){
		log.info("******** START ************");
		
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
			log.info("******** END ************");
		return mimeMessage;
	}
	
	


}
