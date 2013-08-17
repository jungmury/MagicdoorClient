package jy.kim.lancs.magicdoor.account;

import java.util.ArrayList;

import jy.kim.lancs.magicdoor.appointment.Appointment;
import jy.kim.lancs.magicdoor.appointment.ShowAppointmentActivity;
import jy.kim.lancs.magicdoor.bean.AppointmentRespDetailBean;
import jy.kim.lancs.magicdoor.bean.MessageBean;
import jy.kim.lancs.magicdoor.bean.UserAccountInfoBean;
import jy.kim.lancs.magicdoor.bean.parcelable.UserAccountInfoParcelableBean;
import jy.kim.lancs.magicdoor.login.LogInActivity;
import jy.kim.lancs.magicdoor.main.Main;
import jy.kim.lancs.magicdoor.message.MessageModel;
import jy.kim.lancs.magicdoor.message.ShowMessageActivity;
import jy.kim.lancs.magicdoor.query.QueryForAppointments;
import jy.kim.lancs.magicdoor.query.QueryForMessages;
import jy.kim.lancs.magicdoor.util.MagicDoorContants;
import magicdoor.lancs.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class StudentMyAccountActivity extends Activity implements
		OnClickListener {
	private String userName;
	private AccountInfo accInfoModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTitle("My Account");
		setContentView(R.layout.view_student_my_account);

		// Set up the action bar.
		// final ActionBar actionBar = getActionBar();
		// actionBar.setDisplayHomeAsUpEnabled(true);

		accInfoModel = new AccountInfo(this);
		userName = accInfoModel.userName;

		Button btnToAccInfo = (Button) findViewById(R.id.btnToMyAccInfoStu);
		Button btnToConfirmedAppHistory = (Button) findViewById(R.id.btnToMyConfirmedAppointmentHistory);
		Button btnToRejectedAppHistory = (Button) findViewById(R.id.btnToMyRejectedAppointmentHistory);
		Button btnToLogOut = (Button) findViewById(R.id.btnToLogOut);
		Button btnToSentMsgs = (Button) findViewById(R.id.btnToSentMessages);
		Button btnToReceivedMsgs = (Button) findViewById(R.id.btnToReceivedMessages);
		btnToAccInfo.setOnClickListener(this);
		btnToConfirmedAppHistory.setOnClickListener(this);
		btnToRejectedAppHistory.setOnClickListener(this);
		btnToLogOut.setOnClickListener(this);
		btnToSentMsgs.setOnClickListener(this);
		btnToReceivedMsgs.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent nextActivity;
		int id = v.getId();
		String param;
		switch (id) {
		case R.id.btnToMyAccInfoStu:

			param = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_ACC_INFO
					+ userName;
			UserAccountInfoBean userAccountInfo = accInfoModel
					.getAccountInfoForTheUserWith(param);

			if (userAccountInfo != null) {
				nextActivity = new Intent(this, MyAccountInfoActivity.class);
				putExtrasTo(nextActivity, "StudentMyAccountActivity",
						userAccountInfo);
				nextActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(nextActivity);
			} else {
				Toast.makeText(this,
						"Account Retrieving failed. Try it again!",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btnToMyConfirmedAppointmentHistory:
			Appointment appModel = new Appointment(this);
			QueryForAppointments query = new QueryForAppointments();
			query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_RQUEST_ALL_APPOINMENTS_LIST_OF_THE_STUDENT_USER;
			query.requesterUserName = userName;
			ArrayList<AppointmentRespDetailBean> appointments = appModel
					.getAllAppointmentsOfTheStudentUser(query);
			ArrayList<AppointmentRespDetailBean> confirmed = null;
			if (appointments != null) {
				confirmed = new ArrayList<AppointmentRespDetailBean>();
				for (AppointmentRespDetailBean a : appointments) {
					if (a.status.equals("confirmed")) {
						confirmed.add(a);
					}
				}
			}
			// if (appointments != null) {
			nextActivity = new Intent(this, ShowAppointmentActivity.class);
			putAppExtrasTo(nextActivity, confirmed, "MyAccount");
			nextActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(nextActivity);
			// } else {
			// Toast.makeText(this, "No Appointments",
			// Toast.LENGTH_SHORT).show();
			// }

			break;
		case R.id.btnToMyRejectedAppointmentHistory:
			appModel = new Appointment(this);
			query = new QueryForAppointments();
			query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_RQUEST_ALL_APPOINMENTS_LIST_OF_THE_STUDENT_USER;
			query.requesterUserName = userName;
			appointments = appModel.getAllAppointmentsOfTheStudentUser(query);
			ArrayList<AppointmentRespDetailBean> rejected = null;
			if (appointments != null) {
				rejected = new ArrayList<AppointmentRespDetailBean>();
				for (AppointmentRespDetailBean a : appointments) {
					if (a.status.equals("rejected")) {
						rejected.add(a);
					}
				}
			}
			// if (appointments != null) {
			nextActivity = new Intent(this, ShowAppointmentActivity.class);
			putAppExtrasTo(nextActivity, rejected, "MyAccount");
			nextActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(nextActivity);
			// } else {
			// Toast.makeText(this, "No Appointments",
			// Toast.LENGTH_SHORT).show();
			// }

			break;

		case R.id.btnToSentMessages:
			MessageModel msgModel = new MessageModel(this);
			QueryForMessages query2 = new QueryForMessages();
			query2.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GET_ALL_SENT_MESSAGES_FOR_SENDER;
			query2.sender = userName;
			ArrayList<MessageBean> messages = msgModel
					.getMessagesOfTheSender(query2);
			nextActivity = new Intent(this, ShowMessageActivity.class);
			putMsgExtrasTo(nextActivity, messages, "SentMessages", "MyAccount");
			nextActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(nextActivity);

			break;

		case R.id.btnToReceivedMessages:
			msgModel = new MessageModel(this);
			query2 = new QueryForMessages();
			query2.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GET_ALL_RECEIVED_MESSAGES_FOR_SENDER;
			query2.sender = userName;
			messages = msgModel.getMessagesOfTheSender(query2);
			nextActivity = new Intent(this, ShowMessageActivity.class);
			putMsgExtrasTo(nextActivity, messages, "ReceivedMessages",
					"MyAccount");
			nextActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(nextActivity);
			break;

		case R.id.btnToLogOut:
			AlertDialog logOutDialog = new AlertDialog.Builder(this).create();
			logOutDialog.setTitle("Log Out");
			logOutDialog.setMessage("Do you really want to log out?");
			logOutDialog.setButton(Dialog.BUTTON_POSITIVE, "YES",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
							Main studentMainModel = new Main(
									StudentMyAccountActivity.this);
							studentMainModel
									.removeAccountFromTheAccountManager();
							Toast.makeText(StudentMyAccountActivity.this,
									"Logged Out", Toast.LENGTH_SHORT).show();
							Intent nextActivity = new Intent(
									StudentMyAccountActivity.this,
									LogInActivity.class);
							nextActivity
									.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(nextActivity);
						}
					});
			logOutDialog.setButton(Dialog.BUTTON_NEGATIVE, "NO",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
			logOutDialog.show();

		default:
			break;
		}
	}

	/*
	 * private void putAppExtrasTo(Intent nextActivity,
	 * ArrayList<AppointmentRespDetailBean> confirmed, String prevActivityName,
	 * String tagOwner, String tagOwnerName) {
	 * nextActivity.putExtra("appointments", confirmed);
	 * nextActivity.putExtra("userName", userName);
	 * nextActivity.putExtra("prevActivity", prevActivityName);
	 * nextActivity.putExtra("tagOwner", tagOwner);
	 * nextActivity.putExtra("tagOwnerName", tagOwnerName); // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */

	private void putMsgExtrasTo(Intent nextActivity,
			ArrayList<MessageBean> messages, String prevBtn,
			String prevActivityName) {
		// TODO Auto-generated method stub
		nextActivity.putExtra("messages", messages);
		nextActivity.putExtra("userName", userName);
		nextActivity.putExtra("prevBtn", prevBtn);
		nextActivity.putExtra("prevActivity", prevActivityName);
	}

	private void putAppExtrasTo(Intent nextActivity,
			ArrayList<AppointmentRespDetailBean> apps, String prevActivityName) {
		// TODO Auto-generated method stub
		nextActivity.putExtra("appointments", apps);
		nextActivity.putExtra("userName", userName);
		nextActivity.putExtra("prevActivity", prevActivityName);
	}

	private void putExtrasTo(Intent nextActivity, String activityName,
			UserAccountInfoBean result) {
		// TODO Auto-generated method stub
		UserAccountInfoParcelableBean userAccountInfo = new UserAccountInfoParcelableBean();
		userAccountInfo.userName = result.userName;
		userAccountInfo.password1 = result.password;
		userAccountInfo.password2 = result.password;
		userAccountInfo.firstName = result.firstName;
		userAccountInfo.lastName = result.lastName;
		userAccountInfo.eMailAddress = result.eMailAddress;
		userAccountInfo.userType = result.userType;

		nextActivity.putExtra("userName", userName);
		nextActivity.putExtra("prevActivity", activityName);
		nextActivity.putExtra("userAccountInfo", userAccountInfo);
	}

	/*
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // TODO
	 * Auto-generated method stub Intent nextActivity; nextActivity = new
	 * Intent(this, StudentMainFragmentActivity.class);
	 * nextActivity.putExtra("userName", userName);
	 * nextActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	 * startActivity(nextActivity);
	 * 
	 * return true; }
	 */
}
