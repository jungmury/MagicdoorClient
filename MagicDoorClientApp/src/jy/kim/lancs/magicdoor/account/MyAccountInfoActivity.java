package jy.kim.lancs.magicdoor.account;

import jy.kim.lancs.magicdoor.bean.LogInInfoRespBean;
import jy.kim.lancs.magicdoor.bean.UserAccountInfoBean;
import jy.kim.lancs.magicdoor.bean.parcelable.UserAccountInfoParcelableBean;
import jy.kim.lancs.magicdoor.main.StudentMainFragmentActivity;
import jy.kim.lancs.magicdoor.util.MagicDoorContants;
import magicdoor.lancs.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

//using static MagicDoorConstants
public class MyAccountInfoActivity extends Activity implements OnClickListener,
		OnFocusChangeListener {

	private boolean IS_ID_DUPLICATED = true;
	private boolean IS_PASSWORD_MATCHED = false;
	private boolean IS_PASSWORD_MORE_THAN_SIX_DIGITS = false;
	private boolean IS_FIRST_NAME_INSERTED = false;
	private boolean IS_LAST_NAME_INSERTED = false;
	private boolean IS_EMAIL_ADDR_INSERTED = false;

	private TextView tvUserName;
	private EditText etPassword1;
	private EditText etPassword2;
	private EditText etFirstName;
	private EditText etLastName;
	private EditText etEMailAddr;

	private RadioGroup rgUserType;
	private RadioButton rbLecturer;
	private RadioButton rbStudent;

	private Button btnUpdate;

	private AccountInfo myAccInfoModel;
	private String userName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		etPassword1.setOnFocusChangeListener(this);
		etPassword2.setOnFocusChangeListener(this);
		etFirstName.setOnFocusChangeListener(this);
		etLastName.setOnFocusChangeListener(this);
		etEMailAddr.setOnFocusChangeListener(this);

		rgUserType.setOnFocusChangeListener(this);
		rbLecturer.setOnFocusChangeListener(this);
		rbStudent.setOnFocusChangeListener(this);

		btnUpdate.setOnClickListener(this);
		rbLecturer.setOnClickListener(this);
		rbStudent.setOnClickListener(this);

	}

	private void initView() {
		// TODO Auto-generated method stub

		setContentView(R.layout.view_myaccountinfo);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		Log.i("MagicDoor", "MyAccountInfoActivity");
		tvUserName = (TextView) findViewById(R.id.tvUsernameMyAcc);
		etPassword1 = (EditText) findViewById(R.id.etPassword1MyAcc);
		etPassword2 = (EditText) findViewById(R.id.etPassword2MyAcc);
		etFirstName = (EditText) findViewById(R.id.etFirstNameMyAcc);
		etLastName = (EditText) findViewById(R.id.etLastNameMyAcc);
		etEMailAddr = (EditText) findViewById(R.id.etEmailAddressMyAcc);

		btnUpdate = (Button) findViewById(R.id.btnUpdateMyAcc);
		//btnUpdate.setEnabled(false);

		rgUserType = (RadioGroup) findViewById(R.id.rgUserTypeMyAcc);
		rbLecturer = (RadioButton) findViewById(R.id.rbLecturerMyAcc);
		rbStudent = (RadioButton) findViewById(R.id.rbStudentMyAcc);

		// capitalize the first letter
		etFirstName.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
		etLastName.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

		Intent prevActivity = getIntent();
		UserAccountInfoParcelableBean accInfo = prevActivity
				.getParcelableExtra("userAccountInfo");

		tvUserName.setText(accInfo.userName);
		etPassword1.setText(accInfo.password1);
		etPassword2.setText(accInfo.password2);
		etFirstName.setText(accInfo.firstName);
		etLastName.setText(accInfo.lastName);
		etEMailAddr.setText(accInfo.eMailAddress);

		userName = accInfo.userName;

		if (accInfo.userType.equals("lecturer")) {
			rbLecturer.setChecked(true);
		} else if (accInfo.userType.equals("student")) {
			rbStudent.setChecked(true);
		}
		rbLecturer.setEnabled(false);
		rbStudent.setEnabled(false);

		myAccInfoModel = new AccountInfo(this);
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.etPassword1MyAcc:
			if (!hasFocus
					&& myAccInfoModel.isPasswordMoreThanSixDigits(etPassword1
							.getText().toString())) {
				IS_PASSWORD_MORE_THAN_SIX_DIGITS = true;
				if ((etPassword2.length() > 0)
						&& myAccInfoModel.isPasswordMatched(etPassword1
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
			enableUpdateButton();
			break;
		case R.id.etPassword2MyAcc:
			if (!hasFocus
					&& myAccInfoModel.isPasswordMoreThanSixDigits(etPassword1
							.getText().toString())
					&& myAccInfoModel.isPasswordMoreThanSixDigits(etPassword2
							.getText().toString())) {
				IS_PASSWORD_MORE_THAN_SIX_DIGITS = true;
				if (myAccInfoModel.isPasswordMatched(etPassword1.getText()
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
			enableUpdateButton();
			break;
		case R.id.etFirstNameMyAcc:
			if (!hasFocus
					&& myAccInfoModel.isValueInserted(etFirstName.getText()
							.toString())) {
				IS_FIRST_NAME_INSERTED = true;
			} else {
				Toast.makeText(this, "Please type information",
						Toast.LENGTH_SHORT).show();
			}
			enableUpdateButton();
			break;
		case R.id.etLastNameMyAcc:
			if (!hasFocus
					&& myAccInfoModel.isValueInserted(etLastName.getText()
							.toString())) {
				IS_LAST_NAME_INSERTED = true;
			} else {
				Toast.makeText(this, "Please type information",
						Toast.LENGTH_SHORT).show();
			}
			enableUpdateButton();
			break;
		case R.id.etEmailAddressMyAcc:
			if (!hasFocus
					&& myAccInfoModel.isValueInserted(etEMailAddr.getText()
							.toString())) {
				IS_EMAIL_ADDR_INSERTED = true;
			} else {
				Toast.makeText(this, "Please type information",
						Toast.LENGTH_SHORT).show();
			}
			enableUpdateButton();
			break;
		default:
			break;
		}
	}

	private void enableUpdateButton() {
		// TODO Auto-generated method stub
		if (!IS_ID_DUPLICATED && IS_PASSWORD_MORE_THAN_SIX_DIGITS
				&& IS_PASSWORD_MATCHED && IS_FIRST_NAME_INSERTED
				&& IS_LAST_NAME_INSERTED && IS_EMAIL_ADDR_INSERTED) {
			btnUpdate.setEnabled(true);
		} else {
			btnUpdate.setEnabled(false);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		LogInInfoRespBean result = null;
		switch (id) {
		case R.id.btnUpdateMyAcc:
			if (myAccInfoModel.isPasswordMoreThanSixDigits(etPassword1
					.getText().toString())
					&& myAccInfoModel.isPasswordMoreThanSixDigits(etPassword2
							.getText().toString())
					&& myAccInfoModel.isPasswordMatched(etPassword1.getText()
							.toString(), etPassword2.getText().toString())
					&& myAccInfoModel.isValueInserted(etFirstName.getText()
							.toString())
					&& myAccInfoModel.isValueInserted(etLastName.getText()
							.toString())
					&& myAccInfoModel.isValueInserted(etEMailAddr.getText()
							.toString())) {
				UserAccountInfoBean signUpUserInfo = createSignUpInfo();
				result = myAccInfoModel.updateAccInfo(signUpUserInfo);

				if (result != null) {
					Toast.makeText(getApplicationContext(),
							"Account Info Update Succeeded", Toast.LENGTH_SHORT)
							.show();
					myAccInfoModel.updateAccountManagerInfo(etPassword2
							.getText().toString());
					// finish current activity after updating user info
					Intent intent = null;
					if (this.getIntent().getStringExtra("prevActivity")
							.equals("StudentMyAccountActivity")) {
						intent = new Intent(this,
								StudentMyAccountActivity.class);
					}
					intent.putExtra("userName", userName);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);

				} else {
					Toast.makeText(getApplicationContext(), "Try it again",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;

		default:
			btnUpdate.setEnabled(false);
			
			break;
		}

	}

	private UserAccountInfoBean createSignUpInfo() {
		UserAccountInfoBean info = new UserAccountInfoBean();
		info.userName = tvUserName.getText().toString();
		info.password = etPassword1.getText().toString();
		info.firstName = etFirstName.getText().toString();
		info.lastName = etLastName.getText().toString();
		info.eMailAddress = etEMailAddr.getText().toString();
		info.uri = MagicDoorContants.REST_SERVICE_URI_FOR_UPDATE_ACC_INFO;
		return info;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		Intent intent;
		switch (id) {

		default:
			if (this.getIntent().getStringExtra("prevActivity")
					.equals("LecturerMyAccountActivity")) {
				intent = new Intent(this, LecturerMyAccountActivity.class);
			} else {
				intent = new Intent(this, StudentMainFragmentActivity.class);
			}
			intent.putExtra("userName", userName);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);

			break;
		}
		Log.i("MagicDoor", "onOptionsItemSelected");
		return true;
	}

}
