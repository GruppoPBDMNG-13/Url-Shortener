package entity;

import java.time.LocalDate;

public class Click {
	private String browser;
	private String ip;
	private String dateTime;
	
	/**
	 * Costruttore: the attributes of an instance get initialized
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
	@return date and time at the moment of the click
	*/
	public String getTime() {
		return this.dateTime;
	}
	
	public void setBrowser(String b) {
		this.browser=b;
	}
	
	public void setIp(String ip) {
		this.ip=ip;
	}
	
	public void setTime() {
		this.dateTime = LocalDate.now().toString();
	}
}
