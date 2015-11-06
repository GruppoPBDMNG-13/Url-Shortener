package services;

import dao.DaoFactory;
import dao.DaoInterface;
import dao.DaoResponse;
import entity.Click;
import entity.GetLongUrlRequest;
import entity.GetLongUrlResponse;
import entity.GetStatisticRequest;
import entity.GetStatisticResponse;
import entity.NewUrlRequest;
import entity.NewUrlResponse;
import entity.CommonResponse;
import utility.ResponseCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
/**
 * Class that deals with service requests to the database
 * @author Mattia Menna
 * @author Giuseppe Onesto
 *
 */
class ServiceManager {
	/**
	 * The database's interface
	 */
	private static DaoInterface d;
	
	/**
	 * Method for the creation of a new shortened url in the database
	 * @param r Client request
	 * @return Response of the database
	 */
	static CommonResponse newUrl(NewUrlRequest r) {
		
		CommonResponse resp = new NewUrlResponse();
		d = DaoFactory.getInstance(DaoFactory.JEDIS);
		
		DaoResponse dr = d.newUrl(r.getShortUrl(), r.getLongUrl());
		ResponseCode c = dr.getCode();
		
		if(c.equals(ResponseCode.OK))
			resp.setResponse(r.getShortUrl());
		else if(c.equals((ResponseCode.ALREADY_EXISTS)))
			resp.setResponse("The shortUrl you clicked already exists");
		else 
			resp.setResponse("Ops. An error has occured. Please try again.");
		
		resp.setCode(dr.getCode());
		
		return resp;
		
	}
	
	/**
	 * Method for recovering statistics in the database
	 * @param r Parameters of the request
	 * @return The statistics wrapped in a CommonResponse class
	 */
	@SuppressWarnings("unchecked")
	static CommonResponse getStatistic(GetStatisticRequest r) {
		d = DaoFactory.getInstance(DaoFactory.JEDIS);
		DaoResponse dr = d.getStatistics(r.getCustom());
		CommonResponse resp = new GetStatisticResponse();
		ResponseCode c = dr.getCode();
		if(c.equals(ResponseCode.OK)) 
			resp.setResponse((List<Click>) dr.getResponse());
		else if(c.equals((ResponseCode.NOT_EXISTS)))
			resp.setResponse(new LinkedList<Click>());
		else 
			resp.setResponse(new LinkedList<Click>());
		
		resp.setCode(dr.getCode());
		
		return resp;
		
	}
	
	/**
	 * Method that recovers a long url
	 * @param r Parameters of the request
	 * @return the long url wrapped in a CommonResponse class
	 */
	static CommonResponse getLongUrl(GetLongUrlRequest r) {
		d = DaoFactory.getInstance(DaoFactory.JEDIS);
		DaoResponse dr = d.getUrl(r.getCustom(),r.getBrowser(),r.getIp());
		
		CommonResponse resp = new GetLongUrlResponse();
		ResponseCode c = dr.getCode();
		if(c.equals(ResponseCode.OK))
			resp.setResponse((String) dr.getResponse()); 
		else if(c.equals((ResponseCode.NOT_EXISTS)))
			resp.setResponse("NOT_EXISTS");
		else 
			resp.setResponse("ERROR");
		
		resp.setCode(dr.getCode());
		
		return resp;
	}
	
	
	
	
}
