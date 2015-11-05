package entity;

/**
 * Represents the response for a new url request
 * @author Mattia Menna
 * @author Giuseppe Onesto
 */
public class NewUrlResponse extends CommonResponse{
	/**
	 * Either user customizzation or generated short-url
	 */
	private String custom;
	
	@Override
	public void setResponse(Object r) {
		this.custom = (String) r;
	}
	
	@Override
	public Object getResponse() {
		return this.custom;
	}
		
}
