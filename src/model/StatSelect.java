package model;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import dbconnection.InformixConnection;

public class StatSelect {
	private InformixConnection conn;

	private String sql1 = "select count(*) c from syssessions";
	private String sql2 = "select sh_boottime start_time from sysshmvals";

	public StatSelect(InformixConnection conn) {
		super();
		this.conn = conn;
	}
	
	public StatBean select() {
		StatBean bean= new StatBean();
		System.out.println(sql1);
		ResultSet rs1 = conn.ExecuteQuery(sql1);
		if (null == rs1) {
			return null;
		} else {
			try {
				while (rs1.next()) {
					bean.setSessionCount(rs1.getInt(1));
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}
		ResultSet rs2 = conn.ExecuteQuery(sql2);
		System.out.println(sql2);
		if (null == rs2) {
			return null;
		} else {
			try {
				while (rs2.next()) {
					long a = (rs2.getLong(1));
					a = a*1000;
					Date date = new Date(a);
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String time = formatter.format(date);
					System.out.println(time);
					bean.setStartTime(time);
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
