package jy.kim.lancs.magicdoor.scan;

import java.nio.ByteBuffer;

import jy.kim.lancs.magicdoor.util.TagUtility;
import magicdoor.lancs.R;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class ReadTagActivity extends Activity {

	protected NfcAdapter nfcAdapter;
	protected IntentFilter[] tagFilters;
	protected PendingIntent nfcPendingIntent;

	protected TextView tvTagId;
	
	protected Tag myTag;
	protected boolean readMode;

	protected Context curContext;
	protected TagUtility util = new TagUtility(curContext);

	private static String TAG = "MagicDoor";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_regster_new_nfc_tag);

		curContext = getApplicationContext();

		// Obtaining objects
		tvTagId = (TextView) findViewById(R.id.tvForTagID);
	
		// Getting NFC adapter
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);

		nfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

		// intent filters to want to deal with defined
		IntentFilter tagDetected = new IntentFilter(
				NfcAdapter.ACTION_TAG_DISCOVERED);
		/*
		 * IntentFilter ndefDetected = new IntentFilter(
		 * NfcAdapter.ACTION_NDEF_DISCOVERED); IntentFilter techDetected = new
		 * IntentFilter( NfcAdapter.ACTION_TECH_DISCOVERED);
		 */
		tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
		tagFilters = new IntentFilter[] { tagDetected };
		// only ACTION_TAG_DISCOVERED will be caught
		Log.d(TAG, "create");
	}

	@Override
	protected void onResume() {
		super.onResume();
		readModeOn();
		Log.d(TAG, "resume");

	}

	@Override
	protected void onPause() {
		super.onPause();
		readModeOff();
		Log.d(TAG, "pause");
	}

	// when intent caught
	@Override
	public void onNewIntent(Intent intent) {
		Log.d(TAG, "onNewIntent");
		if (nfcAdapter != null) {// do if nfc is enabled
			if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
				// catch the type intent filter
				Log.d(TAG, "NFC is scanned");
				myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
				String tagId = parseIdToString(myTag);
				String[] techs = myTag.getTechList();
				tvTagId.setText(tagId);
				
				// check if scanned tag is supported
				if (util.isTechSupported(techs)) {
					Log.d(TAG, "The tag is supported");

		//			readTagData(intent, myTag);

				} else {
					Toast.makeText(this, "The tag is not supported!",
							Toast.LENGTH_SHORT).show();
				}
				// parse byte[] to int

			} else {
				Toast.makeText(this, "Different intent filters are required",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, "NFC is not available on the device",
					Toast.LENGTH_SHORT).show();
			Log.d(TAG, "NFC is not available on the device");
		}
	}

	private String parseIdToString(Tag tag) {
		String tagId = String.valueOf(ByteBuffer.wrap(tag.getId()).getInt());
		return tagId;
	}

	/*
	private void readTagData(Intent intent, Tag tag) {
		Parcelable[] rawMsgs = intent
				.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		// only one message sent during the beam
		if (rawMsgs != null) {
			NdefMessage msg = (NdefMessage) rawMsgs[0];
			// record 0 contains the MIME type, record 1 is the AAR, if present
			tvContent.setText("size of record = " + msg.getRecords().length
					+ "\n" + "record[0]: "
					+ new String(msg.getRecords()[0].getPayload()));// +"\nrecord[1]: "
																	// + new
																	// String(msg.getRecords()[1].getPayload()));//
																	// +
																	// "\nrecord[2]: "
																	// + new
																	// String(msg.getRecords()[2].getPayload()));

		}
	}
	*/

	private void readModeOn() {
		readMode = true;
		nfcAdapter.enableForegroundDispatch(this, nfcPendingIntent, tagFilters,
				null);
		Log.d(TAG, "write mode on");
		// Toast.makeText(this, "wirte on", Toast.LENGTH_SHORT).show();
	}

	private void readModeOff() {
		readMode = false;
		nfcAdapter.disableForegroundDispatch(this);
		Log.d(TAG, "write mode off");
		// Toast.makeText(this, "wirte off", Toast.LENGTH_SHORT).show();
	}

}
