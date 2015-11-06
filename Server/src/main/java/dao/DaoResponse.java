package dao;

import utility.ResponseCode;

/**
 * Class that offers a common way to represent an output to any operation made on database.
 * @author Giuseppe Onesto
 * @author Mattia Menna
 *
 */
public class DaoResponse {
	
	private static ResponseCode code;
	private Object response;
	
	/**
	 * Assigns to the attribute "code" of this DaoResponse the DaoResponseCode in input
	 * @param c
	 */
	public void setCode(ResponseCode c) {
		code=c;
	}
	
	/**
	 * 
	 * @return the value of the attribute "code" of this DaoResponse
	 */
	public ResponseCode getCode() {
		return code;
	}

	/**
	 * Assigns to the attribute "response" of this DaoResponse the Object in input
	 * @param r
	 */
	public void setResponse(Object r) {
		this.response=r;
	}
	
	/**
	 * 
	 * @return the value of the attribute "response" of this DaoResponse
	 */
	public Object getResponse() {
		return this.response;
	}
	
	public boolean equals(Object o){
		DaoResponse r = (DaoResponse) o;
		
		if(r.getCode().equals(this.getCode()) && r.getResponse().equals(this.getResponse()))
			return true;
		
		return false;
		
	}
}
