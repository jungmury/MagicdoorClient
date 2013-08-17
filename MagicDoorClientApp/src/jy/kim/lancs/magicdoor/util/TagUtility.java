package jy.kim.lancs.magicdoor.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

import android.content.Context;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
//import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.util.Log;
import android.widget.Toast;

public class TagUtility {

	private static final String TAG = TagUtility.class.getSimpleName();
	private Context context;

	public TagUtility(Context context){
		this.context = context;
	}
	
	public NdefRecord createURI(String updateText) {
		NdefRecord rtdUriRecord = NdefRecord.createUri(updateText);
		return rtdUriRecord;
	}

	public NdefRecord createTextRecord(String payload, Locale locale,
			boolean encodeInUtf8) {
		byte[] langBytes = locale.getLanguage().getBytes(
				Charset.forName("US-ASCII"));
		Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset
				.forName("UTF-16");
		byte[] textBytes = payload.getBytes(utfEncoding);
		int utfBit = encodeInUtf8 ? 0 : (1 << 7);
		char status = (char) (utfBit + langBytes.length);
		byte[] data = new byte[1 + langBytes.length + textBytes.length];
		data[0] = (byte) status;
		System.arraycopy(langBytes, 0, data, 1, langBytes.length);
		System.arraycopy(textBytes, 0, data, 1 + langBytes.length,
				textBytes.length);
		NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,
				NdefRecord.RTD_TEXT, new byte[0], data);
		return record;
	}

	public WriteResponse writeTag(NdefMessage message, Tag tag) {
		int size = message.toByteArray().length;
		String mess = "";

		try {
			Ndef ndef = Ndef.get(tag);
			if (ndef != null) {
				ndef.connect();

				if (!ndef.isWritable()) {
					return new WriteResponse(0, "Tag is read-only");

				}
				if (ndef.getMaxSize() < size) {
					mess = "Tag capacity is " + ndef.getMaxSize()
							+ " bytes, message is " + size + " bytes.";
					return new WriteResponse(0, mess);
				}

				ndef.writeNdefMessage(message);
				/*
				 * if (writeProtect) ndef.makeReadOnly();
				 */
				mess = "Wrote message to pre-formatted tag.";
				return new WriteResponse(1, mess);
			} else {
				NdefFormatable format = NdefFormatable.get(tag);
				if (format != null) {
					try {
						format.connect();
						format.format(message);
						mess = "Formatted tag and wrote message";
						return new WriteResponse(1, mess);
					} catch (IOException e) {
						mess = "Failed to format tag.";
						return new WriteResponse(0, mess);
					}
				} else {
					mess = "Tag doesn't support NDEF.";
					return new WriteResponse(0, mess);
				}
			}
		} catch (Exception e) {
			mess = "Failed to write tag";
			return new WriteResponse(0, mess);
		}
	}
	
	public class WriteResponse {
		int status;
		String message;

		WriteResponse(int Status, String Message) {
			this.status = Status;
			this.message = Message;
		}

		public int getStatus() {
			return status;
		}

		public String getMessage() {
			return message;
		}
	}

	public boolean isTechSupported(String[] techList) {
		boolean result = false;
		boolean ultraLightOrisoDep = false;
		boolean nfcA = false;
		boolean ndef = false;

		for (String tech : techList) {
			if (tech.equals("android.nfc.tech.MifareUltralight")
					|| tech.equals("android.nfc.tech.IsoDep")) {
				ultraLightOrisoDep = true;
			} else if (tech.equals("android.nfc.tech.NfcA")) {
				nfcA = true;
			} else if (tech.equals("android.nfc.tech.Ndef")
					|| tech.equals("android.nfc.tech.NdefFormatable")) {
				ndef = true;
			}
		}

		if (ultraLightOrisoDep && nfcA && ndef) {
			result = true;
		}

		return result;
	}

	public boolean isTagWritable(Tag tag) {
		boolean result = false;
		Ndef ndef = Ndef.get(tag);

		if (ndef != null) {
			try {
				ndef.connect();
				if (ndef.isWritable()) {
					Log.d(TAG, "The tag is writable");
					result = true;
					ndef.close();
				} else {
					Toast.makeText(context, "the tag is not writable",
							Toast.LENGTH_SHORT).show();
					Log.d(TAG, "The tag is not writable");
					ndef.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Toast.makeText(context, "the tag is not writable", Toast.LENGTH_SHORT)
					.show();
			Log.d(TAG, "The tag is not writable");
		}
		return result;
	}

}
