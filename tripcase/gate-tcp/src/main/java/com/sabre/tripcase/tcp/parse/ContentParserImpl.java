package com.sabre.tripcase.tcp.parse;

import java.util.List;

import com.sabre.tripcase.tcp.common.constants.Message;
import com.sabre.tripcase.tcp.process.ProcessRouteBuilder;

/**
 * 
 * @author Venkat Pedapudi
 *
 */
public class ContentParserImpl implements ContentParserApi{
	
	private ProcessRouteBuilder processRouteBuilder;

	public void setProcessRouteBuilder(ProcessRouteBuilder processRouteBuilder) {
		this.processRouteBuilder = processRouteBuilder;
	}

	@Override
	public void processMessage(String message,String requestId) {
		processRouteBuilder.processMessage(message,requestId);		
	}

	@Override
	public void processMessage(List<Message> message,String requestId) {
		processRouteBuilder.processMessage(message,requestId);		
	}

}
