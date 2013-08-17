package jy.kim.lancs.magicdoor.message;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import jy.kim.lancs.magicdoor.asynctask.GetAllMessages;
import jy.kim.lancs.magicdoor.asynctask.PostMessage;
import jy.kim.lancs.magicdoor.bean.MessageBean;
import jy.kim.lancs.magicdoor.query.QueryForMagicDoor;
import jy.kim.lancs.magicdoor.query.QueryForMessages;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

public class MessageModel {
	public String tagOwner;
	public String tagOwnerName;
	public String userName;
	public Activity activity;

	public MessageModel(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		Intent prevActivity = activity.getIntent();
		tagOwner = prevActivity.getStringExtra("tagOwner");
		userName = prevActivity.getStringExtra("userName");
		tagOwnerName = prevActivity.getStringExtra("tagOwnerName");
	}

	public ArrayList<MessageBean> postNewMessage(MessageBean msg) {
		// TODO Auto-generated method stub
		ArrayList<MessageBean> result = null;
		// uri included to object
		AsyncTask<MessageBean, Void, ArrayList<MessageBean>> info = new PostMessage(
				activity).execute(msg);

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

	public ArrayList<MessageBean> getMessagesOfTheReceiver(
			QueryForMagicDoor query) {
		// TODO Auto-generated method stub
		ArrayList<MessageBean> result = null;

		AsyncTask<String, Void, ArrayList<MessageBean>> info = new GetAllMessages(
				activity).execute(query.uri + query.tagOwner);

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

	public ArrayList<MessageBean> replyMessage(MessageBean repliedMsg) {
		// TODO Auto-generated method stub
		ArrayList<MessageBean> result = null;
		// uri included to object
		AsyncTask<MessageBean, Void, ArrayList<MessageBean>> info = new PostMessage(
				activity).execute(repliedMsg);

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

	public ArrayList<MessageBean> getMessagesOfTheSender(
			QueryForMessages query) {
		// TODO Auto-generated method stub
		ArrayList<MessageBean> result = null;

		AsyncTask<String, Void, ArrayList<MessageBean>> info = new GetAllMessages(
				activity).execute(query.uri + query.sender);

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

}
