package jy.kim.lancs.magicdoor.appointment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import jy.kim.lancs.magicdoor.adapter.CalendarAdapterForManageAppointment;
import jy.kim.lancs.magicdoor.adapter.CalendarLabelAdapter;
import jy.kim.lancs.magicdoor.bean.AppointmentRespDetailBean;
import jy.kim.lancs.magicdoor.bean.parcelable.BookingDateInfoBean;
import jy.kim.lancs.magicdoor.query.QueryForAppointments;
import jy.kim.lancs.magicdoor.util.MagicDoorContants;
import magicdoor.lancs.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class ManageAppointmentCalendarFragment extends Fragment implements
		OnClickListener, OnItemClickListener {
	private final int PREV_MONTH = -1;
	private final int THIS_MONTH = 0;
	private final int NEXT_MONTH = 1;
	private static final String[] calendarLables = { "Mon", "Tue", "Wed",
			"Thu", "Fri", "Sat", "Sun" };

	private Appointment model;

	private TextView tvCalTitle;
	private GridView gvCalLabel;
	private GridView gvCal;
	private Button btnToPrev;
	private Button btnToNext;

	private String userName;

	// appointments of the tag owner in the month will be stored

	// /////////////////////////////////////////////
	// booking date and time detail will be stored
	private BookingDateInfoBean dateInfo;
	// time the user select will be stored

	private Calendar calendar;
	// /////////////////////////////////////////////

	// adapters
	private CalendarAdapterForManageAppointment calAdapter;

	private Activity activity;
	private View rootView;
	ArrayList<AppointmentRespDetailBean> _appointments;

	public ManageAppointmentCalendarFragment() {
		// TODO Auto-generated constructor stub
	}

	public ManageAppointmentCalendarFragment(Activity activity, ArrayList<AppointmentRespDetailBean> appointments) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this._appointments = appointments;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		rootView = inflater.inflate(R.layout.view_calnedar, container, false);

		model = new Appointment(activity);
		userName = model.userName;

		tvCalTitle = (TextView) rootView.findViewById(R.id.tvCalendarTitle);
		gvCalLabel = (GridView) rootView.findViewById(R.id.gvCalendarLabel);
		gvCal = (GridView) rootView.findViewById(R.id.gvCalendar);
		btnToPrev = (Button) rootView.findViewById(R.id.btnToPrevMonth);
		btnToNext = (Button) rootView.findViewById(R.id.btnToNextMonth);

		gvCalLabel
				.setAdapter(new CalendarLabelAdapter(activity, calendarLables));

		// create calendar object with current time information
		calendar = Calendar.getInstance(Locale.UK);

		updateCalendar(THIS_MONTH, _appointments);

		btnToPrev.setOnClickListener(this);
		btnToNext.setOnClickListener(this);
		gvCal.setOnItemClickListener(this);

		return rootView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.btnToPrevMonth:
			// generate the previous month calendar
			updateCalendar(PREV_MONTH, null);
			break;

		case R.id.btnToNextMonth:
			// generate the next month calendar
			updateCalendar(NEXT_MONTH, null);
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.i("MagicDoor", "onItemClick");

		int pId = parent.getId();
		switch (pId) {
		case R.id.gvCalendar:
			// if selected date is not earlier that today
			if (view.isEnabled()) {
				// dateInfo with selected date will be returned
				dateInfo = (BookingDateInfoBean) gvCal
						.getItemAtPosition(position);
				Log.i("MagicDoor", "dateInfo.bookingDate= "
						+ dateInfo.bookingDate);

				// if selected date is in the next month
				if (calAdapter.isMonthChange) {
					// change month view
					updateCalendar(calAdapter.monthIndicator, null);
				} else {
					QueryForAppointments query = new QueryForAppointments();
					query.date = dateInfo.bookingDate;
					query.tagOwner = userName;
					query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_ALL_APPOINTMENTS_OF_THE_LECTURER_USER_ON_THE_DATE;
					Log.i("MagicDoor", "query date = " + query.date);
					Log.i("MagicDoor", "tagOwner = " + query.tagOwner);

					ArrayList<AppointmentRespDetailBean> appointments = model
							.getAllAppointmentsOfTheLecturerUserOnTheDate(query);
					
					
					Intent intent = new Intent(activity,
							ManageAppointmentListDialogActivity.class);
					intent.putExtra("query", query);
					intent.putExtra("appointments", appointments);
					intent.putExtra("userName", userName);
					startActivity(intent);
				}
			}
			break;

		default:
			break;
		}
	}

	private void updateCalendar(int value,
			ArrayList<AppointmentRespDetailBean> appointments) {
		// TODO Auto-generated method stub
		// initialize the value from previous result

		// determine previous month or next month
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + value);

		// create new dateInfo with selected month
		dateInfo = new BookingDateInfoBean(calendar);

		// create query to obtain appointments
		QueryForAppointments query = new QueryForAppointments();
		query.date = dateInfo.bookingDate;
		query.tagOwner = userName;
		query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_ALL_APPOINTMENTS_OF_THE_LECTURER_USER_IN_THE_MONTH;
		Log.i("MagicDoor", "query date = " + query.date);
		Log.i("MagicDoor", "tagOwner = " + query.tagOwner);
		// get appointments of the tagOwner with updated dateInfo

		if (appointments == null) {
			appointments = model
					.getAllAppointmentsOfTheLecturerUserInTheMonth(query);
		}

		// Log.i("MagicDoor", "returnted data in the month");
		// set label
		tvCalTitle.setText(DateFormat.format("MMMM yyyy", calendar));

		// populate calendar
		if (calAdapter == null) {
			calAdapter = new CalendarAdapterForManageAppointment(activity,
					calendar);
		}
		// redraw calendar
		calAdapter.initDays(appointments);
		gvCal.setAdapter(calAdapter);
		calAdapter.notifyDataSetChanged();
		calAdapter.notifyDataSetInvalidated();
	}
}
