package jy.kim.lancs.magicdoor.bean;

public class LogInInfoRespBean {
	public String userName;
	public String userType;

	public LogInInfoRespBean() {
	} // To resolve jackson mapper problem

	public String toString() {
		return "loginInfo[userName=" + userName + ", userType=" + userType
				+ "]";
	}
}
