package jy.kim.lancs.magicdoor.time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import jy.kim.lancs.magicdoor.adapter.CalendarAdapterForStudent;
import jy.kim.lancs.magicdoor.adapter.CalendarLabelAdapter;
import jy.kim.lancs.magicdoor.adapter.TimeAdapter;
import jy.kim.lancs.magicdoor.appointment.Appointment;
import jy.kim.lancs.magicdoor.bean.AppointmentRespDetailBean;
import jy.kim.lancs.magicdoor.bean.TimeTableBean;
import jy.kim.lancs.magicdoor.bean.parcelable.BookingDateInfoBean;
import jy.kim.lancs.magicdoor.dialog.MagicDoorCustomAlertDialog;
import jy.kim.lancs.magicdoor.dialog.MagicDoorCustomAlertDialogBuilder;
import jy.kim.lancs.magicdoor.query.QueryForAppointments;
import jy.kim.lancs.magicdoor.query.QueryForTimeTable;
import jy.kim.lancs.magicdoor.timetable.TimeTable;
import jy.kim.lancs.magicdoor.util.MagicDoorContants;
import magicdoor.lancs.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class PickDateAndTimeActivity extends Activity implements
		OnClickListener, OnItemClickListener {
	private final int TIME_SLOT_SIZE = 9;
	private final int PREV_MONTH = -1;
	private final int THIS_MONTH = 0;
	private final int NEXT_MONTH = 1;
	private static final String[] calendarLables = { "Mon", "Tue", "Wed",
			"Thu", "Fri", "Sat", "Sun" };

	private Appointment appModel;
	private TimeTable timeTableModel;
	
	private TextView tvCalTitle;
	private GridView gvCalLabel;
	private GridView gvCal;
	private Button btnToPrev;
	private Button btnToNext;

	private String tagOwner;

	// appointments of the tag owner in the month will be stored
	private ArrayList<AppointmentRespDetailBean> appointments;
	private ArrayList<TimeTableBean> timeTable;
	
	// /////////////////////////////////////////////
	// booking date and time detail will be stored
	private BookingDateInfoBean dateInfo;
	// time the user select will be stored
	private int selectedTime;

	private Calendar calendar;
	// /////////////////////////////////////////////

	private MagicDoorCustomAlertDialog timeDialog;

	// adapters
	private CalendarAdapterForStudent calAdapter;
	private TimeAdapter timeAdapter;

	// gesture related
	private GestureDetector detector;
	private View.OnTouchListener listener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		
		setContentView(R.layout.view_calnedar);

		Log.i("MagicDoor", "onCreate");

		timeTableModel = new TimeTable(this);
		
		appModel = new Appointment(this);
		tagOwner = appModel.tagOwner;

		tvCalTitle = (TextView) findViewById(R.id.tvCalendarTitle);
		gvCalLabel = (GridView) findViewById(R.id.gvCalendarLabel);
		gvCal = (GridView) findViewById(R.id.gvCalendar);
		btnToPrev = (Button) findViewById(R.id.btnToPrevMonth);
		btnToNext = (Button) findViewById(R.id.btnToNextMonth);

		gvCalLabel.setAdapter(new CalendarLabelAdapter(this, calendarLables));

		// create calendar object with current time information
		calendar = Calendar.getInstance(Locale.UK);
		updateCalendar(THIS_MONTH);

		btnToPrev.setOnClickListener(this);
		btnToNext.setOnClickListener(this);
		gvCal.setOnItemClickListener(this);

		detector = new GestureDetector(this, new CalendarGestureListener());
		listener = new View.OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return detector.onTouchEvent(event);
			}
		};

		gvCal.setOnTouchListener(listener);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.btnToPrevMonth:
			// generate the previous month calendar
			updateCalendar(PREV_MONTH);
			break;

		case R.id.btnToNextMonth:
			// generate the next month calendar
			updateCalendar(NEXT_MONTH);
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
					updateCalendar(calAdapter.monthIndicator);
				} else {
					showSelectingTimeAlertDialog(position);
				}
			} else {
				Toast.makeText(
						this,
						"You can't pick the date! Please select a different date!",
						Toast.LENGTH_SHORT).show();
			}
			break;

		case R.id.lvForTimeSlots:
			selectedTime = position + TIME_SLOT_SIZE;
			// if selected time is not eariler that current time
			if (view.isEnabled()) {
				showDateAndTimeConfirmAlertDialog();
				Log.i("MagicDoor", "enabled");
			} else {
				Log.i("MagicDoor", "not enabled");
			}
			break;
		default:
			break;
		}
	}

	private void updateCalendar(int value) {
		// TODO Auto-generated method stub
		// initialize the value from previous result
		appointments = null;
		timeTable = null;
		
		// determine previous month or next month
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + value);

		// create new dateInfo with selected month
		dateInfo = new BookingDateInfoBean(calendar);

		// create query to obtain appointments
		QueryForAppointments query = new QueryForAppointments();
		query.date = dateInfo.bookingDate;
		query.tagOwner = tagOwner;
		query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_ALL_APPOINTMENTS_OF_THE_LECTURER_USER_IN_THE_MONTH;

		// get appointments of the tagOwner with updated dateInfo
		appointments = appModel.getAllAppointmentsOfTheLecturerUserInTheMonth(query);

		// Log.i("MagicDoor", "returnted data in the month");
		// set label
		tvCalTitle.setText(DateFormat.format("MMMM yyyy", calendar));

		// populate calendar
		if (calAdapter == null) {
			calAdapter = new CalendarAdapterForStudent(this, calendar);
		}
		// redraw calendar
		calAdapter.initDays(appointments);
		gvCal.setAdapter(calAdapter);
		calAdapter.notifyDataSetChanged();
		calAdapter.notifyDataSetInvalidated();
	}

	// Once a date is picked, dialog to select time will appear
	private void showSelectingTimeAlertDialog(int date) {
		// TODO Auto-generated method stub
		
		/////////Time Table////////////
		QueryForTimeTable query = new QueryForTimeTable();
		query.tagOwner = tagOwner;
		query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_DAY_TIME_TABLE;
		query.day = calendar.get(Calendar.DAY_OF_WEEK);
		timeTable = timeTableModel.getTimeDayTable(query);
		
		/////////////////////////////////
		
		
		timeDialog = new MagicDoorCustomAlertDialog(this);
		// populate time slots for listview
		View view = timeDialog.getViewAfterSetSelectingTimeAlertDialog();
		
	//	Log.i("MagicDoor", "appoitments size= " + appointments.size());
		
		if (timeAdapter == null) {
			timeAdapter = new TimeAdapter(this,
					R.layout.view_listitems_timeslots);
		}
		timeAdapter.initTimeSlotList(appointments, timeTable, dateInfo, calendar);

		timeDialog.tvSelectedDate.setText(dateInfo.selectedDate + " "
				+ dateInfo.monthName + " " + dateInfo.year);

		timeDialog.lvForTime.setAdapter(timeAdapter);
		timeDialog.lvForTime.setOnItemClickListener(this);
		timeAdapter.notifyDataSetChanged();
		timeAdapter.notifyDataSetInvalidated();

		timeDialog.setView(view);
		timeDialog.show();
	}

	// show dialog to ask user's confirmation for the selected detail
	private void showDateAndTimeConfirmAlertDialog() {
		// TODO Auto-generated method stub
		MagicDoorCustomAlertDialogBuilder confirmDialog = new MagicDoorCustomAlertDialogBuilder(
				PickDateAndTimeActivity.this);
		confirmDialog.create();
		confirmDialog.setDateAndTimeConfirmationAlertDialog(calendar, dateInfo,
				selectedTime, timeDialog);
		confirmDialog.show();
	}

	private class CalendarGestureListener extends SimpleOnGestureListener {

		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_MAX_OFF_PATH = 250;
		private static final int SWIPE_THRESHOLD_VELOCITY = 200;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			try {
				if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
					return false;
				// right to left swipe
				if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					updateCalendar(NEXT_MONTH);

				} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					updateCalendar(PREV_MONTH);
				}
			} catch (Exception e) {
				// nothing
			}
			return false;
		}
	}
}
