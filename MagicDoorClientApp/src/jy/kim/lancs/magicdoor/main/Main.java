package jy.kim.lancs.magicdoor.main;

import jy.kim.lancs.magicdoor.util.MagicDoorAccountManager;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;

public class Main {
	public String userName;

	private Activity activity;

	public Main(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		Intent prevActivity = activity.getIntent();
		userName = prevActivity.getStringExtra("userName");

	}

	public void removeAccountFromTheAccountManager() {
		// TODO Auto-generated method stub
		AccountManager manager = MagicDoorAccountManager
				.getAccountManager(activity);
		Account[] accounts = manager
				.getAccountsByType(MagicDoorAccountManager.ACCOUNT_TYPE);
		if (accounts.length > 0) {
			manager.removeAccount(accounts[0], null, null);
		} else {
			// do Nothing
		}
	}
}
