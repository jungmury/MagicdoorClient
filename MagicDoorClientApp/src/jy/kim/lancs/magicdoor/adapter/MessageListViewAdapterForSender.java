package jy.kim.lancs.magicdoor.adapter;

import java.util.ArrayList;

import magicdoor.lancs.R;

import jy.kim.lancs.magicdoor.bean.MessageBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MessageListViewAdapterForSender extends ArrayAdapter<MessageBean> {

	int resource;
	String response;
	Context context;

	private boolean isShowSentMessages;

	// Initialize adapter
	public MessageListViewAdapterForSender(Context context, int resource,
			ArrayList<MessageBean> items, boolean isShowSentMessages) {
		super(context, resource, items);
		this.resource = resource;
		this.isShowSentMessages = isShowSentMessages;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout messageListView;
		// Get the current alert object
		MessageBean msg = getItem(position);

		// Inflate the view
		if (convertView == null) {
			messageListView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi;
			vi = (LayoutInflater) getContext().getSystemService(inflater);
			vi.inflate(resource, messageListView, true);
		} else {
			messageListView = (LinearLayout) convertView;
		}
		// Get the text boxes from the listitem.xml file
		TextView tvReceiver = (TextView) messageListView
				.findViewById(R.id.tvMessageReceiver);
		TextView tvSentDate = (TextView) messageListView
				.findViewById(R.id.tvMessageSentDate);

		if (isShowSentMessages) {
			// Assign the appropriate data from our alert object above
			tvReceiver.setText("Sent to: " + msg.name);
			tvSentDate.setText("Sent Date: " + msg.sentDate.toString());
		} else {
			tvReceiver.setText("Received from: " + msg.name);
			tvSentDate.setText("Received Date: " + msg.sentDate.toString());
		}

		return messageListView;
	}
}