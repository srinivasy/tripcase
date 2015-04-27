package com.sabre.tripcase.tcp.opennlp.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

public final class NlpModels {
	
	private static SentenceDetectorME sentenceDetectorME=null;
	private static Tokenizer tokenizer=null;
	private static NameFinderME nameFinderPersonME=null;
	private static NameFinderME nameFinderDateME=null;
	private static NameFinderME nameFinderTimeME=null;
	private static NameFinderME nameFinderLocationME=null;
	private static NameFinderME nameFinderMoneyME=null;
	private static NameFinderME nameFinderOrganizationME=null;
	private static NameFinderME nameFinderPercentageME=null;
	private static POSTaggerME posTaggerME=null;
	private static ChunkerME chunkerME=null;
	



	
	


}
