package jy.kim.lancs.magicdoor.main;

import java.nio.ByteBuffer;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import jy.kim.lancs.magicdoor.account.LecturerMyAccountActivity;
import jy.kim.lancs.magicdoor.announcement.Announcement;
import jy.kim.lancs.magicdoor.announcement.ManageAnnouncementFragment;
import jy.kim.lancs.magicdoor.announcement.WriteAnnouncementFragment;
import jy.kim.lancs.magicdoor.appointment.Appointment;
import jy.kim.lancs.magicdoor.appointment.AppointmentWeekFragment;
import jy.kim.lancs.magicdoor.appointment.ManageAppointmentListFragment;
import jy.kim.lancs.magicdoor.bean.AnnouncementRespBean;
import jy.kim.lancs.magicdoor.bean.AppointmentRespDetailBean;
import jy.kim.lancs.magicdoor.bean.MessageBean;
import jy.kim.lancs.magicdoor.bean.NFCTagInfoBean;
import jy.kim.lancs.magicdoor.bean.TimeTableBean;
import jy.kim.lancs.magicdoor.dialog.MagicDoorCustomAlertDialogBuilder;
import jy.kim.lancs.magicdoor.message.MessageModel;
import jy.kim.lancs.magicdoor.message.ShowMessageFragment;
import jy.kim.lancs.magicdoor.query.QueryForAnnouncements;
import jy.kim.lancs.magicdoor.query.QueryForAppointments;
import jy.kim.lancs.magicdoor.query.QueryForMagicDoor;
import jy.kim.lancs.magicdoor.query.QueryForMessages;
import jy.kim.lancs.magicdoor.scan.ManageNFCTagFragment;
import jy.kim.lancs.magicdoor.scan.RegisterNewNFCTagFragment;
import jy.kim.lancs.magicdoor.scan.TagModel;
import jy.kim.lancs.magicdoor.schedule.TeachingAndAppointmentFragment;
import jy.kim.lancs.magicdoor.timetable.ManageTimeTableFragment;
import jy.kim.lancs.magicdoor.timetable.TimeTable;
import jy.kim.lancs.magicdoor.util.MagicDoorContants;
import jy.kim.lancs.magicdoor.util.TagUtility;
import magicdoor.lancs.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class LecturerMainActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mMenuTitles;

	private Main lecturerMainModel;

	private String userName;

	// server ip address local host ip should be changed

	// NFC Related
	private boolean isNFCAvailable;
	protected NfcAdapter nfcAdapter;
	protected IntentFilter[] tagFilters;
	protected PendingIntent nfcPendingIntent;

	protected Tag myTag;
	protected String tagId;
	protected boolean readMode;
	protected Context curContext;
	protected TagUtility util = new TagUtility(curContext);

	private static String TAG = "MagicDoor";
	private boolean isRegisterNFCTagFragemnt;

	// /////////
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_lecturer_main_drawer);

		Log.i("MagicDoor", "LecturerMainActivity");

		lecturerMainModel = new Main(this);
		userName = lecturerMainModel.userName;

		// / NFC Related
		// Getting NFC adapter
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);

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

		nfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

		mTitle = mDrawerTitle = getTitle();
		if (isNFCAvailable) {
			mMenuTitles = getResources().getStringArray(
					R.array.menu_names_array_with_nfc);
		} else {
			mMenuTitles = getResources().getStringArray(
					R.array.menu_names_array_without_nfc);
		}
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// set a custom shadow that overlays the main content when the drawer
		// opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
				GravityCompat.START);
		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mMenuTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// enable ActionBar app icon to behave as action to toggle nav drawer
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		mDrawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description for accessibility */
		R.string.drawer_close /* "close drawer" description for accessibility */
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to
											// onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// ManageMessage need updating
			selectItem(0);
		}

		// intent filters to want to deal with defined
		IntentFilter tagDetected = new IntentFilter(
				NfcAdapter.ACTION_TAG_DISCOVERED);
		/*
		 * IntentFilter ndefDetected = new IntentFilter(
		 * NfcAdapter.ACTION_NDEF_DISCOVERED); IntentFilter techDetected = new
		 * IntentFilter( NfcAdapter.ACTION_TECH_DISCOVERED);
		 */
		tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
		tagFilters = new IntentFilter[] { tagDetected };
		// only ACTION_TAG_DISCOVERED will be caught
		Log.d(TAG, "create");
	}

	/* The click listner for ListView in the navigation drawer */
	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		QueryForMagicDoor query = null;
		String tag = "";
		if (isNFCAvailable) {
			switch (position) {
			case 0:
				query = new QueryForMagicDoor();
				query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_WEEK_TIME_TABLE;
				query.tagOwner = userName;
				TimeTable timeTableModel = new TimeTable(this);
				ArrayList<TimeTableBean> timeTable = timeTableModel
						.getWeekTimeTable(query);
				fragment = new ManageTimeTableFragment(this, timeTable);
				// Bundle timeTableBundle = new Bundle();
				// timeTableBundle.putSerializable("timeTable", timeTable);
				isRegisterNFCTagFragemnt = false;
				break;
			case 1:
				Appointment appModel = new Appointment(this);
				QueryForAppointments queryApp = new QueryForAppointments();
				// queryApp.uri =
				// MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_ALL_APPOINTMENTS_OF_THE_LECTURER_USER_IN_THE_MONTH;
				// queryApp.tagOwner = userName;
				// queryApp.date = new
				// Date(Calendar.getInstance().getTimeInMillis());
				queryApp.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_APPOINTMENTS_OF_THE_LECTURER_USER_IN_THE_WEEK;
				queryApp.tagOwner = userName;
				queryApp.date = new Date(Calendar.getInstance()
						.getTimeInMillis());

				ArrayList<AppointmentRespDetailBean> appointments = appModel
						.getAppointmentsOfTheLecturerUserInTheWeek(queryApp);
				// fragment = new ManageAppointmentCalendarFragment(this,
				// appointments);
				fragment = new AppointmentWeekFragment(this, appointments);
				isRegisterNFCTagFragemnt = false;
				break;
			case 2:
				query = new QueryForMagicDoor();
				query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_WEEK_TIME_TABLE;
				query.tagOwner = userName;
				timeTableModel = new TimeTable(this);
				timeTable = timeTableModel.getWeekTimeTable(query);

				appModel = new Appointment(this);
				queryApp = new QueryForAppointments();
				queryApp.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_APPOINTMENTS_OF_THE_LECTURER_USER_IN_THE_WEEK;
				queryApp.tagOwner = userName;
				queryApp.date = new Date(Calendar.getInstance()
						.getTimeInMillis());
				appointments = appModel
						.getAppointmentsOfTheLecturerUserInTheWeek(queryApp);

				fragment = new TeachingAndAppointmentFragment(this, timeTable,
						appointments);
				// Bundle timeTableBundle = new Bundle();
				// timeTableBundle.putSerializable("timeTable", timeTable);
				isRegisterNFCTagFragemnt = false;
				break;

			case 3:
				appModel = new Appointment(this);
				query = new QueryForAppointments();
				query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_REQUESTING_ALL_APPOINTMENTS_OF_THE_LECTURER_USER;
				query.tagOwner = userName;
				appointments = appModel
						.getAllAppointmentsOfTheLecturerUser(query);
				fragment = new ManageAppointmentListFragment(this, appointments);
				isRegisterNFCTagFragemnt = false;
				break;
			case 4:
				fragment = new WriteAnnouncementFragment(this);
				isRegisterNFCTagFragemnt = false;
				break;

			case 5:
				Announcement annModel = new Announcement(this);
				query = new QueryForAnnouncements();
				query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_SHOWING_ANNOUNCEMENTS;
				query.tagOwner = annModel.getWriter();
				ArrayList<AnnouncementRespBean> announcements = annModel
						.getAllAnnouncementsOfTheLecturer(query);
				fragment = new ManageAnnouncementFragment(this, announcements);
				isRegisterNFCTagFragemnt = false;
				break;
			case 6:
				MessageModel msgModel = new MessageModel(this);
				query = new QueryForMessages();
				query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GET_ALL_RECEIVED_MESSAGES_FOR_RECEIVER;
				query.tagOwner = userName;
				ArrayList<MessageBean> messages = msgModel
						.getMessagesOfTheReceiver(query);
				Log.i("MagicDoor", "tagOwner in lecturer main= "
						+ query.tagOwner);
				fragment = new ShowMessageFragment(this, messages, "Received");
				isRegisterNFCTagFragemnt = false;
				// fragment = new Ma
				break;
			case 7:
				msgModel = new MessageModel(this);
				query = new QueryForMessages();
				query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GET_ALL_SENT_MESSAGES_FOR_RECEIVER;
				query.tagOwner = userName;
				messages = msgModel.getMessagesOfTheReceiver(query);
				Log.i("MagicDoor", "tagOwner in lecturer main= "
						+ query.tagOwner);
				fragment = new ShowMessageFragment(this, messages, "Sent");
				// fragment = new Ma
				isRegisterNFCTagFragemnt = false;
				break;
			case 8:
				query = new QueryForMagicDoor();
				query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_ALL_TAGS_OF_THE_OWNER;
				query.tagOwner = userName;
				TagModel tagModel = new TagModel(this);
				ArrayList<NFCTagInfoBean> tags = tagModel
						.getAllTagsOfTheTagOwner(query);
				fragment = new RegisterNewNFCTagFragment();
				Bundle bundle = new Bundle();
				bundle.putSerializable("tags", tags);
				fragment.setArguments(bundle);
				tag = "RegNFC";
				isRegisterNFCTagFragemnt = true;
				break;
			case 9:
				query = new QueryForMagicDoor();
				query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_ALL_TAGS_OF_THE_OWNER;
				query.tagOwner = userName;
				tagModel = new TagModel(this);
				tags = tagModel.getAllTagsOfTheTagOwner(query);
				fragment = new ManageNFCTagFragment();
				bundle = new Bundle();
				bundle.putSerializable("tags", tags);
				fragment.setArguments(bundle);
				tag = "RegNFC";
				isRegisterNFCTagFragemnt = false;
				break;
			default:
				isRegisterNFCTagFragemnt = false;
				break;
			}
		} else {
			switch (position) {
			case 0:
				query = new QueryForMagicDoor();
				query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_WEEK_TIME_TABLE;
				query.tagOwner = userName;
				TimeTable timeTableModel = new TimeTable(this);
				ArrayList<TimeTableBean> timeTable = timeTableModel
						.getWeekTimeTable(query);
				fragment = new ManageTimeTableFragment(this, timeTable);
				// Bundle timeTableBundle = new Bundle();
				// timeTableBundle.putSerializable("timeTable", timeTable);
				isRegisterNFCTagFragemnt = false;
				break;
			case 1:
				Appointment appModel = new Appointment(this);
				QueryForAppointments queryApp = new QueryForAppointments();
				// queryApp.uri =
				// MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_ALL_APPOINTMENTS_OF_THE_LECTURER_USER_IN_THE_MONTH;
				// queryApp.tagOwner = userName;
				// queryApp.date = new
				// Date(Calendar.getInstance().getTimeInMillis());
				queryApp.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_APPOINTMENTS_OF_THE_LECTURER_USER_IN_THE_WEEK;
				queryApp.tagOwner = userName;
				queryApp.date = new Date(Calendar.getInstance()
						.getTimeInMillis());

				ArrayList<AppointmentRespDetailBean> appointments = appModel
						.getAppointmentsOfTheLecturerUserInTheWeek(queryApp);
				// fragment = new ManageAppointmentCalendarFragment(this,
				// appointments);
				fragment = new AppointmentWeekFragment(this, appointments);
				isRegisterNFCTagFragemnt = false;
				break;
			case 2:
				query = new QueryForMagicDoor();
				query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_WEEK_TIME_TABLE;
				query.tagOwner = userName;
				timeTableModel = new TimeTable(this);
				timeTable = timeTableModel.getWeekTimeTable(query);

				appModel = new Appointment(this);
				queryApp = new QueryForAppointments();
				queryApp.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_APPOINTMENTS_OF_THE_LECTURER_USER_IN_THE_WEEK;
				queryApp.tagOwner = userName;
				queryApp.date = new Date(Calendar.getInstance()
						.getTimeInMillis());
				appointments = appModel
						.getAppointmentsOfTheLecturerUserInTheWeek(queryApp);

				fragment = new TeachingAndAppointmentFragment(this, timeTable,
						appointments);
				// Bundle timeTableBundle = new Bundle();
				// timeTableBundle.putSerializable("timeTable", timeTable);
				isRegisterNFCTagFragemnt = false;
				break;

			case 3:
				appModel = new Appointment(this);
				query = new QueryForAppointments();
				query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_REQUESTING_ALL_APPOINTMENTS_OF_THE_LECTURER_USER;
				query.tagOwner = userName;
				appointments = appModel
						.getAllAppointmentsOfTheLecturerUser(query);
				fragment = new ManageAppointmentListFragment(this, appointments);
				isRegisterNFCTagFragemnt = false;
				break;
			case 4:
				fragment = new WriteAnnouncementFragment(this);
				isRegisterNFCTagFragemnt = false;
				break;

			case 5:
				Announcement annModel = new Announcement(this);
				query = new QueryForAnnouncements();
				query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_SHOWING_ANNOUNCEMENTS;
				query.tagOwner = annModel.getWriter();
				ArrayList<AnnouncementRespBean> announcements = annModel
						.getAllAnnouncementsOfTheLecturer(query);
				fragment = new ManageAnnouncementFragment(this, announcements);
				isRegisterNFCTagFragemnt = false;
				break;
			case 6:
				MessageModel msgModel = new MessageModel(this);
				query = new QueryForMessages();
				query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GET_ALL_RECEIVED_MESSAGES_FOR_RECEIVER;
				query.tagOwner = userName;
				ArrayList<MessageBean> messages = msgModel
						.getMessagesOfTheReceiver(query);
				Log.i("MagicDoor", "tagOwner in lecturer main= "
						+ query.tagOwner);
				fragment = new ShowMessageFragment(this, messages, "Received");
				isRegisterNFCTagFragemnt = false;
				// fragment = new Ma
				break;
			case 7:
				msgModel = new MessageModel(this);
				query = new QueryForMessages();
				query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GET_ALL_SENT_MESSAGES_FOR_RECEIVER;
				query.tagOwner = userName;
				messages = msgModel.getMessagesOfTheReceiver(query);
				Log.i("MagicDoor", "tagOwner in lecturer main= "
						+ query.tagOwner);
				fragment = new ShowMessageFragment(this, messages, "Sent");
				// fragment = new Ma
				isRegisterNFCTagFragemnt = false;
				break;
			case 8:
				query = new QueryForMagicDoor();
				query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_ALL_TAGS_OF_THE_OWNER;
				query.tagOwner = userName;
				TagModel tagModel = new TagModel(this);
				ArrayList<NFCTagInfoBean> tags = tagModel
						.getAllTagsOfTheTagOwner(query);
				fragment = new ManageNFCTagFragment();
				Bundle bundle = new Bundle();
				bundle.putSerializable("tags", tags);
				fragment.setArguments(bundle);
				tag = "RegNFC";
				isRegisterNFCTagFragemnt = false;
				break;
			default:
				isRegisterNFCTagFragemnt = false;
				break;
			}
		}
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment, tag).commit();

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		setTitle(mMenuTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	private void putExtrasTo(Intent nextActivity) {
		nextActivity.putExtra("userName", userName);
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
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		Intent nextActivity;
		switch (item.getItemId()) {
		case R.id.myAccount:
			nextActivity = new Intent(this, LecturerMyAccountActivity.class);
			putExtrasTo(nextActivity);
			nextActivity.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			startActivity(nextActivity);
			break;
		case R.id.help:
			LayoutInflater inflater = LayoutInflater.from(this);
			View layout = inflater.inflate(R.layout.help, null);
			final AlertDialog MyDialog;
			AlertDialog.Builder MyBuilder = new AlertDialog.Builder(this);
			MyBuilder.setTitle("Help");
			MyBuilder.setView(layout);
			MyDialog = MyBuilder.create();
			MyDialog.setButton(Dialog.BUTTON_NEUTRAL, "OK",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							MyDialog.cancel();
						}
					});
			MyDialog.show();
		default:
			super.onOptionsItemSelected(item);
		}

		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isNFCAvailable) {
			writeModeOn();
		}
		Log.d(TAG, "resume");

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (isNFCAvailable) {
			writeModeOff();
		}
		Log.d(TAG, "pause");
	}

	// when intent caught
	@Override
	public void onNewIntent(Intent intent) {
		Log.d(TAG, "onNewIntent");
		if (isRegisterNFCTagFragemnt) {
			if (nfcAdapter != null) {// do if nfc is enabled
				if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
					// catch the type intent filter
					Log.d(TAG, "NFC is scanned");
					myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
					tagId = parseIdToString(myTag);
					String[] techs = myTag.getTechList();

					RegisterNewNFCTagFragment fragment = (RegisterNewNFCTagFragment) getFragmentManager()
							.findFragmentByTag("RegNFC");
					fragment.setId(tagId);
					// check if scanned tag is supported
					if (util.isTechSupported(techs)) {
						Log.d(TAG, "The tag is supported");
						NdefMessage ndefMessage = null;
						NdefRecord aar = NdefRecord
								.createApplicationRecord("magicdoor.lancs");
						ndefMessage = new NdefMessage(new NdefRecord[] { aar });
						util.writeTag(ndefMessage, myTag);

						// readTagData(intent, myTag);

					} else {
						Toast.makeText(this, "The tag is not supported!",
								Toast.LENGTH_SHORT).show();
					}
					// parse byte[] to int

				} else {
					Toast.makeText(this,
							"Different intent filters are required",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(this, "NFC is not available on the device",
						Toast.LENGTH_SHORT).show();
				Log.d(TAG, "NFC is not available on the device");
			}
		}
	}

	private String parseIdToString(Tag tag) {
		String tagId = String.valueOf(ByteBuffer.wrap(tag.getId()).getInt());
		return tagId;
	}

	private void writeModeOn() {
		readMode = true;
		nfcAdapter.enableForegroundDispatch(this, nfcPendingIntent, tagFilters,
				null);
		Log.d(TAG, "write mode on");
		// Toast.makeText(this, "wirte on", Toast.LENGTH_SHORT).show();
	}

	private void writeModeOff() {
		readMode = false;
		nfcAdapter.disableForegroundDispatch(this);
		Log.d(TAG, "write mode off");
		// Toast.makeText(this, "wirte off", Toast.LENGTH_SHORT).show();
	}

	private void showEnableNFCdialog() {
		// TODO Auto-generated method stub
		MagicDoorCustomAlertDialogBuilder enableNFCDialog = new MagicDoorCustomAlertDialogBuilder(
				this);
		enableNFCDialog.create();
		enableNFCDialog.setNFCSettingAlertDialog();
		enableNFCDialog.show();
	}

}
