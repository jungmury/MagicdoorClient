package jy.kim.lancs.magicdoor.bean.parcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class UserAccountInfoParcelableBean implements Parcelable {
	public String userName;
	public String password1;
	public String password2;
	public String firstName;
	public String lastName;
	public String eMailAddress;
	public String userType;

	public UserAccountInfoParcelableBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(userName);
		dest.writeString(password1);
		dest.writeString(password2);
		dest.writeString(firstName);
		dest.writeString(lastName);
		dest.writeString(eMailAddress);
		dest.writeString(userType);
	}

	// this is used to regenerate your object. All Parcelables must have a
	// CREATOR that implements these two methods
	public static final Parcelable.Creator<UserAccountInfoParcelableBean> CREATOR = new Parcelable.Creator<UserAccountInfoParcelableBean>() {
		public UserAccountInfoParcelableBean createFromParcel(Parcel in) {
			return new UserAccountInfoParcelableBean(in);
		}

		public UserAccountInfoParcelableBean[] newArray(int size) {
			return new UserAccountInfoParcelableBean[size];
		}
	};

	// example constructor that takes a Parcel and gives you an object populated
	// with it's values
	private UserAccountInfoParcelableBean(Parcel in) {
		userName = in.readString();
		password1 = in.readString();
		password2 = in.readString();
		firstName = in.readString();
		lastName = in.readString();
		eMailAddress = in.readString();
		userType = in.readString();
	}
}
