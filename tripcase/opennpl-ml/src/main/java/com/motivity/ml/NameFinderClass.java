package com.motivity.ml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.NameSampleDataStream;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

public class NameFinderClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//findName();
	}
	public static void findName(String[] tokens , String[] sentenceArray) {
		
		NameSample sample = null;
		try{
		InputStream is1 = new FileInputStream("D:\\projects\\TripCase_Project\\Technical\\OpenNLPProject\\models\\en-ner-person.bin");
		/*InputStream is2 = new FileInputStream("D:\\projects\\TripCase_Project\\Technical\\OpenNLPProject\\models\\en-ner-date.bin");
		InputStream is3 = new FileInputStream("D:\\projects\\TripCase_Project\\Technical\\OpenNLPProject\\models\\en-ner-location.bin");
		InputStream is4 = new FileInputStream("D:\\projects\\TripCase_Project\\Technical\\OpenNLPProject\\models\\en-ner-money.bin");
		InputStream is5 = new FileInputStream("D:\\projects\\TripCase_Project\\Technical\\OpenNLPProject\\models\\en-ner-time.bin");
		InputStream is6 = new FileInputStream("D:\\projects\\TripCase_Project\\Technical\\OpenNLPProject\\models\\en-ner-organization.bin");
		InputStream is7 = new FileInputStream("D:\\projects\\TripCase_Project\\Technical\\OpenNLPProject\\models\\en-ner-percentage.bin");
		*/
		InputStream is8 = new FileInputStream("D:\\projects\\TripCase_Project\\Technical\\email_templates\\OpenNLP\\Airline\\train_data_model.bin");
		
		
		//load the model
		TokenNameFinderModel model1 = new TokenNameFinderModel(is1); 
		/*TokenNameFinderModel model2 = new TokenNameFinderModel(is2);
		TokenNameFinderModel model3 = new TokenNameFinderModel(is3);
		TokenNameFinderModel model4 = new TokenNameFinderModel(is4);
		TokenNameFinderModel model5 = new TokenNameFinderModel(is5);
		TokenNameFinderModel model6 = new TokenNameFinderModel(is6);
		TokenNameFinderModel model7 = new TokenNameFinderModel(is7);*/
		
		TokenNameFinderModel model8 = new TokenNameFinderModel(is8);
		
		is1.close();
		/*is2.close();
		is3.close();
		is4.close();
		is5.close();
		is6.close();
		is7.close();*/
		is8.close();
		
		//NameFinderME has been instantiated.
		NameFinderME nameFinder1 = new NameFinderME(model1);
		/*NameFinderME nameFinder2 = new NameFinderME(model2);
		NameFinderME nameFinder3 = new NameFinderME(model3);
		NameFinderME nameFinder4 = new NameFinderME(model4);
		NameFinderME nameFinder5 = new NameFinderME(model5);
		NameFinderME nameFinder6 = new NameFinderME(model6);
		NameFinderME nameFinder7 = new NameFinderME(model7);
		*/
		NameFinderME nameFinder8 = new NameFinderME(model8);
	 
		/*String []sentence = new String[]{
			    "Mike",
			    "Smith",
			    "is",
			    "a",
			    "good",
			    "person",
			    "Bill",
			    "Gates",
			    "was",
			    "the",
			    "ceo",
			    "of",
			    "Microsoft",
			    "Corp",
			    "he",
			    "stared",
			    "it",
			    "02",
			    "-",
			    "02",
			    "-",
			    "2010"
			    };*/
		//Depart Date : <START:DEPTDATE> 2015 - 09 - 12 <END> Arrival Date : <START:ARRVDATE> 2015 - 10 - 12 <END> 
		//<START:person> Mike Smith <END> is a good person <START:person> Bill Gates <END> was the ceo of Microsoft Corp
			/*for(String tok : tokens)
			{
				//System.out.println( tok.toString());
			}*/
			Span nameSpans1[] = nameFinder1.find(tokens);
			/*Span nameSpans2[] = nameFinder2.find(tokens);
			Span nameSpans3[] = nameFinder3.find(tokens);
			Span nameSpans4[] = nameFinder4.find(tokens);
			Span nameSpans5[] = nameFinder5.find(tokens);
			Span nameSpans6[] = nameFinder6.find(tokens);
			Span nameSpans7[] = nameFinder7.find(tokens);
			*/
			Span nameSpans8[] = nameFinder8.find(tokens);
			
	 
			for(Span s1: nameSpans1){
				//System.out.println("s1:::: " + s1.toString());
			}
			/*	
			for(Span s2: nameSpans2){
				System.out.println(s2.toString());
			}
			for(Span s3: nameSpans3){
				System.out.println(s3.toString());
			}
			for(Span s4: nameSpans4){
				System.out.println(s4.toString());
			}
			for(Span s5: nameSpans5){
				System.out.println(s5.toString());
			}
			for(Span s6: nameSpans6){
				System.out.println(s6.toString());
			}
			for(Span s7: nameSpans7){
				System.out.println(s7.toString());
			}*/
			
			for(Span s8: nameSpans8){
				/*System.out.println("Train Models : " + s8.toString());
				 */
				String str = "";
				for(int i = s8.getStart(); i < s8.getEnd(); i++)
				{
					str += tokens[i] + " ";
				}
				System.out.println("Type : " + s8.getType() + " Value : " + str);
				/* System.out.println("Start : " + s8.getStart());
				System.out.println("End :" + s8.getEnd());
				
				System.out.println("Class : " + s8.getClass());*/
				
				
				 sample = new NameSample(sentenceArray, new Span[]{new Span(s8.getStart(),s8.getEnd() )}, false);
				 
				 Pattern bookReferencePattern= Pattern.compile("B.*\\sR.*:\\s(.*)");//Parse Booking Reference attribute				 
				 Pattern ticketNumberPattern= Pattern.compile("(Ti.*):\\s(.*)[0-9]"); // ticket number
				 Pattern airlinePattern = Pattern.compile("T.*(\\s+)A.*:\\s(.*)"); //ticket airline
				 Pattern namePattern = Pattern.compile("N.*:\\s(.*)"); //name
				 
				 Pattern referenceNumberPattern= Pattern.compile("(R.*):\\s(.*)[0-9]"); // reference number
				 
				 Pattern bookingDatePattern= Pattern.compile("(B.*):\\s(.*)[0-9]"); // booking data
				 
				 
			
				for(String sentence :sample.getSentence() ){
					
					String sentenceToExtract = sentence.toString();
					
					Matcher mRef = bookReferencePattern.matcher(sentenceToExtract.toString());
					Matcher mticket = ticketNumberPattern.matcher(sentenceToExtract.toString());
					Matcher mairline = airlinePattern.matcher(sentenceToExtract.toString());
					Matcher mname = namePattern.matcher(sentenceToExtract.toString());
					Matcher mRefNumber = referenceNumberPattern.matcher(sentenceToExtract.toString());
					Matcher mBookingDate = bookingDatePattern.matcher(sentenceToExtract.toString());
						
					/*if(mRef.find()){
						String[] refArray = sentenceToExtract.toString().split(":");						
						System.out.println("refArray1  : " + refArray[0].toString());
						System.out.println("refArray2  : " + refArray[1].toString());
					}
					
					if(mticket.find()){
						String[] ticketArray = sentenceToExtract.toString().split(":");						
						System.out.println("refArray1  : " + ticketArray[0].toString());
						System.out.println("refArray2  : " + ticketArray[1].toString());
					}
					
					if(mairline.find()){
						String[] airlineArray = sentenceToExtract.toString().split(":");						
						System.out.println("refArray1  : " + airlineArray[0].toString());
						System.out.println("refArray2  : " + airlineArray[1].toString());
					}
					if(mname.find()){
						String[] nameArray = sentenceToExtract.toString().split(":");						
						System.out.println("refArray1  : " + nameArray[0].toString());
						System.out.println("refArray2  : " + nameArray[1].toString());
					}
					if(mRefNumber.find()){
						String[] refNumberArray = sentenceToExtract.toString().split(":");						
						System.out.println("refArray1  : " + refNumberArray[0].toString());
						System.out.println("refArray2  : " + refNumberArray[1].toString());
					}
					if(mBookingDate.find()){
						String[] refBookingDateArray = sentenceToExtract.toString().split(":");						
						System.out.println("refArray1  : " + refBookingDateArray[0].toString());
						System.out.println("refArray2  : " + refBookingDateArray[1].toString());
					}*/
					
				}
				
			}
			
			/*for(int i=0;i<nameSpans8.length;i++){
				Span[] namesWithType = new Span[nameSpans8.length];
				namesWithType[i] = new Span(nameSpans8[i].getStart(), 
						nameSpans8[i].getEnd());
				for(int j=0;j<=tokens.length;j++){
					System.out.println("namesWithoutType" + namesWithType[i].spansToStrings(nameSpans8, tokens[j]).toString());
				}
				
			}*/
			
			//method to build the START and END tags.
			//sentenceBuilder(sentenceArray , nameSpans8);
			
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	public static String sentenceBuilder(String[] sentenceArray , Span[] names){
		StringBuilder result = new StringBuilder();
		
		 for (int tokenIndex = 0; tokenIndex < sentenceArray.length; tokenIndex++) {
		      // token

		      for (Span name : names) {
		        if (name.getStart() == tokenIndex) {
		          // check if nameTypes is null, or if the nameType for this specific
		          // entity is empty. If it is, we leave the nameType blank.
		          if (name.getType() == null) {
		            result.append(NameSampleDataStream.START_TAG).append(' ');
		          }
		          else {
		            result.append(NameSampleDataStream.START_TAG_PREFIX).append(name.getType()).append("> ");
		          }
		        }

		        if (name.getEnd() == tokenIndex) {
		          result.append(NameSampleDataStream.END_TAG).append(' ');
		        }
		      }

		      result.append(sentenceArray[tokenIndex]).append(' ');
		    }
		 
		 System.out.println("Result of Tokens : " + result.toString());
		 return result.toString();
	}

}
