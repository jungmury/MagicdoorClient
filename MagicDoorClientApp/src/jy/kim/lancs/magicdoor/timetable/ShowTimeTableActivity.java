package jy.kim.lancs.magicdoor.timetable;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import jy.kim.lancs.magicdoor.bean.TimeTableBean;
import magicdoor.lancs.R;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ShowTimeTableActivity extends Activity {
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

	private Calendar calendar;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.view_lecturer_time_table);
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		tvSubFri09 = (TextView) findViewById(R.id.tvSubFri09);
		tvSubFri10 = (TextView) findViewById(R.id.tvSubFri10);
		tvSubFri11 = (TextView) findViewById(R.id.tvSubFri11);
		tvSubFri12 = (TextView) findViewById(R.id.tvSubFri12);
		tvSubFri13 = (TextView) findViewById(R.id.tvSubFri13);
		tvSubFri14 = (TextView) findViewById(R.id.tvSubFri14);
		tvSubFri15 = (TextView) findViewById(R.id.tvSubFri15);
		tvSubFri16 = (TextView) findViewById(R.id.tvSubFri16);
		tvSubFri17 = (TextView) findViewById(R.id.tvSubFri17);

		tvSubThu09 = (TextView) findViewById(R.id.tvSubThu09);
		tvSubThu10 = (TextView) findViewById(R.id.tvSubThu10);
		tvSubThu11 = (TextView) findViewById(R.id.tvSubThu11);
		tvSubThu12 = (TextView) findViewById(R.id.tvSubThu12);
		tvSubThu13 = (TextView) findViewById(R.id.tvSubThu13);
		tvSubThu14 = (TextView) findViewById(R.id.tvSubThu14);
		tvSubThu15 = (TextView) findViewById(R.id.tvSubThu15);
		tvSubThu16 = (TextView) findViewById(R.id.tvSubThu16);
		tvSubThu17 = (TextView) findViewById(R.id.tvSubThu17);

		tvSubWed09 = (TextView) findViewById(R.id.tvSubWed09);
		tvSubWed10 = (TextView) findViewById(R.id.tvSubWed10);
		tvSubWed11 = (TextView) findViewById(R.id.tvSubWed11);
		tvSubWed12 = (TextView) findViewById(R.id.tvSubWed12);
		tvSubWed13 = (TextView) findViewById(R.id.tvSubWed13);
		tvSubWed14 = (TextView) findViewById(R.id.tvSubWed14);
		tvSubWed15 = (TextView) findViewById(R.id.tvSubWed15);
		tvSubWed16 = (TextView) findViewById(R.id.tvSubWed16);
		tvSubWed17 = (TextView) findViewById(R.id.tvSubWed17);

		tvSubTue09 = (TextView) findViewById(R.id.tvSubTue09);
		tvSubTue10 = (TextView) findViewById(R.id.tvSubTue10);
		tvSubTue11 = (TextView) findViewById(R.id.tvSubTue11);
		tvSubTue12 = (TextView) findViewById(R.id.tvSubTue12);
		tvSubTue13 = (TextView) findViewById(R.id.tvSubTue13);
		tvSubTue14 = (TextView) findViewById(R.id.tvSubTue14);
		tvSubTue15 = (TextView) findViewById(R.id.tvSubTue15);
		tvSubTue16 = (TextView) findViewById(R.id.tvSubTue16);
		tvSubTue17 = (TextView) findViewById(R.id.tvSubTue17);

		tvSubMon09 = (TextView) findViewById(R.id.tvSubMon09);
		tvSubMon10 = (TextView) findViewById(R.id.tvSubMon10);
		tvSubMon11 = (TextView) findViewById(R.id.tvSubMon11);
		tvSubMon12 = (TextView) findViewById(R.id.tvSubMon12);
		tvSubMon13 = (TextView) findViewById(R.id.tvSubMon13);
		tvSubMon14 = (TextView) findViewById(R.id.tvSubMon14);
		tvSubMon15 = (TextView) findViewById(R.id.tvSubMon15);
		tvSubMon16 = (TextView) findViewById(R.id.tvSubMon16);
		tvSubMon17 = (TextView) findViewById(R.id.tvSubMon17);

		
		Log.i("MagicDoor", "ShowTimeTable Do ti properly!");
		@SuppressWarnings("unchecked")
		ArrayList<TimeTableBean> timeTable = (ArrayList<TimeTableBean>) getIntent()
				.getSerializableExtra("timeTable");

		calendar = Calendar.getInstance(Locale.UK);
		if (timeTable != null) {
			populate(timeTable);
		} else {
			Toast.makeText(this, "No Time Table", Toast.LENGTH_SHORT).show();
		}
	}

	private void populate(ArrayList<TimeTableBean> timeTable) {
		// TODO Auto-generated method stub
		int day = 0;
		Time time = null;
		TextView tv;
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		for (TimeTableBean t : timeTable) {
			day = t.day;
			time = t.time;
			tv = getTextView(day, time);
			tv.setText(t.subject);
		}

	}

	private TextView getTextView(int day, Time time) {
		// TODO Auto-generated method stub
		calendar.setTime(time);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		TextView tv = null;
		switch (day) {
		case Calendar.MONDAY:
			switch (hour) {
			case 9:
				tv = tvSubMon09;
				break;
			case 10:
				tv = tvSubMon10;
				break;
			case 11:
				tv = tvSubMon11;
				break;
			case 12:
				tv = tvSubMon12;
				break;
			case 13:
				tv = tvSubMon13;
				break;
			case 14:
				tv = tvSubMon14;
				break;
			case 15:
				tv = tvSubMon15;
				break;
			case 16:
				tv = tvSubMon16;
				break;
			case 17:
				tv = tvSubMon17;
				break;
			default:
				break;
			}
			break;

		case Calendar.TUESDAY:
			switch (hour) {
			case 9:
				tv = tvSubTue09;
				break;
			case 10:
				tv = tvSubTue10;
				break;
			case 11:
				tv = tvSubTue11;
				break;
			case 12:
				tv = tvSubTue12;
				break;
			case 13:
				tv = tvSubTue13;
				break;
			case 14:
				tv = tvSubTue14;
				break;
			case 15:
				tv = tvSubTue15;
				break;
			case 16:
				tv = tvSubTue16;
				break;
			case 17:
				tv = tvSubTue17;
				break;
			default:
				break;
			}
			break;

		case Calendar.WEDNESDAY:
			switch (hour) {
			case 9:
				tv = tvSubWed09;
				break;
			case 10:
				tv = tvSubWed10;
				break;
			case 11:
				tv = tvSubWed11;
				break;
			case 12:
				tv = tvSubWed12;
				break;
			case 13:
				tv = tvSubWed13;
				break;
			case 14:
				tv = tvSubWed14;
				break;
			case 15:
				tv = tvSubWed15;
				break;
			case 16:
				tv = tvSubWed16;
				break;
			case 17:
				tv = tvSubWed17;
				break;
			default:
				break;
			}
			break;

		case Calendar.THURSDAY:
			switch (hour) {
			case 9:
				tv = tvSubThu09;
				break;
			case 10:
				tv = tvSubThu10;
				break;
			case 11:
				tv = tvSubThu11;
				break;
			case 12:
				tv = tvSubThu12;
				break;
			case 13:
				tv = tvSubThu13;
				break;
			case 14:
				tv = tvSubThu14;
				break;
			case 15:
				tv = tvSubThu15;
				break;
			case 16:
				tv = tvSubThu16;
				break;
			case 17:
				tv = tvSubThu17;
				break;
			default:
				break;
			}
			break;

		case Calendar.FRIDAY:
			switch (hour) {
			case 9:
				tv = tvSubFri09;
				break;
			case 10:
				tv = tvSubFri10;
				break;
			case 11:
				tv = tvSubFri11;
				break;
			case 12:
				tv = tvSubFri12;
				break;
			case 13:
				tv = tvSubFri13;
				break;
			case 14:
				tv = tvSubFri14;
				break;
			case 15:
				tv = tvSubFri15;
				break;
			case 16:
				tv = tvSubFri16;
				break;
			case 17:
				tv = tvSubFri17;
				break;
			default:
				break;
			}
			break;

		default:
			break;
		}
		return tv;
	}
}
