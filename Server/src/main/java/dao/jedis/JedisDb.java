package dao.jedis;

import java.util.List;

import dao.DaoInterface;
import entity.Click;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisDb implements DaoInterface{
	
	private JedisPool p;
	private Jedis instance;
	
	public JedisDb() {
		try {
			connect();	
		} catch (Exception e) {
			e.printStackTrace();	
		}
		
	}
	
	
	@Override
	public String newUrl(String shortUrl, String longUrl) {
		return "";
	}

	
	@Override
	public String getUrl(String shortUrl, String browser, String ip, String dateTime) {
		return "";
		
	}
		
	

	
	@Override
	public List<Click> getStatistics(String shortUrl) {
		
		return null;
		
	}
	
	
	public List<Click> getLastNStatistics(String shortUrl, int lastN) {
		
		return null;
	}

	
	@Override
	public String deleteUrl(String shortUrl) {
		
		return "";
	}
	
	
	/**
	 * 
	 * @return an instance of database "Jedis", connected to localhost.
	 */
	private Jedis connect() throws Exception{
		if(p==null) {
			p = new JedisPool(new JedisPoolConfig(), "localhost");
			System.out.println(p.toString());
			instance = p.getResource();
			
		}
		
		return instance;
	}
	

}
