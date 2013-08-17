package jy.kim.lancs.magicdoor.adapter;

import magicdoor.lancs.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CalendarLabelAdapter extends BaseAdapter {

	private Context context;
	private String[] calLabels;

	public CalendarLabelAdapter(Context context, String[] calLabels) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.calLabels = calLabels;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View gridView = convertView;
		TextView tvDateLable;
		if (convertView == null) { // if it's not recycled, initialize some
									// attributes
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			gridView = layoutInflater.inflate(R.layout.view_calendar_label,
					null);
			tvDateLable = (TextView) gridView.findViewById(R.id.tvDateLabel);
			if(calLabels[position].equals("Sun")){
				tvDateLable.setTextColor(Color.RED);
			}
			tvDateLable.setText(calLabels[position]);
		} 
		return gridView;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return calLabels.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}
