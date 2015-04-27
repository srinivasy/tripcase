/**
 * 
 */
package com.sabre.tripcase.tcp.opennlp.models.nlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.InvalidFormatException;

/**
 * Class: NlpPersonModel
 * 
 * @author Nalini Kanta
 *
 */
public class NlpPersonModel {

	/**
	 * getPersonModel()
	 * 
	 * @return NameFinderME
	 * @throws Throwable
	 */
	public static NameFinderME getPersonModel() throws Throwable {
		InputStream inputStream = null;
		NameFinderME nameFinderPersonME = null;
		try {
			inputStream = new FileInputStream("src/main/java/en-ner-person.bin");
			TokenNameFinderModel model = new TokenNameFinderModel(inputStream);
			nameFinderPersonME = new NameFinderME(model);

		} catch (InvalidFormatException ife) {
			throw ife;
		} catch (IOException ioe) {
			throw ioe;
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				throw e;
			}
		}
		return nameFinderPersonME;
	}

}
