package com.sabre.tripcase.tcp.process;

/**
 * 
 * @author Venkat Pedapudi
 *
 */
public class MessageRouter {
	
	private String routeEndPoint;
    
	public MessageRouter(){
		
	}
	/*
	 * 
	 */
	public MessageRouter(String routeEndPoint) {
		this.routeEndPoint = routeEndPoint;
	}
	
    /**
     * Recipient List
     * @return
     */
	public String routeTo() {
        return routeEndPoint;
    }



		
	
}