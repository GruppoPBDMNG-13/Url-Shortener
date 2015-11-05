package entity;


/**
 * Represents the response for a get long url request
 * @author Mattia Menna
 * @author Giuseppe Onesto
 */
public class GetLongUrlResponse extends CommonResponse{
	
	private String longUrl;
	
	@Override
	public void setResponse(Object r) {
		this.longUrl=(String) r;
	}
	
	@Override
	public String getResponse() {	
		return this.longUrl;
	}	
	
}
