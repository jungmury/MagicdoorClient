package jy.kim.lancs.magicdoor.bean;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class AppointmentRespDetailBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String refValue;
	public String title;
	public String name;
	public String requesterUserName;
	public String description;
	public Date bookingDate;
	public Time bookingTime;
	public String status;
	public String note;
	public int duration;
}
