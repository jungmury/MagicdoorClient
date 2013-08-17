package jy.kim.lancs.magicdoor.appointment;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import jy.kim.lancs.magicdoor.bean.AppointmentRespDetailBean;
import jy.kim.lancs.magicdoor.bean.TimeTableBean;
import jy.kim.lancs.magicdoor.query.QueryForAppointments;
import jy.kim.lancs.magicdoor.util.DateParseUtil;
import jy.kim.lancs.magicdoor.util.MagicDoorContants;
import magicdoor.lancs.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class AppointmentWeekFragment extends Fragment implements
		OnClickListener {
	private TextView tvSubMon09;
	private TextView tvSubMon10;
	private TextView tvSubMon11;
	private TextView tvSubMon12;
	private TextView tvSubMon13;
	private TextView tvSubMon14;
	private TextView tvSubMon15;
	private TextView tvSubMon16;
	private TextView tvSubMon17;

	private TextView tvSubTue09;
	private TextView tvSubTue10;
	private TextView tvSubTue11;
	private TextView tvSubTue12;
	private TextView tvSubTue13;
	private TextView tvSubTue14;
	private TextView tvSubTue15;
	private TextView tvSubTue16;
	private TextView tvSubTue17;

	private TextView tvSubWed09;
	private TextView tvSubWed10;
	private TextView tvSubWed11;
	private TextView tvSubWed12;
	private TextView tvSubWed13;
	private TextView tvSubWed14;
	private TextView tvSubWed15;
	private TextView tvSubWed16;
	private TextView tvSubWed17;

	private TextView tvSubThu09;
	private TextView tvSubThu10;
	private TextView tvSubThu11;
	private TextView tvSubThu12;
	private TextView tvSubThu13;
	private TextView tvSubThu14;
	private TextView tvSubThu15;
	private TextView tvSubThu16;
	private TextView tvSubThu17;

	private TextView tvSubFri09;
	private TextView tvSubFri10;
	private TextView tvSubFri11;
	private TextView tvSubFri12;
	private TextView tvSubFri13;
	private TextView tvSubFri14;
	private TextView tvSubFri15;
	private TextView tvSubFri16;
	private TextView tvSubFri17;

	private TextView tvForDateMon;
	private TextView tvForDateTue;
	private TextView tvForDateWed;
	private TextView tvForDateThu;
	private TextView tvForDateFri;

	private TableRow tr09;
	private TableRow tr10;
	private TableRow tr11;
	private TableRow tr12;
	private TableRow tr13;
	private TableRow tr14;
	private TableRow tr15;
	private TableRow tr16;
	private TableRow tr17;

	private Button btnToPrev;
	private Button btnToNext;

	private Activity _activity;

	private String userName;

	private View _rootView;

	private Appointment appModel;
	private Time timeForNewSubj;
	private int dayForNewSubj;
	private Calendar calendar;
	private Calendar calForAPP;
	private ArrayList<AppointmentRespDetailBean> _appointmetns;
	private final int OFFSET = 1;
	private final int NEXT_MONTH = 1;
	private final int PREV_MONTH = -1;
	private EditText etNote;

	public AppointmentWeekFragment() {
		// TODO Auto-generated constructor stub
	}

	public AppointmentWeekFragment(Activity activity,
			ArrayList<AppointmentRespDetailBean> appointments) {
		// TODO Auto-generated constructor stub
		this._activity = activity;
		this._appointmetns = appointments;
	}

	// @SuppressWarnings("unchecked")
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this._activity = activity;
		// this._timeTable = (ArrayList<TimeTableBean>)
		// getArguments().get("timeTable");

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		_rootView = inflater
				.inflate(R.layout.view_lecturer_time_table_appointment,
						container, false);
		tvSubFri09 = (TextView) _rootView.findViewById(R.id.tvSubFri09);
		tvSubFri10 = (TextView) _rootView.findViewById(R.id.tvSubFri10);
		tvSubFri11 = (TextView) _rootView.findViewById(R.id.tvSubFri11);
		tvSubFri12 = (TextView) _rootView.findViewById(R.id.tvSubFri12);
		tvSubFri13 = (TextView) _rootView.findViewById(R.id.tvSubFri13);
		tvSubFri14 = (TextView) _rootView.findViewById(R.id.tvSubFri14);
		tvSubFri15 = (TextView) _rootView.findViewById(R.id.tvSubFri15);
		tvSubFri16 = (TextView) _rootView.findViewById(R.id.tvSubFri16);
		tvSubFri17 = (TextView) _rootView.findViewById(R.id.tvSubFri17);

		tvSubThu09 = (TextView) _rootView.findViewById(R.id.tvSubThu09);
		tvSubThu10 = (TextView) _rootView.findViewById(R.id.tvSubThu10);
		tvSubThu11 = (TextView) _rootView.findViewById(R.id.tvSubThu11);
		tvSubThu12 = (TextView) _rootView.findViewById(R.id.tvSubThu12);
		tvSubThu13 = (TextView) _rootView.findViewById(R.id.tvSubThu13);
		tvSubThu14 = (TextView) _rootView.findViewById(R.id.tvSubThu14);
		tvSubThu15 = (TextView) _rootView.findViewById(R.id.tvSubThu15);
		tvSubThu16 = (TextView) _rootView.findViewById(R.id.tvSubThu16);
		tvSubThu17 = (TextView) _rootView.findViewById(R.id.tvSubThu17);

		tvSubWed09 = (TextView) _rootView.findViewById(R.id.tvSubWed09);
		tvSubWed10 = (TextView) _rootView.findViewById(R.id.tvSubWed10);
		tvSubWed11 = (TextView) _rootView.findViewById(R.id.tvSubWed11);
		tvSubWed12 = (TextView) _rootView.findViewById(R.id.tvSubWed12);
		tvSubWed13 = (TextView) _rootView.findViewById(R.id.tvSubWed13);
		tvSubWed14 = (TextView) _rootView.findViewById(R.id.tvSubWed14);
		tvSubWed15 = (TextView) _rootView.findViewById(R.id.tvSubWed15);
		tvSubWed16 = (TextView) _rootView.findViewById(R.id.tvSubWed16);
		tvSubWed17 = (TextView) _rootView.findViewById(R.id.tvSubWed17);

		tvSubTue09 = (TextView) _rootView.findViewById(R.id.tvSubTue09);
		tvSubTue10 = (TextView) _rootView.findViewById(R.id.tvSubTue10);
		tvSubTue11 = (TextView) _rootView.findViewById(R.id.tvSubTue11);
		tvSubTue12 = (TextView) _rootView.findViewById(R.id.tvSubTue12);
		tvSubTue13 = (TextView) _rootView.findViewById(R.id.tvSubTue13);
		tvSubTue14 = (TextView) _rootView.findViewById(R.id.tvSubTue14);
		tvSubTue15 = (TextView) _rootView.findViewById(R.id.tvSubTue15);
		tvSubTue16 = (TextView) _rootView.findViewById(R.id.tvSubTue16);
		tvSubTue17 = (TextView) _rootView.findViewById(R.id.tvSubTue17);

		tvSubMon09 = (TextView) _rootView.findViewById(R.id.tvSubMon09);
		tvSubMon10 = (TextView) _rootView.findViewById(R.id.tvSubMon10);
		tvSubMon11 = (TextView) _rootView.findViewById(R.id.tvSubMon11);
		tvSubMon12 = (TextView) _rootView.findViewById(R.id.tvSubMon12);
		tvSubMon13 = (TextView) _rootView.findViewById(R.id.tvSubMon13);
		tvSubMon14 = (TextView) _rootView.findViewById(R.id.tvSubMon14);
		tvSubMon15 = (TextView) _rootView.findViewById(R.id.tvSubMon15);
		tvSubMon16 = (TextView) _rootView.findViewById(R.id.tvSubMon16);
		tvSubMon17 = (TextView) _rootView.findViewById(R.id.tvSubMon17);

		tvForDateMon = (TextView) _rootView.findViewById(R.id.tvForDateMon);
		tvForDateTue = (TextView) _rootView.findViewById(R.id.tvForDateTue);
		tvForDateWed = (TextView) _rootView.findViewById(R.id.tvForDateWed);
		tvForDateThu = (TextView) _rootView.findViewById(R.id.tvForDateThu);
		tvForDateFri = (TextView) _rootView.findViewById(R.id.tvForDateFri);

		tr09 = (TableRow) _rootView.findViewById(R.id.tableRow2);
		tr10 = (TableRow) _rootView.findViewById(R.id.tableRow3);
		tr11 = (TableRow) _rootView.findViewById(R.id.tableRow4);
		tr12 = (TableRow) _rootView.findViewById(R.id.tableRow5);
		tr13 = (TableRow) _rootView.findViewById(R.id.tableRow6);
		tr14 = (TableRow) _rootView.findViewById(R.id.tableRow7);
		tr15 = (TableRow) _rootView.findViewById(R.id.tableRow8);
		tr16 = (TableRow) _rootView.findViewById(R.id.tableRow9);
		tr17 = (TableRow) _rootView.findViewById(R.id.tableRow10);

		tvSubMon09.setOnClickListener(this);
		tvSubMon10.setOnClickListener(this);
		tvSubMon11.setOnClickListener(this);
		tvSubMon12.setOnClickListener(this);
		tvSubMon13.setOnClickListener(this);
		tvSubMon14.setOnClickListener(this);
		tvSubMon15.setOnClickListener(this);
		tvSubMon16.setOnClickListener(this);
		tvSubMon17.setOnClickListener(this);

		tvSubTue09.setOnClickListener(this);
		tvSubTue10.setOnClickListener(this);
		tvSubTue11.setOnClickListener(this);
		tvSubTue12.setOnClickListener(this);
		tvSubTue13.setOnClickListener(this);
		tvSubTue14.setOnClickListener(this);
		tvSubTue15.setOnClickListener(this);
		tvSubTue16.setOnClickListener(this);
		tvSubTue17.setOnClickListener(this);

		tvSubWed09.setOnClickListener(this);
		tvSubWed10.setOnClickListener(this);
		tvSubWed11.setOnClickListener(this);
		tvSubWed12.setOnClickListener(this);
		tvSubWed13.setOnClickListener(this);
		tvSubWed14.setOnClickListener(this);
		tvSubWed15.setOnClickListener(this);
		tvSubWed16.setOnClickListener(this);
		tvSubWed17.setOnClickListener(this);

		tvSubThu09.setOnClickListener(this);
		tvSubThu10.setOnClickListener(this);
		tvSubThu11.setOnClickListener(this);
		tvSubThu12.setOnClickListener(this);
		tvSubThu13.setOnClickListener(this);
		tvSubThu14.setOnClickListener(this);
		tvSubThu15.setOnClickListener(this);
		tvSubThu16.setOnClickListener(this);
		tvSubThu17.setOnClickListener(this);

		tvSubFri09.setOnClickListener(this);
		tvSubFri10.setOnClickListener(this);
		tvSubFri11.setOnClickListener(this);
		tvSubFri12.setOnClickListener(this);
		tvSubFri13.setOnClickListener(this);
		tvSubFri14.setOnClickListener(this);
		tvSubFri15.setOnClickListener(this);
		tvSubFri16.setOnClickListener(this);
		tvSubFri17.setOnClickListener(this);

		tr09 = (TableRow) _rootView.findViewById(R.id.tableRow2);
		tr10 = (TableRow) _rootView.findViewById(R.id.tableRow3);
		tr11 = (TableRow) _rootView.findViewById(R.id.tableRow4);
		tr12 = (TableRow) _rootView.findViewById(R.id.tableRow5);
		tr13 = (TableRow) _rootView.findViewById(R.id.tableRow6);
		tr14 = (TableRow) _rootView.findViewById(R.id.tableRow7);
		tr15 = (TableRow) _rootView.findViewById(R.id.tableRow8);
		tr16 = (TableRow) _rootView.findViewById(R.id.tableRow9);
		tr17 = (TableRow) _rootView.findViewById(R.id.tableRow10);

		tr09.setOnClickListener(this);
		tr10.setOnClickListener(this);
		tr11.setOnClickListener(this);
		tr12.setOnClickListener(this);
		tr13.setOnClickListener(this);
		tr14.setOnClickListener(this);
		tr15.setOnClickListener(this);
		tr16.setOnClickListener(this);
		tr17.setOnClickListener(this);

		btnToPrev = (Button) _rootView.findViewById(R.id.btnToPrevMonthTAPP);
		btnToNext = (Button) _rootView.findViewById(R.id.btnToNextMonthTAPP);

		btnToPrev.setOnClickListener(this);
		btnToNext.setOnClickListener(this);

		appModel = new Appointment(_activity);
		userName = appModel.userName;
		calendar = Calendar.getInstance(Locale.UK);
		calForAPP = Calendar.getInstance(Locale.UK);

		if (_appointmetns != null) {
			populate(_appointmetns, calForAPP);
		} else {
			Toast.makeText(_activity, "No Time Table", Toast.LENGTH_SHORT)
					.show();
		}

		return _rootView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		timeForNewSubj = null;
		dayForNewSubj = 0;
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		switch (id) {
		case R.id.tvSubMon09:
			dayForNewSubj = Calendar.MONDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 9);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubMon09);
			break;
		case R.id.tvSubMon10:
			dayForNewSubj = Calendar.MONDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 10);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubMon10);
			break;
		case R.id.tvSubMon11:
			dayForNewSubj = Calendar.MONDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 11);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubMon11);
			break;
		case R.id.tvSubMon12:
			dayForNewSubj = Calendar.MONDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 12);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubMon12);
			break;
		case R.id.tvSubMon13:
			dayForNewSubj = Calendar.MONDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 13);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubMon13);
			break;
		case R.id.tvSubMon14:
			dayForNewSubj = Calendar.MONDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 14);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubMon14);
			break;
		case R.id.tvSubMon15:
			dayForNewSubj = Calendar.MONDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 15);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubMon15);
			break;
		case R.id.tvSubMon16:
			dayForNewSubj = Calendar.MONDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 16);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubMon16);
			break;
		case R.id.tvSubMon17:
			dayForNewSubj = Calendar.MONDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 17);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubMon17);
			break;

		case R.id.tvSubTue09:
			dayForNewSubj = Calendar.TUESDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 9);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubTue09);
			break;
		case R.id.tvSubTue10:
			dayForNewSubj = Calendar.TUESDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 10);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubTue10);
			break;
		case R.id.tvSubTue11:
			dayForNewSubj = Calendar.TUESDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 11);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubTue11);
			break;
		case R.id.tvSubTue12:
			dayForNewSubj = Calendar.TUESDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 12);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubTue12);
			break;
		case R.id.tvSubTue13:
			dayForNewSubj = Calendar.TUESDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 13);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubTue13);
			break;
		case R.id.tvSubTue14:
			dayForNewSubj = Calendar.TUESDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 14);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubTue14);
			break;
		case R.id.tvSubTue15:
			dayForNewSubj = Calendar.TUESDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 15);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubTue15);
			break;
		case R.id.tvSubTue16:
			dayForNewSubj = Calendar.TUESDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 16);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubTue16);
			break;
		case R.id.tvSubTue17:
			dayForNewSubj = Calendar.TUESDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 17);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubTue17);
			break;
		case R.id.tvSubWed09:
			dayForNewSubj = Calendar.WEDNESDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 9);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubWed09);
			break;
		case R.id.tvSubWed10:
			dayForNewSubj = Calendar.WEDNESDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 10);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubWed10);
			break;
		case R.id.tvSubWed11:
			dayForNewSubj = Calendar.WEDNESDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 11);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubWed11);
			break;
		case R.id.tvSubWed12:
			dayForNewSubj = Calendar.WEDNESDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 12);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubWed12);
			break;
		case R.id.tvSubWed13:
			dayForNewSubj = Calendar.WEDNESDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 13);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubWed13);
			break;
		case R.id.tvSubWed14:
			dayForNewSubj = Calendar.WEDNESDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 14);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubWed14);
			break;
		case R.id.tvSubWed15:
			dayForNewSubj = Calendar.WEDNESDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 15);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubWed15);
			break;
		case R.id.tvSubWed16:
			dayForNewSubj = Calendar.WEDNESDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 16);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubWed16);
			break;
		case R.id.tvSubWed17:
			dayForNewSubj = Calendar.WEDNESDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 17);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubWed17);
			break;
		case R.id.tvSubThu09:
			dayForNewSubj = Calendar.THURSDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 9);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubThu09);
			break;
		case R.id.tvSubThu10:
			dayForNewSubj = Calendar.THURSDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 10);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubThu10);
			break;
		case R.id.tvSubThu11:
			dayForNewSubj = Calendar.THURSDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 11);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubThu11);
			break;
		case R.id.tvSubThu12:
			dayForNewSubj = Calendar.THURSDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 12);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubThu12);
			break;
		case R.id.tvSubThu13:
			dayForNewSubj = Calendar.THURSDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 13);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubThu13);
			break;
		case R.id.tvSubThu14:
			dayForNewSubj = Calendar.THURSDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 14);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubThu14);
			break;
		case R.id.tvSubThu15:
			dayForNewSubj = Calendar.THURSDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 15);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubThu15);
			break;
		case R.id.tvSubThu16:
			dayForNewSubj = Calendar.THURSDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 16);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubThu16);
			break;
		case R.id.tvSubThu17:
			dayForNewSubj = Calendar.THURSDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 17);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubThu17);
			break;
		case R.id.tvSubFri09:
			dayForNewSubj = Calendar.FRIDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 9);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubFri09);
			break;
		case R.id.tvSubFri10:
			dayForNewSubj = Calendar.FRIDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 10);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubFri10);
			break;
		case R.id.tvSubFri11:
			dayForNewSubj = Calendar.FRIDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 11);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubFri11);
			break;
		case R.id.tvSubFri12:
			dayForNewSubj = Calendar.FRIDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 12);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubFri12);
			break;
		case R.id.tvSubFri13:
			dayForNewSubj = Calendar.FRIDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 13);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubFri13);
			break;
		case R.id.tvSubFri14:
			dayForNewSubj = Calendar.FRIDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 14);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubFri14);
			break;
		case R.id.tvSubFri15:
			dayForNewSubj = Calendar.FRIDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 15);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubFri15);
			break;
		case R.id.tvSubFri16:
			dayForNewSubj = Calendar.FRIDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 16);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubFri16);
			break;
		case R.id.tvSubFri17:
			dayForNewSubj = Calendar.FRIDAY;
			calendar.set(Calendar.HOUR_OF_DAY, 17);
			timeForNewSubj = new Time(calendar.getTimeInMillis());
			showSelectOptionDialog(tvSubFri17);
			break;
		case R.id.btnToNextMonthTAPP:
			Appointment appModel = new Appointment(_activity);
			QueryForAppointments queryApp = new QueryForAppointments();
			queryApp.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_APPOINTMENTS_OF_THE_LECTURER_USER_IN_THE_WEEK;
			queryApp.tagOwner = userName;
			calForAPP.set(Calendar.WEEK_OF_MONTH,
					calForAPP.get(Calendar.WEEK_OF_MONTH) + NEXT_MONTH);
			queryApp.date = new Date(calForAPP.getTimeInMillis());
			ArrayList<AppointmentRespDetailBean> appointments = appModel
					.getAppointmentsOfTheLecturerUserInTheWeek(queryApp);
			if (appointments != null) {
				populate(appointments, calForAPP);
			} else {
				populate(calForAPP);
			}
			break;
		case R.id.btnToPrevMonthTAPP:
			appModel = new Appointment(_activity);
			queryApp = new QueryForAppointments();
			queryApp.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_APPOINTMENTS_OF_THE_LECTURER_USER_IN_THE_WEEK;
			queryApp.tagOwner = userName;
			calForAPP.set(Calendar.WEEK_OF_MONTH,
					calForAPP.get(Calendar.WEEK_OF_MONTH) + PREV_MONTH);
			queryApp.date = new Date(calForAPP.getTimeInMillis());
			appointments = appModel
					.getAppointmentsOfTheLecturerUserInTheWeek(queryApp);
			if (appointments != null) {
				populate(appointments, calForAPP);
			} else {
				populate(calForAPP);
			}

			break;
		default:
			break;
		}

	}

	private void showSelectOptionDialog(final TextView givenTextView) {
		// TODO Auto-generated method stub
		if (givenTextView.getTag() != null) {
			final AppointmentRespDetailBean appointment = (AppointmentRespDetailBean) givenTextView
					.getTag();
			AlertDialog dialog = new AlertDialog.Builder(_activity).create();
			dialog.setTitle("Appointment Detail");
			String newDate = DateParseUtil
					.getDateInNewFormat(appointment.bookingDate);
			dialog.setMessage("Requested by: " + appointment.name + "\nTitle: "
					+ appointment.title + "\nDescription: "
					+ appointment.description + "\nDate: " + newDate
					+ "\nFrom: " + appointment.bookingTime + "\nStatus: "
					+ appointment.status);
			if(!appointment.status.equals("confirmed")){
				dialog.setButton(Dialog.BUTTON_POSITIVE, "Confirm",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								QueryForAppointments query = new QueryForAppointments();
								query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_COFIRMING_APPOINTMENT;
								query.tagOwner = userName;
								query.refValue = appointment.refValue;
								ArrayList<AppointmentRespDetailBean> result = appModel
										.confirmAppointment(query);
								if (result != null) {
									result = null;
									query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_APPOINTMENTS_OF_THE_LECTURER_USER_IN_THE_WEEK;
									query.tagOwner = userName;
									query.date = new Date(calForAPP
											.getTimeInMillis());
									result = appModel
											.getAppointmentsOfTheLecturerUserInTheWeek(query);

								}
								// refresh required
								if (result != null) {
									populate(result, calForAPP);
								} else {
									populate(calForAPP);
								}
								dialog.dismiss();

							}
						});
			}

			dialog.setButton(Dialog.BUTTON_NEUTRAL, "Back",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			String msgForButton = "";
			if(appointment.status.equals("confirmed")){
				msgForButton = "Cancel Appointment";
			} else {
				msgForButton = "Do Not Confirm";
			}
			dialog.setButton(Dialog.BUTTON_NEGATIVE, msgForButton,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// ///////

							LayoutInflater inflater = LayoutInflater
									.from(_activity);// (LayoutInflater)
														// ManageAppointmentsListDialogActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
							final View view = inflater.inflate(
									R.layout.view_form_write_note, null);
							etNote = (EditText) view
									.findViewById(R.id.etWriteNote);

							AlertDialog noteDialog = new AlertDialog.Builder(
									_activity).create();
							noteDialog.setView(view);
							noteDialog.setButton(Dialog.BUTTON_NEUTRAL,
									"Proceed!",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method
											// stub
											QueryForAppointments query = new QueryForAppointments();
											query.note = etNote.getText()
													.toString();
											query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_REJECTING_APPOINTMENT;
											query.tagOwner = userName;
											query.refValue = appointment.refValue;
											ArrayList<AppointmentRespDetailBean> result = appModel
													.rejectAppointment(query);

											Toast.makeText(_activity,
													"Request Rejected",
													Toast.LENGTH_SHORT).show();
											if (result != null) {
												result = null;
												query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_APPOINTMENTS_OF_THE_LECTURER_USER_IN_THE_WEEK;
												query.tagOwner = userName;
												query.date = new Date(calForAPP
														.getTimeInMillis());
												result = appModel
														.getAppointmentsOfTheLecturerUserInTheWeek(query);

											}

											// Refresh dialog
											if (result != null) {
												populate(result, calForAPP);
											} else {
												populate(calForAPP);
											}

											dialog.dismiss();
										}
									});
							noteDialog.show();
						}
					});

			dialog.show();
		}

	}

	protected TimeTableBean createTimeTableBean(TextView textView) {
		// TODO Auto-generated method stub
		Toast.makeText(_activity, "Create Timetable", Toast.LENGTH_SHORT)
				.show();

		TimeTableBean bean = new TimeTableBean();
		bean.uri = MagicDoorContants.REST_SERVICE_URI_FOR_INSERTING_TIME_TABLE;
		bean.day = dayForNewSubj;
		bean.subject = textView.getText().toString();
		bean.time = timeForNewSubj;
		bean.userName = userName;
		return bean;
	}

	protected TimeTableBean updateTimeTableBean(TextView textView) {
		// TODO Auto-generated method stub
		Toast.makeText(_activity, "Update Timetable", Toast.LENGTH_SHORT)
				.show();
		TimeTableBean bean = new TimeTableBean();
		bean.uri = MagicDoorContants.REST_SERVICE_URI_FOR_UPDATING_TIME_TABLE;
		bean.day = dayForNewSubj;
		bean.subject = textView.getText().toString();
		bean.time = timeForNewSubj;
		bean.userName = userName;
		return bean;
	}

	private void populate(ArrayList<AppointmentRespDetailBean> appointments,
			Calendar calForDate) {
		// TODO Auto-generated method stub
		refresh();
		int day = 0;
		Time time = null;
		TextView tvForTitle;
		TextView tvForDate;

		calForDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

		for (int i = 1; i <= 5; i++) {
			tvForDate = getTextViewForDate(calForDate);
			tvForDate.setText(calForDate.get(Calendar.DATE) + "/"
					+ (calForDate.get(Calendar.MONTH) + OFFSET));
			Log.i("MagicDoor", "Day " + calForDate.get(Calendar.DATE) + "/"
					+ (calForDate.get(Calendar.MONTH) + OFFSET));
			calForDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY + i);

		}

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		for (AppointmentRespDetailBean a : appointments) {
			day = a.bookingDate.getDay() + OFFSET;
			time = a.bookingTime;
			Log.i("MagicDoor", "a day = " + day + " a time = " + time);
			tvForTitle = getTextViewForTitle(day, time);
			tvForTitle.setTag(a);
			tvForTitle.setText(a.title);
			tvForTitle.setTextColor(Color.WHITE);
			if (a.status.equals("requested")) {
				tvForTitle.setBackgroundColor(getResources().getColor(
						R.color.navy));
			} else {
				tvForTitle.setBackgroundColor(getResources().getColor(
						R.color.green));
			}
		}

	}

	private void populate(Calendar calForDate) {
		// TODO Auto-generated method stub
		TextView tvForDate;
		refresh();

		Log.i("MagicDoor",
				"Before Week_of_month: "
						+ calForDate.get(Calendar.WEEK_OF_MONTH));
		Log.i("MagicDoor", "Before Date: " + calForDate.get(Calendar.DATE));

		calForDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Log.i("MagicDoor",
				"After Week_of_month: "
						+ calForDate.get(Calendar.WEEK_OF_MONTH));
		Log.i("MagicDoor", "After Date: " + calForDate.get(Calendar.DATE));

		for (int i = 1; i <= 5; i++) {
			tvForDate = getTextViewForDate(calForDate);
			tvForDate.setText(calForDate.get(Calendar.DATE) + "/"
					+ (calForDate.get(Calendar.MONTH) + OFFSET));
			Log.i("MagicDoor", "Day " + calForDate.get(Calendar.DATE) + "/"
					+ (calForDate.get(Calendar.MONTH) + OFFSET));
			calForDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY + i);

		}
	}

	private TextView getTextViewForTitle(int day, Time time) {
		// TODO Auto-generated method stub
		calendar.setTime(time);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		TextView tvForTitle = null;
		Log.i("MagicDoor", "day = " + day + " hour = " + hour);
		switch (day) {
		case Calendar.MONDAY:
			switch (hour) {
			case 9:
				tvForTitle = tvSubMon09;
				break;
			case 10:
				tvForTitle = tvSubMon10;
				break;
			case 11:
				tvForTitle = tvSubMon11;
				break;
			case 12:
				tvForTitle = tvSubMon12;
				break;
			case 13:
				tvForTitle = tvSubMon13;
				break;
			case 14:
				tvForTitle = tvSubMon14;
				break;
			case 15:
				tvForTitle = tvSubMon15;
				break;
			case 16:
				tvForTitle = tvSubMon16;
				break;
			case 17:
				tvForTitle = tvSubMon17;
				break;
			default:
				break;
			}
			break;

		case Calendar.TUESDAY:
			switch (hour) {
			case 9:
				tvForTitle = tvSubTue09;
				break;
			case 10:
				tvForTitle = tvSubTue10;
				break;
			case 11:
				tvForTitle = tvSubTue11;
				break;
			case 12:
				tvForTitle = tvSubTue12;
				break;
			case 13:
				tvForTitle = tvSubTue13;
				break;
			case 14:
				tvForTitle = tvSubTue14;
				break;
			case 15:
				tvForTitle = tvSubTue15;
				break;
			case 16:
				tvForTitle = tvSubTue16;
				break;
			case 17:
				tvForTitle = tvSubTue17;
				break;
			default:
				break;
			}
			break;

		case Calendar.WEDNESDAY:
			switch (hour) {
			case 9:
				tvForTitle = tvSubWed09;
				break;
			case 10:
				tvForTitle = tvSubWed10;
				break;
			case 11:
				tvForTitle = tvSubWed11;
				break;
			case 12:
				tvForTitle = tvSubWed12;
				break;
			case 13:
				tvForTitle = tvSubWed13;
				break;
			case 14:
				tvForTitle = tvSubWed14;
				break;
			case 15:
				tvForTitle = tvSubWed15;
				break;
			case 16:
				tvForTitle = tvSubWed16;
				break;
			case 17:
				tvForTitle = tvSubWed17;
				break;
			default:
				break;
			}
			break;

		case Calendar.THURSDAY:
			switch (hour) {
			case 9:
				tvForTitle = tvSubThu09;
				break;
			case 10:
				tvForTitle = tvSubThu10;
				break;
			case 11:
				tvForTitle = tvSubThu11;
				break;
			case 12:
				tvForTitle = tvSubThu12;
				break;
			case 13:
				tvForTitle = tvSubThu13;
				break;
			case 14:
				tvForTitle = tvSubThu14;
				break;
			case 15:
				tvForTitle = tvSubThu15;
				break;
			case 16:
				tvForTitle = tvSubThu16;
				break;
			case 17:
				tvForTitle = tvSubThu17;
				break;
			default:
				break;
			}
			break;

		case Calendar.FRIDAY:
			switch (hour) {
			case 9:
				tvForTitle = tvSubFri09;
				break;
			case 10:
				tvForTitle = tvSubFri10;
				break;
			case 11:
				tvForTitle = tvSubFri11;
				break;
			case 12:
				tvForTitle = tvSubFri12;
				break;
			case 13:
				tvForTitle = tvSubFri13;
				break;
			case 14:
				tvForTitle = tvSubFri14;
				break;
			case 15:
				tvForTitle = tvSubFri15;
				break;
			case 16:
				tvForTitle = tvSubFri16;
				break;
			case 17:
				tvForTitle = tvSubFri17;
				break;
			default:
				break;
			}
			break;

		default:
			break;
		}
		return tvForTitle;
	}

	private TextView getTextViewForDate(Calendar cal) {
		// TODO Auto-generated method stub
		TextView tvForDate = null;
		switch (cal.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.MONDAY:
			tvForDate = tvForDateMon;
			break;

		case Calendar.TUESDAY:
			tvForDate = tvForDateTue;
			break;

		case Calendar.WEDNESDAY:
			tvForDate = tvForDateWed;
			break;

		case Calendar.THURSDAY:
			tvForDate = tvForDateThu;
			break;

		case Calendar.FRIDAY:
			tvForDate = tvForDateFri;
			break;

		default:
			break;
		}
		return tvForDate;
	}

	private void refresh() {

		tvSubMon09.setBackgroundColor(Color.TRANSPARENT);
		tvSubMon10.setBackgroundColor(Color.TRANSPARENT);
		tvSubMon11.setBackgroundColor(Color.TRANSPARENT);
		tvSubMon12.setBackgroundColor(Color.TRANSPARENT);
		tvSubMon13.setBackgroundColor(Color.TRANSPARENT);
		tvSubMon14.setBackgroundColor(Color.TRANSPARENT);
		tvSubMon15.setBackgroundColor(Color.TRANSPARENT);
		tvSubMon16.setBackgroundColor(Color.TRANSPARENT);
		tvSubMon17.setBackgroundColor(Color.TRANSPARENT);

		tvSubTue09.setBackgroundColor(Color.TRANSPARENT);
		tvSubTue10.setBackgroundColor(Color.TRANSPARENT);
		tvSubTue11.setBackgroundColor(Color.TRANSPARENT);
		tvSubTue12.setBackgroundColor(Color.TRANSPARENT);
		tvSubTue13.setBackgroundColor(Color.TRANSPARENT);
		tvSubTue14.setBackgroundColor(Color.TRANSPARENT);
		tvSubTue15.setBackgroundColor(Color.TRANSPARENT);
		tvSubTue16.setBackgroundColor(Color.TRANSPARENT);
		tvSubTue17.setBackgroundColor(Color.TRANSPARENT);

		tvSubWed09.setBackgroundColor(Color.TRANSPARENT);
		tvSubWed10.setBackgroundColor(Color.TRANSPARENT);
		tvSubWed11.setBackgroundColor(Color.TRANSPARENT);
		tvSubWed12.setBackgroundColor(Color.TRANSPARENT);
		tvSubWed13.setBackgroundColor(Color.TRANSPARENT);
		tvSubWed14.setBackgroundColor(Color.TRANSPARENT);
		tvSubWed15.setBackgroundColor(Color.TRANSPARENT);
		tvSubWed16.setBackgroundColor(Color.TRANSPARENT);
		tvSubWed17.setBackgroundColor(Color.TRANSPARENT);

		tvSubThu09.setBackgroundColor(Color.TRANSPARENT);
		tvSubThu10.setBackgroundColor(Color.TRANSPARENT);
		tvSubThu11.setBackgroundColor(Color.TRANSPARENT);
		tvSubThu12.setBackgroundColor(Color.TRANSPARENT);
		tvSubThu13.setBackgroundColor(Color.TRANSPARENT);
		tvSubThu14.setBackgroundColor(Color.TRANSPARENT);
		tvSubThu15.setBackgroundColor(Color.TRANSPARENT);
		tvSubThu16.setBackgroundColor(Color.TRANSPARENT);
		tvSubThu17.setBackgroundColor(Color.TRANSPARENT);

		tvSubFri09.setBackgroundColor(Color.TRANSPARENT);
		tvSubFri10.setBackgroundColor(Color.TRANSPARENT);
		tvSubFri11.setBackgroundColor(Color.TRANSPARENT);
		tvSubFri12.setBackgroundColor(Color.TRANSPARENT);
		tvSubFri13.setBackgroundColor(Color.TRANSPARENT);
		tvSubFri14.setBackgroundColor(Color.TRANSPARENT);
		tvSubFri15.setBackgroundColor(Color.TRANSPARENT);
		tvSubFri16.setBackgroundColor(Color.TRANSPARENT);
		tvSubFri17.setBackgroundColor(Color.TRANSPARENT);

		tvSubMon09.setText("");
		tvSubMon10.setText("");
		tvSubMon11.setText("");
		tvSubMon12.setText("");
		tvSubMon13.setText("");
		tvSubMon14.setText("");
		tvSubMon15.setText("");
		tvSubMon16.setText("");
		tvSubMon17.setText("");

		tvSubTue09.setText("");
		tvSubTue10.setText("");
		tvSubTue11.setText("");
		tvSubTue12.setText("");
		tvSubTue13.setText("");
		tvSubTue14.setText("");
		tvSubTue15.setText("");
		tvSubTue16.setText("");
		tvSubTue17.setText("");

		tvSubWed09.setText("");
		tvSubWed10.setText("");
		tvSubWed11.setText("");
		tvSubWed12.setText("");
		tvSubWed13.setText("");
		tvSubWed14.setText("");
		tvSubWed15.setText("");
		tvSubWed16.setText("");
		tvSubWed17.setText("");

		tvSubThu09.setText("");
		tvSubThu10.setText("");
		tvSubThu11.setText("");
		tvSubThu12.setText("");
		tvSubThu13.setText("");
		tvSubThu14.setText("");
		tvSubThu15.setText("");
		tvSubThu16.setText("");
		tvSubThu17.setText("");

		tvSubFri09.setText("");
		tvSubFri10.setText("");
		tvSubFri11.setText("");
		tvSubFri12.setText("");
		tvSubFri13.setText("");
		tvSubFri14.setText("");
		tvSubFri15.setText("");
		tvSubFri16.setText("");
		tvSubFri17.setText("");

		tvSubMon09.setTag(null);
		tvSubMon10.setTag(null);
		tvSubMon11.setTag(null);
		tvSubMon12.setTag(null);
		tvSubMon13.setTag(null);
		tvSubMon14.setTag(null);
		tvSubMon15.setTag(null);
		tvSubMon16.setTag(null);
		tvSubMon17.setTag(null);

		tvSubTue09.setTag(null);
		tvSubTue10.setTag(null);
		tvSubTue11.setTag(null);
		tvSubTue12.setTag(null);
		tvSubTue13.setTag(null);
		tvSubTue14.setTag(null);
		tvSubTue15.setTag(null);
		tvSubTue16.setTag(null);
		tvSubTue17.setTag(null);

		tvSubWed09.setTag(null);
		tvSubWed10.setTag(null);
		tvSubWed11.setTag(null);
		tvSubWed12.setTag(null);
		tvSubWed13.setTag(null);
		tvSubWed14.setTag(null);
		tvSubWed15.setTag(null);
		tvSubWed16.setTag(null);
		tvSubWed17.setTag(null);

		tvSubThu09.setTag(null);
		tvSubThu10.setTag(null);
		tvSubThu11.setTag(null);
		tvSubThu12.setTag(null);
		tvSubThu13.setTag(null);
		tvSubThu14.setTag(null);
		tvSubThu15.setTag(null);
		tvSubThu16.setTag(null);
		tvSubThu17.setTag(null);

		tvSubFri09.setTag(null);
		tvSubFri10.setTag(null);
		tvSubFri11.setTag(null);
		tvSubFri12.setTag(null);
		tvSubFri13.setTag(null);
		tvSubFri14.setTag(null);
		tvSubFri15.setTag(null);
		tvSubFri16.setTag(null);
		tvSubFri17.setTag(null);
	}

}
