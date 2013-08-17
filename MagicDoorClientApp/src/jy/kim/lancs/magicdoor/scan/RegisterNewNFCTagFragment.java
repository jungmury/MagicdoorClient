package jy.kim.lancs.magicdoor.scan;

import java.util.ArrayList;

import jy.kim.lancs.magicdoor.bean.NFCTagInfoBean;
import jy.kim.lancs.magicdoor.util.MagicDoorContants;
import magicdoor.lancs.R;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
@SuppressLint("ValidFragment")
public class RegisterNewNFCTagFragment extends Fragment implements
		OnClickListener {
	protected String tagId;
	private View _rootView;
	private Activity _activity;
	// private Activity _activity;

	protected TextView tvTagId;
	private ArrayList<NFCTagInfoBean> tags;

	public RegisterNewNFCTagFragment() {
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

		_rootView = inflater.inflate(R.layout.view_regster_new_nfc_tag,
				container, false);
		tvTagId = (TextView) _rootView.findViewById(R.id.tvForTagID);
		Button btnForAppointment = (Button) _rootView
				.findViewById(R.id.btnForRegAppointment);
		Button btnForAnnouncement = (Button) _rootView
				.findViewById(R.id.btnForRegAnnouncement);
		Button btnForMessage = (Button) _rootView
				.findViewById(R.id.btnForRegMessage);
		Button btnForTimetable = (Button) _rootView
				.findViewById(R.id.btnForRegTimetable);

		btnForAnnouncement.setOnClickListener(this);
		btnForAppointment.setOnClickListener(this);
		btnForMessage.setOnClickListener(this);
		btnForTimetable.setOnClickListener(this);
		tags = (ArrayList<NFCTagInfoBean>) getArguments().get("tags");
		return _rootView;
	}

	public void setId(String id) {
		tvTagId.setText(id);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		String uri = MagicDoorContants.REST_SERVICE_URI_FOR_ADDING_NEW_NFC_TAG;
		String tagOwner = _activity.getIntent().getStringExtra("userName");
		String tagId = tvTagId.getText().toString();
		String serviceName = "";
		switch (id) {
		case R.id.btnForRegAnnouncement:
			serviceName = "announcement";
			break;
		case R.id.btnForRegAppointment:
			serviceName = "appointment";
			break;
		case R.id.btnForRegMessage:
			serviceName = "message";
			break;
		case R.id.btnForRegTimetable:
			serviceName = "timetable";
			break;
		default:
			break;
		}
		boolean isIdAlreadyAdded = false;
		if (tags != null) {
			for (NFCTagInfoBean tag : tags) {
				if (tag.tagId.equals(tagId)) {
					isIdAlreadyAdded = true;
				}
			}
			if (!isIdAlreadyAdded) {
				registerNewNFCTag(uri, tagOwner, serviceName, tagId);
			} else {
				Toast.makeText(_activity, "The tag ID is already registered",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(_activity, "tags is null", Toast.LENGTH_SHORT)
					.show();

		}

	}

	private void registerNewNFCTag(String uri, String tagOwner,
			String serviceName, String tagId) {
		// TODO Auto-generated method stub
		NFCTagInfoBean bean = new NFCTagInfoBean();
		bean.uri = uri;
		bean.tagId = tagId;
		bean.tagOwner = tagOwner;
		bean.serviceDescription = serviceName;
		TagModel model = new TagModel(_activity);
		ArrayList<NFCTagInfoBean> tags = model.addNewTag(bean);
		if (tags != null) {
			Toast.makeText(_activity, "The NFC tag is successfully registered",
					Toast.LENGTH_SHORT).show();
		}
	}

}
