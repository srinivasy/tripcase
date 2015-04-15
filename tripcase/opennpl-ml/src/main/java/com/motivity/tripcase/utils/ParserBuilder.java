package com.motivity.tripcase.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import com.motivity.tripcase.emailreader.EmailReader;
import com.motivity.tripcase.pojo.EMail;
import com.motivity.tripcase.pojo.Parser;

public class ParserBuilder {

	
	public Parser buildParser(File file){
		Parser parser = null;
		try{
			Properties p = System.getProperties();
			Session session = Session.getInstance(p);

			MimeMessage mimeMessage;
			EMail email = null;

			mimeMessage = new MimeMessage(session, new FileInputStream(file.getPath()));
			email = new EmailReader().convertToString(mimeMessage);
			
			//System.out.println("Email Data String Format ::: " + email.getBody().toString());
			parser = new Parser();
			parser.setEmailFile(file.getName());
			parser.setEmail(email);
			String rulesName = file.getName().replace("eml", "txt");
			//parser.setRulesFile(Config.getRulesFileName(rulesName));
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return parser;
	}
	
	
}
