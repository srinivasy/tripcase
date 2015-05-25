package com.sabre.tripcase.tcp.process;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.spring.SpringCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.sabre.tripcase.tcp.common.constants.Message;

/**
 * 
 * @author Venkat Pedapudi
 *
 */
public class ProcessRouteBuilder extends RouteBuilder implements InitializingBean, ApplicationContextAware{
	
	private static final Logger LOG = LoggerFactory.getLogger(ProcessRouteBuilder.class);
	private CamelContext camelContext;
	private ProducerTemplate template; 
	private ApplicationContext applicationContext;
	private String route="";
	private int batchSize=0;

	private boolean enableGateParser;
	
	private boolean enableNlpParser;
	
	private boolean enableTableParser;
	
	
	
	/**
	 * @param enableGateParser the enableGateParser to set
	 */
	public void setEnableGateParser(boolean enableGateParser) {
		this.enableGateParser = enableGateParser;
		if(enableGateParser){
			this.route +="bean:gateContentParser?method=invokeHandler,";
			batchSize++;
		}
		
	}

	/**
	 * @param enableNlpParser the enableNlpParser to set
	 */
	public void setEnableNlpParser(boolean enableNlpParser) {
		if(enableNlpParser){
			this.route +="bean:nlpparser?method=invokeHandler,";
			batchSize++;
		}
		this.enableNlpParser = enableNlpParser;
	}

	/**
	 * @param enableTableParser the enableTableParser to set
	 */
	public void setEnableTableParser(boolean enableTableParser) {
		if(enableTableParser){
			this.route +="bean:tableparser?method=invokeHandler,";
			batchSize++;
		}
		this.enableTableParser = enableTableParser;
	}

	


	@Override
	public void configure() throws IOException {
		
		String routeEndPoint=route.substring(0,route.lastIndexOf(","));
		
		LOG.info("ProcessRouteBuilder end Point destinations:"+routeEndPoint+" and batch size:"+batchSize);
		
		from("direct:startProcess").id("processRequest").recipientList().method(new MessageRouter(routeEndPoint),"routeTo");
		
		from("direct:aggragateProcess").id("aggRequest")
		.aggregate(new ParsedOutputAggregator()).header("RequestID").completionSize(batchSize)
		.to("bean:contentFinalProcess?method=processFinalResults");
		//Need to add completionTimeout(1000L) for better results in aggregator.
		
		/*from("file://"+getResource("lookup/").getFile().getAbsolutePath()+"?noop=true&fileName=airlines.csv")
		.split(body().tokenize("\n")).streaming()
        .unmarshal().bindy(BindyType.Csv, AirlinesLookup.class)
        .beanRef("loadLookup", "loadAirlinesData");*/
	}
	
		
	/**
	 * Used to send the Message
	 * @param message
	 */
    public void processMessage(String message,String requestId){
    	Map<String,Object> header = new HashMap<String,Object>();
    	header.put("RequestID", requestId);
    	Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(message);
		exchange.getIn().setHeaders(header);
		
    	template.send("direct:startProcess",exchange);
    }
    
    /**
     * Used to send the List of Messages
     * @param message
     */
    public void processMessage(List<Message> message,String requestId){
    	Map<String,Object> header = new HashMap<String,Object>();
    	header.put("RequestID", requestId);
    	Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(message);
		exchange.getIn().setHeaders(header);
		
    	template.send("direct:startProcess",exchange);
    }
    
    
    /**
     * Used to send the List of Messages
     * @param message
     */
    public void processfinalMessages(Exchange message){
    	template.asyncSend("direct:aggragateProcess",message);
    
    }
	
	@Override
	public void afterPropertiesSet() {
		if(camelContext == null){
			try{
				camelContext = new  SpringCamelContext(getApplicationContext());
				camelContext.addRoutes(this);
				template = camelContext.createProducerTemplate();
				camelContext.start();
			}catch(Exception e){
				LOG.error("Error while creating camelContext in ProcessRouteBuilder "+e.getMessage());
			}
		}
		
	}
	
	

	@Override
	public void setApplicationContext(ApplicationContext applicationContext){
		this.applicationContext=applicationContext;
		
	}
	
    /**
     * getApplicationContext
     * @return ApplicationContext
     */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
  	/**
  	 * 
  	 * @param path
  	 * @return
  	 */
	private Resource getResource(String path) {
		return new ClassPathResource(path,this.getClass().getClassLoader());
	}
}

