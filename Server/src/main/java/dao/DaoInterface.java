package dao;

import java.util.List;

import entity.Click;

public interface DaoInterface {
	/**
	 * 
	 * @param longUrl
	 * @param shortUrl
	 * @return: response about the request to insert a new Url in the database
	 */
	public String newUrl(String longUrl, String shortUrl);
	/**
	 * 
	 * @param shortUrl
	 * @param browser
	 * @param ip
	 * @param dateTime
	 * @return the longUrl associated to the shortUrl in input 
	 * if it exists in the database; false otherwise
	 */
	public String getUrl(String shortUrl, String browser, String ip, String dateTime);
	/**
	 * 
	 * @param shortUrl
	 * @return a list of Click which contains metadata about clicks for the shortUrl in input
	 */
	public List<Click> getStatistics(String shortUrl);
	/**
	 * 
	 * @param shortUrl
	 * @return response about the request to delete an existing shortUrl from the database
	 */
	public String deleteUrl(String shortUrl);
}
