package myConf;

import java.io.BufferedReader;
import java.io.FileReader;

public class MyConf {
	private long nodeId = 0L;
	private String IP;
	private int port;
	
	private String dbUrl;
	private String dbUser;
	private String dbPassword;

	public int read(String path) {
		//String path = getHomePath() + "/Desktop/conf.txt";
		System.out.println(path);
		try {
			// read file content from file
			FileReader reader = new FileReader(path);
			BufferedReader br = new BufferedReader(reader);

			String str = null;

			while ((str = br.readLine()) != null) {
				if(str.indexOf("id:") >= 0 || str.indexOf("ID:") > 0){
					str = str.replace("id:", "");
					str = str.trim();
					System.out.println(str);
					this.nodeId = Long.parseLong(str);
				} else if(str.indexOf("serverip:") >= 0 || str.indexOf("SERVERIP:") > 0){
					str = str.replace("serverip:", "");
					str = str.trim();
					System.out.println(str);
					this.IP = str;
				} else if(str.indexOf("serverport:") >= 0 || str.indexOf("SERVERIP:") > 0){
					str = str.replace("serverport:", "");
					str = str.trim();
					System.out.println(str);
					this.port = Integer.parseInt(str);
				} else if(str.indexOf("dburl:") >= 0 || str.indexOf("DBURL:") > 0) {
					str = str.replace("dburl:", "");
					str = str.trim();
					System.out.println(str);
					this.dbUrl = str;
				} else if(str.indexOf("dbuser:") >= 0 || str.indexOf("DBUSER:") > 0) {
					str = str.replace("dbuser:", "");
					str = str.trim();
					System.out.println(str);
					this.dbUser = str;
				} else if(str.indexOf("dbpassword:") >= 0 || str.indexOf("dbpassword:") > 0) {
					str = str.replace("dbpassword:", "");
					str = str.trim();
					System.out.println(str);
					this.dbPassword = str;
				}
			}
			br.close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public long getNodeId() {
		return nodeId;
	}

	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getDbUser() {
		return dbUser;
	}

	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	public String getDbPassword() {
		return dbPassword;
	}

	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	
}
