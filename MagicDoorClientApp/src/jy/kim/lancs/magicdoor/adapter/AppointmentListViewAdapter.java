package jy.kim.lancs.magicdoor.adapter;

import java.util.ArrayList;

import magicdoor.lancs.R;

import jy.kim.lancs.magicdoor.bean.AppointmentRespDetailBean;
import jy.kim.lancs.magicdoor.util.DateParseUtil;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AppointmentListViewAdapter extends
		ArrayAdapter<AppointmentRespDetailBean> {

	int resource;
	String response;
	Context context;

	// Initialize adapter
	public AppointmentListViewAdapter(Context context, int resource,
			ArrayList<AppointmentRespDetailBean> items) {
		super(context, resource, items);
		this.resource = resource;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout appointmentListView;
		// Get the current alert object
		AppointmentRespDetailBean app = getItem(position);

		// Inflate the view
		if (convertView == null) {
			appointmentListView = new LinearLayout(getContext());
			String inflater = Context.LAYOUT_INFLATER_SERVICE;
			LayoutInflater vi;
			vi = (LayoutInflater) getContext().getSystemService(inflater);
			vi.inflate(resource, appointmentListView, true);
		} else {
			appointmentListView = (LinearLayout) convertView;
		}
		// Get the text boxes from the listitem.xml file
		TextView tvTitle = (TextView) appointmentListView
				.findViewById(R.id.tvAppointmentTitle);
		TextView tvWrittenDate = (TextView) appointmentListView
				.findViewById(R.id.tvAppointmentDate);
		
		// Assign the appropriate data from our alert object above
		tvTitle.setTextColor(Color.BLACK);
		tvWrittenDate.setTextColor(Color.BLACK);
		tvTitle.setText(app.title);
		
		tvWrittenDate.setText("Date Info: "
				+ DateParseUtil.getDateInNewFormat(app.bookingDate)
				+ "\nTime Info: " + app.bookingTime);
		return appointmentListView;
	}
}