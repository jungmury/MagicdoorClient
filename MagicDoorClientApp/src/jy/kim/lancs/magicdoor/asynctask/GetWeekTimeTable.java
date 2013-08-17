package jy.kim.lancs.magicdoor.asynctask;

import java.io.IOException;
import java.util.ArrayList;

import jy.kim.lancs.magicdoor.bean.TimeTableBean;
import jy.kim.lancs.magicdoor.query.QueryForMagicDoor;
import magicdoor.lancs.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

public class GetWeekTimeTable extends
		AsyncTask<QueryForMagicDoor, Void, ArrayList<TimeTableBean>> {

	private ProgressDialog dialog;
	private Context context;

	public GetWeekTimeTable(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		dialog = new ProgressDialog(context);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		LayoutInflater vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = vi.inflate(R.layout.view_progress_dialog_background, null);
		ProgressBar pb = (ProgressBar) view.findViewById(R.id.progressBar);
		dialog.setView(pb);
		dialog.setMessage("Please wait");
		dialog.show();
		Log.i("MagicDoor", "preExecute");
	}

	@Override
	protected ArrayList<TimeTableBean> doInBackground(
			QueryForMagicDoor... params) {
		// TODO Auto-generated method stub
		ArrayList<TimeTableBean> result = null;
		HttpClient httpclient = new DefaultHttpClient();

		// Prepare a request object
		HttpGet httpGet = new HttpGet(params[0].uri + params[0].tagOwner);

		// Execute the request
		HttpResponse response;

		try {

			response = httpclient.execute(httpGet);

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
					result = mapper.readValue(entity.getContent(),
							new TypeReference<ArrayList<TimeTableBean>>() {
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

	@Override
	protected void onPostExecute(ArrayList<TimeTableBean> result) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		Log.i("MagicDoor", "postExecute");
	}
}
