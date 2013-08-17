package jy.kim.lancs.magicdoor.login;

import jy.kim.lancs.magicdoor.account.SignUpActivity;
import jy.kim.lancs.magicdoor.bean.LogInInfoRespBean;
import jy.kim.lancs.magicdoor.dialog.MagicDoorCustomAlertDialogBuilder;
import jy.kim.lancs.magicdoor.util.MagicDoorContants;
import magicdoor.lancs.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//using static constants
public class LogInActivity extends Activity implements OnClickListener {
	private EditText etUserName;
	private EditText etPassword;
	private Button loginButton;
	private Button signUpButton;

	// ////////// will be passed //////////
	private String userName;
	private String password;
	private String userType;
	private LogIn logInModel;
	// //////////////////////////////////////

	private LogInInfoRespBean result;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_login);

		Log.i("MagicDoor", "LoginActivity");

		etUserName = (EditText) findViewById(R.id.etUserNameLogIn);
		etPassword = (EditText) findViewById(R.id.etPasswordLogIn);

		loginButton = (Button) findViewById(R.id.btnLogInLogIn);
		signUpButton = (Button) findViewById(R.id.btnRegisterLogIn);

		loginButton.setOnClickListener(this);
		signUpButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int viewId = v.getId();
		switch (viewId) {
		case R.id.btnLogInLogIn:
			Log.i("MAGICDOOR", "btnLogInLogIn");
			logInModel = new LogIn(this);

			// get userName, password input from view
			userName = etUserName.getText().toString();
			password = etPassword.getText().toString();
			userType = null;

			result = logInModel.doLogIn(
					MagicDoorContants.REST_SERVICE_URI_FOR_LOG_IN, userName,
					password);
			if (result != null) {
				Toast.makeText(this, "Log In Successful", Toast.LENGTH_SHORT)
						.show();

				// get userType from result that is returned from server
				userType = result.userType;
				// save user info to account manager

				// show dialog to ask if user want to log in automatically

				showAutoLogInConfirmationDialog();

			} else {
				Toast.makeText(this, "Try it again", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btnRegisterLogIn:
			Log.i("MAGICDOOR", "btnRegisterLogIn");
			signUp();
			break;
		default:
			break;
		}
	}

	private void showAutoLogInConfirmationDialog() {
		// TODO Auto-generated method stub
		MagicDoorCustomAlertDialogBuilder autoLogIn = new MagicDoorCustomAlertDialogBuilder(
				this);
		autoLogIn.create();
		autoLogIn.setAutoLogInAlterDialog(logInModel, userName, password,
				userType);
		autoLogIn.show();

	}

	private void signUp() {
		// TODO Auto-generated method stub
		Intent nextActivity = new Intent(this, SignUpActivity.class);
		nextActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(nextActivity);
	}
}
