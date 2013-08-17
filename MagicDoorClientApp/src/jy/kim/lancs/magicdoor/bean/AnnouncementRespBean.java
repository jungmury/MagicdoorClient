package jy.kim.lancs.magicdoor.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class AnnouncementRespBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int announcementNum;
	public String title;
	public String announcementContent;
	public Timestamp writtenDate;
	public String announcerName;

	public String userName;

	public AnnouncementRespBean() {
	}
}
