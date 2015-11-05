package services;

import spark.Request;

/**
 * Common interface to handle a Spark incoming request
 * @author Honestus
 *
 */
public interface BaseHandler {
	/**
	 * Method to process a client request
	 * @param r: the client request
	 * @return an opportune response to the request handled
	 */
	public String process(Request r);
}
