package utility;

/**
 * 
 * @author Giuseppe Onesto
 * @author Mattia Menna
 */
public enum RequestCode {
	URL(0),
	STATISTIC(1);
	
	private int code;
	
	/**
	 * Constructor: Assigns to the attribute "code" of this DaoRequestCode the int in input
	 * @param c
	 */
	private RequestCode(int i) {
		this.code=i;
	}
	
	/**
	 * 
	 * @return the value of the attribute "code" of this DaoRequestCode
	 */
	public int getCode() {
		return this.code;
	}
}
