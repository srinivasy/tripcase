package com.motivity.labs.opennpl.models;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;

public class ModelCreation {
	
	public static void createDepartModel() throws IOException{
		
						      
	      Charset charset = Charset.forName("UTF-8");
	      ObjectStream<String> lineStream =	new PlainTextByLineStream(new FileInputStream("C:/Users/CB34388493/opennlp/models/en-depart.train"), charset);
	      ObjectStream<NameSample> sampleStream = new NameSampleDataStream(lineStream);

	      TokenNameFinderModel model;
	      BufferedOutputStream modelOut=null;
	      try {
      
	     //   model = NameFinderME.train("en", "depart", sampleStream, null);
	        model = NameFinderME.train("en","depart",sampleStream,(AdaptiveFeatureGenerator)null,Collections.<String,Object>emptyMap(),70,1);
	       
	      }
	      finally {
	        sampleStream.close();
	      }

	     
		try {
	        modelOut = new BufferedOutputStream(new FileOutputStream("C:/Users/CB34388493/opennlp/models/en-depart.bin"));
	        model.serialize(modelOut);
	      } finally {
	        if (modelOut != null) 
	        	modelOut.close();      
	      }
	      
	}

}
