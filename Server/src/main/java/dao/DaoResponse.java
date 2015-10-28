package dao;

/**
 * Class that offers a common way to represent an output to any operation made on database.
 * @author Giuseppe Onesto
 * @author Mattia Menna
 *
 */
public class DaoResponse {
	
	private static DaoResponseCode code;
	private Object response;
	
	/**
	 * Assigns to the attribute "code" of this DaoResponse the DaoResponseCode in input
	 * @param c
	 */
	public void setCode(DaoResponseCode c) {
		code=c;
	}
	
	/**
	 * 
	 * @return the value of the attribute "code" of this DaoResponse
	 */
	public DaoResponseCode getCode() {
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
}
