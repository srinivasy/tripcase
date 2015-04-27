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
 * Class: NlpChunkerModel
 * @author Nalini Kanta
 *
 */
public class NlpChunkerModel {
	
/**
 * getChunkerModel()
 * @return ChunkerME
 * @throws Throwable
 */
	public static ChunkerME getChunkerModel() throws Throwable{
		InputStream inputStream=null;
		ChunkerME chunkerME=null;
			try{
			inputStream = new FileInputStream("src/main/java/en-chunker.bin");
			ChunkerModel cModel = new ChunkerModel(inputStream);
		 	chunkerME = new ChunkerME(cModel);
		}
		catch(InvalidFormatException ife){
			throw ife;
		}catch(IOException ioe){
			throw ioe;
		}
			finally{
				 try {
					 inputStream.close();
				} catch (IOException e) {
					throw e;
				}
			}
		
		return chunkerME;
	}

}
