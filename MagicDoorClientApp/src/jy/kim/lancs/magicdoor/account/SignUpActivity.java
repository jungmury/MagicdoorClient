package jy.kim.lancs.magicdoor.account;

import jy.kim.lancs.magicdoor.bean.LogInInfoRespBean;
import jy.kim.lancs.magicdoor.bean.UserAccountInfoBean;
import jy.kim.lancs.magicdoor.dialog.MagicDoorCustomAlertDialogBuilder;
import jy.kim.lancs.magicdoor.login.LogIn;
import jy.kim.lancs.magicdoor.util.MagicDoorContants;
import magicdoor.lancs.R;
import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class SignUpActivity extends Activity implements
		OnCheckedChangeListener, OnClickListener, OnFocusChangeListener {
	private boolean IS_ID_DUPLICATED = true;
	private boolean IS_PASSWORD_MATCHED = false;
	private boolean IS_PASSWORD_MORE_THAN_SIX_DIGITS = false;
	private boolean IS_FIRST_NAME_INSERTED = false;
	private boolean IS_LAST_NAME_INSERTED = false;
	private boolean IS_EMAIL_ADDR_INSERTED = false;

	private EditText etUserName;
	private EditText etPassword1;
	private EditText etPassword2;
	private EditText etFirstName;
	private EditText etLastName;
	private EditText etEMailAddr;

	private RadioGroup rgUserType;
	private RadioButton rbLecturer;
	private RadioButton rbStudent;

	private Button btnRegister;

	private AccountInfo registerModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_sign_up);
		Log.i("MagicDoor", "RegisterActivity");

		etUserName = (EditText) findViewById(R.id.etUsernameRegister);
		etPassword1 = (EditText) findViewById(R.id.etPassword1Register);
		etPassword2 = (EditText) findViewById(R.id.etPassword2Register);
		etFirstName = (EditText) findViewById(R.id.etFirstNameRegister);
		etLastName = (EditText) findViewById(R.id.etLastNameRegister);
		etEMailAddr = (EditText) findViewById(R.id.etEmailAddressRegister);

		btnRegister = (Button) findViewById(R.id.btnRegisterRegister);
		btnRegister.setEnabled(false);

		rgUserType = (RadioGroup) findViewById(R.id.rgUserTypeRegister);
		rbLecturer = (RadioButton) findViewById(R.id.rbLecturerRegister);
		rbStudent = (RadioButton) findViewById(R.id.rbStudentRegister);

		// capitalize the first letter
		etFirstName.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		etLastName.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

		registerModel = new AccountInfo(this);

		etUserName.setOnFocusChangeListener(this);
		etPassword1.setOnFocusChangeListener(this);
		etPassword2.setOnFocusChangeListener(this);
		etFirstName.setOnFocusChangeListener(this);
		etLastName.setOnFocusChangeListener(this);
		etEMailAddr.setOnFocusChangeListener(this);

		rgUserType.setOnFocusChangeListener(this);
		rbLecturer.setOnFocusChangeListener(this);
		rbStudent.setOnFocusChangeListener(this);

		rgUserType.setOnCheckedChangeListener(this);

		btnRegister.setOnClickListener(this);
		rbLecturer.setOnClickListener(this);
		rbStudent.setOnClickListener(this);

	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.etUsernameRegister:
			if (!hasFocus
					&& !registerModel
							.isIDDuplicate(MagicDoorContants.REST_SERVICE_URI_FOR_DUP_ID_CHK,  etUserName.getText().toString())) {
				// check the id if it is duplicated
				IS_ID_DUPLICATED = false;
			} else {
				Toast.makeText(this, "You cannot use the ID",
						Toast.LENGTH_SHORT).show();
			}
			enableRegisterButton();
			break;
		case R.id.etPassword1Register:
			if (!hasFocus
					&& registerModel.isPasswordMoreThanSixDigits(etPassword1
							.getText().toString())) {
				IS_PASSWORD_MORE_THAN_SIX_DIGITS = true;
				if ((etPassword2.length() > 0)
						&& registerModel.isPasswordMatched(etPassword1
								.getText().toString(), etPassword2.getText()
								.toString())) {
					IS_PASSWORD_MATCHED = true;
				} else {
					Toast.makeText(this, "Please check the password",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(this,
						"Password should be more than 6 characters",
						Toast.LENGTH_SHORT).show();
			}
			enableRegisterButton();
			break;
		case R.id.etPassword2Register:
			if (!hasFocus
					&& registerModel.isPasswordMoreThanSixDigits(etPassword1
							.getText().toString())
					&& registerModel.isPasswordMoreThanSixDigits(etPassword2
							.getText().toString())) {
				IS_PASSWORD_MORE_THAN_SIX_DIGITS = true;
				if (registerModel.isPasswordMatched(etPassword1.getText()
						.toString(), etPassword2.getText().toString())) {
					IS_PASSWORD_MATCHED = true;
				} else {
					etPassword2.setNextFocusDownId(R.id.etPassword2Register);
					Toast.makeText(this, "Please check the password",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(this,
						"Password should be more than 6 characters",
						Toast.LENGTH_SHORT).show();
			}
			enableRegisterButton();
			break;
		case R.id.etFirstNameRegister:
			if (!hasFocus
					&& registerModel.isValueInserted(etFirstName.getText()
							.toString())) {
				IS_FIRST_NAME_INSERTED = true;
			} else {
				Toast.makeText(this, "Please type information",
						Toast.LENGTH_SHORT).show();
			}
			enableRegisterButton();
			break;
		case R.id.etLastNameRegister:
			if (!hasFocus
					&& registerModel.isValueInserted(etLastName.getText()
							.toString())) {
				IS_LAST_NAME_INSERTED = true;
			} else {
				Toast.makeText(this, "Please type information",
						Toast.LENGTH_SHORT).show();
			}
			enableRegisterButton();
			break;
		case R.id.etEmailAddressRegister:
			if (!hasFocus
					&& registerModel.isValueInserted(etEMailAddr.getText()
							.toString())) {
				IS_EMAIL_ADDR_INSERTED = true;
			} else {
				Toast.makeText(this, "Please type information",
						Toast.LENGTH_SHORT).show();
			}
			enableRegisterButton();
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.btnRegisterRegister:
			if (!registerModel
					.isIDDuplicate(MagicDoorContants.REST_SERVICE_URI_FOR_DUP_ID_CHK, etUserName.getText().toString())
					&& registerModel.isPasswordMoreThanSixDigits(etPassword1
							.getText().toString())
					&& registerModel.isPasswordMoreThanSixDigits(etPassword2
							.getText().toString())
					&& registerModel.isPasswordMatched(etPassword1.getText()
							.toString(), etPassword2.getText().toString())
					&& registerModel.isValueInserted(etFirstName.getText()
							.toString())
					&& registerModel.isValueInserted(etLastName.getText()
							.toString())
					&& registerModel.isValueInserted(etEMailAddr.getText()
							.toString())) {
				UserAccountInfoBean signUpUserInfo = new UserAccountInfoBean();
				signUpUserInfo.userName = etUserName.getText().toString();
				signUpUserInfo.password = etPassword1.getText().toString();
				signUpUserInfo.firstName = etFirstName.getText().toString();
				signUpUserInfo.lastName = etLastName.getText().toString();
				signUpUserInfo.eMailAddress = etEMailAddr.getText().toString();
				signUpUserInfo.uri = MagicDoorContants.REST_SERVICE_URI_FOR_SIGN_UP;
				if (rbLecturer.isChecked()) {
					signUpUserInfo.userType = "lecturer";
				} else {
					signUpUserInfo.userType = "student";
				}
				registerModel.signUp(signUpUserInfo);
				LogIn logIn = new LogIn(this);
				LogInInfoRespBean result = logIn.doLogIn(
						MagicDoorContants.REST_SERVICE_URI_FOR_LOG_IN, signUpUserInfo.userName,
						signUpUserInfo.password);
				if (result != null) {
					Toast.makeText(this, "Log In Successful", Toast.LENGTH_SHORT)
							.show();

					// get userType from result that is returned from server
			        String userType = result.userType;
					// save user info to account manager

					// show dialog to ask if user want to log in automatically

					showAutoLogInConfirmationDialog(logIn, signUpUserInfo.userName, signUpUserInfo.password, userType);

				} else {
					Toast.makeText(this, "Try it again", Toast.LENGTH_SHORT).show();
				}

			}
			break;
		case R.id.rbLecturerRegister:
			if (registerModel.isValueInserted(etEMailAddr.getText().toString())) {
				IS_EMAIL_ADDR_INSERTED = true;
			}
			enableRegisterButton();
			break;
		case R.id.rbStudentRegister:
			if (registerModel.isValueInserted(etEMailAddr.getText().toString())) {
				IS_EMAIL_ADDR_INSERTED = true;
			}
			enableRegisterButton();
			break;
		default:
			break;
		}

	}
	
	private void showAutoLogInConfirmationDialog(LogIn logIn, String userName, String password, String userType) {
		// TODO Auto-generated method stub
		MagicDoorCustomAlertDialogBuilder autoLogIn = new MagicDoorCustomAlertDialogBuilder(
				this);
		autoLogIn.create();
		autoLogIn.setAutoLogInAlterDialog(logIn, userName, password,
				userType);
		autoLogIn.show();

	}

	// enable register button
	private void enableRegisterButton() {
		if (!IS_ID_DUPLICATED && IS_PASSWORD_MORE_THAN_SIX_DIGITS
				&& IS_PASSWORD_MATCHED && IS_FIRST_NAME_INSERTED
				&& IS_LAST_NAME_INSERTED && IS_EMAIL_ADDR_INSERTED) {
			btnRegister.setEnabled(true);
		} else {
			btnRegister.setEnabled(false);
		}
	}
}
