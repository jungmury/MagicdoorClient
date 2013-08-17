package jy.kim.lancs.magicdoor.adapter;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

import jy.kim.lancs.magicdoor.bean.AppointmentRespDetailBean;
import jy.kim.lancs.magicdoor.bean.TimeTableBean;
import jy.kim.lancs.magicdoor.bean.parcelable.BookingDateInfoBean;
import magicdoor.lancs.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TimeAdapter extends ArrayAdapter<AppointmentRespDetailBean> {

	private Calendar calendar;
	private final int OFFICE_HOUR_DURATION = 9;
	private ArrayList<AppointmentRespDetailBean> appsOnTheDate;
	private int resource;
	private ArrayList<AppointmentRespDetailBean> temp;

	// Initialize adapter
	public TimeAdapter(Context context, int resource) {
		super(context, resource);
		this.resource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		AppointmentRespDetailBean app = appsOnTheDate.get(position);

		View timeSlotListView = convertView;
		ViewHolder holder;
		// Get the current alert object

		// Inflate the view
		if (timeSlotListView == null) {
			LayoutInflater vi = (LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			timeSlotListView = vi.inflate(resource, null);

			// use ViewHolder
			holder = new ViewHolder();
			holder.tvTimeSlotTitle = (TextView) timeSlotListView
					.findViewById(R.id.tvTimeSlotTitle);
			holder.tvTimeSlotStatus = (TextView) timeSlotListView
					.findViewById(R.id.tvTimeSlotStatus);
			timeSlotListView.setTag(holder);
		} else {
			holder = (ViewHolder) timeSlotListView.getTag();
		}

		// initialize the view for use
		timeSlotListView.setEnabled(true);
		holder.tvTimeSlotStatus.setEnabled(true);
		holder.tvTimeSlotTitle.setEnabled(true);

		holder.tvTimeSlotTitle.setText(app.bookingTime.toString());
		if (app.status.equals("requested") || app.status.equals("confirmed")) {
			disableComponents(holder, timeSlotListView);
			holder.tvTimeSlotStatus.setText("Not available");
		} else if (app.status.contains("lecture")) {
			disableComponents(holder, timeSlotListView);
			holder.tvTimeSlotStatus.setText(app.status);
		} else {
			holder.tvTimeSlotStatus.setText(app.status);
		}

		// disable widget to disable to pick up the earlier time than current
		// time
		String[] timeComp = appsOnTheDate.get(position).bookingTime.toString()
				.split(":");

		Calendar compCal = Calendar.getInstance();
		if (Integer.parseInt(timeComp[0]) <= calendar.get(Calendar.HOUR_OF_DAY)
				&& calendar.get(Calendar.DATE) == compCal.get(Calendar.DATE)
				&& calendar.get(Calendar.MONTH) == compCal.get(Calendar.MONTH)
				&& calendar.get(Calendar.YEAR) == compCal.get(Calendar.YEAR)) {
			holder.tvTimeSlotStatus.setText("");
			disableComponents(holder, timeSlotListView);
		}
		return timeSlotListView;
	}

	private void disableComponents(ViewHolder holder, View timeSlotListView) {
		holder.tvTimeSlotTitle.setEnabled(false);
		holder.tvTimeSlotStatus.setEnabled(false);
		timeSlotListView.setEnabled(false);
	}

	// should be called after data set changes
	public void initTimeSlotList(
			ArrayList<AppointmentRespDetailBean> appResult,
			ArrayList<TimeTableBean> timeTableResult,
			BookingDateInfoBean dateInfo, Calendar calendar) {
		this.calendar = calendar;
		Calendar tempCal = (Calendar) calendar.clone();

		// only appointments of tag owner on the given date will be stored after
		// processed from result which contains appointments of the tag owner in
		// the month

		if (appsOnTheDate == null) {
			Log.i("MagicDoor", "appsOnTheDate is null");
			appsOnTheDate = new ArrayList<AppointmentRespDetailBean>(
					OFFICE_HOUR_DURATION);
		} else {
			// clear the list before use
			appsOnTheDate.clear();
		}

		// if the appointments exist
		if (appResult != null) {
			Log.i("MagicDoor", "result not null");
			if (temp == null) {
				temp = new ArrayList<AppointmentRespDetailBean>(
						OFFICE_HOUR_DURATION);
			} else {
				// clear the list before use
				temp.clear();
			}

			// process the result and get the appointments on the date and store
			// in the temp
			for (AppointmentRespDetailBean app : appResult) {
				// Log.i("MagicDoor", "app.bookingDate  = " + app.bookingDate);
				if (dateInfo.bookingDate.toString().equals(
						app.bookingDate.toString())) {
					temp.add(app);
					Log.i("MagicDoor", "app.bookingDate = " + app.bookingDate);
					// Log.i("MagicDoor", "timeComp[0]  = " + timeComp[0]);
				}
			}

			// create empty objects according to time table information
			for (int i = 9; i < 18; i++) {
				AppointmentRespDetailBean emptyObj = new AppointmentRespDetailBean();
				tempCal.set(Calendar.HOUR_OF_DAY, i);
				tempCal.set(Calendar.MINUTE, 0);
				tempCal.set(Calendar.SECOND, 0);
				Time newTime = new Time(tempCal.getTimeInMillis());
				emptyObj.status = "available";
				emptyObj.bookingTime = newTime;
				// make time slot unavailable according to time table
				if (timeTableResult != null) {
					for (TimeTableBean timeTableBean : timeTableResult) {
						if (timeTableBean.time.toString().equals(
								newTime.toString())) {
							Log.i("MagicDoor", "timeTable time: "
									+ timeTableBean.time + " newTime: "
									+ newTime);
							emptyObj.status = "lecture: " + timeTableBean.subject;
						}
					}
				}
				appsOnTheDate.add(i - 9, emptyObj);
			}

			// replace the empty object with processed result
			for (AppointmentRespDetailBean app : temp) {
				String[] timeComp = app.bookingTime.toString().split(":");
				for (int i = 9; i < 18; i++) {
					if (Integer.parseInt(timeComp[0]) == i) {
						appsOnTheDate.remove(i - 9);
						appsOnTheDate.add(i - 9, app);
					}
				}
			}
		} else {
			Log.i("MagicDoor", "result  null");

			// if result does not exist create list with free time slot
			for (int i = 9; i < 18; i++) {
				AppointmentRespDetailBean emptyObj = new AppointmentRespDetailBean();
				tempCal.set(Calendar.HOUR_OF_DAY, i);
				tempCal.set(Calendar.MINUTE, 0);
				tempCal.set(Calendar.SECOND, 0);
				Time newTime = new Time(tempCal.getTimeInMillis());
				emptyObj.bookingTime = newTime;
				emptyObj.status = "available";
				if (timeTableResult != null) {
					for (TimeTableBean timeTableBean : timeTableResult) {
						if (timeTableBean.time.toString().equals(
								newTime.toString())) {
							Log.i("MagicDoor", "timeTable time: "
									+ timeTableBean.time + " newTime: "
									+ newTime);
							emptyObj.status = "lecture: " + timeTableBean.subject;
						}
					}
				}
				appsOnTheDate.add(i - 9, emptyObj);
			}
		}
		// assign to result
	}

	static class ViewHolder {
		TextView tvTimeSlotTitle;
		TextView tvTimeSlotStatus;
	}

	@Override
	public AppointmentRespDetailBean getItem(int position) {
		// TODO Auto-generated method stub
		return super.getItem(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return appsOnTheDate.size();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return super.getItemId(position);
	}

}
