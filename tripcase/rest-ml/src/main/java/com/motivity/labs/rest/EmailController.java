package com.motivity.labs.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
 
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;


 
@Path("/")
public class EmailController {
    @POST
    @Path("/emailFile")
    @Consumes(MediaType.WILDCARD)
    @Produces(MediaType.APPLICATION_JSON)
 
    public void putEmailFile(@Suspended final AsyncResponse asyncResponse,InputStream incomingData) {
    	System.out.println("Insid putEmail File");
        StringBuilder emailBuilder = new StringBuilder();
        String uploadedFileLocation = "d://uploaded/" + "1234.eml";
        try {
//            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
//            String line = null;
//            while ((line = in.readLine()) != null) {
//            	emailBuilder.append(line);
//            }
           
            
    		// save it
    		writeToFile(incomingData, uploadedFileLocation);
    		
        } catch (Exception e) {
            System.out.println("Error Parsing: - ");
        }
        System.out.println("Data Received: " + emailBuilder.toString());
        
 
		String output = "File uploaded to : " + uploadedFileLocation;
		asyncResponse.resume(output);
		//return Response.status(200).entity(output).build();
 
        // return HTTP response 200 in case of success
      //  return Response.status(200).entity(emailBuilder.toString()).build();
    }
    
	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream,
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
		}
 
	}
}