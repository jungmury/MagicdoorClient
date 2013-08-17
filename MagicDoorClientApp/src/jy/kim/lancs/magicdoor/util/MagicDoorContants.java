package jy.kim.lancs.magicdoor.util;

public class MagicDoorContants {// 10.32.81.161
	private final static String LOCAL_HOST_ADDR = "http://10.0.2.2:8080/magicdoor/restful/webservice";

	// POST New Announcement
	private final static String URI_FOR_ADDING_NEW_ANNOUNCEMENT = "/post-announcement/";
	
	//PUT Announcement
	private static final String URI_FOR_UPDATING_AN_ANNOUNCEMENT = "/put-announcement/";

	// DELETE An Announcement
	private final static String URI_FOR_DELETING_AN_ANNOUNCEMENT = "/delete-announcement/";
	// GET All Appointments for the lecturer user
	private final static String URI_FOR_REQUESTING_ALL_APPOINTMENTS_OF_THE_LECTURER_USER = "/get-all-appointments-of-the-lecturer-user/";
	// POST Reject An Appointment
	private final static String URI_FOR_REJECTING_APPOINTMENT = "/reject-appointment/";

	// POST Confirm An Appointment
	private final static String URI_FOR_COFIRMING_APPOINTMENT = "/confirm-appointment/";

	
	
	
	// GET Web service description
	private final static String URI_FOR_WEBSERVICE = "/get-web-service/";

	private static final String URI_FOR_WEBSERVICE_FOR_QR_CODE = "/get-web-service-for-qr-code/";

	
	
	// PUT New Appointment
	private final static String URI_FOR_REQUESTING_NEW_APPOINTMENT = "/request-appointment/";
	// GET All Appointments requested by the student
	private final static String URI_FOR_REQUESTING_ALL_APPOINTMENTS_LIST_FOR_THE_STUDENT_USER = "/get-all-appointments-of-the-student-user/";
	// DELETE An Appointment Request
	private final static String URI_FOR_DELETING_AN_APPOINTMENT_OF_THE_STUDENT_USER = "/delete-an-appointment-of-the-student-user/";
	
	
	// GET All appointments of the lecturer in the date
	private final static String URI_FOR_GETTING_ALL_APPOINTMENTS_OF_THE_LECTURER_USER_ON_THE_DATE = "/get-all-appointments-of-the-lecturer-user-on-the-date/";

	// To display appointments on the calendar
	private final static String URI_FOR_GETTING_ALL_APPOINTMENTS_OF_THE_LECTURER_USER_IN_THE_MONTH = "/get-appointments-of-the-lecturer-user-in-the-month/";

	private static final String URI_FOR_GETTING_APPOINTMENTS_OF_THE_LECTURER_USER_IN_THE_WEEK = "/get-appointments-of-the-lecturer-user-in-the-week/";
	
	private static final String URI_FOR_GETTING_LECTURER_INFO_FOR_THE_REJECTED_APPOINTMENT = "/get-lecturer-info-for-the-rejected-appointment/";

	
	//GET All messages for sender
	private final static String URI_FOR_GET_ALL_MESSAGES_FOR_SENDER = "/get-all-messages-of-the-sender/";
	//GET ALL sent messages for sender
	private final static String URI_FOR_GET_ALL_SENT_MESSAGES_FOR_SENDER = "/get-all-sent-messages-of-the-sender/";
	//GET ALL received messages for sender	
	private final static String URI_FOR_GET_ALL_RECEIVED_MESSAGES_FOR_SENDER = "/get-all-received-messages-of-the-sender/";
	
	
	//POST new message
	private final static String URI_FOR_POST_MESSAGE = "/post-new-message/";
	
	
	//GET All received messages for receiver
	private final static String URI_FOR_GET_ALL_RECEIVED_MESSAGES_FOR_RECEIVER = "/get-all-received-messages-of-the-receiver/";
	//GET all sent messages for receiver
	private final static String URI_FOR_GET_ALL_SENT_MESSAGES_FOR_RECEIVER = "/get-all-sent-messages-of-the-receiver/";
	
	//POST reply message
	private final static String URI_FOR_REPLYING_MESSAGE = "/post-reply-message/";
	
	//NFC TAG
	private final static String URI_FOR_ADDING_NEW_NFC_TAG = "/post-new-nfc-tag";

