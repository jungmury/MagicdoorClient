package jy.kim.lancs.magicdoor.timetable;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import jy.kim.lancs.magicdoor.asynctask.GetDayTimeTable;
import jy.kim.lancs.magicdoor.asynctask.GetWeekTimeTable;
import jy.kim.lancs.magicdoor.asynctask.PostTimeTable;
import jy.kim.lancs.magicdoor.asynctask.PutTimeTable;
import jy.kim.lancs.magicdoor.bean.TimeTableBean;
import jy.kim.lancs.magicdoor.query.QueryForMagicDoor;
import jy.kim.lancs.magicdoor.query.QueryForTimeTable;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class TimeTable {
	public String userName;

	private Activity _activity;

	public TimeTable(Activity activity) {
		// TODO Auto-generated constructor stub
		this._activity = activity;
		Intent prevActivity = activity.getIntent();
		userName = prevActivity.getStringExtra("userName");
		Log.i("MagicDoor", "Timetable userName = " + userName);
		// userName = "ken";
	}

	public ArrayList<TimeTableBean> getWeekTimeTable(QueryForMagicDoor query) {
		AsyncTask<QueryForMagicDoor, Void, ArrayList<TimeTableBean>> info = new GetWeekTimeTable(
				_activity).execute(query);
		ArrayList<TimeTableBean> result = null;

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

	public ArrayList<TimeTableBean> insertTimeTable(TimeTableBean timeTable) {
		// TODO Auto-generated method stub
		AsyncTask<TimeTableBean, Void, ArrayList<TimeTableBean>> info = new PostTimeTable(
				_activity).execute(timeTable);

		ArrayList<TimeTableBean> result = null;

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

	public ArrayList<TimeTableBean> updateTimeTable(TimeTableBean timeTable) {
		// TODO Auto-generated method stub
		AsyncTask<TimeTableBean, Void, ArrayList<TimeTableBean>> info = new PutTimeTable(
				_activity).execute(timeTable);

		ArrayList<TimeTableBean> result = null;

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

	public ArrayList<TimeTableBean> getTimeDayTable(QueryForTimeTable query) {
		// TODO Auto-generated method stub
		AsyncTask<QueryForTimeTable, Void, ArrayList<TimeTableBean>> info = new GetDayTimeTable(
				_activity).execute(query);
		ArrayList<TimeTableBean> result = null;

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
