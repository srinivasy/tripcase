package com.sabre.tripcase.tcp.common.validation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


import org.apache.tika.exception.TikaException;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class LangIdentifier {

	/* Function overloaded to identifies the language of given text */
	public String identifyLanguage(String text) {
		LanguageIdentifier identifier = new LanguageIdentifier(text);
		String language = identifier.getLanguage();
		return language;

	}

	/* Function overloaded to identifies the language of given File */
	public String identifyLanguage(File file) {

		//Parser method parameters
		Parser parser = new AutoDetectParser();
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
		FileInputStream content;
		try {
			content = new FileInputStream(file);

			//Parsing the given document
			parser.parse(content, handler, metadata, new ParseContext());
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TikaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		LanguageIdentifier object = new LanguageIdentifier(handler.toString());
		return object.getLanguage();
	}

}
