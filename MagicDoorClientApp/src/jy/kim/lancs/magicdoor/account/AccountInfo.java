package jy.kim.lancs.magicdoor.account;

import java.util.concurrent.ExecutionException;

import jy.kim.lancs.magicdoor.asynctask.GetAccInfo;
import jy.kim.lancs.magicdoor.asynctask.PutAccountInfo;
import jy.kim.lancs.magicdoor.asynctask.GetDuplicateIDCheck;
import jy.kim.lancs.magicdoor.asynctask.PostSignUp;
import jy.kim.lancs.magicdoor.bean.LogInInfoRespBean;
import jy.kim.lancs.magicdoor.bean.UserAccountInfoBean;
import jy.kim.lancs.magicdoor.util.MagicDoorAccountManager;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

public class AccountInfo {
	public String userName;
	

	private Activity activity;

	public AccountInfo(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		Intent prevActivity = activity.getIntent();
		userName = prevActivity.getStringExtra("userName");
	}

	public UserAccountInfoBean getAccountInfoForTheUserWith(String userInfo) {
		// TODO Auto-generated method stub
		UserAccountInfoBean signUpParams = null;

		AsyncTask<String, Void, UserAccountInfoBean> signUpResult = new GetAccInfo(
				activity).execute(userInfo);

		try {
			signUpParams = signUpResult.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return signUpParams;
	}

	private UserAccountInfoBean createSignUpInfoObj(UserAccountInfoBean info) {
		UserAccountInfoBean signUpInfo = new UserAccountInfoBean();
		signUpInfo.userName = info.userName;
		signUpInfo.password = info.password;
		signUpInfo.firstName = info.firstName;
		signUpInfo.lastName = info.lastName;
		signUpInfo.eMailAddress = info.eMailAddress;
		signUpInfo.uri = info.uri;
		signUpInfo.userType = info.userType;
		return signUpInfo;
	}

	public LogInInfoRespBean updateAccInfo(UserAccountInfoBean signUpInfo) {
		// TODO Auto-generated method stub
		LogInInfoRespBean result = null;
		UserAccountInfoBean signUpParams = createSignUpInfoObj(signUpInfo);
		AsyncTask<UserAccountInfoBean, Void, LogInInfoRespBean> signUpResult = new PutAccountInfo(
				activity).execute(signUpParams);

		try {
			result = signUpResult.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public LogInInfoRespBean signUp(UserAccountInfoBean signUpInfo) {
		LogInInfoRespBean result = null;
		UserAccountInfoBean signUpParams = createSignUpInfoObj(signUpInfo);
		AsyncTask<UserAccountInfoBean, Void, LogInInfoRespBean> signUpResult = new PostSignUp(
				activity).execute(signUpParams);

		try {
			result = signUpResult.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	public void updateAccountManagerInfo(String password) {
		// TODO Auto-generated method stub
		AccountManager manager = MagicDoorAccountManager
				.getAccountManager(activity);
		Account[] accounts = manager
				.getAccountsByType(MagicDoorAccountManager.ACCOUNT_TYPE);
		manager.setPassword(accounts[0], password);
	}

	// check duplicated ID
	public boolean isIDDuplicate(String uri, String userName) {
		// TODO Auto-generated method stub
		boolean result = false;
		AsyncTask<String, Void, LogInInfoRespBean> info = new GetDuplicateIDCheck(
				activity).execute(uri + userName);

		try {
			// get if the id is duplicated
			LogInInfoRespBean temp = info.get();
			// if the web service return no username then the id is not
			// duplicated
			if (temp != null) {
				result = true;
			} else {
				result = false;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public boolean isPasswordMatched(String pwd1, String pwd2) {
		boolean result = false;
		if (pwd1.equals(pwd2)) {
			result = true;
		}
		return result;
	}

	// check password numbers of digit
	public boolean isPasswordMoreThanSixDigits(String pwd) {
		boolean result = false;
		if (pwd.length() >= 6) {
			result = true;
		}
		return result;
	}

	// check whether edittext contains values
	public boolean isValueInserted(String str) {
		boolean result = false;
		if (str.length() > 0) {
			result = true;
		}
		return result;
	}
}