	private static final String URI_FOR_GETTING_ALL_TAGS_OF_THE_OWNER = "/get-all-nfc-tags-of-the-tag-owner";

	
	// For Both type of users
	private final static String URI_FOR_GETTING_ACC_INFO = "/get-account-info/";
	private final static String URI_FOR_UPDATING_ACC_INFO = "/update-account-info/";
	private final static String URI_FOR_LOG_IN = "/log-in/";
	private final static String URI_FOR_DUPLICATE_ID_CHECK = "/check-duplicate-id/";
	private final static String URI_FOR_SIGN_UP = "/sign-up/";
	private final static String URI_FOR_SHOWING_ANNOUNCEMENTS = "/get-all-announcements/";

	private final static String URI_FOR_UPDATING_TIME_TABLE = "/update-time-table/";

	private final static String URI_FOR_INSERTING_TIME_TABLE = "/insert-time-table/";

	private final static String URI_FOR_GETTING_WEEK_TIME_TABLE = "/get-week-time-table/";

	private final static String URI_FOR_GETTING_DAY_TIME_TABLE = "/get-day-time-table/";

	public final static String REST_SERVICE_URI_FOR_GETTING_ACC_INFO = LOCAL_HOST_ADDR
			+ URI_FOR_GETTING_ACC_INFO;

	public final static String REST_SERVICE_URI_FOR_UPDATE_ACC_INFO = LOCAL_HOST_ADDR
			+ URI_FOR_UPDATING_ACC_INFO;

	public final static String REST_SERVICE_URI_FOR_LOG_IN = LOCAL_HOST_ADDR
			+ URI_FOR_LOG_IN;

	public final static String REST_SERVICE_URI_FOR_DUP_ID_CHK = LOCAL_HOST_ADDR
			+ URI_FOR_DUPLICATE_ID_CHECK;

	public final static String REST_SERVICE_URI_FOR_SIGN_UP = LOCAL_HOST_ADDR
			+ URI_FOR_SIGN_UP;

	public final static String REST_SERVICE_URI_FOR_WEB_SERIVCE = LOCAL_HOST_ADDR
			+ URI_FOR_WEBSERVICE;

	public final static String REST_SERVICE_URI_FOR_WEB_SERIVCE_FOR_QR_CODE = LOCAL_HOST_ADDR
			+ URI_FOR_WEBSERVICE_FOR_QR_CODE;

	
	
	public static final String REST_SERVICE_URI_FOR_SHOWING_ANNOUNCEMENTS = LOCAL_HOST_ADDR
			+ URI_FOR_SHOWING_ANNOUNCEMENTS;

	public static final String REST_SERVICE_URI_FOR_DELETING_AN_ANNOUNCEMENT = LOCAL_HOST_ADDR
			+ URI_FOR_DELETING_AN_ANNOUNCEMENT;

	public static final String REST_SERVICE_URI_FOR_ADDING_ANNOUNCEMENTS = LOCAL_HOST_ADDR
			+ URI_FOR_ADDING_NEW_ANNOUNCEMENT;

	public static final String REST_SERVICE_URI_FOR_RQUEST_APPOINMENT = LOCAL_HOST_ADDR
			+ URI_FOR_REQUESTING_NEW_APPOINTMENT;

	// all requests
	public static final String REST_SERVICE_URI_FOR_RQUEST_ALL_APPOINMENTS_LIST_OF_THE_STUDENT_USER = LOCAL_HOST_ADDR
			+ URI_FOR_REQUESTING_ALL_APPOINTMENTS_LIST_FOR_THE_STUDENT_USER;

	
	public static final String REST_SERVICE_URI_FOR_GETTING_ALL_APPOINTMENTS_OF_THE_LECTURER_USER_IN_THE_MONTH = LOCAL_HOST_ADDR
			+ URI_FOR_GETTING_ALL_APPOINTMENTS_OF_THE_LECTURER_USER_IN_THE_MONTH;

	// get appointments excluding status = "rejected"
	public static final String REST_SERVICE_URI_FOR_GETTING_APPOINTMENTS_OF_THE_LECTURER_USER_IN_THE_WEEK = LOCAL_HOST_ADDR
			+ URI_FOR_GETTING_APPOINTMENTS_OF_THE_LECTURER_USER_IN_THE_WEEK;
	
	public static final String REST_SERVICE_URI_FOR_DELETING_AN_APPOINTMENT_OF_THE__STUDENT_USER = LOCAL_HOST_ADDR
			+ URI_FOR_DELETING_AN_APPOINTMENT_OF_THE_STUDENT_USER;

	public static final String REST_SERVICE_URI_FOR_REJECTING_APPOINTMENT = LOCAL_HOST_ADDR
			+ URI_FOR_REJECTING_APPOINTMENT;

