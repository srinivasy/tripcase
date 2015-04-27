package com.motivity.labs.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.CompletionCallback;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Path("/parsing")
public class AsyncEmailParsingService {
    private static int numberOfSuccessResponses = 0;
    private static int numberOfFailures = 0;
    private static Throwable lastException = null;
 
    @GET
    @Path("/simpleAsync")
    public void asyncGetWithTimeout(@Suspended final AsyncResponse asyncResponse) {
        asyncResponse.register(new CompletionCallback() {
            @Override
            public void onComplete(Throwable throwable) {
                if (throwable == null) {
                    // no throwable - the processing ended successfully
                    // (response already written to the client)
                    numberOfSuccessResponses++;
                } else {
                    numberOfFailures++;
                    lastException = throwable;
                }
            }
        });
 
        new Thread(new Runnable() {
            @Override
            public void run() {
                String result = veryExpensiveOperation();
                System.out.println("Result="+result);
                asyncResponse.resume(result);
                System.out.println("Returned Result="+result);
            }
 
            private String veryExpensiveOperation() {
                return "Very expensive operation";
            }
        }).start();
        
       
    }
    
    @PUT
    @Path("/asyncPutEmail/{transactionId}")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
     public void asyncPutEmail(final @PathParam("transactionId") String transactionId,final InputStream emailStream,@Suspended final AsyncResponse asyncResponse) {
    	
        asyncResponse.register(new CompletionCallback() {
            @Override
            public void onComplete(Throwable throwable) {
                if (throwable == null) {
                    // no throwable - the processing ended successfully
                    // (response already written to the client)
                    numberOfSuccessResponses++;
                } else {
                    numberOfFailures++;
                    lastException = throwable;
                }
            }
        });
 
        new Thread(new Runnable() {
            @Override
            public void run() {
            	String uploadedFileLocation = "d://uploaded/" + transactionId+".eml";
                String result = writeToFile(emailStream,uploadedFileLocation);
                System.out.println("Result="+result);
                asyncResponse.resume(result);
                System.out.println("Received Result:"+emailStream);
            }
 
           
         // save uploaded file to new location
        	private String writeToFile(InputStream uploadedInputStream,
        		String uploadedFileLocation) {
         
        		try {
        			OutputStream out = new FileOutputStream(new File(
        					uploadedFileLocation));
        			int read = 0;
        			byte[] bytes = new byte[1024];
         
        			out = new FileOutputStream(new File(uploadedFileLocation));
        			while ((read = uploadedInputStream.read(bytes)) != -1) {
        				out.write(bytes, 0, read);
        			}
        			out.flush();
        			out.close();
        		} catch (IOException e) {
        			
         
        			e.printStackTrace();
        			return "Couldn't Save File Successfully....";
        		}
        		return "File Saved Successfully....";
         
        	}
         

        }).start();
        
       
    }
}
