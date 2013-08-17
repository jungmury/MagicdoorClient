package jy.kim.lancs.magicdoor.util;

import java.sql.Date;

import android.util.Log;

public class DateParseUtil {
	private static DateParseUtil instance = null;

	static DateParseUtil getInstance() {
		if (instance == null) {
			instance = new DateParseUtil();
		}
		return instance;
	}

	public static String getDateInNewFormat(Date date) {
		String[] givenDate = date.toString().split("-");
		int i = 0;
		for (String string : givenDate) {
			Log.i("MagicDoor", i + " " + string);
			i++;
		}
		return givenDate[2] + "-" + givenDate[1] + "-" + givenDate[0];
	}
}
