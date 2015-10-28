package dao;

import dao.jedis.JedisDb;

public class DaoFactory {
	public static final int JEDIS=1;
	
	/**
	 * 
	 * @param i: integer which represents the database to instantiate
	 * @return an instance of desired database
	 */
	public DaoInterface getInstance(int i) {
		DaoInterface db=null;
		
		switch(i) {
		
		case JEDIS:
		db = new JedisDb();
		break;
		
		default:
			break;
		}
		
		return db;
	}
}
