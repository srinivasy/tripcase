package com.motivity.ml;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSample;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;
import opennlp.tools.util.featuregen.AdaptiveFeatureGenerator;
import opennlp.tools.util.featuregen.BigramNameFeatureGenerator;
import opennlp.tools.util.featuregen.CachedFeatureGenerator;
import opennlp.tools.util.featuregen.OutcomePriorFeatureGenerator;
import opennlp.tools.util.featuregen.PreviousMapFeatureGenerator;
import opennlp.tools.util.featuregen.SentenceFeatureGenerator;
import opennlp.tools.util.featuregen.TokenClassFeatureGenerator;
import opennlp.tools.util.featuregen.TokenFeatureGenerator;
import opennlp.tools.util.featuregen.WindowFeatureGenerator;

public class PersonModelTrain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Charset charset = Charset.forName("UTF-8");
		

		TokenNameFinderModel model = null;
		BufferedOutputStream modelOut = null;
		ObjectStream<NameSample> sampleStream = null;
		AdaptiveFeatureGenerator featureGenerator = null;
		try {
		  /*model = NameFinderME.train(
				  "en", 
				  "person", 
				   sampleStream,
				   TrainingParameters.defaultParams(),
		           null,
		            Collections.<String, Object>emptyMap());*/
			featureGenerator = new CachedFeatureGenerator(
								new AdaptiveFeatureGenerator[]{
										new WindowFeatureGenerator(new TokenFeatureGenerator(), 2, 2),
										new WindowFeatureGenerator(new TokenClassFeatureGenerator(true), 2, 2),
										new OutcomePriorFeatureGenerator(),
										new PreviousMapFeatureGenerator(),
										new BigramNameFeatureGenerator(),
										new SentenceFeatureGenerator(true, false)
									});
			
			ObjectStream<String> lineStream =
					new PlainTextByLineStream(new FileInputStream("C:/Users/CB34388493/opennlp/models/train_data.txt"), charset);
			
			 sampleStream = new NameSampleDataStream(lineStream);			
			model =  NameFinderME.train("en", "person", sampleStream,TrainingParameters.defaultParams(), featureGenerator, Collections.<String, Object> emptyMap());
			 
			 // model = NameFinderME.train("en", "person", sampleStream, Collections.<String, Object>emptyMap(), 1, 0);
			 
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		finally {
		  try {
			sampleStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

		try {
		  try {
			modelOut = new BufferedOutputStream(new FileOutputStream("D:\\projects\\TripCase_Project\\Technical\\email_templates\\OpenNLP\\Airline\\train_data_model.bin"));
			model.serialize(modelOut);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		  
		} finally {
		  if (modelOut != null)
			try {
				modelOut.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}      
		}
	}

	
}
