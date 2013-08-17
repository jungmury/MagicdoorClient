package jy.kim.lancs.magicdoor.adapter;

import java.util.ArrayList;
import java.util.Calendar;

import jy.kim.lancs.magicdoor.bean.AppointmentRespDetailBean;
import jy.kim.lancs.magicdoor.bean.parcelable.BookingDateInfoBean;
import magicdoor.lancs.R;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CalendarAdapterForStudent extends BaseAdapter {
	public boolean isMonthChange = false;
	public int monthIndicator;

	private final int GRID_VIEW_SIZE = 42;
	// For the Locale.UK
	private final int OFFSET_FOR_MON_TO_FRI = -2;
	private final int OFFSET_FOR_SAT = -2;
	private final int OFFSET_FOR_SUN = 5;
	private final int SUNDAY = 1;
	private final int SATURDAY = 7;
	private final int PREV_MONTH = -1;
	private final int NEXT_MONTH = 2;
	private final int THIS_MONTH = 0;

	private Context context;
	private Calendar calendar;

	private int currentMonthStartIndex;
	private int currentMonthEndDateIndex;
	private String[] days;
	private ArrayList<AppointmentRespDetailBean> result;
	private Calendar initialCalenar;

	public CalendarAdapterForStudent(Context context, Calendar calendar) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.calendar = calendar;
		initialCalenar = (Calendar) calendar.clone();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return days.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		// change date info for with selected info
		/*
		 * Log.i("MagicDoor", "position = " + position + " endInde = " +
		 * currentMonthEndDateIndex + " startIndext= " +
		 * currentMonthStartIndex);
		 */
		if (position > currentMonthEndDateIndex) {
			monthIndicator = THIS_MONTH;
			isMonthChange = true;
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
		} else if (position < currentMonthStartIndex) {
			monthIndicator = THIS_MONTH;
			isMonthChange = true;
			calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
		} else {
			monthIndicator = THIS_MONTH;
			isMonthChange = false;
		}
		calendar.set(Calendar.DATE, Integer.parseInt(days[position]));
		BookingDateInfoBean bean = new BookingDateInfoBean(calendar);
		return bean;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View gridView = convertView;
		ViewHolder holder;

		// initiate components
		if (gridView == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			gridView = vi.inflate(R.layout.view_calendar_items, null);
			holder = new ViewHolder();
			holder.tvDays = (TextView) gridView.findViewById(R.id.tvDays);
			holder.tvNumOfAppointments = (TextView) gridView
					.findViewById(R.id.tvNumOfAppintments);
			gridView.setTag(holder);
		} else {
			holder = (ViewHolder) gridView.getTag();
		}

		// initialize components before use
		initComponetsSetting(holder, gridView, position);

		// apply a digit for a date on the textview
		holder.tvDays.setText(days[position]);

		// display digits according to the number of appointments on a date
		showNumbersOfAppointmentsOnADate(holder, position);

		return gridView;

	}

	public void initDays(ArrayList<AppointmentRespDetailBean> result) {
		// TODO Auto-generated method stub
		Log.i("MagicDoor", "initDays");
		this.result = result;
		Calendar thisMonth = (Calendar) calendar.clone();
		thisMonth.set(Calendar.DATE, 1);

		// get the first day of first week of the month
		int firstDayOfTheWeek = thisMonth.getFirstDayOfWeek();
		int lastDay = thisMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
		int firstDay = (int) thisMonth.get(Calendar.DAY_OF_WEEK);
		// sun = 1, mon = 2, tue = 3, wed =4, thr = 5, fri = 6, sat = 7
		// DAY_OF_WEEK = 2, Locale.UK
		// figure size of the array
		days = new String[GRID_VIEW_SIZE];

		int j = 0;
		Calendar prevMonth = thisMonth;
		prevMonth.set(Calendar.MONTH, thisMonth.get(Calendar.MONTH)
				+ PREV_MONTH);
		int prevLastDay = prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH);

		// Monday to Friday
		if (SUNDAY < firstDay && firstDayOfTheWeek <= firstDay
				&& firstDay < SATURDAY) {
			// if first day is either sat or sun
			for (j = firstDay + OFFSET_FOR_MON_TO_FRI - 1; j >= 0; j--) {

				days[j] = String.valueOf(prevLastDay--);
			}
			j = firstDay + OFFSET_FOR_MON_TO_FRI; // -3
		} else if (firstDay == SUNDAY) {
			// sun
			for (j = firstDay + OFFSET_FOR_SUN - 1; j >= 0; j--) {
				days[j] = String.valueOf(prevLastDay--);
			}
			j = firstDay + OFFSET_FOR_SUN;
		} else if (firstDay == SATURDAY) {
			// sat
			for (j = firstDay + OFFSET_FOR_SAT - 1; j >= 0; j--) {
				days[j] = String.valueOf(prevLastDay--);
				// Log.i("MagicDoor", "days[" + j + "] = " + days[j]);
			}
			j = firstDay + OFFSET_FOR_SAT;
		}

		// populate days
		int dayNumber = 1;
		Calendar nextMonth = thisMonth;
		nextMonth.set(Calendar.MONTH, thisMonth.get(Calendar.MONTH)
				+ NEXT_MONTH);
		int nextMonthFirstDay = nextMonth.get(Calendar.DATE);
		currentMonthStartIndex = j;
		for (; j < days.length; j++) {
			// current month
			if (dayNumber <= lastDay) {
				days[j] = "" + dayNumber;
				dayNumber++;
				currentMonthEndDateIndex = j;
			} else {
				// next month
				days[j] = String.valueOf(nextMonthFirstDay++);
			}
		}
	}

	static class ViewHolder {
		TextView tvDays;
		TextView tvNumOfAppointments;
	}

	// initialize components before use
	private void initComponetsSetting(ViewHolder holder, View view, int position) {
		// TODO Auto-generated method stub
		holder.tvDays.setTextColor(Color.BLACK);
		holder.tvNumOfAppointments.setTextColor(Color.CYAN);
		view.setBackgroundColor(Color.TRANSPARENT);
		view.setEnabled(true);

		// red color if sunday
		if (position % 7 == 6) {
			holder.tvDays.setTextColor(Color.RED);
		}

		if ((currentMonthStartIndex < position || position <= currentMonthEndDateIndex)
				&& (position % 7 == 5 || position % 7 == 6)) {
			view.setEnabled(false);
			Log.i("MaigcDoor", "start= " + currentMonthStartIndex + " end= "
					+ currentMonthEndDateIndex);
			Log.i("MaigcDoor", "position = " + position);
		}

		// gray color if date in both prev and next month
		if ((position < currentMonthStartIndex)
				|| (position > currentMonthEndDateIndex)) {
			holder.tvDays.setTextColor(Color.GRAY);
		}

		// gray background color on the current date
		if ((calendar.get(Calendar.MONTH) == initialCalenar.get(Calendar.MONTH) && calendar
				.get(Calendar.YEAR) == initialCalenar.get(Calendar.YEAR))
				&& (position > currentMonthStartIndex && position < currentMonthEndDateIndex)
				&& Integer.parseInt(days[position]) == initialCalenar
						.get(Calendar.DATE)) {
			view.setBackgroundColor(Color.GRAY);
		}

		// disable gridview to prevent choose the date that is earlier than
		// current date
		// only disable before startDateInedx and after endDateIndex
		if (
		// disable earlier date in the current month
		(initialCalenar.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
				&& initialCalenar.get(Calendar.MONTH) == calendar
						.get(Calendar.MONTH) && (position < currentMonthEndDateIndex && initialCalenar
				.get(Calendar.DATE) > Integer.parseInt(days[position])))

		||
		// disable if calendar is previous month to prevent to pick a date
				(initialCalenar.get(Calendar.YEAR) >= calendar
						.get(Calendar.YEAR)
						&& initialCalenar.get(Calendar.MONTH) > calendar
								.get(Calendar.MONTH) && (position < currentMonthEndDateIndex && initialCalenar
						.get(Calendar.DATE) > Integer.parseInt(days[position])))) {

			// Log.i("MagicDoor", "position = " + position);
			holder.tvNumOfAppointments.setEnabled(false);
			holder.tvDays.setEnabled(false);
			view.setEnabled(false);
		}

	}

	private void showNumbersOfAppointmentsOnADate(ViewHolder holder,
			int position) {
		// TODO Auto-generated method stub
		String[] temp;
		int count = 0;
		if (result != null) {
			for (AppointmentRespDetailBean a : result) {
				temp = a.bookingDate.toString().split("-");
				if (temp[2].equals(days[position])) {
					count++;
				}
			}
			if (count != 0 && currentMonthStartIndex < position
					&& position <= currentMonthEndDateIndex) {
				holder.tvNumOfAppointments.setText(String.valueOf(count));
			}
		}
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
}
