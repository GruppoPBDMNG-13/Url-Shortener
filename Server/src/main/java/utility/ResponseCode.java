package utility;

/**
 * @author Giuseppe Onesto
 * @author Mattia Menna
 *
 */
public enum ResponseCode {
	
	NOT_CONNECTED(-1),
	NOT_EXISTS(-2),
	ALREADY_EXISTS(-3),
	BAD_INPUT(-4),
	ERROR(-5),
	ERROR_GENERATING_URL(-6),
	OK(0);
	
	private int code;
	
	/**
	 * Assigns to the attribute "code" of this DaoResponseCode the int in input
	 * @param i
	 */
	private ResponseCode(int i) {
		this.code=i;
	}
	
	/**
	 * Assigns to the attribute "code" of this DaoResponseCode the int in input
	 * @param i
	 */
	public void setCode(int i) {
		this.code=i;
	}
	
	/**
	 * 
	 * @return the value of the attribute code of this DaoResponseCode
	 */
	public int getCode() {
		return this.code;
	}
	
}
