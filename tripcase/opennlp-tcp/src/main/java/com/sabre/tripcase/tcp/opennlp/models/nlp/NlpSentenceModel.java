/**
 * 
 */
package com.sabre.tripcase.tcp.opennlp.models.nlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import opennlp.tools.util.InvalidFormatException;

/**
 * @author Nalini Kanta
 *
 */
public class NlpSentenceModel {
	
	
	private String modelPath="C:/Users/CB34388493/opennlp/models/en-sent.bin";
	
	/**
	 * getSentenceModel()
	 * @return the sentenceDetectorME
	 */
	public SentenceDetectorME getSentenceModel() {
		InputStream inputStream=null;
		SentenceDetectorME sentenceDetectorME=null;
		
			try{
			inputStream = new FileInputStream(modelPath);
			SentenceModel model = new SentenceModel(inputStream);
			sentenceDetectorME = new SentenceDetectorME(model);
			
		}catch(InvalidFormatException ife){
			ife.printStackTrace();
		}catch(IOException io){
			io.printStackTrace();
		
		}
			finally{
				try {
					inputStream.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		
		return sentenceDetectorME;
	}
	
	

}
