package com.sabre.tripcase.tcp.opennlp.models;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;

import com.sabre.tripcase.tcp.opennlp.constants.ModelTypes;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;

public class TrainedModels {
	
	private static HashMap<String,NameFinderME> customModelMap=null;
	
	public void createModel(String modelType,String trainFile) throws IOException{
	      Charset charset = Charset.forName("UTF-8");
	      System.out.println("File path:"+trainFile);
	      ObjectStream<String> lineStream =	new PlainTextByLineStream(new FileInputStream(ModelTypes.TRAIN_FILE_BASE_LOCATION+trainFile+".train"), charset);
	      ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream);

	      TokenNameFinderModel model;

	      BufferedOutputStream modelOut=null;
	      try {
	    	  
	    	  Collections.<String,Object>emptyMap();
      
	   /** model = NameFinderME.train("en", modelType, sampleStream, null); */
	 model = NameFinderME.train("en",modelType,sampleStream,(AdaptiveFeatureGenerator)null,Collections.<String,Object>emptyMap(),100,1);
	  /**  model= NameFinderME.train("en", modelType, sampleStream, Collections.<String,Object>emptyMap(), 70, 1); */
	       
	      }
	      finally {
	        sampleStream.close();
	      }

	     
		try {
	        modelOut = new BufferedOutputStream(new FileOutputStream(ModelTypes.BIN_FILE_BASE_LOCATION+trainFile+".bin"));
	        model.serialize(modelOut);
	      } finally {
	        if (modelOut != null) 
	        	modelOut.close();      
	      }
	      
	}
	
	private  NameFinderME loadCustomModel(String loadModelFile){
		InputStream is=null;
		NameFinderME finderME=null;
		try{
			is = new FileInputStream(ModelTypes.BIN_FILE_BASE_LOCATION+loadModelFile+".bin");
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
	
public  NameFinderME getCustomTrainedModel(String modelType,String loadModelFile){
		
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
	
}
