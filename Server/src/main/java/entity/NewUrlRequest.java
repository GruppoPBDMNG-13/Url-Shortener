package entity;

/**
 * 
 * @author Giuseppe Onesto
 * @author Mattia Menna
 */
public class NewUrlRequest {
	private String shortUrl;
	private final String longUrl;
	
	/**
	 * Constructor: the attributes of an instance get initialized
	 * @param shortUrl
	 * @param longUrl
	 */
	public NewUrlRequest(String shortUrl, String longUrl) {
		this.shortUrl=shortUrl;
		this.longUrl=longUrl;
	}
	
	/**
	 * Assigns to the attribute "shortUrl" of this NewUrlRequest the value in input
	 * @param url: shortUrl associated to the specified longUrl
	 */
	public void setShortUrl(String url) {
		this.shortUrl=url;
	}
	
	/**
	 * 
	 * @return shortUrl
	 */
	public String getShortUrl() {
		return this.shortUrl;
	}
	
	/**
	 * 
	 * @return longUrl
	 */
	public String getLongUrl() {
		return this.longUrl;
	}
}
