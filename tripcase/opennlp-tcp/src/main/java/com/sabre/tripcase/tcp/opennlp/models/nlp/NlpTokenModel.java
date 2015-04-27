/**
 * 
 */
package com.sabre.tripcase.tcp.opennlp.models.nlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

/**
 * Class: NlpTokenModel
 * 
 * @author Nalini Kanta
 *
 */
public class NlpTokenModel {

	/**
	 * getTokenModel()
	 * 
	 * @return Tokenizer
	 * @throws Throwable
	 */
	public Tokenizer getTokenModel() throws Throwable {
		InputStream inputStream = null;
		Tokenizer tokenizer = null;
		if (null == tokenizer) {
			try {
				inputStream = new FileInputStream("src/main/java/en-token.bin");
				TokenizerModel model = new TokenizerModel(inputStream);
				tokenizer = new TokenizerME(model);

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
		}
		return tokenizer;
	}

}
