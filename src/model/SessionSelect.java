package model;

import java.sql.ResultSet;
import java.util.LinkedList;

import dbconnection.InformixConnection;

public class SessionSelect {
	private InformixConnection conn;

	private String sql = "select count(progname), username,hostname,progname from ("
			+"select x0.username ,x0.uid ,x0.pid ,x0.hostname ,x0.ttyerr ,x0.connected ,"
			+"x0.progname  from sysscblst x0 ,sysrstcb x1 where ((x0.address = x1.scb ) AND (bitval(x1.flags ,'0x80000' )= 1 ) ) "
			+")  group by username,hostname,progname;";

	public SessionSelect(InformixConnection conn) {
		super();
		this.conn = conn;
	}
	
	public LinkedList<SessionBean> select() {
		LinkedList<SessionBean> list= new LinkedList<SessionBean>();
		System.out.println(sql);
		ResultSet rs = conn.ExecuteQuery(sql);
		if (null == rs) {
			return null;
		} else {
			try {
				while (rs.next()) {
					SessionBean bean = new SessionBean();
					bean.setSessionCount(rs.getInt(1));
					bean.setUser(rs.getString(2).trim());
					bean.setHost(rs.getString(3).trim());
					bean.setAppName(rs.getString(4).trim());
					list.add(bean);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return list;
	}
}
