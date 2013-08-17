package jy.kim.lancs.magicdoor.announcement;

import java.util.ArrayList;

import jy.kim.lancs.magicdoor.adapter.AnnouncementListViewAdapter;
import jy.kim.lancs.magicdoor.bean.AnnouncementRespBean;
import jy.kim.lancs.magicdoor.dialog.MagicDoorCustomAlertDialogBuilder;
import magicdoor.lancs.R;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ShowAnnouncementActivity extends Activity implements
		OnItemClickListener {

	private TextView tvLabel;
	private ListView lvForAnnouncements;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.i("MagicDoor", "ShowAnnouncementsActivity");
		setContentView(R.layout.view_show_announcements);
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		
		tvLabel = (TextView) findViewById(R.id.tvLabelForAnnouncementsShow);
		lvForAnnouncements = (ListView) findViewById(R.id.lvForAnnouncementsShow);

		ArrayList<AnnouncementRespBean> announcements = (ArrayList<AnnouncementRespBean>) getIntent()
				.getSerializableExtra("announcements");
		if (announcements != null) {
			tvLabel.setText(announcements.get(0).announcerName + "'s Announcements");
			populateListView(announcements);
		} else {
			populateListView(announcements);
		}

		lvForAnnouncements.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		showAnnoucementContentDialog(position);
	}

	private void showAnnoucementContentDialog(int position) {
		// TODO Auto-generated method stub
		AnnouncementRespBean announcement = (AnnouncementRespBean) lvForAnnouncements
				.getItemAtPosition(position);
		MagicDoorCustomAlertDialogBuilder dialog = new MagicDoorCustomAlertDialogBuilder(
				this);
		dialog.create();
		dialog.setAnnouncementContentAlertDialog(announcement);
		dialog.show();
	}

	private void populateListView(ArrayList<AnnouncementRespBean> announcements) {
		// TODO Auto-generated method stub
		lvForAnnouncements = (ListView) findViewById(R.id.lvForAnnouncementsShow);
		TextView tvEmptyAnn = (TextView) findViewById(R.id.tvEmptyAnnListViewAnnShow);
		// Initialize our ArrayList
		// Initialize our array adapter notice how it references the
		// listitems.xml layout
		AnnouncementListViewAdapter arrayAdapter = null;

		if (announcements != null) {
			Log.i("MagicDoor", "annoucements not null");
			arrayAdapter = new AnnouncementListViewAdapter(
					ShowAnnouncementActivity.this,
					R.layout.view_listitems_announcement, announcements);

			tvEmptyAnn.setVisibility(View.INVISIBLE);

			// Set the above adapter as the adapter of choice for our list
			lvForAnnouncements.setAdapter(arrayAdapter);
			arrayAdapter.notifyDataSetChanged();
			// it will show items correctly
			arrayAdapter.notifyDataSetInvalidated();
		} else {
			tvEmptyAnn.setVisibility(View.VISIBLE);
			Log.i("MagicDoor", "annoucements null");
			lvForAnnouncements.setAdapter(arrayAdapter);
		}

	}

}
