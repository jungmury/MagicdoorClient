package jy.kim.lancs.magicdoor.adapter;

import java.util.ArrayList;

import magicdoor.lancs.R;

import jy.kim.lancs.magicdoor.bean.NFCTagInfoBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TagListViewAdapter extends ArrayAdapter<NFCTagInfoBean> {

	int resource;
	String response;
	Context context;

	// Initialize adapter
	public TagListViewAdapter(Context context, int resource,
			ArrayList<NFCTagInfoBean> items) {
		super(context, resource, items);
		this.resource = resource;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout tagListView;
		// Get the current alert object
		NFCTagInfoBean tag = getItem(position);

		// Inflate the view
		if (convertView == null) {
			tagListView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi;
			vi = (LayoutInflater) getContext().getSystemService(inflater);
			vi.inflate(resource, tagListView, true);
		} else {
			tagListView = (LinearLayout) convertView;
		}
		// Get the text boxes from the listitem.xml file
		TextView tvID = (TextView) tagListView.findViewById(R.id.tvForTagID);
		TextView tvServiceDesc = (TextView) tagListView
				.findViewById(R.id.tvForTagServiceDescription);

		// Assign the appropriate data from our alert object above
		tvID.setText("Tag ID: " + tag.tagId);
		tvServiceDesc.setText("Service Type: " + tag.serviceDescription);

		return tagListView;
	}
}