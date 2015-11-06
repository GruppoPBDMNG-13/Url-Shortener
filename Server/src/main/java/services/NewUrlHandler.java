
package services;



import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import entity.NewUrlRequest;
import entity.NewUrlResponse;
import entity.CommonResponse;
import spark.Request;
import utility.ResponseCode;
/**
 * Class that handles a request for a new shortened url
 * @author Mattia Menna
 * @author Giuseppe Onesto
 */
public class NewUrlHandler{
	
	/**
	 * Method that builds the response of the request
	 * @return The response
	 */
	public String process(Request clientRequest){
		Gson gson = new GsonBuilder().create();
		
		
		NewUrlRequest req = gson.fromJson(clientRequest.body(), NewUrlRequest.class);
		String s = req.getShortUrl();
		
		if(s!=null) {
			CommonResponse resp;
			if(appropriateCustom(s)) 
				resp = ServiceManager.newUrl(req);	
			else {
				resp=new NewUrlResponse();
				resp.setCode(ResponseCode.BAD_INPUT);
			}

			return gson.toJson(resp);	
		}
		
		while(s == null)
			s=generateCustom(req.getLongUrl());	
			
		req.setShortUrl(s);
		CommonResponse resp = ServiceManager.newUrl(req);
		
		return gson.toJson(resp);
	}
	
	/**
	 * Method that checks for bad words
	 * @param shortUrl String to check
	 * @return Outcome of the check
	 */
	private boolean appropriateCustom(String shortUrl) {
		final String RELATIVE_PATH = "/src/main/java/services/badwords.json";
		
		JsonObject badJson;
		JsonParser parser = new JsonParser();
		
		try {
			
			Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + RELATIVE_PATH ));
			badJson = (JsonObject)obj;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
			badJson = null;
			e.printStackTrace();
		}
		
		
		boolean appropriate = true;
		String aNastyWord ;
		
		for (Map.Entry<String, JsonElement> entry : badJson.entrySet()){
			
			JsonArray jarray = entry.getValue().getAsJsonArray();
			for (JsonElement j : jarray){
				aNastyWord = j.toString();
				aNastyWord = Normalizer.normalize(aNastyWord, Normalizer.Form.NFD)
							.replaceAll("[^0-9.a-zA-Z-_]", "");
				
				
				int intIndex = shortUrl.indexOf(aNastyWord);
				
				if(intIndex != -1){
					appropriate = false; 
					break;
				}	
			}
		} 
		
		return appropriate;
	}

		
	
	/**
	 * Method for the generation of a shortUrl based on a hash algorithm on the long url
	 * @param url long url
	 * @return The generated shortUrl
	 */
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
