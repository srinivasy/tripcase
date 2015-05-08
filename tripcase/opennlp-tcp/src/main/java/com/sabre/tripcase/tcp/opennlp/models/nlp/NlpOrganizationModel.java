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
 * Class: NlpOrganizationModel
 * 
 * @author Nalini Kanta
 *
 */
public class NlpOrganizationModel {

	/**
	 * getOrganizationModel()
	 * 
	 * @return NameFinderME
	 * @throws Throwable
	 */
	public static NameFinderME getOrganizationModel() throws Throwable {

		InputStream inputStream = null;
		NameFinderME organizationME = null;

		try {
			inputStream = new FileInputStream(ModelTypes.NLP_FILE_BASE_LOCATION+"/en-ner-organization.bin");
			TokenNameFinderModel model = new TokenNameFinderModel(inputStream);
			organizationME = new NameFinderME(model);
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

		return organizationME;
	}

}
