package jy.kim.lancs.magicdoor.startup;

import java.util.concurrent.ExecutionException;

import jy.kim.lancs.magicdoor.asynctask.PutLogIn;
import jy.kim.lancs.magicdoor.bean.LogInInfoReqBean;
import jy.kim.lancs.magicdoor.bean.LogInInfoRespBean;
import jy.kim.lancs.magicdoor.login.LogInActivity;
import jy.kim.lancs.magicdoor.main.LecturerMainActivity;
import jy.kim.lancs.magicdoor.main.StudentMainFragmentActivity;
import jy.kim.lancs.magicdoor.util.MagicDoorContants;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class StartUp {
	private Activity activity;

	public StartUp(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		// get Account manager to get account info for the app
	}

	// move to MainActivity if logged in
	public Intent doManualLogin() {
		// TODO Auto-generated method stub
		Intent nextActivity = new Intent(activity, LogInActivity.class);
		return nextActivity;
	}

	// login with user info the from account manager
	public Intent doAutomaticLogIn(String uri, String userName, String password) {
		// TODO Auto-generated method stub

		// create user information object to log in with
		LogInInfoReqBean params = new LogInInfoReqBean();
		params.userName = userName;
		params.password = password;
		params.uri = uri;

		// log in by querying server with username and password that are
		// obtained from account manager
		AsyncTask<LogInInfoReqBean, Void, LogInInfoRespBean> info = new PutLogIn(
				activity).execute(params);

		LogInInfoRespBean result = null;
		Intent nextActivity = null;
		try {
			result = info.get();

			if (result != null) {
				// Login accordingly depending on user type
				if (result.userType.equals(MagicDoorContants.STUDENT_USER)) {
					// log in as a student user
					nextActivity = new Intent(activity,
							StudentMainFragmentActivity.class);
					nextActivity.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					
				} else if (result.userType
						.equals(MagicDoorContants.LECTURER_USER)) {
					// log in as a lecturer user
					nextActivity = new Intent(activity,
							LecturerMainActivity.class);
					nextActivity.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
					
					
				//	LecturerMainActivityWithNavigationDrawer
				}
				// save user information
				putExtraTo(nextActivity, result.userName, result.userType);
			} else {
				Toast.makeText(activity,
						"Automatic log in failed. Log in manually!",
						Toast.LENGTH_SHORT).show();
				nextActivity = new Intent(activity, LogInActivity.class);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nextActivity;
	}

	private void putExtraTo(Intent nextActivity, String userName,
			String userType) {
		// TODO Auto-generated method stub
		nextActivity.putExtra("userName", userName);
		nextActivity.putExtra("userType", userType);

	}

	// CHECK INTERNET METHOD
	public final boolean isInternetOn() {
		Log.d("MagicDoor", "MagicDoor Checking connectivity ...");
		ConnectivityManager conn = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conn != null) {
			Log.d("MagicDoor", "MagicDoor internet connection available");
			NetworkInfo status = conn
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (status != null && status.isConnectedOrConnecting())
				;
			return true;
		}
		Log.d("MagicDoor", "MagicDoor internet connection not available");
		return false;
	}
}
