package jy.kim.lancs.magicdoor.message;

import java.util.ArrayList;

import jy.kim.lancs.magicdoor.account.StudentMyAccountActivity;
import jy.kim.lancs.magicdoor.adapter.MessageListViewAdapterForSender;
import jy.kim.lancs.magicdoor.bean.MessageBean;
import jy.kim.lancs.magicdoor.dialog.MagicDoorCustomAlertDialogBuilder;
import jy.kim.lancs.magicdoor.main.StudentMainFragmentActivity;
import magicdoor.lancs.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowMessageActivity extends Activity implements
		OnItemClickListener {

	private TextView tvLabel;
	private ListView lvForMsgs;
	private String userName;

	private boolean isShowSentMessages;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("MagicDoor", "ShowMessageActivity");
		if (getIntent().getStringExtra("prevBtn").equals("SentMessages")) {
			setTitle("Sent Messages");
		} else {
			setTitle("Received Messages");
		}
		setContentView(R.layout.view_show_messages);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		tvLabel = (TextView) findViewById(R.id.tvLabelForMessageShow);
		lvForMsgs = (ListView) findViewById(R.id.lvForMessageShow);

		ArrayList<MessageBean> messages = (ArrayList<MessageBean>) getIntent()
				.getSerializableExtra("messages");
		if (messages != null) {
			tvLabel.setText("My Messages");
			userName = getIntent().getStringExtra("userName");
			populateListView(messages);

		} else {
			populateListView(messages);
		}

		lvForMsgs.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		showMessageContentDialog(position);
	}

	private void showMessageContentDialog(int position) {
		// TODO Auto-generated method stub
		MessageBean message = (MessageBean) lvForMsgs
				.getItemAtPosition(position);
		MagicDoorCustomAlertDialogBuilder dialog = new MagicDoorCustomAlertDialogBuilder(
				this);
		dialog.create();
		dialog.setMessageContentAlertDialogForSender(message);
		dialog.show();
	}

	private void populateListView(ArrayList<MessageBean> messages) {
		// TODO Auto-generated method stub
		lvForMsgs = (ListView) findViewById(R.id.lvForMessageShow);
		TextView tvEmptyAnn = (TextView) findViewById(R.id.tvEmptyMsgListViewMsgShow);
		// Initialize our ArrayList
		// Initialize our array adapter notice how it references the
		// listitems.xml layout
		MessageListViewAdapterForSender arrayAdapter = null;

		if (messages != null) {
			Log.i("MagicDoor", "messages not null");
			if (getIntent().getStringExtra("prevBtn").equals("SentMessage")) {
				isShowSentMessages = true;
			} else {
				isShowSentMessages = false;
			}
			arrayAdapter = new MessageListViewAdapterForSender(
					ShowMessageActivity.this, R.layout.view_listitems_message,
					messages, isShowSentMessages);
			tvEmptyAnn.setVisibility(View.INVISIBLE);

			// Set the above adapter as the adapter of choice for our list
			lvForMsgs.setAdapter(arrayAdapter);
			arrayAdapter.notifyDataSetChanged();
			// it will show items correctly
			arrayAdapter.notifyDataSetInvalidated();
		} else {
			Toast.makeText(this, "No Mssessages", Toast.LENGTH_SHORT).show();
			tvEmptyAnn.setVisibility(View.VISIBLE);
			Log.i("MagicDoor", "messages null");
			lvForMsgs.setAdapter(arrayAdapter);
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent nextActivity;
		if (getIntent().getStringExtra("prevActivity").equals("MyAccount")) {
			nextActivity = new Intent(this, StudentMainFragmentActivity.class);
			nextActivity.putExtra("userName", userName);
		} else {
			nextActivity = new Intent(this, StudentMainFragmentActivity.class);
			nextActivity.putExtra("userName", userName);
		}
		nextActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(nextActivity);

		return true;
	}

}
