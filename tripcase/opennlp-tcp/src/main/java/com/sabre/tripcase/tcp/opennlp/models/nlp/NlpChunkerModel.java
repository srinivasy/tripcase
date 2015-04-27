/**
 * 
 */
package com.sabre.tripcase.tcp.opennlp.models.nlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.util.InvalidFormatException;

/**
 * @author Nalini Kanta
 *
 */
public class NlpChunkerModel {
	
	/**
	 * getChunkerModel()
	 * @return the chunkerME
	 */
	public static ChunkerME getChunkerModel() {
		InputStream inputStream=null;
		ChunkerME chunkerME=null;
			try{
			inputStream = new FileInputStream("C:/Users/CB34388493/opennlp/models/en-chunker.bin");
			ChunkerModel cModel = new ChunkerModel(inputStream);
		 	chunkerME = new ChunkerME(cModel);
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
		
		return chunkerME;
	}

}
