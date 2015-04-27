package com.motivity.labs.client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
 
 
/**
 * @author Crunchify.com
 * 
 */
 
public class EmailClient {
    public static void main(String[] args) {
        String string = "";
        try {
 
            // Step1: Let's 1st read file from fileSystem
            InputStream emailInputStream = new FileInputStream(
                    "C:/Users/CB34388493/tabulardata/tabulardata/2254014.eml");
            InputStreamReader emailStreamReader = new InputStreamReader(emailInputStream);
            BufferedReader br = new BufferedReader(emailStreamReader);
            String line;
            while ((line = br.readLine()) != null) {
                string += line + "\n";
            }
 
           
            System.out.println(string);
 
            // Step2: Now pass JSON File Data to REST Service
            try {
                URL url = new URL("http://localhost:8080/rest-ml/emailFile");
                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "multipart/alternative");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(string);
                out.close();
 
//                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
// 
//                while (in.readLine() != null) {
//                }
                System.out.println("\nREST Service Invoked Successfully..");
              //  in.close();
            } catch (Exception e) {
                System.out.println("\nError while calling REST Service");
                System.out.println(e);
            }
 
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
