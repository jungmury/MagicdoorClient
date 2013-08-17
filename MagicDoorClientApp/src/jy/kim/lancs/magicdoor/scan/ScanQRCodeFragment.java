package jy.kim.lancs.magicdoor.scan;

import magicdoor.lancs.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

@SuppressLint("ValidFragment")
public class ScanQRCodeFragment extends Fragment {
	private Activity _activity;
	private View _rootView;

	public ScanQRCodeFragment(Activity activity) {
		// TODO Auto-generated constructor stub
		this._activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i("MagicDoor", "ScanQRCodeFragment");

		_rootView = inflater.inflate(R.layout.view_scan, container, false);
		return _rootView;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		_activity.finish();
		return true;
	}

}
