package dao.jedis;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dao.DaoInterface;
import dao.DaoRequest;
import dao.DaoRequestCode;
import dao.DaoResponse;
import dao.DaoResponseCode;
import entity.Click;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Class to properly implement the methods defined into DaoInterface for a Redis database
 * 
 * @author Giuseppe Onesto
 * @author Mattia Menna
 *
 */
public class JedisDb implements DaoInterface{
	
	private JedisPool p;
	private Jedis instance;
	private DaoResponse resp;
	
	/**
	 * Constructor: initialize the connection and properly instantiates the attribute "resp"
	 * and its own code and response
	 */
	public JedisDb() {
		resp=new DaoResponse();
		
		try {
			connect();
			if(instance.get("lastClick")==null)
				instance.set("lastClick", "1");
			
			resp.setCode(DaoResponseCode.OK);
		} catch (Exception e) {
			resp.setCode(DaoResponseCode.NOT_CONNECTED);
			resp.setResponse("Cannot connect to jedis");	
		}
	}
	
	
	@Override
	public DaoResponse newUrl(String shortUrl, String longUrl) {
		if(! resp.getCode().equals(DaoResponseCode.NOT_CONNECTED)) {
			DaoRequest r = new DaoRequest(DaoRequestCode.URL,shortUrl); 
		
			if(!instance.exists(r.getShortUrlForScope())) {
				if(instance.set(r.getShortUrlForScope(), longUrl).equals("OK")) {
					resp.setCode(DaoResponseCode.OK);
					resp.setResponse(shortUrl);
				}
				else {
					resp.setCode(DaoResponseCode.ERROR);
					resp.setResponse("An error has occurred");
				}
			
			}
			else {
				resp.setCode(DaoResponseCode.ALREADY_EXISTS);
				resp.setResponse("The shortUrl already exists");
			}
		
		}
		return resp;
	}

	
	@Override
	public DaoResponse getUrl(String shortUrl, String browser, String ip, String dateTime) {
		if(! resp.getCode().equals(DaoResponseCode.NOT_CONNECTED)) {
			DaoRequest r = new DaoRequest(DaoRequestCode.URL,shortUrl);
			
			String longUrl = instance.get(r.getShortUrlForScope());
			if(longUrl!=null) {	
				
				HashMap<String,String> clickValues=new HashMap<>();
				clickValues.put("URL", shortUrl);
				clickValues.put("Browser", browser);
				clickValues.put("ip", ip);
				clickValues.put("dateTime", LocalDate.now().toString());
				String lastClickId = instance.get("lastClick");
				
				if(instance.hmset(lastClickId, clickValues).equals("OK")) {
					instance.incr("lastClick");
					r.setRequestCode(DaoRequestCode.STATISTIC);
					instance.lpush(r.getShortUrlForScope(), lastClickId);
					resp.setCode(DaoResponseCode.OK);
					resp.setResponse(longUrl);
				}
				
				else {
						resp.setCode(DaoResponseCode.ERROR);
						resp.setResponse("An error has occurred.");
				}
					
			
			}
			else {
				
					resp.setCode(DaoResponseCode.NOT_EXISTS);
					resp.setResponse("The short Url isn't correct");
			}
		
		}
		return resp;
		
	}
		
	

	
	@Override
	public DaoResponse getStatistics(String shortUrl) {
		
		if(! resp.getCode().equals(DaoResponseCode.NOT_CONNECTED)) {
			DaoRequest r = new DaoRequest(DaoRequestCode.STATISTIC, shortUrl);
			
			List<String> clickList = instance.lrange(r.getShortUrlForScope(), 0, -1);
			
			if(clickList.isEmpty()) {
				resp.setCode(DaoResponseCode.NOT_EXISTS);
				resp.setResponse("No clicks made on this shortUrl. "
						+ "Please check it's associated to some longUrl");
			
			}
			
			else if(clickList.equals("error")) {
					resp.setCode(DaoResponseCode.ERROR);
					resp.setResponse("An error has occurred");
			}
			
			
			else {
				LinkedList<Click> s = new LinkedList<>();
				
				for(String clickid:clickList) {
					Map<String,String> d = instance.hgetAll(clickid);
					
					if(d.equals("error")) {
						resp.setCode(DaoResponseCode.ERROR);
						resp.setResponse("An error has occurred");
						return resp;
					}
					else {
						String browser = d.get("Browser");
						String ip = d.get("ip");
						String timestamp = d.get("dateTime");
						Click c = new Click(browser,ip,timestamp);
						
						s.add(c);
					}
				
				}
				
				resp.setResponse(s);
					
		}
		}
		return resp;
		
	}
	
	
	@Override
	public DaoResponse getLastNStatistics(String shortUrl, int lastN) {
		
		if(! resp.getCode().equals(DaoResponseCode.NOT_CONNECTED)) {
			DaoRequest r = new DaoRequest(DaoRequestCode.STATISTIC, shortUrl);
			
			List<String> clickList = instance.lrange(r.getShortUrlForScope(), 0, lastN-1);
			
			if(clickList.isEmpty()) {
				resp.setCode(DaoResponseCode.NOT_EXISTS);
				resp.setResponse("No clicks made on this shortUrl. "
						+ "Please check it's associated to some longUrl");
			
			}
			
			else if(clickList.equals("error")) {
					resp.setCode(DaoResponseCode.ERROR);
					resp.setResponse("An error has occurred");
			}
			
			
			else {
				LinkedList<Click> s = new LinkedList<>();
				
				for(String clickid:clickList) {
					Map<String,String> d = instance.hgetAll(clickid);
					
					if(d.equals("error")) {
						resp.setCode(DaoResponseCode.ERROR);
						resp.setResponse("An error has occurred");
						return resp;
					}
					else {
						String browser = d.get("Browser");
						String ip = d.get("ip");
						String timestamp = d.get("dateTime");
						Click c = new Click(browser,ip,timestamp);
						
						s.add(c);
					}
				
				}
				
				resp.setResponse(s);
					
		}
		}
		return resp;
		
	}

	
	@Override
	public DaoResponse deleteUrl(String shortUrl) {
		
		if(! resp.getCode().equals(DaoResponseCode.NOT_CONNECTED)) {
			DaoRequest r = new DaoRequest(DaoRequestCode.URL,shortUrl);
			
			if(instance.exists(r.getShortUrlForScope())) {
				String jedisresp=instance.del(r.getShortUrlForScope()).toString();
				
				if(jedisresp.equals("error")) {
					resp.setCode(DaoResponseCode.ERROR);
					resp.setResponse("An error has occurred");
				}
				else {
					resp.setCode(DaoResponseCode.OK);
					resp.setResponse(instance.del(r.getShortUrlForScope()));
				}
				
			}
			else {
				resp.setCode(DaoResponseCode.NOT_EXISTS);
				resp.setResponse("The short Url isn't associated to any longUrl");
			}
			
		}
		
		return resp;
	}
	
	
	/**
	 * Initialize a connection to Redis server
	 * @return an instance of database "Jedis", connected to localhost.
	 */
	private Jedis connect() throws Exception{
		if(p==null) {
			p = new JedisPool(new JedisPoolConfig(), "localhost");
			instance = p.getResource();
			
		}
		
		return instance;
	}
	

}
