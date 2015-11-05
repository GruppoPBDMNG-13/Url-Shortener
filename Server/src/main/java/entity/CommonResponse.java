package entity;

import utility.ResponseCode;

/**
 * Class that offers a common way to represent an output to any request made by client.
 * @author Giuseppe Onesto
 * @author Mattia Menna
 *
 */
public abstract class CommonResponse {
	
	protected ResponseCode code;
	
	
	/**
	 * Assigns to the attribute "code" of this Response the ResponseCode in input
	 * @param c
	 */
	public void setCode(ResponseCode c) {
		code=c;
	}
	
	/**
	 * 
	 * @return the value of the attribute "code" of this Response
	 */
	public ResponseCode getCode() {
		return code;
	}

	/**
	 * Assigns to the attribute "response" of this Response the Object in input
	 * @param r
	 */
	public abstract void setResponse(Object r);
	
	/**
	 * 
	 * @return the value of the attribute "response" of this Response
	 */
	public abstract Object getResponse();
}
