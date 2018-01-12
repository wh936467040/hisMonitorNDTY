package model;

public class SessionBean {
	private String appName;
	private String user;
	private String host;
	private int sessionCount;
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getSessionCount() {
		return sessionCount;
	}
	public void setSessionCount(int sessionCount) {
		this.sessionCount = sessionCount;
	}
	

}
