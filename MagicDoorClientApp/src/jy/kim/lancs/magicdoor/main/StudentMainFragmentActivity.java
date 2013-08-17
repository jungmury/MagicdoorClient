package jy.kim.lancs.magicdoor.main;

import java.util.ArrayList;

import com.abhi.barcode.fragment.library.BarCodeFragment;

import jy.kim.lancs.magicdoor.account.StudentMyAccountActivity;
import jy.kim.lancs.magicdoor.announcement.Announcement;
import jy.kim.lancs.magicdoor.announcement.ShowAnnouncementActivity;
import jy.kim.lancs.magicdoor.appointment.MakeAppointmentsActivity;
import jy.kim.lancs.magicdoor.bean.AnnouncementRespBean;
import jy.kim.lancs.magicdoor.bean.TimeTableBean;
import jy.kim.lancs.magicdoor.bean.WebServiceInfoRespBean;
import jy.kim.lancs.magicdoor.dialog.MagicDoorCustomAlertDialogBuilder;
import jy.kim.lancs.magicdoor.message.WriteMessageActivity;
import jy.kim.lancs.magicdoor.query.QueryForMagicDoor;
import jy.kim.lancs.magicdoor.scan.ScanNFCTagFragment;
import jy.kim.lancs.magicdoor.scan.TagModel;
import jy.kim.lancs.magicdoor.timetable.ShowTimeTableActivity;
import jy.kim.lancs.magicdoor.timetable.TimeTable;
import jy.kim.lancs.magicdoor.util.MagicDoorContants;
import magicdoor.lancs.R;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class StudentMainFragmentActivity extends FragmentActivity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the three primary sections of the app. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	AppSectionsPagerAdapter mAppSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will display the three primary sections of the
	 * app, one at a time.
	 */
	ViewPager mViewPager;

	private NfcAdapter nfcAdapter;
	private TagModel tagModel;
	private PendingIntent nfcPendingIntent;
	private String userName;
	private String tagOwner;
	private String tagOwnerName;

	private boolean isNFCAvailable;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_student_main_fragment);

		nfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

		tagModel = new TagModel(this, nfcPendingIntent);
		userName = tagModel.userName;
		Log.i("MagicDoor", "userName = " + userName);

		nfcAdapter = tagModel.nfcAdapter;

		if (nfcAdapter != null) {
			isNFCAvailable = true;
			if (!nfcAdapter.isEnabled()) {
				Log.d("MagicDoor", "NFC is not enabled");
				Toast.makeText(this,
						"NFC is not enabled on the deivce! Enable NFC please!",
						Toast.LENGTH_SHORT).show();
				showEnableNFCdialog();
			} else {
				Toast.makeText(this,
						"NFC is enabled on the deivce! Scan a tag please!",
						Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, "NFC is not available on the device",
					Toast.LENGTH_SHORT).show();

			isNFCAvailable = false;
		}

		// Create the adapter that will return a fragment for each of the three
		// primary sections
		// of the app.
		if (isNFCAvailable) {
			mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(
					getSupportFragmentManager(), this, isNFCAvailable);
		}

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();

		// Specify that we will be displaying tabs in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Set up the ViewPager, attaching the adapter and setting up a listener
		// for when the
		// user swipes between sections.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// When swiping between different app sections, select
						// the corresponding tab.
						// We can also use ActionBar.Tab#select() to do this if
						// we have a reference to the
						// Tab.
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter.
			// Also specify this Activity object, which implements the
			// TabListener interface, as the
			// listener for when this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mAppSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the primary sections of the app.
	 */
	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {
		private Activity _activity;
		private boolean _isNFCAvailable;

		public AppSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		public AppSectionsPagerAdapter(FragmentManager fm, Activity activity,
				boolean isNFCAvailable) {
			super(fm);
			this._activity = activity;
			this._isNFCAvailable = isNFCAvailable;
		}

		@Override
		public Fragment getItem(int i) {
			if (_isNFCAvailable) {
				switch (i) {
				case 0:
					return new ScanNFCTagFragment();
				case 1:
					return new BarCodeFragment();

				default:
					return null;
				}
			} else {
				switch (i) {
				case 0:
					return new BarCodeFragment();

				default:
					return null;
				}
			}
		}

		@Override
		public int getCount() {
			if (_isNFCAvailable) {
				return 2;
			} else {
				return 1;
			}
		}

		@Override
		public CharSequence getPageTitle(int position) {
			CharSequence title = "";
			if (position == 0) {
				title = "NFC Tag Scanner";
			} else {
				title = "QR Code Scanner";
			}
			return title;
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		Log.d("MagicDoor", "onNewIntent");
		WebServiceInfoRespBean result = tagModel.initNfcManager(
				MagicDoorContants.REST_SERVICE_URI_FOR_WEB_SERIVCE, intent);

		// Trigger appropriate services for the scanned tag
		if (result != null) {
			tagOwner = result.tagOwner;
			tagOwnerName = result.tagOwnerName;
			Toast.makeText(this, result.serviceDesc, Toast.LENGTH_SHORT).show();
			Intent nextActivity = null;
			QueryForMagicDoor query;
			if (result.serviceDesc.equals("announcement")) {
				// if service description is announcement then
				// forward to announcement activity
				Announcement annModel = new Announcement(this);
				query = new QueryForMagicDoor();
				query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_SHOWING_ANNOUNCEMENTS;
				query.tagOwner = tagOwner;
				ArrayList<AnnouncementRespBean> announcements = annModel
						.getAllAnnouncementsOfTheLecturer(query);
				nextActivity = new Intent(this, ShowAnnouncementActivity.class)
						.putExtra("tagOwner", result.tagOwner).putExtra(
								"announcements", announcements);
			} else if (result.serviceDesc.equals("appointment")) {
				nextActivity = new Intent(this, MakeAppointmentsActivity.class)
						.putExtra("tagOwner", tagOwner)
						.putExtra("tagOwnerName", tagOwnerName)
						.putExtra("userName", userName);
			} else if (result.serviceDesc.equals("message")) {
				nextActivity = new Intent(this, WriteMessageActivity.class)
						.putExtra("tagOwner", tagOwner)
						.putExtra("tagOwnerName", tagOwnerName)
						.putExtra("userName", userName);
			} else if (result.serviceDesc.equals("timetable")) {
				nextActivity = new Intent(this, ShowTimeTableActivity.class);
				query = new QueryForMagicDoor();
				query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_WEEK_TIME_TABLE;
				query.tagOwner = tagOwner;
				TimeTable timeTableModel = new TimeTable(this);
				ArrayList<TimeTableBean> timeTable = timeTableModel
						.getWeekTimeTable(query);
				putExtrasTo(nextActivity, timeTable, userName);
			}
			// nextActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(nextActivity);

		} else {
			Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show();
		}

	}

	private void showEnableNFCdialog() {
		// TODO Auto-generated method stub
		MagicDoorCustomAlertDialogBuilder enableNFCDialog = new MagicDoorCustomAlertDialogBuilder(
				this);
		enableNFCDialog.create();
		enableNFCDialog.setNFCSettingAlertDialog();
		enableNFCDialog.show();
	}

	private void putExtrasTo(Intent nextActivity,
			ArrayList<TimeTableBean> timeTable, String userName) {
		nextActivity.putExtra("timeTable", timeTable);
		nextActivity.putExtra("userName", userName);
	}

	private void putExtraTo(Intent nextActivity) {
		// TODO Auto-generated method stub
		nextActivity.putExtra("userName", userName);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("MagicDoor", "resume");
		if (nfcAdapter != null) {
			tagModel.readModeOn();
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("MagicDoor", "pause");
		if (nfcAdapter != null) {
			tagModel.readModeOff();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.my_account, menu);
		return true;// return true so to menu pop up is opens
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent nextActivity;
		switch (item.getItemId()) {
		case R.id.myAccount:
			nextActivity = new Intent(this, StudentMyAccountActivity.class);
			nextActivity.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			putExtraTo(nextActivity);
			startActivity(nextActivity);
			break;
		default:
			break;
		}
		return true;
	}

}
