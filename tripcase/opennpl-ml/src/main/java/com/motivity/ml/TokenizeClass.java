package com.motivity.ml;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

public class TokenizeClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Tokenize();
		
	}
	
	public static String[] Tokenize(String sentence , String[] sentenceArray){
		String tokens[] = null;
		
		try{
		InputStream is = new FileInputStream("D:\\projects\\TripCase_Project\\Technical\\OpenNLPProject\\models\\en-token.bin");
	 
		TokenizerModel model = new TokenizerModel(is);
	 
		Tokenizer tokenizer = new TokenizerME(model);
	 
		 tokens = tokenizer.tokenize(sentence);
		 String tokStr = "";
		 for(String tok : tokens)
		 {
			 tokStr += tok + " ";
			 
		 }
		// System.out.println(tokStr); //print the all tokens in single line which is being used to train the model.
		 NameFinderClass nameFinderClass = new NameFinderClass();
		 
		// System.out.println("Tokens : " + tokens);
		 
		 /*for(int i=0;i<tokens.length;i++){
			 if(tokens[i].equalsIgnoreCase("Hotel, Name")){
				 System.out.println("Hotel Name From email input..");
			 }
		 }*/
		 nameFinderClass.findName(tokens , sentenceArray);
		 
		/*for (String token : tokens){
			System.out.println(token);
			nameFinderClass.findName(token);
			
		}*/
		is.close();
		}catch(InvalidFormatException ife){
			ife.printStackTrace();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		return tokens;
	}
}
