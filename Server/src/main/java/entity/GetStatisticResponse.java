package entity;

import java.util.List;

/**
 * Represents the response to a request to get Statistics.
 * @author Mattia Menna
 * @author Giuseppe Onesto
 */
public class GetStatisticResponse extends CommonResponse {
	
	private List<Click> stat;
	
	@SuppressWarnings("unchecked")
	@Override
	public void setResponse(Object r) {
		this.stat = (List<Click>) r;
	}
	
	@Override
	public List<Click> getResponse() {
		return this.stat;
	}
	
	
	
	
}
