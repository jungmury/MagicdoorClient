package jy.kim.lancs.magicdoor.appointment;

import java.util.ArrayList;

import jy.kim.lancs.magicdoor.adapter.AppointmentListViewAdapter;
import jy.kim.lancs.magicdoor.bean.AppointmentRespDetailBean;
import jy.kim.lancs.magicdoor.dialog.MagicDoorCustomAlertDialogBuilder;
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
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class ManageAppointmentListFragment extends Fragment implements
		OnItemClickListener, OnClickListener {
	private ListView lvForAppointments;
	private Appointment model;
	private Activity activity;
	private String userName;
	private ArrayList<AppointmentRespDetailBean> _appointments;
	
	private View rootView;

	private EditText etNote;

	public ManageAppointmentListFragment() {
		// TODO Auto-generated constructor stub
	}

	public ManageAppointmentListFragment(Activity activity,
			ArrayList<AppointmentRespDetailBean> appointments) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		_appointments = appointments;
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

		rootView = inflater.inflate(R.layout.view_show_appointments, container,
				false);

		lvForAppointments = (ListView) rootView
				.findViewById(R.id.lvForAppointmentsShow);
		model = new Appointment(activity);
		userName = model.userName;

		
		populateListView(_appointments);
		return rootView;

	}

	private void populateListView(ArrayList<AppointmentRespDetailBean> result) {
		// TODO Auto-generated method stub
		Log.i("MagicDoor", "ShowAppointActivity pop without pacelable");
		lvForAppointments = (ListView) rootView
				.findViewById(R.id.lvForAppointmentsShow);

		// Initialize our ArrayList
		// Initialize our array adapter notice how it references the
		// listitems.xml layout
		// Set the above adapter as the adapter of choice for our list
		AppointmentListViewAdapter arrayAdapter = null;
		TextView tvEmpty = (TextView) rootView
				.findViewById(R.id.tvEmptyAppListView);
		if (result != null) {
			arrayAdapter = new AppointmentListViewAdapter(activity,
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
				activity);
		dialog.create();
		dialog.setTitle("Appointment Detail");
		String newDate = DateParseUtil
				.getDateInNewFormat(appointment.bookingDate);

		dialog.setMessage("Requested by: " + appointment.name + "\nTitle: "
				+ appointment.title + "\nDescription: "
				+ appointment.description + "\nDate: " + newDate + "\nFrom: "
				+ appointment.bookingTime + "\nStatus: " + appointment.status
				+ "\nNote: " + appointment.note);

		if (!appointment.status.equals("confirmed")) {
			dialog.setPositiveButton("Request Confirm",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							QueryForAppointments query = new QueryForAppointments();
							query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_COFIRMING_APPOINTMENT;
							query.tagOwner = userName;
							query.refValue = appointment.refValue;

							ArrayList<AppointmentRespDetailBean> result = model
									.confirmAppointment(query);

							Toast.makeText(activity, "Request Confirmed",
									Toast.LENGTH_SHORT).show();
							populateListView(result);

							dialog.cancel();
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

							LayoutInflater inflater = LayoutInflater
									.from(activity);// (LayoutInflater)
													// ManageAppointmentsListDialogActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
							final View view = inflater.inflate(
									R.layout.view_form_write_note, null);
							etNote = (EditText) view
									.findViewById(R.id.etWriteNote);

							AlertDialog noteDialog = new AlertDialog.Builder(
									activity).create();
							noteDialog.setView(view);
							noteDialog.setButton(Dialog.BUTTON_NEUTRAL,
									"Confirm",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											// !! need to change for note.... if
											// there is blank it crahses
											// parameter do not allow blank
											QueryForAppointments query = new QueryForAppointments();
											query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_REJECTING_APPOINTMENT;
											query.tagOwner = userName;
											query.refValue = appointment.refValue;
											query.note = etNote.getText()
													.toString();
											ArrayList<AppointmentRespDetailBean> result = model
													.rejectAppointment(query);

											Toast.makeText(activity,
													"Request Rejected",
													Toast.LENGTH_SHORT).show();
											populateListView(result);

											// ///////
											dialog.cancel();
										}
									});
							dialog.cancel();
							noteDialog.show();
						}
					});
		}
		dialog.show();
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