package jy.kim.lancs.magicdoor.scan;

import java.util.ArrayList;

import jy.kim.lancs.magicdoor.adapter.TagListViewAdapter;
import jy.kim.lancs.magicdoor.bean.NFCTagInfoBean;
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
public class ManageNFCTagFragment extends Fragment implements
		OnItemClickListener {
	private ListView lvForTags;
	private TagModel tagModel;
	private Activity _activity;
	private View _rootView;

	private ArrayList<NFCTagInfoBean> _tags;
	public ManageNFCTagFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this._activity = activity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i("MagicDoor", "ManageAnnouncementsFragment");

		_rootView = inflater.inflate(
				R.layout.view_lecturer_manage_tags, container,
				false);
		lvForTags = (ListView) _rootView
				.findViewById(R.id.lvForTags);
		_tags = (ArrayList<NFCTagInfoBean>) getArguments().get("tags");
		tagModel = new TagModel(_activity);

		
		populateListView(_tags);
		if (_tags == null) {
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
		final NFCTagInfoBean tag = (NFCTagInfoBean) lvForTags
				.getItemAtPosition(position);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				_activity);

		// set dialog message
		alertDialogBuilder
				.setTitle("Tag Information")
				.setCancelable(true)
				.setMessage(
						"ID: " + tag.tagId + "\nService Desciption: "
								+ tag.serviceDescription)
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
							//	query.uri = MagicDoorContants.REST_SERVICE_URI_FOR_DELETING_AN_ANNOUNCEMENT;
							//	query. = tag.tagId;
							//	query.tagOwner = tag.tagOwner;
							//	ArrayList<NFCTagInfoBean> result = tagModel
				//						.deleteTheSelectedAnnouncement(query);
				//				if (result != null) {
									Toast.makeText(_activity,
											"The Announcement deleted",
											Toast.LENGTH_SHORT).show();
				//				}
					//			populateListView(result);

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

	private void populateListView(ArrayList<NFCTagInfoBean> announcements) {
		// TODO Auto-generated method stub
		Log.i("MagicDoor", "populateListView");
		lvForTags = (ListView) _rootView
				.findViewById(R.id.lvForTags);

		// when there are announcements
		// Initialize our ArrayList
		// Initialize our array adapter notice how it references the
		// listitems.xml layout

		TagListViewAdapter arrayAdapter = null;
		TextView tvEmpty = (TextView) _rootView
				.findViewById(R.id.tvEmptyForTags);
		if (_tags != null) {
			arrayAdapter = new TagListViewAdapter(_activity,
					R.layout.view_listitems_tag, _tags);

			// Set the above adapter as the adapter of choice for our list
			tvEmpty.setVisibility(View.INVISIBLE);
			lvForTags.setAdapter(arrayAdapter);

			arrayAdapter.notifyDataSetChanged();
			arrayAdapter.notifyDataSetInvalidated();
			lvForTags.setOnItemClickListener(this);
		} else {
			// when there are no announcements
			tvEmpty.setVisibility(View.VISIBLE);
			// force list view to redraw
			lvForTags.setAdapter(arrayAdapter);
		}

	}
}
