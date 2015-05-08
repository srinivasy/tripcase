/**
 * 
 */
package com.sabre.tripcase.tcp.opennlp.models.nlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.sabre.tripcase.tcp.opennlp.constants.ModelTypes;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;

/**
 * Class: NlpSentenceModel
 * 
 * @author Nalini Kanta
 *
 */
public class NlpSentenceModel {

	/**
	 * getSentenceModel()
	 * 
	 * @return SentenceDetectorME
	 * @throws Throwable
	 */
	public SentenceDetectorME getSentenceModel() throws Throwable {
		InputStream inputStream = null;
		SentenceDetectorME sentenceDetectorME = null;

		try {
			inputStream = new FileInputStream(ModelTypes.NLP_FILE_BASE_LOCATION+"/en-sent.bin");
			SentenceModel model = new SentenceModel(inputStream);
			sentenceDetectorME = new SentenceDetectorME(model);

		} catch (InvalidFormatException ife) {
			throw ife;
		} catch (IOException io) {
			throw io;

		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {

				throw e;
			}
		}

		return sentenceDetectorME;
	}

}
