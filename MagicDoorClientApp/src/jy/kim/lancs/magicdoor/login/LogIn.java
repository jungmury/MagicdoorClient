package jy.kim.lancs.magicdoor.login;

import java.util.concurrent.ExecutionException;

import jy.kim.lancs.magicdoor.asynctask.PutLogIn;
import jy.kim.lancs.magicdoor.bean.LogInInfoReqBean;
import jy.kim.lancs.magicdoor.bean.LogInInfoRespBean;
import jy.kim.lancs.magicdoor.util.MagicDoorAccountManager;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class LogIn {
	private AccountManager manager;
	private Activity activity;

	public LogIn(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.manager = MagicDoorAccountManager.getAccountManager(activity
				.getApplicationContext());
	}

	public LogInInfoRespBean doLogIn(String uri, String userName, String password) {
		// TODO Auto-generated method stub

		LogInInfoReqBean params = new LogInInfoReqBean();
		params.userName = userName;
		params.password = password;
		params.uri = uri;
		Log.i("MagicDoor", uri);

		// AsyncTask<Params, Progress, Result>
		AsyncTask<LogInInfoReqBean, Void, LogInInfoRespBean> info = new PutLogIn(activity)
				.execute(params);

		LogInInfoRespBean result = null;
		try {
			result = info.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public void setAutomaticLogin(String userName, String password,
			String userType) {
		// TODO Auto-generated method stub
		// add user info to the account manager
		Account account = new Account(userName,
				MagicDoorAccountManager.ACCOUNT_TYPE);
		Bundle userData = new Bundle();
		userData.putString("userType", userType);
		manager.addAccountExplicitly(account, password, userData);
	}
}
