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
 * @author Nalini Kanta
 *
 */
public class NlpTokenModel {
	private String modelPath="C:/Users/CB34388493/opennlp/models/en-token.bin";
	
	/**
	 * getTokenModel()
	 * @return the tokenizer
	 */
	public  Tokenizer getTokenModel() {
		InputStream inputStream=null;
		Tokenizer tokenizer=null;
		if(null==tokenizer){
			try{
			inputStream = new FileInputStream(modelPath);
		 	TokenizerModel model = new TokenizerModel(inputStream);
		 	tokenizer = new TokenizerME(model);
		 	//tokenizer.tokenizePos("|");
		 	
			
		}catch(InvalidFormatException ife){
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
		}
		return tokenizer;
	}

}
