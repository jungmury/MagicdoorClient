package jy.kim.lancs.magicdoor.dialog;

import magicdoor.lancs.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MagicDoorCustomAlertDialog extends AlertDialog {

	private Activity activity;
	public TextView tvSelectedDate;
	public ListView lvForTime;

	public MagicDoorCustomAlertDialog(Activity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
		this.activity = activity;
	}

	public View getViewAfterSetSelectingTimeAlertDialog() {
		// layout setting
		LayoutInflater vi = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = vi.inflate(R.layout.view_time_slot, null);

		tvSelectedDate = (TextView) view
				.findViewById(R.id.tvSelectedBookingDate);
		lvForTime = (ListView) view.findViewById(R.id.lvForTimeSlots);
		return view;

	}
}
