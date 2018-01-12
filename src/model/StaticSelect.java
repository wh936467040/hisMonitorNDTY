package model;

import java.sql.ResultSet;

import dbconnection.InformixConnection;

public class StaticSelect {
	private InformixConnection conn;

	private String sql = "select sh_ovlmaxcons from informix.sysshmvals";

	public StaticSelect(InformixConnection conn) {
		super();
		this.conn = conn;
	}
	
	public StaticBean select() {
		StaticBean bean= new StaticBean();
		System.out.println(sql);
		ResultSet rs = conn.ExecuteQuery(sql);
		if (null == rs) {
			return null;
		} else {
			try {
				while (rs.next()) {
					bean.setMaxSessionCount(rs.getInt(1));
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
