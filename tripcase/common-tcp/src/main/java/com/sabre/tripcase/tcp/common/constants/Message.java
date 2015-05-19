package com.sabre.tripcase.tcp.common.constants;

import com.sabre.tripcase.tcp.common.constants.Constants.ContentType;
import com.sabre.tripcase.tcp.common.constants.Constants.LangCode;

public class Message
{
	String content;
	LangCode langCode;	
	ContentType contentType;
	
	public void setContent(String content) {
		this.content=content;		
	}
	
	public String getContent() {
		return content;	
	}
	
	public void setLangCode(LangCode langCode) {
		this.langCode = langCode;				
	}
	
	public LangCode getLangCode() {
		return langCode;
	}
	
	public void setContentType(ContentType contentType) {
		this.contentType= contentType;
	}
	
	public ContentType getContentType()	{
		return contentType;
	}
	
}
