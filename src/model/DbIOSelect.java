package model;

import java.sql.ResultSet;

import dbconnection.InformixConnection;

public class DbIOSelect {
	private InformixConnection conn;

	private String sql1 = "select value from informix.sysprofile t where name = 'dskreads';";
	private String sql2 = "select value from informix.sysprofile where name ='dskwrites';";

	public DbIOSelect(InformixConnection conn) {
		super();
		this.conn = conn;
	}
	
	public DbIOBean select() {
		DbIOBean bean= new DbIOBean();
		System.out.println(sql1);
		ResultSet rs1 = conn.ExecuteQuery(sql1);
		if (null == rs1) {
			return null;
		} else {
			try {
				while (rs1.next()) {
					bean.setDiskRead(rs1.getLong(1));
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}
		ResultSet rs2 = conn.ExecuteQuery(sql2);
		if (null == rs2) {
			return null;
		} else {
			try {
				while (rs2.next()) {
					bean.setDiskWrite(rs2.getLong(1));
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}
		return bean;
	}
}
