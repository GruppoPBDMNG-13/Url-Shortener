
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.LinkedList;
import java.util.Random;

import org.junit.Test;

import dao.DaoFactory;
import dao.DaoInterface;
import dao.DaoResponse;
import entity.Click;
import utility.ResponseCode;

import org.junit.Before;


public class JedisDBTest {
	private static final String EXISTS = "The shortUrl already exists";
	private static final String NOTEXISTS = "The short Url isn't correct";
	private static final int NUM_TEST = 2;
	private static final String longUrl = "http://facebook.com";
	private static final String browser = "Mozilla";
	private static final String ip = "127.0.0.1";
	
	private String newshortUrl;
	private static final String[] GetUrlShort= {"exists","not-exists"};
	
	private DaoResponse[] newUrlResponses;
	private DaoResponse[] getLongUrlResponses;
	private DaoResponse[] getStatisticResponses;
	private DaoInterface jedis;
	
	@Before
	public void setUp(){
		newUrlResponses = new DaoResponse[NUM_TEST];
		jedis = DaoFactory.getInstance(DaoFactory.JEDIS);
		newshortUrl = generateCustom(longUrl);
		DaoResponse response1 = new DaoResponse();
		response1.setCode(ResponseCode.OK);
		response1.setResponse(newshortUrl);
		DaoResponse response2 = new DaoResponse();
		response2.setCode(ResponseCode.ALREADY_EXISTS);
		response2.setResponse(EXISTS);
		newUrlResponses[0] = response1;
		newUrlResponses[1] = response2;
		
		
		getLongUrlResponses = new DaoResponse[NUM_TEST];
		DaoResponse geturlresponse1 = new DaoResponse();
		geturlresponse1.setCode(ResponseCode.OK);
		geturlresponse1.setResponse(longUrl);
		DaoResponse geturlresponse2= new DaoResponse();
		geturlresponse2.setCode(ResponseCode.NOT_EXISTS);
		geturlresponse2.setResponse(NOTEXISTS);
		getLongUrlResponses[0] = geturlresponse1;
		getLongUrlResponses[1] = geturlresponse2;
		jedis.newUrl(GetUrlShort[0], longUrl);
		
		getStatisticResponses = new DaoResponse[NUM_TEST];
		DaoResponse getstatresp1 = new DaoResponse();
		getstatresp1.setCode(ResponseCode.OK);
		getstatresp1.setResponse(new LinkedList<Click>());
		DaoResponse getstatresp2 = new DaoResponse();
		getstatresp2.setCode(ResponseCode.NOT_EXISTS);
		getstatresp2.setResponse("");
		getStatisticResponses[0] = getstatresp1;
		getStatisticResponses[1] = getstatresp2;

		
	}
	
	@Test
	public final void newUrlTest(){
		for(int  i=0; i < NUM_TEST; i++){
			assertTrue(newUrlResponses[i].equals(jedis.newUrl(newshortUrl, longUrl)));
		
		}
	}
	
	@Test
	public final void getLongUrlTest(){
		for(int i=0; i < NUM_TEST; i++){
			assertTrue(getLongUrlResponses[i].equals(jedis.getUrl(GetUrlShort[i], longUrl,browser)));
		}
		
	}
	
	@Test
	public final void getStatisticTest() {
		for(int i=0; i< NUM_TEST; i++) {
			assertTrue(getStatisticResponses[i].getCode().equals(jedis.getStatistics(GetUrlShort[i]).getCode())
			&& getStatisticResponses[i].getResponse().getClass().equals(jedis.getStatistics(GetUrlShort[i]).getResponse().getClass()));
		}
	}
	private  String generateCustom(String url) {
		String result = null;
		if (url == null || url.equals("") ) 
			return null;
		
		else {			
			try {
				Random r = new Random();
				byte[] urlBytes = url.getBytes("UTF-8");
				byte[] random = new byte[urlBytes.length];
				r.nextBytes(random);
				byte[] resultByte = new byte[urlBytes.length];
				
				for (int i = 0; i < urlBytes.length; i++) 
					resultByte[i] = (byte) (urlBytes[i] + random[i]);
				
				MessageDigest md;
				byte[] hashBytes = null;
				md = MessageDigest.getInstance("SHA-512");

				hashBytes = md.digest(resultByte);
				hashBytes = Arrays.copyOf(hashBytes, 6);
				result = Base64.getUrlEncoder().encodeToString(hashBytes);
				result = result.replaceAll("/", "@");

				} 
				catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					
					return null;
				}
			}
			
		return result;
	}
}


