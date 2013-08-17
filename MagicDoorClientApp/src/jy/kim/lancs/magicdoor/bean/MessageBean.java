package jy.kim.lancs.magicdoor.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class MessageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MessageBean() {
		// TODO Auto-generated constructor stub
	}

	public String uri;
	public int msgNo;
	public Timestamp sentDate;
	public String content;
	public String sender;
	public String name;
	public String receiver;
	public String status;
}
