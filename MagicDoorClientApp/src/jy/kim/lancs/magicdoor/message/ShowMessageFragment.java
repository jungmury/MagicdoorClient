package jy.kim.lancs.magicdoor.message;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import jy.kim.lancs.magicdoor.adapter.MessageListViewAdapterForReceiver;
import jy.kim.lancs.magicdoor.bean.MessageBean;
import jy.kim.lancs.magicdoor.dialog.MagicDoorCustomAlertDialogBuilder;
import jy.kim.lancs.magicdoor.main.StudentMainFragmentActivity;
import jy.kim.lancs.magicdoor.util.MagicDoorContants;
import magicdoor.lancs.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class ShowMessageFragment extends Fragment implements
		OnItemClickListener {

	private ListView lvForMsgs;
	private TextView tvEmptyMsg;
	private String userName;
	private Activity _activity;
	private View _rootView;
	private boolean isSentShowMessageView;

	private ArrayList<MessageBean> messages;

	public ShowMessageFragment() {
		// TODO Auto-generated constructor stub
	}

	public ShowMessageFragment(Activity activity,
			ArrayList<MessageBean> messages, String mode) {
		// TODO Auto-generated constructor stub
		this._activity = activity;
		this.messages = messages;
		if (mode.equals("Sent")) {
			isSentShowMessageView = true;
		} else {
			isSentShowMessageView = false;
		}
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		this._activity = activity;
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		_rootView = inflater.inflate(R.layout.view_show_received_sent_messages,
				container, false);
		lvForMsgs = (ListView) _rootView
				.findViewById(R.id.lvForRecSentMessageShow);
		if (messages != null) {
			userName = messages.get(0).sender;
			populateListView(messages);

		} else {
			populateListView(messages);
		}

		lvForMsgs.setOnItemClickListener(this);
		return _rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		showMessageContentDialog(position);
	}

	private void showMessageContentDialog(int position) {
		// TODO Auto-generated method stub
		final MessageBean message = (MessageBean) lvForMsgs
				.getItemAtPosition(position);
		MagicDoorCustomAlertDialogBuilder dialog = new MagicDoorCustomAlertDialogBuilder(
				_activity);
		dialog.create();
		dialog.setTitle("Message Detail");
		dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
			}
		});
		if (isSentShowMessageView) {
			dialog.setMessage("Sent to: " + message.name + "\nSent date: "
					+ message.sentDate + "\nContent: " + message.content);
		} else {
			dialog.setMessage("Received from: " + message.name + "\nReceived: "
					+ message.sentDate + "\nContent: " + message.content
					+ "\nStatus: " + message.status);
			dialog.setPositiveButton("Reply",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							LayoutInflater inflater = LayoutInflater
									.from(_activity);// (LayoutInflater)
														// ManageAppointmentsListDialogActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
							final View view = inflater.inflate(
									R.layout.view_form_reply_message, null);
							final EditText etNote = (EditText) view
									.findViewById(R.id.etWriteNote);

							AlertDialog noteDialog = new AlertDialog.Builder(
									_activity).create();
							noteDialog.setView(view);
							noteDialog.setButton(Dialog.BUTTON_NEUTRAL, "Send",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub
											MessageBean repliedMsg = new MessageBean();
											MessageModel model = new MessageModel(
													_activity);
											repliedMsg.uri = MagicDoorContants.REST_SERVICE_URI_FOR_REPLYING_MESSAGE;
											repliedMsg.receiver = message.receiver;
											repliedMsg.status = "replied";
											repliedMsg.content = etNote
													.getText().toString();
											repliedMsg.msgNo = message.msgNo;
											repliedMsg.sentDate = new Timestamp(
													Calendar.getInstance()
															.getTimeInMillis());
											ArrayList<MessageBean> result = model
													.replyMessage(repliedMsg);

											Toast.makeText(_activity,
													"Replied to the message",
													Toast.LENGTH_SHORT).show();
											populateListView(result);
										}
									});
							noteDialog.show();
							dialog.cancel();
						}
					});
		}

		dialog.show();
	}

	private void populateListView(ArrayList<MessageBean> messages) {
		// TODO Auto-generated method stub
		lvForMsgs = (ListView) _rootView
				.findViewById(R.id.lvForRecSentMessageShow);
		tvEmptyMsg = (TextView) _rootView
				.findViewById(R.id.tvEmptyRecSentsgListViewMsgShow);
		// Initialize our ArrayList
		// Initialize our array adapter notice how it references the
		// listitems.xml layout
		MessageListViewAdapterForReceiver arrayAdapter = null;

		if (messages != null) {
			Log.i("MagicDoor", "messages not null");
			arrayAdapter = new MessageListViewAdapterForReceiver(_activity,
					R.layout.view_listitems_message_with_status, messages,
					isSentShowMessageView);

			tvEmptyMsg.setVisibility(View.INVISIBLE);
			// Set the above adapter as the adapter of choice for our list
			lvForMsgs.setAdapter(arrayAdapter);
			arrayAdapter.notifyDataSetChanged();
			// it will show items correctly
			arrayAdapter.notifyDataSetInvalidated();
		} else {
			Toast.makeText(_activity, "No Messages", Toast.LENGTH_SHORT).show();
			tvEmptyMsg.setVisibility(View.VISIBLE);
			Log.i("MagicDoor", "messages null");
			lvForMsgs.setAdapter(arrayAdapter);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent nextActivity;
		nextActivity = new Intent(_activity, StudentMainFragmentActivity.class);
		nextActivity.putExtra("userName", userName);
		nextActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(nextActivity);
		return true;
	}

}
