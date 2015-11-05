package dao;

/**
 * Interface which offers the methods that will be implemented in a class which represents
 * a well determined database(such as JedisDb)
 *
 * @author Giuseppe Onesto
 * @author Mattia Menna
 */
public interface DaoInterface {
	/**
	 * 
	 * @param shortUrl
	 * @param longUrl
	 * @return: a DaoResponse which Response contains the shortUrl associated to the longUrl
	 */
	public DaoResponse newUrl(String shortUrl, String longUrl);
	/**
	 * 
	 * @param shortUrl
	 * @param browser
	 * @param ip
	 * @param dateTime
	 * @return a DaoResponse which Response contains the longUrl associated to the shortUrl 
	 * in input, if it exists in the database; a message of error or missing otherwise
	 */
	public DaoResponse getUrl(String shortUrl, String browser, String ip, String dateTime);
	/**
	 * 
	 * @param shortUrl
	 * @return a DaoResponse which Response contains the clicks made on the shortUrl in input
	 */
	public DaoResponse getStatistics(String shortUrl);
	
	/**
	 * 
	 * @param shortUrl
	 * @param lastN: number of last clicks made on shortUrl to get from Redis
	 * @return a DaoResponse which Response contains the last n clicks made on the shortUrl in input
	 */
	public DaoResponse getLastNStatistics(String shortUrl, int lastN);
	/**
	 * 
	 * @param shortUrl
	 * @return a DaoResponse which Response will be true if the shortUrl has been correctly deleted
	 * from Redis, false otherwise.
	 */
	public DaoResponse deleteUrl(String shortUrl);
}
