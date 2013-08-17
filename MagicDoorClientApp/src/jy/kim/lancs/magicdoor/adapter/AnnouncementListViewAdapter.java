package jy.kim.lancs.magicdoor.adapter;

import java.util.ArrayList;

import magicdoor.lancs.R;

import jy.kim.lancs.magicdoor.bean.AnnouncementRespBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AnnouncementListViewAdapter extends
		ArrayAdapter<AnnouncementRespBean> {

	int resource;
	String response;
	Context context;

	// Initialize adapter
	public AnnouncementListViewAdapter(Context context, int resource,
			ArrayList<AnnouncementRespBean> items) {
		super(context, resource, items);
		this.resource = resource;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout announcementListView;
		// Get the current alert object
		AnnouncementRespBean ann = getItem(position);

		// Inflate the view
		if (convertView == null) {
			announcementListView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi;
			vi = (LayoutInflater) getContext().getSystemService(inflater);
			vi.inflate(resource, announcementListView, true);
		} else {
			announcementListView = (LinearLayout) convertView;
		}
		// Get the text boxes from the listitem.xml file
		TextView tvTitle = (TextView) announcementListView
				.findViewById(R.id.tvAnnouncementTitle);
		TextView tvWrittenDate = (TextView) announcementListView
				.findViewById(R.id.tvAnnouncementWrittenDate);

		// Assign the appropriate data from our alert object above
		tvTitle.setText(ann.title);
		tvWrittenDate.setText(ann.writtenDate.toString());

		return announcementListView;
	}
}