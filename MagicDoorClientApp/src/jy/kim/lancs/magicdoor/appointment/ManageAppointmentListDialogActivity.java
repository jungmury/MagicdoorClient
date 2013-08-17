package jy.kim.lancs.magicdoor.appointment;

import java.util.ArrayList;

import jy.kim.lancs.magicdoor.adapter.AppointmentListViewAdapter;
import jy.kim.lancs.magicdoor.bean.AppointmentRespDetailBean;
import jy.kim.lancs.magicdoor.dialog.MagicDoorCustomAlertDialogBuilder;
import jy.kim.lancs.magicdoor.query.QueryForAppointments;
import jy.kim.lancs.magicdoor.util.DateParseUtil;
import jy.kim.lancs.magicdoor.util.MagicDoorContants;
import magicdoor.lancs.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ManageAppointmentListDialogActivity extends Activity implements
		OnItemClickListener, OnClickListener {
	private ListView lvForAppointments;
	private Appointment model;
	private QueryForAppointments query;
	private EditText etNote;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("MagicDoor", "ManageAppointmentsActivity");

		setContentView(R.layout.view_show_appointments);

		lvForAppointments = (ListView) findViewById(R.id.lvForAppointmentsShow);
		model = new Appointment(ManageAppointmentListDialogActivity.this);

		// get data from previous activity
		Intent prevActivity = getIntent();
		@SuppressWarnings("unchecked")
		ArrayList<AppointmentRespDetailBean> appointments = (ArrayList<AppointmentRespDetailBean>) prevActivity
				.getSerializableExtra("appointments");
		query = (QueryForAppointments) prevActivity
				.getSerializableExtra("query");
		// ////////////////////////////////////////

		populateListView(appointments);
	}

	private void populateListView(ArrayList<AppointmentRespDetailBean> result) {
		// TODO Auto-generated method stub
		Log.i("MagicDoor",
				"ManageAppointmentsListDialogActivity pop without pacelable");
		lvForAppointments = (ListView) findViewById(R.id.lvForAppointmentsShow);

		// Initialize our ArrayList
		// Initialize our array adapter notice how it references the
		// listitems.xml layout
		// Set the above adapter as the adapter of choice for our list
		AppointmentListViewAdapter arrayAdapter = null;
		TextView tvEmpty = (TextView) findViewById(R.id.tvEmptyAppListView);
		if (result != null) {
			arrayAdapter = new AppointmentListViewAdapter(
					ManageAppointmentListDialogActivity.this,
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
		if (appointment.status.equals("rejected")) {
			dialog.setMessage("Lecturer name: " + appointment.name
					+ "\nTitle: " + appointment.title + "\nDescription: "
					+ appointment.description + "\nDate: " + newDate
					+ "\nFrom: " + appointment.bookingTime + "\nStatus: "
					+ appointment.status + "\nNote: " + appointment.note);
		} else {
			dialog.setMessage("Requested by: " + appointment.name + "\nTitle: "
					+ appointment.title + "\nDescription: "
					+ appointment.description + "\nDate: " + newDate
					+ "\nFrom: " + appointment.bookingTime + "\nStatus: "
					+ appointment.status);
		}

		if (!appointment.status.equals("confirmed")) {
			dialog.setPositiveButton("Request Confirm",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_COFIRMING_APPOINTMENT;
							query.tagOwner = model.userName;
							query.refValue = appointment.refValue;
							ArrayList<AppointmentRespDetailBean> result = model
									.confirmAppointment(query);
							if (result != null) {
								result = null;
								query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_ALL_APPOINTMENTS_OF_THE_LECTURER_USER_ON_THE_DATE;
								result = model
										.getAllAppointmentsOfTheLecturerUserOnTheDate(query);

							}

							Toast.makeText(
									ManageAppointmentListDialogActivity.this,
									"Request Confirmed", Toast.LENGTH_SHORT)
									.show();
							// Refresh dialog
							refreshDialog(result);
							dialog.dismiss();

						}
					});
		}

		dialog.setNeutralButton("Back", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		if (!appointment.status.equals("rejected")) {
			dialog.setNegativeButton("Request Reject",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// ///////

							LayoutInflater inflater = LayoutInflater
									.from(ManageAppointmentListDialogActivity.this);// (LayoutInflater)
																					// ManageAppointmentsListDialogActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
							final View view = inflater.inflate(
									R.layout.view_form_write_note, null);
							etNote = (EditText) view
									.findViewById(R.id.etWriteNote);

							AlertDialog noteDialog = new AlertDialog.Builder(
									ManageAppointmentListDialogActivity.this)
									.create();
							noteDialog.setView(view);
							noteDialog.setButton(Dialog.BUTTON_NEUTRAL,
									"Reject",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											query.note = etNote.getText()
													.toString();
											query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_REJECTING_APPOINTMENT;
											query.refValue = appointment.refValue;
											ArrayList<AppointmentRespDetailBean> result = model
													.rejectAppointment(query);

											Toast.makeText(
													ManageAppointmentListDialogActivity.this,
													"Request Rejected",
													Toast.LENGTH_SHORT).show();
											if (result != null) {
												result = null;
												query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_ALL_APPOINTMENTS_OF_THE_LECTURER_USER_ON_THE_DATE;
												result = model
														.getAllAppointmentsOfTheLecturerUserOnTheDate(query);
											}

											// Refresh dialog
											dialog.dismiss();
											refreshDialog(result);
										}
									});
							noteDialog.show();
						}
					});
		}

		dialog.show();
	}

	// refresh dialog
	protected void refreshDialog(ArrayList<AppointmentRespDetailBean> result) {
		// TODO Auto-generated method stub
		populateListView(result);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		default:
			break;
		}

	}
}