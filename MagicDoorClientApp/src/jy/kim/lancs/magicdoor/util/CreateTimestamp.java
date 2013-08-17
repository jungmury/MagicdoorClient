package jy.kim.lancs.magicdoor.util;

import java.sql.Timestamp;
import java.util.Date;

public class CreateTimestamp {
	/*
	private static CreateTimestamp instance = null;

	static CreateTimestamp getInstance() {
		if (instance == null) {
			instance = new CreateTimestamp();
		}
		return instance;
	}
*/
	public static Timestamp getTodayTimestamp() {
		Timestamp writtenDate = null;
		Date date = new Date();
		long time = date.getTime();
		writtenDate = new Timestamp(time);

		return writtenDate;
	}
}