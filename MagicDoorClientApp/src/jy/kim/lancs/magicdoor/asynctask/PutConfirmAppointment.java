package jy.kim.lancs.magicdoor.asynctask;

import java.io.IOException;
import java.util.ArrayList;

import jy.kim.lancs.magicdoor.bean.AppointmentRespDetailBean;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class PutConfirmAppointment extends
		AsyncTask<String, Void, ArrayList<AppointmentRespDetailBean>> {

	private Context context;
	private ProgressDialog dialog;

	public PutConfirmAppointment(Context ctx) {
		// TODO Auto-generated constructor stub
		context = ctx;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		dialog = new ProgressDialog(context);
		dialog.setTitle("Please wait");
		dialog.show();
		Log.i("MagicDoor", "preExecute");
	}

	@Override
	protected ArrayList<AppointmentRespDetailBean> doInBackground(
			String... params) {
		// TODO Auto-generated method stub
		Log.i("MagicDoor", "doInBackground");

		HttpClient httpclient = new DefaultHttpClient();

		// Prepare a request object
		HttpPut httpPut = new HttpPut(params[0]);

		// Execute the request
		HttpResponse response;

		ArrayList<AppointmentRespDetailBean> result = null;
		try {
			
			httpPut.setHeader("Accept", "application/json");
			httpPut.setHeader("Content-type", "application/json");

			response = httpclient.execute(httpPut);

			// Examine the response status
			Log.i("MagicDoor", response.getStatusLine().toString());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release
			if (entity != null) {
				// create ProfessorDataBean object from Json using
				// ObjectMapper
				ObjectMapper mapper = new ObjectMapper();

				try {
					result = mapper
							.readValue(
									entity.getContent(),
									new TypeReference<ArrayList<AppointmentRespDetailBean>>() {
									});

				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	protected void onPostExecute(
			ArrayList<AppointmentRespDetailBean> result) {
		dialog.dismiss();
	}
}