	public static final String REST_SERVICE_URI_FOR_COFIRMING_APPOINTMENT = LOCAL_HOST_ADDR
			+ URI_FOR_COFIRMING_APPOINTMENT;

	public static final String REST_SERVICE_URI_FOR_REQUESTING_ALL_APPOINTMENTS_OF_THE_LECTURER_USER = LOCAL_HOST_ADDR
			+ URI_FOR_REQUESTING_ALL_APPOINTMENTS_OF_THE_LECTURER_USER;

	public static final String REST_SERVICE_URI_FOR_GETTING_ALL_APPOINTMENTS_OF_THE_LECTURER_USER_ON_THE_DATE = LOCAL_HOST_ADDR
			+ URI_FOR_GETTING_ALL_APPOINTMENTS_OF_THE_LECTURER_USER_ON_THE_DATE;

	public static final String REST_SERVICE_URI_FOR_UPDATING_TIME_TABLE = LOCAL_HOST_ADDR
			+ URI_FOR_UPDATING_TIME_TABLE;

	public static final String REST_SERVICE_URI_FOR_INSERTING_TIME_TABLE = LOCAL_HOST_ADDR
			+ URI_FOR_INSERTING_TIME_TABLE;

	public static final String REST_SERVICE_URI_FOR_GETTING_WEEK_TIME_TABLE = LOCAL_HOST_ADDR
			+ URI_FOR_GETTING_WEEK_TIME_TABLE;

	public static final String REST_SERVICE_URI_FOR_GETTING_DAY_TIME_TABLE = LOCAL_HOST_ADDR
			+ URI_FOR_GETTING_DAY_TIME_TABLE;
	
	public static final String REST_SERVICE_URI_FOR_GET_ALL_MESSAGES_FOR_SENDER = LOCAL_HOST_ADDR + URI_FOR_GET_ALL_MESSAGES_FOR_SENDER;
	
	public static final String REST_SERVICE_URI_FOR_POST_MESSAGE = LOCAL_HOST_ADDR + URI_FOR_POST_MESSAGE;
	
	public static final String REST_SERVICE_URI_FOR_REPLYING_MESSAGE = LOCAL_HOST_ADDR + URI_FOR_REPLYING_MESSAGE;

	public static final String  REST_SERVICE_URI_FOR_GET_ALL_RECEIVED_MESSAGES_FOR_RECEIVER = LOCAL_HOST_ADDR + URI_FOR_GET_ALL_RECEIVED_MESSAGES_FOR_RECEIVER;
	
	public static final String REST_SERVICE_URI_FOR_GET_ALL_SENT_MESSAGES_FOR_SENDER = LOCAL_HOST_ADDR + URI_FOR_GET_ALL_SENT_MESSAGES_FOR_SENDER;

	public static final String REST_SERVICE_URI_FOR_GET_ALL_RECEIVED_MESSAGES_FOR_SENDER = LOCAL_HOST_ADDR + URI_FOR_GET_ALL_RECEIVED_MESSAGES_FOR_SENDER;

	public static final String REST_SERVICE_URI_FOR_GET_ALL_SENT_MESSAGES_FOR_RECEIVER = LOCAL_HOST_ADDR + URI_FOR_GET_ALL_SENT_MESSAGES_FOR_RECEIVER;

	public static final String REST_SERVICE_URI_FOR_ADDING_NEW_NFC_TAG = LOCAL_HOST_ADDR + URI_FOR_ADDING_NEW_NFC_TAG;

	public static final String REST_SERVICE_URI_FOR_UPDATING_AN_ANNOUNCEMENT = LOCAL_HOST_ADDR + URI_FOR_UPDATING_AN_ANNOUNCEMENT ;

	public static String REST_SERVICE_URI_FOR_GETTING_ALL_TAGS_OF_THE_OWNER = LOCAL_HOST_ADDR + URI_FOR_GETTING_ALL_TAGS_OF_THE_OWNER;
	
	public final static String STUDENT_USER = "student";
	public final static String LECTURER_USER = "lecturer";

	public static final String REST_SERVICE_URI_FOR_GETTING_LECTURER_INFO_FOR_THE_REJECTED_APPOINTMENT = LOCAL_HOST_ADDR + URI_FOR_GETTING_LECTURER_INFO_FOR_THE_REJECTED_APPOINTMENT;

	private static MagicDoorContants instance = null;

	static MagicDoorContants getInstance() {
		if (instance == null) {
			instance = new MagicDoorContants();
		}
		return instance;
	}

	public MagicDoorContants() {
		// Exists only to defeat instantiation.
	}
}
