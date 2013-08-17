package jy.kim.lancs.magicdoor.message;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import jy.kim.lancs.magicdoor.account.AccountInfo;
import jy.kim.lancs.magicdoor.bean.MessageBean;
import jy.kim.lancs.magicdoor.bean.UserAccountInfoBean;
import jy.kim.lancs.magicdoor.util.MagicDoorContants;
import magicdoor.lancs.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WriteMessageActivity extends Activity {

	private TextView tvForReceiver;
	private TextView tvForSender;
	private EditText etContent;

	private MessageModel model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_form_message);

		model = new MessageModel(this);
		AccountInfo aiModel = new AccountInfo(this);
		String param = MagicDoorContants.REST_SERVICE_URI_FOR_GETTING_ACC_INFO
				+ model.userName;
		UserAccountInfoBean accInfo = aiModel
				.getAccountInfoForTheUserWith(param);

		tvForReceiver = (TextView) findViewById(R.id.tvReceiver);
		tvForSender = (TextView) findViewById(R.id.tvSender);
		etContent = (EditText) findViewById(R.id.etContentForMessage);

		Button sendBtn = (Button) findViewById(R.id.btnToSendMessage);

		tvForReceiver.setText(model.tagOwnerName);
		tvForSender.setText(accInfo.firstName + " " + accInfo.lastName);

		sendBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent nextActivity = null;
				// TODO Auto-generated method stub
				if (etContent.getText().length() > 0) {
					MessageBean msg = createMessageBean();
					ArrayList<MessageBean> messages = model.postNewMessage(msg);

					if (messages != null) {
						Toast.makeText(WriteMessageActivity.this,
								"Message sent", Toast.LENGTH_SHORT).show();
						nextActivity = new Intent(WriteMessageActivity.this,
								ShowMessageActivity.class);
						putExtraTo(nextActivity, messages, "SentMessages");
						nextActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(nextActivity);
					} else {
						Toast.makeText(WriteMessageActivity.this,
								"Message Sending Failed. Try it Again!",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(WriteMessageActivity.this,
							"Please Insert Message", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	protected void putExtraTo(Intent nextActivity,
			ArrayList<MessageBean> messages, String prevBtnPressed) {
		// TODO Auto-generated method stub
		nextActivity.putExtra("messages", messages);
		nextActivity.putExtra("userName", model.userName);
		nextActivity.putExtra("prevBtn", prevBtnPressed);
		
	}

	protected MessageBean createMessageBean() {
		// TODO Auto-generated method stub
		MessageBean msg = new MessageBean();
		msg.sender = model.userName;
		msg.receiver = model.tagOwner;
		msg.status = "new";
		msg.content = etContent.getText().toString();
		msg.sentDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
		msg.uri = MagicDoorContants.REST_SERVICE_URI_FOR_POST_MESSAGE;
		return msg;
	}
}
