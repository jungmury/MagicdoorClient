package jy.kim.lancs.magicdoor.announcement;

import java.util.ArrayList;

import jy.kim.lancs.magicdoor.adapter.AnnouncementListViewAdapter;
import jy.kim.lancs.magicdoor.bean.AnnouncementRespBean;
import jy.kim.lancs.magicdoor.query.QueryForAnnouncements;
import jy.kim.lancs.magicdoor.util.MagicDoorContants;
import magicdoor.lancs.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class ManageAnnouncementFragment extends Fragment implements
		OnItemClickListener {
	private ListView lvForAnnouncements;
	private Announcement manageAnnModel;
	private Activity _activity;
	private View _rootView;

	private ArrayList<AnnouncementRespBean> _announcements;
	public ManageAnnouncementFragment() {
		// TODO Auto-generated constructor stub
	}

	public ManageAnnouncementFragment(Activity activity, ArrayList<AnnouncementRespBean> announcements) {
		// TODO Auto-generated constructor stub
		this._activity = activity;
		this._announcements = announcements;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this._activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i("MagicDoor", "ManageAnnouncementsFragment");

		_rootView = inflater.inflate(
				R.layout.view_lecturer_manage_announcements, container,
				false);
		lvForAnnouncements = (ListView) _rootView
				.findViewById(R.id.lvForAnnouncementsManage);

		manageAnnModel = new Announcement(_activity);

		
		populateListView(_announcements);
		if (_announcements == null) {
			Log.i("MagicDoor", "null");
		} else {
			Log.i("MagicDoor", "not null");
		}
		return _rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		showAnnoucementContentDialog(position);
	}

	private void showAnnoucementContentDialog(int position) {
		// TODO Auto-generated method stub
		final AnnouncementRespBean announcement = (AnnouncementRespBean) lvForAnnouncements
				.getItemAtPosition(position);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				_activity);

		// set dialog message
		alertDialogBuilder
				.setTitle("Announcement Content")
				.setCancelable(true)
				.setMessage(
						"Title: " + announcement.title + "\nPosted: "
								+ announcement.writtenDate + "\nContent: "
								+ announcement.announcementContent)
				.setPositiveButton("Edit",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								
							}

						})
				.setNeutralButton("Delete",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								QueryForAnnouncements query = new QueryForAnnouncements();
								query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_DELETING_AN_ANNOUNCEMENT;
								query.annNo = announcement.announcementNum;
								query.tagOwner = announcement.userName;
								ArrayList<AnnouncementRespBean> result = manageAnnModel
										.deleteTheSelectedAnnouncement(query);
								if (result != null) {
									Toast.makeText(_activity,
											"The Announcement deleted",
											Toast.LENGTH_SHORT).show();
								}
								populateListView(result);

							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();

	}

	private void populateListView(ArrayList<AnnouncementRespBean> announcements) {
		// TODO Auto-generated method stub
		Log.i("MagicDoor", "populateListView");
		lvForAnnouncements = (ListView) _rootView
				.findViewById(R.id.lvForAnnouncementsManage);

		// when there are announcements
		// Initialize our ArrayList
		// Initialize our array adapter notice how it references the
		// listitems.xml layout

		AnnouncementListViewAdapter arrayAdapter = null;
		TextView tvEmpty = (TextView) _rootView
				.findViewById(R.id.tvEmptyAnnListViewAnnMNG);
		if (announcements != null) {
			arrayAdapter = new AnnouncementListViewAdapter(_activity,
					R.layout.view_listitems_announcement, announcements);

			// Set the above adapter as the adapter of choice for our list
			tvEmpty.setVisibility(View.INVISIBLE);
			lvForAnnouncements.setAdapter(arrayAdapter);

			arrayAdapter.notifyDataSetChanged();
			arrayAdapter.notifyDataSetInvalidated();
			lvForAnnouncements.setOnItemClickListener(this);
		} else {
			// when there are no announcements
			tvEmpty.setVisibility(View.VISIBLE);
			// force list view to redraw
			lvForAnnouncements.setAdapter(arrayAdapter);
		}

	}
}
