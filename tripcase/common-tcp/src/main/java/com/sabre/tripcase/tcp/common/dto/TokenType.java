/**
 * 
 */
package com.sabre.tripcase.tcp.common.dto;

/**
 * @author Nalini Kanta
 *
 */
public class TokenType {
	
	/** tag,value */
	private String firstTag;
	private String value;
	private String endTag;
	
	/**
	 * Default Constructor :TokenType
	 */
	public TokenType(){
		
	}

	
	/**
	 * @param firstTag
	 * @param value
	 * @param endTag
	 */
	public TokenType(String firstTag, String value, String endTag) {
		this.firstTag = firstTag;
		this.value = value;
		this.endTag = endTag;
	}



	/**
	 * @return the firstTag
	 */
	public String getFirstTag() {
		return firstTag;
	}



	/**
	 * @param firstTag the firstTag to set
	 */
	public void setFirstTag(String firstTag) {
		this.firstTag = firstTag;
	}



	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}



	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		
		StringBuilder sb=null;
		if(null==this.value){
		sb=new StringBuilder("");
		}
		else{
			sb=new StringBuilder(this.value);
		}
		sb.append(value+" ");
		this.value = sb.toString();
	}



	/**
	 * @return the endTag
	 */
	public String getEndTag() {
		return endTag;
	}



	/**
	 * @param endTag the endTag to set
	 */
	public void setEndTag(String endTag) {
		this.endTag = endTag;
	}



	/**
	 * toString
	 */
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append("firstTag:"+firstTag+",");
		sb.append("value:"+value+",");
		sb.append("endTag:"+endTag+",");
		return sb.toString();
	}
	
	public String toStringXML(){
		StringBuilder sb=new StringBuilder();
		sb.append(firstTag+value+endTag);
		return sb.toString();
	}
	

}
