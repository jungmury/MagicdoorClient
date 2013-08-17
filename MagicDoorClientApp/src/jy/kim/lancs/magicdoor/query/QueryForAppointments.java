package jy.kim.lancs.magicdoor.query;

import java.sql.Date;
public class QueryForAppointments extends QueryForMagicDoor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Date date;
	public String status;
	public String requesterUserName;
	public String note;
	public String refValue;
}
