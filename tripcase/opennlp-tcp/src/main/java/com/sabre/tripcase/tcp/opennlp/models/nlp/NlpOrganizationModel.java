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
public class NlpOrganizationModel {
	
	/**
	 * getOrganizationModel()
	 * @return the organizationME
	 */
	public static NameFinderME getOrganizationModel() {

		InputStream inputStream=null;
		NameFinderME organizationME=null;
		
			try{
				inputStream = new FileInputStream("C:/Users/CB34388493/opennlp/models/en-ner-organization.bin");
				TokenNameFinderModel model = new TokenNameFinderModel(inputStream); 
				organizationME = new NameFinderME(model);
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

		return organizationME;
	}


}
