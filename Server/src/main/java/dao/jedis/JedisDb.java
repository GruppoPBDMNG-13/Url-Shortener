package dao.jedis;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import dao.DaoInterface;
import dao.DaoRequest;
import utility.RequestCode;
import dao.DaoResponse;
import utility.ResponseCode;
import entity.Click;


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
			
			resp.setCode(ResponseCode.OK);
		} catch (Exception e) {
			resp.setCode(ResponseCode.NOT_CONNECTED);
			resp.setResponse("Cannot connect to jedis");	
		}
	}
	
	
	@Override
	public DaoResponse newUrl(String shortUrl, String longUrl) {
		if(! resp.getCode().equals(ResponseCode.NOT_CONNECTED)) {
			DaoRequest r = new DaoRequest(RequestCode.URL,shortUrl); 
		
			if(!instance.exists(r.getShortUrlForScope())) {
				if(instance.set(r.getShortUrlForScope(), longUrl).equals("OK")) {
					resp.setCode(ResponseCode.OK);
					resp.setResponse(shortUrl);
				}
				else {
					resp.setCode(ResponseCode.ERROR);
					resp.setResponse("An error has occurred");
				}
			
			}
			else {
				resp.setCode(ResponseCode.ALREADY_EXISTS);
				resp.setResponse("The shortUrl already exists");
			}
		
		}
		return resp;
	}

	
	@Override
	public DaoResponse getUrl(String shortUrl, String browser, String ip, String dateTime) {
		if(! resp.getCode().equals(ResponseCode.NOT_CONNECTED)) {
			DaoRequest r = new DaoRequest(RequestCode.URL,shortUrl);
			
			String longUrl = instance.get(r.getShortUrlForScope());
			if(longUrl!=null) {	
				
				HashMap<String,String> clickValues=new HashMap<>();
				clickValues.put("URL", shortUrl);
				clickValues.put("Browser", browser);
				clickValues.put("ip", ip);
				clickValues.put("dateTime", getNowDate());
				String lastClickId = instance.get("lastClick");
				
				if(instance.hmset(lastClickId, clickValues).equals("OK")) {
					instance.incr("lastClick");
					r.setRequestCode(RequestCode.STATISTIC);
					instance.lpush(r.getShortUrlForScope(), lastClickId);
					resp.setCode(ResponseCode.OK);
					resp.setResponse(longUrl);
				}
				
				else {
						resp.setCode(ResponseCode.ERROR);
						resp.setResponse("An error has occurred.");
				}
					
			
			}
			else {
				
					resp.setCode(ResponseCode.NOT_EXISTS);
					resp.setResponse("The short Url isn't correct");
			}
		
		}
		return resp;
		
	}
		
	

	
	@Override
	public DaoResponse getStatistics(String shortUrl) {
		
		if(! resp.getCode().equals(ResponseCode.NOT_CONNECTED)) {
			DaoRequest r = new DaoRequest(RequestCode.STATISTIC, shortUrl);
			
			List<String> clickList = instance.lrange(r.getShortUrlForScope(), 0, -1);
			
			if(clickList.isEmpty()) {
				resp.setCode(ResponseCode.NOT_EXISTS);
				resp.setResponse("No clicks made on this shortUrl. "
						+ "Please check it's associated to some longUrl");
			
			}
			
			else if(clickList.equals("error")) {
					resp.setCode(ResponseCode.ERROR);
					resp.setResponse("An error has occurred");
			}
			
			
			else {
				LinkedList<Click> s = new LinkedList<>();
				
				for(String clickid:clickList) {
					Map<String,String> d = instance.hgetAll(clickid);
					
					if(d.equals("error")) {
						resp.setCode(ResponseCode.ERROR);
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
		
		if(! resp.getCode().equals(ResponseCode.NOT_CONNECTED)) {
			DaoRequest r = new DaoRequest(RequestCode.STATISTIC, shortUrl);
			
			List<String> clickList = instance.lrange(r.getShortUrlForScope(), 0, lastN-1);
			
			if(clickList.isEmpty()) {
				resp.setCode(ResponseCode.NOT_EXISTS);
				resp.setResponse("No clicks made on this shortUrl. "
						+ "Please check it's associated to some longUrl");
			
			}
			
			else if(clickList.equals("error")) {
					resp.setCode(ResponseCode.ERROR);
					resp.setResponse("An error has occurred");
			}
			
			
			else {
				LinkedList<Click> s = new LinkedList<>();
				
				for(String clickid:clickList) {
					Map<String,String> d = instance.hgetAll(clickid);
					
					if(d.equals("error")) {
						resp.setCode(ResponseCode.ERROR);
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
		
		if(! resp.getCode().equals(ResponseCode.NOT_CONNECTED)) {
			DaoRequest r = new DaoRequest(RequestCode.URL,shortUrl);
			
			Long jedisresp=instance.del(r.getShortUrlForScope());
			resp.setCode(ResponseCode.OK);
			resp.setResponse("");
				
			if(jedisresp==0) {
				resp.setCode(ResponseCode.NOT_EXISTS);
				resp.setResponse("The short Url isn't associated to any longUrl");
			}
			else if(jedisresp==1){
				resp.setCode(ResponseCode.OK);
				resp.setResponse("Correctly removed");
			}
			else {
				resp.setCode(ResponseCode.ERROR);
				resp.setResponse("An error has occurred");
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
	
	private String getNowDate(){
		LocalDateTime timestamp = LocalDateTime.now();
		ZonedDateTime timestampzoned = ZonedDateTime.of(timestamp, ZoneId.of("Europe/Rome"));
		String time = timestampzoned.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG));
	    return time;
	}
	

}
