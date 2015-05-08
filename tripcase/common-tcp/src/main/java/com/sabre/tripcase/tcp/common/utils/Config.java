package com.sabre.tripcase.tcp.common.utils;

import java.io.File;


public class Config {

	public static String path = "/mail/";
	public static String rules_path = "/rules/rules_";
	public static String output_path = "/output/";
	public static String models_path = "/models/stanford-corenlp-3.4-models";
	public static String models_dir_path = "/edu/stanford/nlp/models";
	
	public static String resource_path = "/resources/";
	
	public static String getModelsPath(){
		String rootPath =  getRootFolderPath();
		return String.format("%s%s",rootPath , Config.models_path );
	}
	
	public static String getModelsDirectoryPath(){
		return getModelsPath() + models_dir_path;
	}
	
	public static String getNERDirectoryPath(){
		return String.format("%s%s",getModelsPath() , "/AIRLINE_TRIPCASE1.txt" );
	}
	
	public static String getCurrentFolderPath(){
		return System.getProperty("user.dir");
	}
	
	public static String getRootFolderPath(){
		return new File(System.getProperty("user.dir")).getPath();
	}
	
	
	public static String getEmailPath(){
		String currentPath =  getCurrentFolderPath();
		return String.format("%s%s",currentPath , Config.path );
	}
	
	public static String getEMailPath(String category){
		return String.format("%s%s", getEmailPath(),category,"/");
	}
	
	public static String getOutputFileXMLName(String emailName){
		  String currentPath =  System.getProperty("user.dir");
		  return String.format("%s%s%s",currentPath , Config.output_path , emailName+".xml");
		 }
	
	public static String getOutputFileHTMLName(String emailName, String category){
		  String currentPath =  System.getProperty("user.dir");
		  return String.format("%s%s%s%s%s%s%s",currentPath , Config.output_path,category,"/" ,category,"_", emailName +".html");
		 }
	
	public static String getRulesFileName(String emailName){
		String currentPath =  System.getProperty("user.dir");
		return String.format("%s%s%s",currentPath , Config.rules_path , "generic.txt");
	}
	
	public static String getResourcePath(){
		String currentPath =  getCurrentFolderPath();
		return String.format("%s%s",currentPath , Config.resource_path );
		
	}
}
