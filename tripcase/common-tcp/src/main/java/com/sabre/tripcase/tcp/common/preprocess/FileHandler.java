/**
 * 
 */
package com.sabre.tripcase.tcp.common.preprocess;

import java.io.File;

/**
 * @author Nalini Kanta
 *
 */
public class FileHandler {
	
	private String filePath="c:/Users/CB34388493/opennlp/emails/";
	private String processFile="ALL";
	
	/*
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the processFile
	 */
	public String getProcessFile() {
		return processFile;
	}

	/**
	 * @param processFile the processFile to set
	 */
	public void setProcessFile(String processFile) {
		this.processFile = processFile;
	}
	
	/**
	 * getFilesList()
	 * @return
	 * @throws Throwable
	 */
	public File[] getFilesList() throws Throwable{
		
		 File[] files =null;
		 if(this.processFile.equals("ALL")){
			files = new File(filePath).listFiles();
		}
		else{
			files=new File[1];
			files[0] = new File(filePath+"/"+processFile);
		}
		
		return files;
	}
	
	/**
	 * getFilesList(String directoryPath)
	 * @param directoryPath
	 * @return
	 * @throws Throwable
	 */
	public File[] getFilesList(String directoryPath) throws Throwable{
		
		 File[] files =null;
		 if(null!=directoryPath && !directoryPath.isEmpty()){
			files = new File(directoryPath).listFiles();
		}
		return files;
	}
	

}
