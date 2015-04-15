package com.motivity.labs.opennpl.models;

public final class NLPHelper {
	
	public static String[] sentenceDetect(String content) {
		String sentences[] = null;
		sentences=NLPModels.getSentenceDetectorME().sentDetect(content);
		return sentences;
	}
	
	public static void printSentences(String[] sentences){
		for(int i=0;i<sentences.length;i++){
			System.out.println("sentence::: " + sentences[i]);
			System.out.println();
			
		 }
	}
	
	public static String[] Tokenize(String sentence){
		String tokens[] = null;
		tokens = NLPModels.getTokenizer().tokenize(sentence);
		return tokens;
	}
	
	public static void printTokens(String[] tokens){
		for(int i=0;i<tokens.length;i++){
			System.out.println("token::: " + tokens[i]);
			System.out.println();
			
		 }
	}
	


}
