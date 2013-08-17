package jy.kim.lancs.magicdoor.bean.parcelable;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;

public class BookingDateInfoBean implements Parcelable {
	private Calendar calendar;
	public String year;
	public String month;
	public String monthName;
	public String selectedDate;
	public Date bookingDate;
	public Time bookingTime;

	public BookingDateInfoBean(Calendar calendar) {
		// TODO Auto-generated constructor stub
		this.calendar = calendar;
		this.selectedDate = String.valueOf(calendar.get(Calendar.DATE));
		init();
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(year);
		dest.writeString(month);
		dest.writeString(monthName);
		dest.writeString(selectedDate);
		dest.writeSerializable(bookingDate);
		dest.writeSerializable(bookingTime);
	}

	// this is used to regenerate your object. All Parcelables must have a
	// CREATOR that implements these two methods
	public static final Parcelable.Creator<BookingDateInfoBean> CREATOR = new Parcelable.Creator<BookingDateInfoBean>() {
		public BookingDateInfoBean createFromParcel(Parcel in) {
			return new BookingDateInfoBean(in);
		}

		public BookingDateInfoBean[] newArray(int size) {
			return new BookingDateInfoBean[size];
		}
	};

	// example constructor that takes a Parcel and gives you an object populated
	// with it's values
	private BookingDateInfoBean(Parcel in) {
		year = in.readString();
		month = in.readString();
		monthName = in.readString();
		selectedDate = in.readString();
		bookingDate = (Date) in.readSerializable();
		bookingTime = (Time) in.readSerializable();
	}

	private void init() {
		// TODO Auto-generated method stub
		year = String.valueOf(calendar.get(Calendar.YEAR));
		month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		monthName = (String) DateFormat.format("MMMM", calendar);
		bookingDate = new Date(calendar.getTimeInMillis());
		bookingTime = new Time(calendar.getTimeInMillis());
	}
}
