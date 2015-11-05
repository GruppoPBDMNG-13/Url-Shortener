package entity;

public class GetStatisticRequest {
	private String customUrl;
	
	public void setCustom(String url) {
		this.customUrl = url;
	}
	
	public String getCustom() {
		return this.customUrl;
	}
}
