package jy.kim.lancs.magicdoor.startup;

import jy.kim.lancs.magicdoor.util.AccountUtil;
import jy.kim.lancs.magicdoor.util.MagicDoorContants;
import magicdoor.lancs.R;
import android.accounts.AccountAuthenticatorActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

public class StartUpActivityNoDataFromNFCTagOnLanuch extends AccountAuthenticatorActivity {

	private String userName;
	private String password;

	private Intent nextActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_startup);

		Log.i("MagicDoor", "StartUpActivity");
		Log.i("MagicDoor", "create");

		// user log in related
		AccountUtil getAccountInfo = new AccountUtil(this);
		userName = getAccountInfo.userName;
		password = getAccountInfo.password;

		// start up log in process
		final StartUp model = new StartUp(StartUpActivityNoDataFromNFCTagOnLanuch.this);

		if (model.isInternetOn()) {
			final ProgressDialog pd = new ProgressDialog(this);

			ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
			pd.setView(pb);

			// if there is account information in account manager
			if (userName != null && password != null) {
				pd.setMessage("Automatically Loggin in...");
				CountDownTimer t = new CountDownTimer(1500, 100) {

					public void onFinish() {
						nextActivity = model.doAutomaticLogIn(
								MagicDoorContants.REST_SERVICE_URI_FOR_LOG_IN,
								userName, password);
						pd.dismiss();
						Log.d("MagicDoor", "prgressbar dismissed");
						StartUpActivityNoDataFromNFCTagOnLanuch.this.finish();
						StartUpActivityNoDataFromNFCTagOnLanuch.this.startActivity(nextActivity);
					}

					@Override
					public void onTick(long millisUntilFinished) {
						// TODO Auto-generated method stub
					}
				};
				pd.show();
				t.start();
			} else {
				nextActivity = model.doManualLogin();
				finish();
				startActivity(nextActivity);
			}
		} else {
			Toast.makeText(this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
		}
	}
}
