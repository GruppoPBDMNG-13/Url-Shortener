package entity;

/**
 * 
 * @author Giuseppe Onesto
 * @author Mattia Menna
 */
public class Click {
	private String browser;
	private String ip;
	private String dateTime;
	
	/**
	 * Constructor: the attributes of an instance get initialized
	 * @param b: the browser from which the request has been made
	 * @param ip 
	 * @param d: date and time at the moment of the request
	 */
	public Click(String b, String ip, String d) {
		browser = b;
		this.ip = ip;
		this.dateTime = d;
	}
	
	/**
	 * 
	 * @return the browser which the click has been made from
	 */
	public String getBrowser() {
		return this.browser;
	}
	
	/**
	 * 
	 * @return ip 
	 */
	public String getIp() {
		return this.ip;
	}
	/**
	 * 
	@return the attribute "dateTime" of this click which represents 
	* date and time at the moment of the click
	*/
	public String getTime() {
		return this.dateTime;
	}
	
	/**
	 * Assigns to the attribute "browser" of this click the value in input
	 * @param b: the browser which the click was made from
	 */ 
	public void setBrowser(String b) {
		this.browser=b;
	}
	
	/**
	 * Assigns to the attribute "ip" of this click the value in input
	 * @param ip
	 */
	public void setIp(String ip) {
		this.ip=ip;
	}
	
	/**
	 * Assigns to the attribute "dateTime" of this click the value in input
	 * @param t
	 */
	public void setTime(String t) {
		this.dateTime = t;
	}
}
