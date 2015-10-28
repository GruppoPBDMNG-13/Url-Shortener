package dao;

/**
 * Class that offers a common way to handle any operation of writing a newUrl
 * (or a list associated to this newUrl to represent the clicks made on it) in a database.
 * 
 * @author Giuseppe Onesto
 * @author Mattia Menna
 * 
 */
public class DaoRequest {
	private static DaoRequestCode request;
	private String shortUrl;
	private String shortUrlForScope;
	
	/**
	 * Constructor: given the DaoRequestCode and the shortUrl to save in the database, properly
	 * handle the value of the String "shortUrlForScope" thanks to the @method "setUrlForScope".
	 * @param code
	 * @param url
	 */
	public DaoRequest(DaoRequestCode code, String url) {
		request = code;
		this.shortUrl=url;
		setShortUrlForScope(code);
	}
	
	/**
	 * Assigns to the attribute "request" of this DaoRequest the DaoRequestCode in input
	 * and properly updates the value of the attribute "shortUrlForScope".
	 * @param code
	 */
	public void setRequestCode(DaoRequestCode code) {
		if(code.getCode()!=(request.getCode())) {
			request = code;
			setShortUrlForScope(code);
		}
	}
	
	/**
	 * 
	 * @return the value of the attribute "request" of this DaoRequest
	 */
	public DaoRequestCode getRequestCode() {
		return request;
		
	}
	
	/**
	 * Assigns to the attribute "shortUrl" of this DaoResponse the DaoResponseCode in input
	 * @param url
	 */
	public void setShortUrl(String url) {
		this.shortUrl=url;
	}
	
	/**
	 * 
	 * @return the value of the attribute "shortUrl" of this DaoRequest
	 */
	public String getShortUrl() {
		return this.shortUrl;
	}
	
	/**
	 * Properly sets the value of the String "shortUrlForScope" so to save the Url metadata 
	 * in the database in two different ways:
	 * 1)shortUrl preceded by "Url:" to save a new short URL associated to its own longUrl
	 * 2)shortUrl preceded by "UrlStatistics:" to save the short URL associated to its own list
	 * @param code
	 */
	private void setShortUrlForScope(DaoRequestCode code) {
		switch(code) {
		
		case URL : 
			this.shortUrlForScope = "Url:";
			break;
		case STATISTIC :
			this.shortUrlForScope = "UrlStatistics:";
			break;
		default:
			this.shortUrlForScope="";
		}
	}
	
	/**
	 * 
	 * @return the value of the attribute "shortUrlForScope" associated to the shortUrl
	 * of this DaoRequest
	 */
	public String getShortUrlForScope() {
		return this.shortUrlForScope + this.shortUrl;
	}
		
	
}
