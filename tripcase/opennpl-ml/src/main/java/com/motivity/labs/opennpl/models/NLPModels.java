package com.motivity.labs.opennpl.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.SimpleTokenizer;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

public final class NLPModels {
	private static SentenceDetectorME sentenceDetectorME=null;
	private static Tokenizer tokenizer=null;
	private static NameFinderME nameFinderPersonME=null;
	private static NameFinderME nameFinderDateME=null;
	private static NameFinderME nameFinderTimeME=null;
	private static NameFinderME nameFinderLocationME=null;
	private static NameFinderME nameFinderMoneyME=null;
	private static NameFinderME nameFinderOrganizationME=null;
	private static NameFinderME nameFinderPercentageME=null;
	private static NameFinderME nameFinderDepartME=null;
	private static HashMap<String,NameFinderME> customModelMap=null;
	private static POSTaggerME posTaggerME=null;
	private static ChunkerME chunkerME=null;
	
	private NLPModels(){}
	/**
	 * @return the sentenceDetectorME
	 */
	public static SentenceDetectorME getSentenceDetectorME() {
		InputStream is=null;
		if(null==sentenceDetectorME){
			try{
			is = new FileInputStream("C:/Users/CB34388493/opennlp/models/en-sent.bin");
			SentenceModel model = new SentenceModel(is);
			sentenceDetectorME = new SentenceDetectorME(model);
			
		}catch(InvalidFormatException ife){
			ife.printStackTrace();
		}catch(IOException io){
			io.printStackTrace();
		
		}
			finally{
				try {
					is.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
		return sentenceDetectorME;
	}
	/**
	 * @return the tokenizer
	 */
	public static Tokenizer getTokenizer() {
		InputStream is=null;
		if(null==tokenizer){
			try{
			is = new FileInputStream("C:/Users/CB34388493/opennlp/models/en-token.bin");
		 	TokenizerModel model = new TokenizerModel(is);
		 	//tokenizer=SimpleTokenizer.INSTANCE;
		 	tokenizer = new TokenizerME(model);
		 	//tokenizer.tokenizePos("|");
		 	
			
		}catch(InvalidFormatException ife){
			ife.printStackTrace();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
			finally{
				 try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return tokenizer;
	}
	/**
	 * @return the nameFinderME
	 */
	public static NameFinderME getNameFinderPersonME() {
		InputStream is=null;
		if(null==nameFinderPersonME){
			try{
				is = new FileInputStream("C:/Users/CB34388493/opennlp/models/en-ner-person.bin");
				TokenNameFinderModel model = new TokenNameFinderModel(is); 
				nameFinderPersonME = new NameFinderME(model);
				
			}
			catch(InvalidFormatException ife){
				ife.printStackTrace();
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
				finally{
					 try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			
			
		}
		return nameFinderPersonME;
	}
	/**
	 * @return the posTaggerME
	 */
	public static POSTaggerME getPosTaggerME() {
		
		if(null==posTaggerME){
			try{
			POSModel model = new POSModelLoader().load(new File("C:/Users/CB34388493/opennlp/models/en-pos-maxent.bin"));
			//PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
			posTaggerME = new POSTaggerME(model);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
			
		}
		return posTaggerME;
	}
	/**
	 * @return the chunkerME
	 */
	public static ChunkerME getChunkerME() {
		InputStream is=null;
		if(null==chunkerME){
			try{
			is = new FileInputStream("C:/Users/CB34388493/opennlp/models/en-chunker.bin");
			ChunkerModel cModel = new ChunkerModel(is);
		 	chunkerME = new ChunkerME(cModel);
		}
		catch(InvalidFormatException ife){
			ife.printStackTrace();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
			finally{
				 try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
	}
		return chunkerME;
	}
	/**
	 * @return the nameFinderDateME
	 */
	public static NameFinderME getNameFinderDateME() {

		InputStream is=null;
		if(null==nameFinderDateME){
			try{
				is = new FileInputStream("C:/Users/CB34388493/opennlp/models/en-ner-date.bin");
				TokenNameFinderModel model = new TokenNameFinderModel(is); 
				nameFinderDateME = new NameFinderME(model);
			}
			catch(InvalidFormatException ife){
				ife.printStackTrace();
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
				finally{
					 try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			
			
		}
		
	
		return nameFinderDateME;
	}
	/**
	 * @return the nameFinderTimeME
	 */
	public static NameFinderME getNameFinderTimeME() {

		InputStream is=null;
		if(null==nameFinderTimeME){
			try{
				is = new FileInputStream("C:/Users/CB34388493/opennlp/models/en-ner-time.bin");
				TokenNameFinderModel model = new TokenNameFinderModel(is); 
				nameFinderTimeME = new NameFinderME(model);
			}
			catch(InvalidFormatException ife){
				ife.printStackTrace();
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
				finally{
					 try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			
			
		}
			
		return nameFinderTimeME;
	}
	/**
	 * @return the nameFinderLocationME
	 */
	public static NameFinderME getNameFinderLocationME() {

		InputStream is=null;
		if(null==nameFinderLocationME){
			try{
				is = new FileInputStream("C:/Users/CB34388493/opennlp/models/en-ner-location.bin");
				TokenNameFinderModel model = new TokenNameFinderModel(is); 
				nameFinderLocationME = new NameFinderME(model);
			}
			catch(InvalidFormatException ife){
				ife.printStackTrace();
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
				finally{
					 try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			
			
		}
			
		return nameFinderLocationME;
	}
	/**
	 * @return the nameFinderMoneyME
	 */
	public static NameFinderME getNameFinderMoneyME() {

		InputStream is=null;
		if(null==nameFinderMoneyME){
			try{
				is = new FileInputStream("C:/Users/CB34388493/opennlp/models/en-ner-money.bin");
				TokenNameFinderModel model = new TokenNameFinderModel(is); 
				nameFinderMoneyME = new NameFinderME(model);
			}
			catch(InvalidFormatException ife){
				ife.printStackTrace();
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
				finally{
					 try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			
			
		}
		
		return nameFinderMoneyME;
	}
	/**
	 * @return the nameFinderOrganizationME
	 */
	public static NameFinderME getNameFinderOrganizationME() {

		InputStream is=null;
		if(null==nameFinderOrganizationME){
			try{
				is = new FileInputStream("C:/Users/CB34388493/opennlp/models/en-ner-organization.bin");
				TokenNameFinderModel model = new TokenNameFinderModel(is); 
				nameFinderOrganizationME = new NameFinderME(model);
			}
			catch(InvalidFormatException ife){
				ife.printStackTrace();
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
				finally{
					 try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			
			
		}

		return nameFinderOrganizationME;
	}
	/**
	 * @return the nameFinderPercentageME
	 */
	public static NameFinderME getNameFinderPercentageME() {

		InputStream is=null;
		if(null==nameFinderPercentageME){
			try{
				is = new FileInputStream("C:/Users/CB34388493/opennlp/models/en-ner-percentage.bin");
				TokenNameFinderModel model = new TokenNameFinderModel(is); 
				nameFinderPercentageME = new NameFinderME(model);
			}
			catch(InvalidFormatException ife){
				ife.printStackTrace();
			}catch(IOException ioe){
				ioe.printStackTrace();
			}
				finally{
					 try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			
			
		}
	
		return nameFinderPercentageME;
	}
	
	
	public static NameFinderME getCustomTrainedModel(String modelType,String loadModelFile){
		
		NameFinderME finderME=null;
		if(null==customModelMap){
			customModelMap = new HashMap<String,NameFinderME> ();
			finderME=loadCustomModel(loadModelFile);
			customModelMap.put(modelType,finderME);
		}
		else{
			if(customModelMap.containsKey(modelType)){
				finderME=customModelMap.get(modelType);
			}
			else{
				finderME=loadCustomModel(loadModelFile);
				customModelMap.put(modelType,finderME);
			}

		}
	
		return finderME;
	}
	
	private static NameFinderME loadCustomModel(String loadModelFile){
		InputStream is=null;
		NameFinderME finderME=null;
		try{
			is = new FileInputStream(loadModelFile+".bin");
			TokenNameFinderModel model = new TokenNameFinderModel(is); 
			finderME = new NameFinderME(model);
			
		}
		catch(InvalidFormatException ife){
			ife.printStackTrace();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
			finally{
				 try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		return finderME;
	}
	

}
