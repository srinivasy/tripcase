/**
 * 
 */
package com.sabre.tripcase.tcp.opennlp.models;

import java.io.IOException;

import opennlp.tools.namefind.NameFinderME;

import org.springframework.beans.factory.annotation.Autowired;

import com.sabre.tripcase.tcp.opennlp.constants.ModelTypes;
import com.sabre.tripcase.tcp.opennlp.models.TrainedModels;

/**
 * @author Nalini Kanta
 *
 */
public class Trainer {
	
	@Autowired
	TrainedModels trainedModels=null;
	
	public void train() throws IOException{
		
		trainedModels.createModel(ModelTypes.MIXED_MODEL,ModelTypes.MIXED_MODEL_FILE);
		trainedModels.createModel(ModelTypes.SPLIT_MODEL,ModelTypes.SPLIT_MODEL_FILE);
		trainedModels.createModel(ModelTypes.DEPART_MODEL,ModelTypes.DEPART_MODEL_FILE);
		trainedModels.createModel(ModelTypes.DEPART_TIME_MODEL,ModelTypes.DEPART_TIME_MODEL_FILE);
		trainedModels.createModel(ModelTypes.DEPART_DATE_MODEL,ModelTypes.DEPART_DATE_MODEL_FILE);
		trainedModels.createModel(ModelTypes.BOOKING_REF_MODEL,ModelTypes.BOOKING_REF_MODEL_FILE);
		trainedModels.createModel(ModelTypes.ARRIVE_MODEL,ModelTypes.ARRIVE_MODEL_FILE);
		trainedModels.createModel(ModelTypes.ARRIVE_DATE_MODEL,ModelTypes.ARRIVE_DATE_MODEL_FILE);
		trainedModels.createModel(ModelTypes.ARRIVE_TIME_MODEL,ModelTypes.ARRIVE_TIME_MODEL_FILE);
		trainedModels.createModel(ModelTypes.FLIGHT_MODEL,ModelTypes.FLIGHT_MODEL_FILE);
		trainedModels.createModel(ModelTypes.SENTENCE_MODEL,ModelTypes.SENTENCE_MODEL_FILE);
		
	}
	
	public  NameFinderME loadTrainedModels(String modelName)throws IOException{
		NameFinderME modelME=null;
		
		if(ModelTypes.MIXED_MODEL.equals(modelName)){
			modelME=trainedModels.getCustomTrainedModel(ModelTypes.MIXED_MODEL,ModelTypes.MIXED_MODEL_FILE);
		}
		else if(ModelTypes.DEPART_MODEL.equals(modelName)){
			modelME=trainedModels.getCustomTrainedModel(ModelTypes.DEPART_MODEL,ModelTypes.DEPART_MODEL_FILE);
		}
		else if(ModelTypes.DEPART_TIME_MODEL.equals(modelName)){
			modelME=trainedModels.getCustomTrainedModel(ModelTypes.DEPART_TIME_MODEL,ModelTypes.DEPART_TIME_MODEL_FILE);
		}
		else if(ModelTypes.DEPART_DATE_MODEL.equals(modelName)){
			modelME=trainedModels.getCustomTrainedModel(ModelTypes.DEPART_DATE_MODEL,ModelTypes.DEPART_DATE_MODEL_FILE);
		}
		else if(ModelTypes.BOOKING_REF_MODEL.equals(modelName)){
			modelME=trainedModels.getCustomTrainedModel(ModelTypes.BOOKING_REF_MODEL,ModelTypes.BOOKING_REF_MODEL_FILE);
		}
		else if(ModelTypes.ARRIVE_MODEL.equals(modelName)){
			modelME=trainedModels.getCustomTrainedModel(ModelTypes.ARRIVE_MODEL,ModelTypes.ARRIVE_MODEL_FILE);
		}
		else if(ModelTypes.ARRIVE_DATE_MODEL.equals(modelName)){
			modelME=trainedModels.getCustomTrainedModel(ModelTypes.ARRIVE_DATE_MODEL,ModelTypes.ARRIVE_DATE_MODEL_FILE);
		}
		else if(ModelTypes.ARRIVE_TIME_MODEL.equals(modelName)){
			modelME=trainedModels.getCustomTrainedModel(ModelTypes.ARRIVE_TIME_MODEL,ModelTypes.ARRIVE_TIME_MODEL_FILE);
		}
		else if(ModelTypes.FLIGHT_MODEL.equals(modelName)){
			modelME=trainedModels.getCustomTrainedModel(ModelTypes.FLIGHT_MODEL,ModelTypes.FLIGHT_MODEL_FILE);
		}
		else if(ModelTypes.SENTENCE_MODEL.equals(modelName)){
			modelME=trainedModels.getCustomTrainedModel(ModelTypes.FLIGHT_MODEL,ModelTypes.SENTENCE_MODEL_FILE);
		}
		else if(ModelTypes.SPLIT_MODEL.equals(modelName)){
			modelME=trainedModels.getCustomTrainedModel(ModelTypes.SPLIT_MODEL,ModelTypes.SPLIT_MODEL_FILE);
		}
		return modelME;
		
	}

	/**
	 * @return the trainedModels
	 */
	public TrainedModels getTrainedModels() {
		return trainedModels;
	}

	/**
	 * @param trainedModels the trainedModels to set
	 */
	public void setTrainedModels(TrainedModels trainedModels) {
		this.trainedModels = trainedModels;
	}
	
	

}
