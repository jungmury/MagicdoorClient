package jy.kim.lancs.magicdoor.appointment;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import jy.kim.lancs.magicdoor.asynctask.DeleteAnAppointmentOfTheUser;
import jy.kim.lancs.magicdoor.asynctask.GetAllAppointmentsOfTheLecturerUser;
import jy.kim.lancs.magicdoor.asynctask.GetLecturerInfoForTheRejectedAppointment;
import jy.kim.lancs.magicdoor.asynctask.PostAppointmentReqToTheTagOwner;
import jy.kim.lancs.magicdoor.asynctask.GetAllAppointmentsOfTheStudentUser;
import jy.kim.lancs.magicdoor.asynctask.PostAppointmentsOfTheLecturerUserInTheDate;
import jy.kim.lancs.magicdoor.asynctask.PostAppointmentsOfTheLecturerUserInTheMonth;
import jy.kim.lancs.magicdoor.asynctask.PutConfirmAppointment;
import jy.kim.lancs.magicdoor.asynctask.PutRejectAppointments;
import jy.kim.lancs.magicdoor.bean.AppointmentReqDetailBean;
import jy.kim.lancs.magicdoor.bean.AppointmentRespDetailBean;
import jy.kim.lancs.magicdoor.bean.UserAccountInfoBean;
import jy.kim.lancs.magicdoor.query.QueryForAppointments;
import jy.kim.lancs.magicdoor.query.QueryForMagicDoor;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

public class Appointment {

	public String tagOwner;
	public String tagOwnerName;
	public String userName;

	private Activity activity;

	public Appointment(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		Intent prevActivity = activity.getIntent();
		tagOwnerName = prevActivity.getStringExtra("tagOwnerName");
		tagOwner = prevActivity.getStringExtra("tagOwner");
		userName = prevActivity.getStringExtra("userName");

	}

	// request appointments list of the tag owner
	// param includes uri and tagOwner
	public ArrayList<AppointmentRespDetailBean> getAllAppointmentsOfTheLecturerUserInTheMonth(
			QueryForAppointments param) {
		// TODO Auto-generated method stub
		ArrayList<AppointmentRespDetailBean> result = null;
		AsyncTask<QueryForAppointments, Void, ArrayList<AppointmentRespDetailBean>> info = new PostAppointmentsOfTheLecturerUserInTheMonth(
				activity).execute(param);

		try {
			result = info.get();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// perform when item selected
		return result;

	}
	
	public ArrayList<AppointmentRespDetailBean> getAppointmentsOfTheLecturerUserInTheWeek(
			QueryForAppointments param) {
		// TODO Auto-generated method stub
		ArrayList<AppointmentRespDetailBean> result = null;
		AsyncTask<QueryForAppointments, Void, ArrayList<AppointmentRespDetailBean>> info = new PostAppointmentsOfTheLecturerUserInTheMonth(
				activity).execute(param);

		try {
			result = info.get();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// perform when item selected
		return result;

	}
	
	

	// request new appointment
	public ArrayList<AppointmentRespDetailBean> putAppointmentWithInfo(
			AppointmentReqDetailBean bean) {
		// TODO Auto-generated method stub
		ArrayList<AppointmentRespDetailBean> result = null;
		// uri included to object
		AsyncTask<AppointmentReqDetailBean, Void, ArrayList<AppointmentRespDetailBean>> info = new PostAppointmentReqToTheTagOwner(
				activity).execute(bean);

		try {
			result = info.get();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// perform when item selected
		return result;

	}

	// param includes uri and tagOwner
	public ArrayList<AppointmentRespDetailBean> getAllAppointmentsOfTheLecturerUserOnTheDate(
			QueryForAppointments param) {
		// TODO Auto-generated method stub
		ArrayList<AppointmentRespDetailBean> result = null;
		AsyncTask<QueryForAppointments, Void, ArrayList<AppointmentRespDetailBean>> info = new PostAppointmentsOfTheLecturerUserInTheDate(
				activity).execute(param);

		try {
			result = info.get();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// perform when item selected
		return result;

	}

	// get appointments for a student user
	public ArrayList<AppointmentRespDetailBean> getAllAppointmentsOfTheStudentUser(
			QueryForAppointments query) {
		// TODO Auto-generated method stub
		ArrayList<AppointmentRespDetailBean> result = null;
		AsyncTask<String, Void, ArrayList<AppointmentRespDetailBean>> info = new GetAllAppointmentsOfTheStudentUser(
				activity).execute(query.uri + query.requesterUserName);

		try {
			result = info.get();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// perform when item selected
		return result;
	}

	// delete an appointment for a student user
	public ArrayList<AppointmentRespDetailBean> deleteAnAppointmentOfTheUser(
			QueryForAppointments query) {
		// TODO Auto-generated method stub
		ArrayList<AppointmentRespDetailBean> result = null;

		AsyncTask<String, Void, ArrayList<AppointmentRespDetailBean>> info = new DeleteAnAppointmentOfTheUser(
				activity).execute(query.uri + query.requesterUserName + "/"
				+ query.refValue);

		try {
			result = info.get();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// perform when item selected
		return result;
	}

	public ArrayList<AppointmentRespDetailBean> getAllAppointmentsOfTheLecturerUser(
			QueryForMagicDoor query) {
		// TODO Auto-generated method stub
		ArrayList<AppointmentRespDetailBean> result = null;

		AsyncTask<String, Void, ArrayList<AppointmentRespDetailBean>> info = new GetAllAppointmentsOfTheLecturerUser(
				activity).execute(query.uri + query.tagOwner);

		try {
			result = info.get();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// perform when item selected
		return result;
	}

	public ArrayList<AppointmentRespDetailBean> rejectAppointment(
			QueryForAppointments query) {
		// TODO Auto-generated method stub
		ArrayList<AppointmentRespDetailBean> result = null;

		AsyncTask<QueryForAppointments, Void, ArrayList<AppointmentRespDetailBean>> info = new PutRejectAppointments(
				activity).execute(query);

		try {
			result = info.get();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// perform when item selected
		return result;
	}

	public ArrayList<AppointmentRespDetailBean> confirmAppointment(
			QueryForAppointments query) {
		// TODO Auto-generated method stub
		ArrayList<AppointmentRespDetailBean> result = null;

		AsyncTask<String, Void, ArrayList<AppointmentRespDetailBean>> info = new PutConfirmAppointment(
				activity).execute(query.uri + query.tagOwner + "/"
				+ query.refValue);

		try {
			result = info.get();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// perform when item selected
		return result;
	}

	public UserAccountInfoBean getLecutrerInfoForTheRejectedAppointment(String uri,
			String refValue) {
		// TODO Auto-generated method stub
		UserAccountInfoBean result = null;

		AsyncTask<String, Void, UserAccountInfoBean> info = new GetLecturerInfoForTheRejectedAppointment(
				activity).execute(uri + refValue);

		try {
			result = info.get();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// perform when item selected
		return result;
	}
}
