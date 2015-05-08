/**
 * 
 */
package com.sabre.tripcase.tcp.opennlp.models.nlp;

import java.io.File;

import com.sabre.tripcase.tcp.opennlp.constants.ModelTypes;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

/**
 * Class: NlpPOSTaggerModel
 * 
 * @author Nalini Kanta
 *
 */
public class NlpPOSTaggerModel {

	/**
	 * getPosTaggerModel()
	 * 
	 * @return POSTaggerME
	 * @throws Throwable
	 */
	public static POSTaggerME getPosTaggerModel() throws Throwable {
		POSTaggerME posTaggerME = null;
		try {
			POSModel model = new POSModelLoader().load(new File(
					ModelTypes.NLP_FILE_BASE_LOCATION+"/en-pos-maxent.bin"));
			posTaggerME = new POSTaggerME(model);
		} catch (Exception ex) {
			throw ex;
		}

		return posTaggerME;
	}

}
