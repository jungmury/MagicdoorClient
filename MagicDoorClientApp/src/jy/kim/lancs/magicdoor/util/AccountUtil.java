package jy.kim.lancs.magicdoor.util;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;

public class AccountUtil {
	private AccountManager manager;
	private Account[] accounts;
	public String userName;
	public String password;

	public AccountUtil(Activity activity) {
		// TODO Auto-generated constructor stub
		manager = MagicDoorAccountManager.getAccountManager(activity
				.getApplicationContext());
		accounts = manager
				.getAccountsByType(MagicDoorAccountManager.ACCOUNT_TYPE);

		if (accounts != null) {
			// if there is no user info in the account manager
			if (accounts.length > 0) {
				// if account exists, get username and password
				userName = accounts[0].name;
				password = manager.getPassword(accounts[0]);
				// login automatically
			}
		}
	}

}
