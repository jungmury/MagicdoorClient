package jy.kim.lancs.magicdoor.util;

import android.accounts.AccountManager;
import android.content.Context;

public class MagicDoorAccountManager {
	public final static String ACCOUNT_TYPE = "jy.kim.lancs.magicdoor.accountinfo";
	public final static String AUTH_TOKEN = "jy.kim.lancs.magicdoor.accountinfo";
	private static MagicDoorAccountManager instance = null;

	static MagicDoorAccountManager getInstance() {
		if (instance == null) {
			instance = new MagicDoorAccountManager();
		}
		return instance;
	}

	public MagicDoorAccountManager() {
		// Exists only to defeat instantiation.
	}

	public static AccountManager getAccountManager(Context context) {
		AccountManager manager = AccountManager.get(context);
		return manager;
	}

}
