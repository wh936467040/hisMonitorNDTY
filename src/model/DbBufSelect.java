package model;

import java.sql.ResultSet;

import dbconnection.InformixConnection;

public class DbBufSelect {
	private InformixConnection conn;

	private String sql1 = "select (bufwaits/(bufwaits+ pagreads))*100 buff_wait_rate,"
			+ " 100 * (bufreads-dskreads)/ bufreads buff_read_rate,"
			+ " 100 * (bufwrites-dskwrites)/ bufwrites buff_write_rate"
			+ " from sysbufpool where indx =7;";

	public DbBufSelect(InformixConnection conn) {
		super();
		this.conn = conn;
	}
	
	public DbBufBean select() {
		DbBufBean bean= new DbBufBean();
		System.out.println(sql1);
		ResultSet rs = conn.ExecuteQuery(sql1);
		if (null == rs) {
			return null;
		} else {
			try {
				while (rs.next()) {
					bean.setBuffWaitRate(rs.getFloat(1));
					bean.setBuffReadRate(rs.getFloat(2));			
					bean.setBuffWriteRate(rs.getFloat(3));
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
