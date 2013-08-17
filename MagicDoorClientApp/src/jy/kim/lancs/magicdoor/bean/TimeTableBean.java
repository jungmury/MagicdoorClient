package jy.kim.lancs.magicdoor.bean;

import java.io.Serializable;
import java.sql.Time;

public class TimeTableBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public TimeTableBean() {
		// TODO Auto-generated constructor stub
	}
	public int id;
	public String subject;
	public String uri;
	public Time time;
	public String userName;
	public int day;
}
