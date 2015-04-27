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
public class NlpPersonModel {
	
	/**
	 * getPersonModel()
	 * @return the nameFinderPersonME
	 */
	public static NameFinderME getPersonModel() {
		InputStream inputStream=null;
		NameFinderME	nameFinderPersonME=null;
				try{
				inputStream = new FileInputStream("C:/Users/CB34388493/opennlp/models/en-ner-person.bin");
				TokenNameFinderModel model = new TokenNameFinderModel(inputStream); 
				nameFinderPersonME = new NameFinderME(model);
				
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
		return nameFinderPersonME;
	}

}
