package com.motivity.ml;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;

public class SentenceDetectClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String paragraph = "Hi. How are you? This is Mike.";
		//SentenceDetect(String paragraph);
	}

	public static String[] SentenceDetect(String paragraph) 
	 {
		String sentences[] = null;
		
		try{
			
		// always start with a model, a model is learned from training data
		InputStream is = new FileInputStream("D:\\projects\\TripCase_Project\\Technical\\OpenNLPProject\\models\\en-sent.bin");
		SentenceModel model = new SentenceModel(is);
		SentenceDetectorME sdetector = new SentenceDetectorME(model);
		//System.out.println("paragraph:::: " + paragraph);
			
		 sentences = sdetector.sentDetect(paragraph);
		 TokenizeClass tokenClass = new TokenizeClass();
		 
		 for(int i=0;i<sentences.length;i++){
			// System.out.println("sentence::: " + sentences[i]);
			 tokenClass.Tokenize(sentences[i],sentences);
		 }
		
		//System.out.println("Sentence0 :::" + sentences[0]);
		//System.out.println("Sentence1 :::" +  sentences[1]);
		is.close();
		}catch(InvalidFormatException ife){
			ife.printStackTrace();
		}catch(IOException io){
			io.printStackTrace();
		}
		return sentences;
}
}
