package services;

import entity.GetLongUrlRequest;
import entity.CommonResponse;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import spark.Request;
/**
 * Class that handle the building of a response for a long url request
 * @author Mattia Menna	
 * @author Giuseppe Onesto
 *
 */
public class GetLongUrlHandler {
	
	/**
	 * Main method for the building of the response
	 * @param clientRequest Request of the client
	 * @return A string that represents the response
	 */
	public String process(Request clientRequest){
	
		
		GetLongUrlRequest req = new GetLongUrlRequest();
		req.setCustom(clientRequest.params(":tiny"));
		req.setBrowser(getBrowserFromAgent(clientRequest.userAgent()));
		req.setIp(clientRequest.ip());
		
		CommonResponse resp = ServiceManager.getLongUrl(req);
		
		return (String) resp.getResponse();
	}
	/**
	 * Method for the extraction of the browser from the user agent string in the request
	 * @param userAgent user agent string
	 * @return String that represents the browser
	 */
	private String getBrowserFromAgent(String userAgent) {
		UserAgent agent = UserAgent.parseUserAgentString(userAgent);
		Browser br = agent.getBrowser();
		return br.getGroup().getName();
	}

	
}
