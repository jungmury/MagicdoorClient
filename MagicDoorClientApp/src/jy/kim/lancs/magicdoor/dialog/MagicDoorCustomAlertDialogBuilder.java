package jy.kim.lancs.magicdoor.dialog;

import java.util.Calendar;
import jy.kim.lancs.magicdoor.bean.AnnouncementRespBean;
import jy.kim.lancs.magicdoor.bean.MessageBean;
import jy.kim.lancs.magicdoor.bean.parcelable.BookingDateInfoBean;
import jy.kim.lancs.magicdoor.login.LogIn;
import jy.kim.lancs.magicdoor.main.LecturerMainActivity;
import jy.kim.lancs.magicdoor.main.StudentMainFragmentActivity;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;

public class MagicDoorCustomAlertDialogBuilder extends AlertDialog.Builder {
	private Activity activity;

	public MagicDoorCustomAlertDialogBuilder(Activity activity) {
		// TODO Auto-generated constructor stub
		super(activity);
		this.activity = activity;
	}

	// dialog for autologin
	public void setAutoLogInAlterDialog(final LogIn model,
			final String userName, final String password, final String userType) {
		setMessage("Do you wnat to automatically log in?");
		setCancelable(true);
		setPositiveButton("YES", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent nextActivity = null;
				if (userType.equals("lecturer")) {
					nextActivity = new Intent(activity,
							LecturerMainActivity.class);
				} else if (userType.endsWith("student")) {
					nextActivity = new Intent(activity,
							StudentMainFragmentActivity.class);
				}
				// store user login info to intent
				putExtraTo(nextActivity, userName, userType);
				model.setAutomaticLogin(userName, password, userType);
				nextActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				activity.startActivity(nextActivity);
			}
		});
		setNegativeButton("NO", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
				Intent nextActivity = null;
				if (userType.equals("lecturer")) {
					nextActivity = new Intent(activity,
							LecturerMainActivity.class);
				} else if (userType.endsWith("student")) {
					nextActivity = new Intent(activity,
							StudentMainFragmentActivity.class);
				}
				// store user login info to intent
				putExtraTo(nextActivity, userName, userType);
				nextActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				activity.startActivity(nextActivity);
			}
		});
	}

	protected void putExtraTo(Intent nextActivity, String userName,
			String userType) {
		// TODO Auto-generated method stub
		nextActivity.putExtra("userName", userName);
		nextActivity.putExtra("userType", userType);
	}

	// dialog for nfc setting
	public void setNFCSettingAlertDialog() {
		setMessage("NFC is disabled, do you want to enable it at the Settings?");
		setPositiveButton("Settings", new DialogInterface.OnClickListener() {
			// set dialog message
			Intent nextActivity;

			@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
			public void onClick(DialogInterface dialog, int id) {
				if (android.os.Build.VERSION.SDK_INT >= 16) {
					nextActivity = new Intent(
							android.provider.Settings.ACTION_NFC_SETTINGS);
					nextActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					activity.startActivity(nextActivity);
				} else {
					nextActivity = new Intent(
							android.provider.Settings.ACTION_WIRELESS_SETTINGS);
					nextActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					activity.startActivity(nextActivity);
				}
			}
		});
		setNegativeButton("NO", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				Intent nextActivity = new Intent(activity,
						StudentMainFragmentActivity.class);
				nextActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				nextActivity.putExtra("userName", activity.getIntent()
						.getStringExtra("userName"));
				dialog.dismiss();
			//	activity.startActivity(nextActivity);
				// activity.finish();// back to MainActivity
			}
		});
	}

	public void setDateAndTimeConfirmationAlertDialog(final Calendar calendar,
			final BookingDateInfoBean dateInfo, final int time,
			final MagicDoorCustomAlertDialog prevDialog) {
		setTitle("Please, confirm the detail");
		setMessage("Date: " + dateInfo.selectedDate + " " + dateInfo.monthName
				+ " " + dateInfo.year + "\nTime: " + time);
		setPositiveButton("YES", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent dateAndTimeDetail = new Intent();
				calendar.set(Calendar.HOUR_OF_DAY, time);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				// store result for the returning
				BookingDateInfoBean newDateInfo = new BookingDateInfoBean(
						calendar);
				dateAndTimeDetail.putExtra("dateInfo", newDateInfo);
				activity.setResult(Activity.RESULT_OK, dateAndTimeDetail);
				// close dialogs
				dialog.dismiss();
				prevDialog.dismiss();
				activity.finish();
			}
		});
		setNegativeButton("NO", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();

			}
		});
	}

	public void setAnnouncementContentAlertDialog(
			AnnouncementRespBean announcement) {
		setTitle("Announcement Detail");
		setMessage("Title: " + announcement.title + "\nPosted: "
				+ announcement.writtenDate + "\nContent: "
				+ announcement.announcementContent);
		setNegativeButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});

	}

	public void setMessageContentAlertDialogForSender(MessageBean message) {
		setTitle("Message Detail");
		setMessage("Receiver: " + message.name + "\nSent: " + message.sentDate
				+ "\nContent: " + message.content);
		setNegativeButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});

	}

}
