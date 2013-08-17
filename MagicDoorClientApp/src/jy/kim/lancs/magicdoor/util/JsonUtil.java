package jy.kim.lancs.magicdoor.util;

import java.io.UnsupportedEncodingException;

import jy.kim.lancs.magicdoor.bean.AnnouncementReqBean;
import jy.kim.lancs.magicdoor.bean.AppointmentReqDetailBean;
import jy.kim.lancs.magicdoor.bean.LogInInfoReqBean;
import jy.kim.lancs.magicdoor.bean.MessageBean;
import jy.kim.lancs.magicdoor.bean.NFCTagInfoBean;
import jy.kim.lancs.magicdoor.bean.TimeTableBean;
import jy.kim.lancs.magicdoor.bean.UserAccountInfoBean;
import jy.kim.lancs.magicdoor.query.QueryForAppointments;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
	private static JsonUtil instance = null;

	static JsonUtil getInstance() {
		if (instance == null) {
			instance = new JsonUtil();
		}
		return instance;
	}

	public JsonUtil() {
		// Exists only to defeat instantiation.
	}

	public static StringEntity getJsonUserName(LogInInfoReqBean[] params) {
		// TODO Auto-generated method stub
		JSONObject jObjectData = new JSONObject();
		StringEntity stringEntity = null;

		try {
			jObjectData.put("userName", params[0].userName);
			stringEntity = new StringEntity(jObjectData.toString());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// /////////////////
		return stringEntity;
	}

	// generate json data from LogInInfoReqBean object
	public static StringEntity getJsonUserNameAndPassword(
			LogInInfoReqBean[] params) {
		// TODO Auto-generated method stub
		JSONObject jObjectData = new JSONObject();
		StringEntity stringEntity = null;

		try {
			jObjectData.put("userName", params[0].userName);
			jObjectData.put("password", params[0].password);
			stringEntity = new StringEntity(jObjectData.toString());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// /////////////////
		return stringEntity;
	}

	public static StringEntity getJsonSignUpInfo(UserAccountInfoBean[] params) {
		// TODO Auto-generated method stub
		JSONObject jObjectData = new JSONObject();
		StringEntity stringEntity = null;

		try {
			jObjectData.put("userName", params[0].userName);
			jObjectData.put("password", params[0].password);
			jObjectData.put("firstName", params[0].firstName);
			jObjectData.put("lastName", params[0].lastName);
			jObjectData.put("eMailAddress", params[0].eMailAddress);
			jObjectData.put("userType", params[0].userType);
			stringEntity = new StringEntity(jObjectData.toString());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// /////////////////
		return stringEntity;
	}

	public static StringEntity getJsonUpdateAccountInfo(
			UserAccountInfoBean[] params) {
		// TODO Auto-generated method stub
		JSONObject jObjectData = new JSONObject();
		StringEntity stringEntity = null;

		try {
			jObjectData.put("userName", params[0].userName);
			jObjectData.put("password", params[0].password);
			jObjectData.put("firstName", params[0].firstName);
			jObjectData.put("lastName", params[0].lastName);
			jObjectData.put("eMailAddress", params[0].eMailAddress);
			stringEntity = new StringEntity(jObjectData.toString());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// /////////////////
		return stringEntity;
	}

	public static StringEntity getJsonNewAnnouncement(
			AnnouncementReqBean[] params) {
		// TODO Auto-generated method stub
		JSONObject jObjectData = new JSONObject();
		StringEntity stringEntity = null;

		try {
			jObjectData.put("userName", params[0].userName);
			jObjectData.put("title", params[0].title);
			jObjectData.put("content", params[0].announcementContent);
			jObjectData.put("writtenDate", params[0].writtenDate);
			stringEntity = new StringEntity(jObjectData.toString());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// /////////////////
		return stringEntity;
	}

	public static StringEntity getJsonNewMessage(MessageBean[] params) {
		// TODO Auto-generated method stub
		JSONObject jObjectData = new JSONObject();
		StringEntity stringEntity = null;

		try {
			jObjectData.put("msgNo", params[0].msgNo);
			jObjectData.put("content", params[0].content);
			jObjectData.put("receiver", params[0].receiver);
			jObjectData.put("sender", params[0].sender);
			jObjectData.put("sentDate", params[0].sentDate.getTime());
			jObjectData.put("status", params[0].status);

			stringEntity = new StringEntity(jObjectData.toString());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// /////////////////
		return stringEntity;
	}

	public static StringEntity getJsonNewAppointment(
			AppointmentReqDetailBean[] params) {
		// TODO Auto-generated method stub
		JSONObject jObjectData = new JSONObject();
		StringEntity stringEntity = null;

		try {
			jObjectData.put("refValue", params[0].refValue);
			jObjectData.put("title", params[0].title);
			jObjectData.put("lecturerUserName", params[0].lecturerUserName);
			jObjectData.put("requesterUserName", params[0].requesterUserName);
			jObjectData.put("description", params[0].description);
			jObjectData.put("bookingDate", params[0].bookingDate);
			jObjectData.put("bookingTime", params[0].bookingTime);
			jObjectData.put("status", params[0].status);
			jObjectData.put("duration", params[0].duration);
			stringEntity = new StringEntity(jObjectData.toString());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// /////////////////
		return stringEntity;
	}

	public static StringEntity getJsonQueryForAppointmentsWithDateOnly(
			QueryForAppointments[] params) {
		// TODO Auto-generated method stub
		JSONObject jObjectData = new JSONObject();
		StringEntity stringEntity = null;

		try {
			jObjectData.put("requesterUserName", params[0].requesterUserName);
			jObjectData.put("tagOwner", params[0].tagOwner);
			jObjectData.put("date", params[0].date);
			stringEntity = new StringEntity(jObjectData.toString());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// /////////////////
		return stringEntity;
	}

	public static StringEntity getJsonQuery(QueryForAppointments[] params) {
		// TODO Auto-generated method stub
		JSONObject jObjectData = new JSONObject();
		StringEntity stringEntity = null;

		try {
			jObjectData.put("date", params[0].date);
			jObjectData.put("status", params[0].status);
			jObjectData.put("requesterUserName", params[0].requesterUserName);
			jObjectData.put("tagOwner", params[0].tagOwner);
			jObjectData.put("note", params[0].note);
			jObjectData.put("refValue", params[0].refValue);

			stringEntity = new StringEntity(jObjectData.toString());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// /////////////////
		return stringEntity;
	}

	public static StringEntity getJsonTimeTable(TimeTableBean[] params) {
		// TODO Auto-generated method stub
		JSONObject jObjectData = new JSONObject();
		StringEntity stringEntity = null;

		try {
			jObjectData.put("id", params[0].id);
			jObjectData.put("subject", params[0].subject);
			jObjectData.put("day", params[0].day);
			jObjectData.put("time", params[0].time);
			jObjectData.put("userName", params[0].userName);
			stringEntity = new StringEntity(jObjectData.toString());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// /////////////////
		return stringEntity;
	}

	public static StringEntity getJsonTagInfo(NFCTagInfoBean[] params) {
		// TODO Auto-generated method stub
		JSONObject jObjectData = new JSONObject();
		StringEntity stringEntity = null;

		try {
			jObjectData.put("serviceDescription", params[0].serviceDescription);
			jObjectData.put("tagId", params[0].tagId);
			jObjectData.put("tagOwner", params[0].tagOwner);
			stringEntity = new StringEntity(jObjectData.toString());
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// /////////////////
		return stringEntity;
	}
}
