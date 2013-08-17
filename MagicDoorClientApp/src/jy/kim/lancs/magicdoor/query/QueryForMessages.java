package jy.kim.lancs.magicdoor.query;

import java.sql.Timestamp;

public class QueryForMessages extends QueryForMagicDoor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int msgNo;
	public String status;
	public String sender;
	public String content;
	public Timestamp sentDate;
}
