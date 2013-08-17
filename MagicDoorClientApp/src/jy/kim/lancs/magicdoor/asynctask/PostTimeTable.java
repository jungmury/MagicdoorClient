package jy.kim.lancs.magicdoor.asynctask;

import java.io.IOException;
import java.util.ArrayList;

import magicdoor.lancs.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import jy.kim.lancs.magicdoor.bean.TimeTableBean;
import jy.kim.lancs.magicdoor.util.JsonUtil;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

public class PostTimeTable extends
		AsyncTask<TimeTableBean, Void, ArrayList<TimeTableBean>> {

	private ProgressDialog dialog;
	private Context context;

	public PostTimeTable(Context context) {
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
	protected ArrayList<TimeTableBean> doInBackground(TimeTableBean... params) {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();

		// Prepare a request object
		HttpPost httpPost = new HttpPost(params[0].uri);

		// Execute the request
		HttpResponse response;

		ArrayList<TimeTableBean> result = null;
		try {
			// prepare to transfer by parsing object to json
			StringEntity json = JsonUtil.getJsonTimeTable(params);
			httpPost.setEntity(json);
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");

			response = httpclient.execute(httpPost);

			// Examine the response status
			Log.i("MagicDoor", response.getStatusLine().toString());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();

			// If the response does not enclose an entity, there is no need
			// to worry about connection release
			if (entity != null) {
				Log.i("MagicDoor", entity.toString());
				// create ProfessorDataBean object from Json using
				// ObjectMapper
				ObjectMapper mapper = new ObjectMapper();

				try {
					// parsing ArrayList objects of json
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
	}
}
