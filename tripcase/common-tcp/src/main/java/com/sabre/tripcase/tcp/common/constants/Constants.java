package com.sabre.tripcase.tcp.common.constants;

public interface Constants {
	public static final String RESPONSE_FILE_BASE_LOCATION="C:/Users/CB34388493/opennlp/response";
	
	public static final String INDEPENDENT="INDEPENDENT";
	public static final String PARENT="PARENT";
	public static final String CHILD="CHILD";
	public static final String EVEN="EVEN";
	public static final String ODD="ODD";
	public static final String WHITESPACE=" ";
	public static final String NEWLINE="\n";
	public static final String EMPTY="";
	
	public static final String HTML_TAG="<html>";
	public static final String HTML_DIV="<div>";
	public static final String HTML_TABLE="<table>";
	public static final String ALL_FILES="ALL";
	
	public static final String HTML="HTML";
	public static final String TEXT="TEXT";
	
	public static final String SPACE_DELIMITER=" ";
	public static final String PIPE_DELIMITER=" | ";
	
	public static final String TAG_OPEN="OPEN";
	public static final String TAG_CLOSE="CLOSE";
	public static final String TAG_NONE="NONE";
	
	public static final String TAG="TAG";
	public static final String VALUE="VALUE";
	
	//content type
	public enum LangCode
	{
		EN, RO;			
	}
	
	enum ContentType
	{
		PDF, HTML, TEXT;	
	}
}
