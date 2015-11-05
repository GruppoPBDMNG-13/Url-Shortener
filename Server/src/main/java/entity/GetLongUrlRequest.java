package entity;

public class GetLongUrlRequest {
	private String customUrl;
	private String browser;
	private String ip;
	
	public void setCustom(String url) {
		this.customUrl = url;
	}
	
	public String getCustom() {
		return this.customUrl;
	}
	
	public void setBrowser(String b) {
		this.browser = b;
	}
	
	public String getBrowser() {
		return this.browser;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getIp() {
		return this.ip;
	}
} 
