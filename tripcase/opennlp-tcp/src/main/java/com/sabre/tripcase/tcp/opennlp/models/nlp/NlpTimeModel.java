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
 * Class: NlpTimeModel
 * 
 * @author Nalini Kanta
 *
 */
public class NlpTimeModel {

	/**
	 * getTimeModel()
	 * 
	 * @return NameFinderME
	 * @throws Throwable
	 */
	public static NameFinderME getTimeModel() throws Throwable {

		InputStream inputStream = null;
		NameFinderME timeME = null;

		try {
			inputStream = new FileInputStream("src/main/java/en-ner-time.bin");
			TokenNameFinderModel model = new TokenNameFinderModel(inputStream);
			timeME = new NameFinderME(model);
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

		return timeME;
	}
}
