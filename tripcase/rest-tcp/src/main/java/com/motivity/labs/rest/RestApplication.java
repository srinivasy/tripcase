package com.motivity.labs.rest;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;




import com.motivity.labs.rest.AsyncResource;
import com.motivity.labs.rest.EmailController;

 

public class RestApplication extends ResourceConfig {
 
    /**
	* Register JAX-RS application components.
	*/	
	public RestApplication(){
		register(RequestContextFilter.class);
		register(EmailController.class);
		register(AsyncResource.class);
		register(JacksonFeature.class);	
		
		
	}
}
