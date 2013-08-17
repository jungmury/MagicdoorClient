package jy.kim.lancs.magicdoor.scan;

import magicdoor.lancs.R;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class ScanNFCTagFragment extends Fragment {
	private View _rootView;

	// private Activity _activity;

	public ScanNFCTagFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.i("MagicDoor", "ManageAnnouncementsFragment");

		_rootView = inflater.inflate(R.layout.view_scan, container, false);
		TextView tv = (TextView) _rootView
				.findViewById(R.id.tvForScanInstruction);
		tv.setText("Scan NFC tag!");
		ImageView iv = (ImageView) _rootView.findViewById(R.id.ivForSymbol);
		iv.setImageResource(R.drawable.nfc_image);
		return _rootView;
	}
}
