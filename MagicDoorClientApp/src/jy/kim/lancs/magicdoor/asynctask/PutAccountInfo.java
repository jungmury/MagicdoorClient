package jy.kim.lancs.magicdoor.asynctask;

import java.io.IOException;

import jy.kim.lancs.magicdoor.bean.LogInInfoRespBean;
import jy.kim.lancs.magicdoor.bean.UserAccountInfoBean;
import jy.kim.lancs.magicdoor.util.JsonUtil;

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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

public class PutAccountInfo extends
		AsyncTask<UserAccountInfoBean, Void, LogInInfoRespBean> {

	private Context context;
	private ProgressDialog dialog;
	public PutAccountInfo(Context ctx) {
		// TODO Auto-generated constructor stub
		context= ctx;
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
	protected LogInInfoRespBean doInBackground(UserAccountInfoBean... params) {
		// TODO Auto-generated method stub
		Log.i("MagicDoor", "doInBackground");

		SystemClock.sleep(1000);
		HttpClient httpclient = new DefaultHttpClient();

		// Prepare a request object
		HttpPost httpPost = new HttpPost(params[0].uri);

		// Execute the request
		HttpResponse response;

		LogInInfoRespBean signUpResult = null;
		try {
			StringEntity json = JsonUtil.getJsonUpdateAccountInfo(params);
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
					signUpResult = mapper.readValue(entity.getContent(),
							LogInInfoRespBean.class);

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
		return signUpResult;
	}

	@Override
	protected void onPostExecute(LogInInfoRespBean result) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		Log.i("MagicDoor", "postExecute");
	}
}