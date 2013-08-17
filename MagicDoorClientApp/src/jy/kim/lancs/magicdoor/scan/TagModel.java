package jy.kim.lancs.magicdoor.scan;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import jy.kim.lancs.magicdoor.asynctask.GETNFCTagsInfo;
import jy.kim.lancs.magicdoor.asynctask.GetWebServiceDesc;
import jy.kim.lancs.magicdoor.asynctask.PostNFCTag;
import jy.kim.lancs.magicdoor.bean.NFCTagInfoBean;
import jy.kim.lancs.magicdoor.bean.WebServiceInfoRespBean;
import jy.kim.lancs.magicdoor.query.QueryForMagicDoor;
import jy.kim.lancs.magicdoor.util.TagUtility;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

public class TagModel {

	public String userName;
	public NfcAdapter nfcAdapter;

	private Activity activity;
	private Context context;

	private IntentFilter[] tagFilters;
	private PendingIntent nfcPendingIntent;

	private TagUtility util;
	private Tag myTag;

	public TagModel(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
	}

	public TagModel(Activity activity, PendingIntent nfcPendingIntent) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		this.nfcPendingIntent = nfcPendingIntent;
		Intent prevActivity = activity.getIntent();
		userName = prevActivity.getStringExtra("userName");
		context = activity.getApplicationContext();

		// Getting NFC adapter
		nfcAdapter = NfcAdapter.getDefaultAdapter(activity);

		// only ACTION_TAG_DISCOVERED will be caught
		// intent filters to want to deal with defined
		IntentFilter tagDetected = new IntentFilter(
				NfcAdapter.ACTION_TAG_DISCOVERED);

		tagDetected.addCategory(Intent.CATEGORY_DEFAULT);
		tagFilters = new IntentFilter[] { tagDetected };

		util = new TagUtility(context);
	}

	public String parseIdToString(Tag tag) {
		String tagId = String.valueOf(ByteBuffer.wrap(tag.getId()).getInt());
		return tagId;
	}

	public void readTagData(Intent intent, Tag tag) {
		Parcelable[] rawMsgs = intent
				.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

		if (rawMsgs != null) {
			// only one message sent during the beam
			NdefMessage msg = (NdefMessage) rawMsgs[0];
			// record 0 contains the MIME type, record 1 is the AAR, if present
			Log.i("MagicDoor",
					"size of record = " + msg.getRecords().length + "\n"
							+ "record[0]: "
							+ new String(msg.getRecords()[0].getPayload()));
		}

	}

	public void readModeOn() {
		// readMode = true;
		nfcAdapter.enableForegroundDispatch(activity, nfcPendingIntent,
				tagFilters, null);
		Log.d("MagicDoor", "read mode on");
	}

	public void readModeOff() {
		// readMode = false;
		nfcAdapter.disableForegroundDispatch(activity);
		Log.d("MagicDoor", "read mode off");
	}

	public WebServiceInfoRespBean getTagOwnerInfo(String uri, String tagOwner) {
		// TODO Auto-generated method stub
		Log.d("MagicDoor", "nfcManager");
		WebServiceInfoRespBean result = null;
		// /////////////////////////////////
		AsyncTask<String, Void, WebServiceInfoRespBean> info = new GetWebServiceDesc(
				activity).execute(uri + tagOwner);
		try {
			result = info.get();
			// /////////////////////////////////
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public WebServiceInfoRespBean initNfcManager(String uri, Intent intent) {
		// TODO Auto-generated method stub
		Log.d("MagicDoor", "nfcManager");
		WebServiceInfoRespBean result = null;
		// do if nfc is enabled
		if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
			// catch the type intent filter
			Log.d("MagicDoor", "NFC is scanned");
			myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			String tagId = parseIdToString(myTag);
			String[] techs = myTag.getTechList();
			String techAppedned = "";
			// /////////////////////////////////
			AsyncTask<String, Void, WebServiceInfoRespBean> info = new GetWebServiceDesc(
					activity).execute(uri + tagId);

			try {
				result = info.get();

				// /////////////////////////////////
				for (String tech : techs) {
					techAppedned += tech + "\n";
				}
				Log.i("MagicDoor", "techlist =" + techAppedned);

				// check if scanned tag is supported
				if (util.isTechSupported(techs)) {
					Log.d("MagicDoor", "The tag is supported");

					readTagData(intent, myTag);

				} else {
					Toast.makeText(activity.getApplicationContext(),
							"The tag is not supported!", Toast.LENGTH_SHORT)
							.show();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			Log.d("MagicDoor", "Different intent filters are required");
		}

		return result;
	}

	public ArrayList<NFCTagInfoBean> addNewTag(NFCTagInfoBean bean) {
		// TODO Auto-generated method stub
		ArrayList<NFCTagInfoBean> tags = null;
		AsyncTask<NFCTagInfoBean, Void, ArrayList<NFCTagInfoBean>> info = new PostNFCTag(
				activity).execute(bean);

		try {
			tags = info.get();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// perform when item selected
		return tags;
	}

	public ArrayList<NFCTagInfoBean> getAllTagsOfTheTagOwner(
			QueryForMagicDoor query) {
		// TODO Auto-generated method stub
		ArrayList<NFCTagInfoBean> tags = null;
		AsyncTask<String, Void, ArrayList<NFCTagInfoBean>> info = new GETNFCTagsInfo(
				activity).execute(query.uri + "/" + query.tagOwner);

		try {
			tags = info.get();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// perform when item selected
		return tags;
	}
}
