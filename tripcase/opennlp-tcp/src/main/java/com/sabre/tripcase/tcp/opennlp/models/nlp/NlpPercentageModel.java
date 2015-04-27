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
 * @author Nalini Kanta
 *
 */
public class NlpPercentageModel {
	
	/**
	 * getPercentageModel()
	 * @return the percentageME
	 */
	public static NameFinderME getPercentageModel() {

		InputStream inputStream=null;
		NameFinderME percentageME=null;

			try{
				inputStream = new FileInputStream("src/main/java/en-ner-percentage.bin");
				TokenNameFinderModel model = new TokenNameFinderModel(inputStream); 
				percentageME = new NameFinderME(model);
			}
			catch(InvalidFormatException ife){
				ife.printStackTrace();
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
				finally{
					 try {
						 inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
		}
	
		return percentageME;
	}
	


}
