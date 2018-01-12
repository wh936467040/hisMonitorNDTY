package model;

public class DbLockBean {
	private String dbsName;
	private String tabName;
	private String hostName;
	private String type;
	private String owner;
	private String userName;
	public String getDbsName() {
		return dbsName;
	}
	public void setDbsName(String dbsName) {
		this.dbsName = dbsName;
	}
	public String getTabName() {
		return tabName;
	}
	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
