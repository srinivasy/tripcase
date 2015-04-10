package com.motivity.tripcase.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class HtmlFileReader {
	BufferedReader br = null;
	StringBuilder sb = null;

	public  String htmlFileRead(String filePath) throws Throwable{

		try {

			String sCurrentLine;
			if(null!=filePath){
			br = new BufferedReader(
					new FileReader(filePath));
			sb = new StringBuilder();
			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine);
			}
		}

		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				throw ex;
			}
		}
		return sb.toString();
	}

}
