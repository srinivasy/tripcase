package com.sabre.tripcase.tcp.parse;

import java.util.List;

import com.sabre.tripcase.tcp.common.constants.Message;

/**
 * 
 * @author Venkat Pedapudi
 *
 */
public interface ContentParserApi {
	
	/**
	 * processMessage of email content with unique RequestId
	 * @param message
	 * @param requestId
	 */
	void processMessage(String message,String requestId);
	
	/**
	 * processMessage List of email contents with unique RequestId
	 * @param message
	 * @param requestId
	 */
	void processMessage(List<Message> message,String requestId);

}
