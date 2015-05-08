/**
 * 
 */
package com.sabre.tripcase.tcp.opennlp.models.nlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.sabre.tripcase.tcp.opennlp.constants.ModelTypes;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.InvalidFormatException;

/**
 * Class: NlpDateModel
 * 
 * @author Nalini Kanta
 *
 */
public class NlpDateModel {

	/**
	 * getDateModel()
	 * 
	 * @return NameFinderME
	 * @throws Throwable
	 */
	public static NameFinderME getDateModel() throws Throwable {

		InputStream inputStream = null;
		NameFinderME dateME = null;

		try {
			inputStream = new FileInputStream(ModelTypes.NLP_FILE_BASE_LOCATION+"/en-ner-date.bin");
			TokenNameFinderModel model = new TokenNameFinderModel(inputStream);
			dateME = new NameFinderME(model);
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

		return dateME;
	}

}
