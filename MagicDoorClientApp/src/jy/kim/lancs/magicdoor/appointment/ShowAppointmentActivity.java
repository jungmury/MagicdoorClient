package jy.kim.lancs.magicdoor.appointment;

import java.util.ArrayList;

import jy.kim.lancs.magicdoor.adapter.AppointmentListViewAdapter;
import jy.kim.lancs.magicdoor.bean.AppointmentRespDetailBean;
import jy.kim.lancs.magicdoor.bean.UserAccountInfoBean;
import jy.kim.lancs.magicdoor.dialog.MagicDoorCustomAlertDialogBuilder;
import jy.kim.lancs.magicdoor.main.StudentMainFragmentActivity;
import jy.kim.lancs.magicdoor.query.QueryForAppointments;
import jy.kim.lancs.magicdoor.util.DateParseUtil;
import jy.kim.lancs.magicdoor.util.MagicDoorContants;
import magicdoor.lancs.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ShowAppointmentActivity extends Activity implements
		OnItemClickListener {
	private ListView lvForAppointments;
	private Appointment appModel;

	private String tagOwner;
	private String tagOwnerName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("MagicDoor", "ShowAppointmentsActivity");

		tagOwner = getIntent().getStringExtra("tagOwner");
		tagOwnerName = getIntent().getStringExtra("tagOwnerName");
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		if (getIntent().getStringExtra("prevActivity").equals("MyAccount")) {
			setTitle("My Appointment History");
		}

		setContentView(R.layout.view_show_appointments);
		appModel = new Appointment(this);

		lvForAppointments = (ListView) findViewById(R.id.lvForAppointmentsShow);

		Intent prevActivity = getIntent();
		@SuppressWarnings("unchecked")
		ArrayList<AppointmentRespDetailBean> appointments = (ArrayList<AppointmentRespDetailBean>) prevActivity
				.getSerializableExtra("appointments");

		populateListView(appointments);

	}

	private void populateListView(ArrayList<AppointmentRespDetailBean> result) {
		// TODO Auto-generated method stub
		Log.i("MagicDoor", "ShowAppointActivity pop without pacelable");
		lvForAppointments = (ListView) findViewById(R.id.lvForAppointmentsShow);

		// Initialize our ArrayList
		// Initialize our array adapter notice how it references the
		// listitems.xml layout
		// Set the above adapter as the adapter of choice for our list
		AppointmentListViewAdapter arrayAdapter = null;
		TextView tvEmpty = (TextView) findViewById(R.id.tvEmptyAppListView);
		if (result != null) {
			arrayAdapter = new AppointmentListViewAdapter(
					ShowAppointmentActivity.this,
					R.layout.view_listitems_appointment, result);
			tvEmpty.setVisibility(View.INVISIBLE);
			lvForAppointments.setAdapter(arrayAdapter);

			// will redraw when data set changes
			arrayAdapter.notifyDataSetChanged();
			arrayAdapter.notifyDataSetInvalidated();
			lvForAppointments.setOnItemClickListener(this);

		} else {
			tvEmpty.setVisibility(View.VISIBLE);
			// force list view to redraw
			lvForAppointments.setAdapter(arrayAdapter);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		showSelectedAppointmentDetailAlertDialog(position);
	}

	private void showSelectedAppointmentDetailAlertDialog(int position) {
		// TODO Auto-generated method stub
		final AppointmentRespDetailBean appointment = (AppointmentRespDetailBean) lvForAppointments
				.getItemAtPosition(position);
		MagicDoorCustomAlertDialogBuilder dialog = new MagicDoorCustomAlertDialogBuilder(
				this);
		dialog.create();
		dialog.setTitle("Appointment Detail");
		String newDate = DateParseUtil
				.getDateInNewFormat(appointment.bookingDate);
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});

		if (appointment.status.equals("confirmed")) {
			dialog.setMessage("Lecturer name: " + appointment.name
					+ "\nTitle: " + appointment.title + "\nDescription: "
					+ appointment.description + "\nDate: " + newDate
					+ "\nFrom: " + appointment.bookingTime);
		/*	dialog.setNegativeButton("Cancel Request",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// ///////
							QueryForAppointments query = new QueryForAppointments();
							query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_DELETING_AN_APPOINTMENT_OF_THE__STUDENT_USER;
							query.requesterUserName = appointment.requesterUserName;
							query.refValue = appointment.refValue;
							ArrayList<AppointmentRespDetailBean> result = appModel
									.deleteAnAppointmentOfTheUser(query);

							// populate list view after deletion
							if (result != null) {
								populateListView(result);
								Toast.makeText(ShowAppointmentActivity.this,
										"Request Cancelled", Toast.LENGTH_SHORT)
										.show();
								// ohterwise finish current activity
							}
							populateListView(result);

							// ///////
							dialog.cancel();
						}
					});
					*/
		}

		if (appointment.status.equals("rejected")) {
			String uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_LECTURER_INFO_FOR_THE_REJECTED_APPOINTMENT;
			final UserAccountInfoBean lectureInfo = appModel.getLecutrerInfoForTheRejectedAppointment(uri, appointment.refValue);
			dialog.setMessage("Lecturer name: " + appointment.name
					+ "\nTitle: " + appointment.title + "\nDescription: "
					+ appointment.description + "\nDate: " + newDate
					+ "\nFrom: " + appointment.bookingTime + "\nNote: "
					+ appointment.note);
			dialog.setNegativeButton("Request Alternative",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// ///////
							//delete when alternative requested
							QueryForAppointments query = new QueryForAppointments();
							query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_DELETING_AN_APPOINTMENT_OF_THE__STUDENT_USER;
							query.requesterUserName = appointment.requesterUserName;
							query.refValue = appointment.refValue;
							ArrayList<AppointmentRespDetailBean> result = appModel
									.deleteAnAppointmentOfTheUser(query);

							// populate list view after deletion
							if (result != null) {
								populateListView(result);
							//	Toast.makeText(ShowAppointmentActivity.this,
								//		"Request Cancelled", Toast.LENGTH_SHORT)
									//	.show();
								// ohterwise finish current activity
							}
							///populateListView(result);

							Intent nextActivity = new Intent(
									ShowAppointmentActivity.this,
									MakeAppointmentsActivity.class)
									.putExtra("tagOwner", lectureInfo.userName)
									.putExtra("tagOwnerName", lectureInfo.firstName + " " + lectureInfo.lastName)
									.putExtra("userName",
											appointment.requesterUserName);
						//	nextActivity.setFlags();
							startActivity(nextActivity);
						}
					});
		}

		dialog.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent;
		if (getIntent().getStringExtra("prevActivity").equals("MyAccount")) {
			intent = new Intent(this, StudentMainFragmentActivity.class);
		} else {
			intent = new Intent(this, StudentMainFragmentActivity.class);
		}
		intent.putExtra("userName", appModel.userName);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		return true;
	}
}
