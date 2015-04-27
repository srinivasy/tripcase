/**
 * 
 */
package com.sabre.tripcase.tcp.opennlp.models.nlp;

import java.io.File;

import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

/**
 * @author Nalini Kanta
 *
 */
public class NlpPOSTaggerModel {
	
	/**
	 * getPosTaggerModel()
	 * @return the posTaggerME
	 */
	public static POSTaggerME getPosTaggerModel() {
		POSTaggerME posTaggerME=null;
				try{
			POSModel model = new POSModelLoader().load(new File("C:/Users/CB34388493/opennlp/models/en-pos-maxent.bin"));
			posTaggerME = new POSTaggerME(model);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			
		
		return posTaggerME;
	}

}
