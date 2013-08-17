package jy.kim.lancs.magicdoor.asynctask;

import java.io.IOException;

import jy.kim.lancs.magicdoor.bean.LogInInfoReqBean;
import jy.kim.lancs.magicdoor.bean.LogInInfoRespBean;
import jy.kim.lancs.magicdoor.startup.StartUpActivity;
import jy.kim.lancs.magicdoor.util.JsonUtil;

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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

//Login class, return userName, userType
public class PutLogIn extends
		AsyncTask<LogInInfoReqBean, Void, LogInInfoRespBean> {

	private ProgressDialog pd;
	private Context context;

	public PutLogIn(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		if (!context.getClass().equals(StartUpActivity.class)) {
			pd = new ProgressDialog(context);
		}

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		if (!context.getClass().equals(StartUpActivity.class)) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = vi.inflate(R.layout.view_progress_dialog_background,
					null);
			ProgressBar pb = (ProgressBar) view.findViewById(R.id.progressBar);
			pd.setView(pb);
			pd.setMessage("Loggin in...");
			pd.show();
		}
	}

	@Override
	protected LogInInfoRespBean doInBackground(LogInInfoReqBean... params) {
		// TODO Auto-generated method stub
		if (!context.getClass().equals(StartUpActivity.class)) {
			SystemClock.sleep(2000);
		}
		HttpClient httpclient = new DefaultHttpClient();
		// Prepare a request object
		HttpPost httpPost = new HttpPost(params[0].uri);
		// Execute the request
		HttpResponse response;
		LogInInfoRespBean loginInfo = null;
		try {
			// generate json value from LogInInfoReqBean object
			StringEntity json = JsonUtil.getJsonUserNameAndPassword(params);
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
					// Object mapping to LogInInfoRespBean from json data that
					// is returned from server
					loginInfo = mapper.readValue(entity.getContent(),
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
		return loginInfo;
	}

	@Override
	protected void onPostExecute(LogInInfoRespBean result) {
		// TODO Auto-generated method stub
		if (!context.getClass().equals(StartUpActivity.class)) {

			pd.dismiss();
		}
	}

}
