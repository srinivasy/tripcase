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
public class NlpMoneyModel {
	
	/**
	 * @return the nameFinderMoneyME
	 */
	public static NameFinderME getMoneyModel() {

		InputStream inputStream=null;
		NameFinderME moneyME=null;

			try{
				inputStream = new FileInputStream("C:/Users/CB34388493/opennlp/models/en-ner-money.bin");
				TokenNameFinderModel model = new TokenNameFinderModel(inputStream); 
				moneyME = new NameFinderME(model);
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
		
		return moneyME;
	}


}
