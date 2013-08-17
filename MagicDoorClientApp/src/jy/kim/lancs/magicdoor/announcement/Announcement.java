package jy.kim.lancs.magicdoor.announcement;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import jy.kim.lancs.magicdoor.asynctask.DeleteAnnouncementsForTheTagOwner;
import jy.kim.lancs.magicdoor.asynctask.GetAnnouncementsForTheTagOwner;
import jy.kim.lancs.magicdoor.asynctask.PostNewAnnouncement;
import jy.kim.lancs.magicdoor.bean.AnnouncementReqBean;
import jy.kim.lancs.magicdoor.bean.AnnouncementRespBean;
import jy.kim.lancs.magicdoor.query.QueryForAnnouncements;
import jy.kim.lancs.magicdoor.query.QueryForMagicDoor;
import jy.kim.lancs.magicdoor.util.CreateTimestamp;
import jy.kim.lancs.magicdoor.util.MagicDoorAccountManager;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class Announcement {
	private Activity activity;
	private AccountManager manager;
	private Account[] accounts;

	private String writer;
	private String tagOwner;

	public Announcement(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		manager = MagicDoorAccountManager.getAccountManager(activity
				.getApplicationContext());
		accounts = manager
				.getAccountsByType(MagicDoorAccountManager.ACCOUNT_TYPE);
		writer = getWriter();
	}

	public String getWriter() {
		if (accounts.length > 0) {
			writer = accounts[0].name;
			Log.i("MagicDoor", "1");
		} else {
			Intent prevActivity = activity.getIntent();
			writer = prevActivity.getStringExtra("userName");
			// Log.i("MagicDoor", "2");
		}
		return writer;
	}

	public String getTagOwner() {
		// get tagOwner value from prev intent that obtained from server db with
		// tagId
		Intent prevIntent = activity.getIntent();
		tagOwner = prevIntent.getStringExtra("tagOwner");
		return tagOwner;
	}

	public ArrayList<AnnouncementRespBean> getAllAnnouncementsOfTheLecturer(
			QueryForMagicDoor query) {
		// TODO Auto-generated method stub
		AsyncTask<String, Void, ArrayList<AnnouncementRespBean>> info = new GetAnnouncementsForTheTagOwner(
				activity).execute(query.uri + query.tagOwner);

		ArrayList<AnnouncementRespBean> result = null;
		try {
			result = info.get();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// perform when item selected
		return result;
	}

	protected ArrayList<AnnouncementRespBean> addNewAnnouncement(
			AnnouncementReqBean ann) {
		// TODO Auto-generated method stub
		ann.userName = writer;
		ann.writtenDate = CreateTimestamp.getTodayTimestamp();

		AsyncTask<AnnouncementReqBean, Void, ArrayList<AnnouncementRespBean>> info = new PostNewAnnouncement(
				activity).execute(ann);

		ArrayList<AnnouncementRespBean> result = null;
		try {
			result = null;
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

	protected ArrayList<AnnouncementRespBean> deleteTheSelectedAnnouncement(
			QueryForAnnouncements query) {
		// TODO Auto-generated method stub
		AsyncTask<String, Void, ArrayList<AnnouncementRespBean>> info = new DeleteAnnouncementsForTheTagOwner(
				activity).execute(query.uri + query.annNo + "/"
				+ query.tagOwner);
		ArrayList<AnnouncementRespBean> result = null;
		try {
			result = null;
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

	
}
