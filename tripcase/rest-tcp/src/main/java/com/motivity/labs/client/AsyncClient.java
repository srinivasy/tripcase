package com.motivity.labs.client;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Future;

import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.MultivaluedHashMap;



public class AsyncClient {

	  public static void main(String args[]){
		  AsyncClient client=new AsyncClient();
		  //client.testAsync();
		  client.testPutAsync();
	  }
	  
	public void testAsync(){
			//WebTarget target = ClientBuilder.newClient().target("http://localhost:8080/rest-ml/parsing/");
		
			WebTarget target = ClientBuilder.newClient().target("http://10.10.15.195:8080/rest-ml/parsing/");
		    try{
		    	 
		           
		    final AsyncInvoker asyncInvoker = target.path("simpleAsync").request().async();
	              
		    final Future<Response> responseFuture = asyncInvoker.get();
		    
		    System.out.println("Request is being processed asynchronously.");
		    final Response response = responseFuture.get();
		   System.out.println("Get Entity:"+ response.getEntity());
		    // get() waits for the response to be ready
		    System.out.println("Response received : " + response);
		    System.out.println("Entity Result="+response.readEntity(String.class).toString());
		    }
		    catch(Exception e){
		    	System.out.println("Exception raised:"+e);
		    }
		   

	}
	
	public void testPutAsync(){

		String fileContent="";
		//WebTarget target = ClientBuilder.newClient().target("http://localhost:8080/rest-ml/resource/");
		WebTarget target = ClientBuilder.newClient().target("http://10.10.15.195:8080/rest-ml/parsing/");
		    try{
		    	 InputStream emailInputStream = new FileInputStream("C:/Users/CB34388493/tabulardata/tabulardata/2254014.eml");
		            InputStreamReader emailStreamReader = new InputStreamReader(emailInputStream);
		            BufferedReader br = new BufferedReader(emailStreamReader);
		            String line;
		            while ((line = br.readLine()) != null) {
		            	fileContent += line + "\n";
		            }
		           
		            System.out.println(fileContent);
		           
		    final AsyncInvoker asyncInvoker = target.path("asyncPutEmail").path("100002").request(MediaType.TEXT_PLAIN).async();
		   	              
		    byte[] stringByte = fileContent.getBytes();
		    ByteArrayOutputStream bos = new ByteArrayOutputStream(fileContent.length());
		    bos.write(stringByte);
		    final Future<Response> responseFuture = asyncInvoker.put(Entity.entity(stringByte, MediaType.APPLICATION_OCTET_STREAM));
		   
		    System.out.println("Put Request is being processed asynchronously.");
		    final Response response = responseFuture.get();
		   System.out.println("Get Entity:"+ response.getEntity());
		    // get() waits for the response to be ready
		    System.out.println("Response received : " + response);
		    System.out.println("Entity Result="+response.readEntity(String.class).toString());
		    }
		    catch(Exception e){
		    	System.out.println("Exception raised:"+e);
		    }
		   

	
	}
	   

}
