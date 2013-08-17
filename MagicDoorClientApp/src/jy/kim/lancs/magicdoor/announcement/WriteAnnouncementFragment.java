package jy.kim.lancs.magicdoor.announcement;

import java.util.ArrayList;

import jy.kim.lancs.magicdoor.bean.AnnouncementReqBean;
import jy.kim.lancs.magicdoor.bean.AnnouncementRespBean;
import jy.kim.lancs.magicdoor.util.MagicDoorContants;
import magicdoor.lancs.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

@SuppressLint("ValidFragment")
public class WriteAnnouncementFragment extends Fragment implements
		OnClickListener {
	private EditText etTitle;
	private EditText etContent;
	private Button btnToConfirm;

	private Activity _activity;
	private View _rootView;

	public WriteAnnouncementFragment() {
		// TODO Auto-generated constructor stub
	}

	public WriteAnnouncementFragment(Activity activity) {
		// TODO Auto-generated constructor stub
		this._activity = activity;
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

		_rootView = inflater.inflate(R.layout.view_form_write_announcement,
				container, false);
		etTitle = (EditText) _rootView.findViewById(R.id.etAnnouncementTitle);
		etContent = (EditText) _rootView
				.findViewById(R.id.etAnnouncementContent);
		btnToConfirm = (Button) _rootView
				.findViewById(R.id.btnToConfirmAnnouncement);
		btnToConfirm.setOnClickListener(this);
		return _rootView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.btnToConfirmAnnouncement:
			addNewAnnouncement();
			break;

		default:
			break;
		}
	}

	private void addNewAnnouncement() {
		// TODO Auto-generated method stub
		AnnouncementReqBean ann = new AnnouncementReqBean();
		ann.title = etTitle.getText().toString();
		ann.announcementContent = etContent.getText().toString();
		ann.uri = MagicDoorContants.REST_SERVICE_URI_FOR_ADDING_ANNOUNCEMENTS;
		Announcement model = new Announcement(_activity);
		ArrayList<AnnouncementRespBean> announcements = model
				.addNewAnnouncement(ann);
		_activity.getIntent().putExtra("announcements", announcements);
		
		/*
		Intent intent = new Intent(_activity, ManageAnnouncementActivity.class);
		intent.putExtra("announcements", announcements);
		startActivity(intent);*/
		// _activity.getIntent().putExtra("announcements", announcements);
		// _activity.finish();
		// _activity.startActivity(_activity.getIntent());
	}
}
