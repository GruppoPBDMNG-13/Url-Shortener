package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import entity.GetStatisticRequest;
import entity.CommonResponse;
import spark.Request;
/**
 * Class that handle the building of a response for a get statistics request
 * @author Mattia Menna	
 * @author Giuseppe Onesto
 *
 */
public class GetStatisticHandler{
	/**
	 * Main method for the building of the response
	 * @param clientRequest Request of the client
	 * @return A string that represents the response
	 */
	public String process(Request clientRequest){
		Gson gson = new GsonBuilder().create();
		GetStatisticRequest req = new GetStatisticRequest();
		req.setCustom(clientRequest.params(":tiny"));
		
		CommonResponse resp = ServiceManager.getStatistic(req);
		return gson.toJson(resp);
	}


}
