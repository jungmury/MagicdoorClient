package jy.kim.lancs.magicdoor.appointment;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;

import jy.kim.lancs.magicdoor.account.AccountInfo;
import jy.kim.lancs.magicdoor.bean.AppointmentReqDetailBean;
import jy.kim.lancs.magicdoor.bean.AppointmentRespDetailBean;
import jy.kim.lancs.magicdoor.bean.UserAccountInfoBean;
import jy.kim.lancs.magicdoor.bean.parcelable.BookingDateInfoBean;
import jy.kim.lancs.magicdoor.time.PickDateAndTimeActivity;
import jy.kim.lancs.magicdoor.util.MagicDoorContants;
import magicdoor.lancs.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class MakeAppointmentsActivity extends Activity implements
		OnClickListener {

	private static final int DATE_RESULT = 1;
	private Button btnToSelectDateAndTime;
	private Button btnToConfirm;

	private EditText bookingTitle;
	private EditText bookingRequester;
	private EditText bookingDescrition;

	private TextView bookingResponder;

	private Appointment appModel;

	private String tagOwnerName;
	private String tagOwner;
	private String userName;

	private Date date;
	private Time time;
	//private int duration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Log.d("MagicDoor", "MakeAppointmentControllerActivity");

		setContentView(R.layout.view_student_make_appointments);

		btnToConfirm = (Button) findViewById(R.id.btnToConfirmDetail);
		btnToSelectDateAndTime = (Button) findViewById(R.id.btnToSelectDateAndTime);

		bookingTitle = (EditText) findViewById(R.id.etTitleForAppointmentReq);
		bookingRequester = (EditText) findViewById(R.id.etNameForAppointmentReq);
		bookingDescrition = (EditText) findViewById(R.id.etDescForAppointmentReq);
		bookingResponder = (TextView) findViewById(R.id.tvAppointmentResponderMakeAnApp);

		appModel = new Appointment(this);
		tagOwnerName = appModel.tagOwnerName;
		tagOwner = appModel.tagOwner;
		userName = appModel.userName;
		Log.d("MagicDoor", "userName = " + userName);
		Log.d("MagicDoor", "tagOwner = " + tagOwner);

		// setting name textview with logged in user's fist name and last name
		AccountInfo aiModel = new AccountInfo(this);
		String param = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_ACC_INFO
				+ userName;
		UserAccountInfoBean accInfo = aiModel
				.getAccountInfoForTheUserWith(param);
		bookingRequester.setText(accInfo.firstName + " " + accInfo.lastName);
		bookingRequester.setEnabled(false);

		btnToConfirm.setOnClickListener(this);
		btnToSelectDateAndTime.setOnClickListener(this);

		bookingResponder.setText(" " + tagOwnerName);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		AppointmentReqDetailBean bean = null;
		Intent nextActivity = null;
		int id = v.getId();
		switch (id) {
		// To request appointment
		case R.id.btnToConfirmDetail:

			// if there is no missing field, then proceed to next
			if (bookingTitle.getText().length() > 0
					&& bookingDescrition.getText().length() > 0 && date != null) {

				bean = createAppReqDetailBean();
				ArrayList<AppointmentRespDetailBean> appointments = appModel
						.putAppointmentWithInfo(bean);
				if (appointments != null) {
					Toast.makeText(this, "Booking Requested",
							Toast.LENGTH_SHORT).show();
					nextActivity = new Intent(this,
							ShowAppointmentActivity.class);
					putExtraTo(nextActivity, appointments, "MakeAppointment");
					nextActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(nextActivity);
				} else {
					Toast.makeText(this, "Request Failed. Try it Again!",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(this,
						"Please make sure if there is missing field",
						Toast.LENGTH_SHORT).show();
			}
			break;
		// to select date and time for an appointment
		case R.id.btnToSelectDateAndTime:
			nextActivity = new Intent(this, PickDateAndTimeActivity.class);
			// pass tagOwner, userName value to PickDateAndTimControllerActivity
			putExtraTo(nextActivity);
			startActivityForResult(nextActivity, DATE_RESULT);
			break;

		default:
			break;
		}

	}

	/*
	 * private void showDurationPicker() { // TODO Auto-generated method stub
	 * AlertDialog durPicker = new AlertDialog.Builder(this).create();
	 * 
	 * LayoutInflater inflater = (LayoutInflater)
	 * getSystemService(Context.LAYOUT_INFLATER_SERVICE); View view =
	 * inflater.inflate(R.layout.view_duration_picker, null); final NumberPicker
	 * picker = (NumberPicker) view .findViewById(R.id.durationPicker);
	 * picker.setMaxValue(2); picker.setMinValue(1); picker.setValue(1);
	 * durPicker.setView(picker);
	 * durPicker.setTitle("Please select duration for your appointment!");
	 * durPicker.setButton(Dialog.BUTTON_NEUTRAL, "Select", new
	 * DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface dialog, int which) { //
	 * TODO Auto-generated method stub duration = picker.getValue();
	 * btnToSelectDuration.setText(duration + " hour"); dialog.dismiss(); } });
	 * durPicker.show();
	 * 
	 * }
	 */

	// create data object with appointment detail
	private AppointmentReqDetailBean createAppReqDetailBean() {
		AppointmentReqDetailBean bean = new AppointmentReqDetailBean();
		bean.uri = MagicDoorContants.REST_SERVICE_URI_FOR_RQUEST_APPOINMENT;
		bean.refValue = String.valueOf(new Date(Calendar.getInstance()
				.getTimeInMillis()))
				+ "_"
				+ String.valueOf(new Time(Calendar.getInstance()
						.getTimeInMillis()));
		bean.title = bookingTitle.getText().toString();
		bean.lecturerUserName = tagOwner;
		bean.requesterUserName = userName;
		bean.description = bookingDescrition.getText().toString();
		bean.bookingDate = date;
		bean.bookingTime = time;
		bean.status = "requested";
		bean.duration = 1;
		// ///////////

		// //////////
		return bean;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case DATE_RESULT:
			if (resultCode == RESULT_OK) {
				BookingDateInfoBean dateInfo = (BookingDateInfoBean) data
						.getParcelableExtra("dateInfo");
				date = dateInfo.bookingDate;
				time = dateInfo.bookingTime;
				btnToSelectDateAndTime
						.setText(dateInfo.selectedDate + " "
								+ dateInfo.monthName + " " + dateInfo.year
								+ " " + time);
			}
			break;

		default:
			break;
		}
	}

	private void putExtraTo(Intent nextActivity,
			ArrayList<AppointmentRespDetailBean> apps, String prevActivity) {
		// TODO Auto-generated method stub
		nextActivity.putExtra("appointments", apps);
		nextActivity.putExtra("userName", userName);
		nextActivity.putExtra("prevActivity", prevActivity);
	}

	private void putExtraTo(Intent nextActivity) {
		// TODO Auto-generated method stub
		nextActivity.putExtra("tagOwner", tagOwner);
		nextActivity.putExtra("userName", userName);
	}
}
