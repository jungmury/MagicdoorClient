package jy.kim.lancs.magicdoor.account;

import jy.kim.lancs.magicdoor.bean.UserAccountInfoBean;
import jy.kim.lancs.magicdoor.bean.parcelable.UserAccountInfoParcelableBean;
import jy.kim.lancs.magicdoor.login.LogInActivity;
import jy.kim.lancs.magicdoor.main.Main;
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

public class LecturerMyAccountActivity extends Activity implements
		OnClickListener {
	private String userName;
	private AccountInfo accInfoModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.view_lecturer_my_account);

		// Set up the action bar.
	//	final ActionBar actionBar = getActionBar();
//		actionBar.setDisplayHomeAsUpEnabled(true);

		accInfoModel = new AccountInfo(this);
		userName = accInfoModel.userName;

		Button btnToAccInfo = (Button) findViewById(R.id.btnToMyAccInfoLec);
		Button btnToLogOut = (Button) findViewById(R.id.btnToLogOut);

		btnToAccInfo.setOnClickListener(this);
		btnToLogOut.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		Intent nextActivity;
		String param;
		switch (id) {
		case R.id.btnToMyAccInfoLec:

			param = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_ACC_INFO
					+ userName;
			UserAccountInfoBean userAccountInfo = accInfoModel
					.getAccountInfoForTheUserWith(param);

			if (userAccountInfo != null) {
				nextActivity = new Intent(this, MyAccountInfoActivity.class);
				putExtrasTo(nextActivity, "LecturerMyAccountActivity",
						userAccountInfo);
				nextActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(nextActivity);
			} else {
				Toast.makeText(this,
						"Account Retrieving failed. Try it again!",
						Toast.LENGTH_SHORT).show();
			}
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
									LecturerMyAccountActivity.this);
							studentMainModel
									.removeAccountFromTheAccountManager();
							Toast.makeText(LecturerMyAccountActivity.this,
									"Logged Out", Toast.LENGTH_SHORT).show();
							Intent nextActivity = new Intent(
									LecturerMyAccountActivity.this,
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
		nextActivity.putExtra("userAccountInfo", userAccountInfo);
		nextActivity.putExtra("prevActivity", "LecturerMyAccountActivity");
	}

	/*
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		Intent intent = new Intent(this, LecturerMainActivity.class);
		intent.putExtra("userName", userName);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		return true;
	}*/
}
