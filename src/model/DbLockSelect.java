package model;

import java.sql.ResultSet;
import java.util.LinkedList;

import dbconnection.InformixConnection;

public class DbLockSelect {
	private InformixConnection conn;

	private String sql = "select dbsname, tabname,hostname,  type, owner, username "
			+ " from  syssessions s, syslocks l "
			+ " where sid  = owner;";

	public DbLockSelect(InformixConnection conn) {
		super();
		this.conn = conn;
	}
	
	public LinkedList<DbLockBean> select() {
		LinkedList<DbLockBean> list= new LinkedList<DbLockBean>();
		System.out.println(sql);
		ResultSet rs = conn.ExecuteQuery(sql);
		System.out.println("select lock success");
		if (rs.equals(null)) {
			return null;
		} else {
			try {
				while (rs.next()) {
					System.out.println("2222222");
					DbLockBean bean = new DbLockBean();
					bean.setDbsName(rs.getString(1).trim());
					bean.setTabName(rs.getString(2).trim());
					bean.setHostName(rs.getString(3).trim());
					//String code = rs.getString(4).trim() ;
					bean.setType(rs.getString(4).trim());
					bean.setOwner(rs.getString(5).trim());;
					bean.setUserName(rs.getString(6).trim());;		
					/*
					String type = null;
					if(code == "B") {
						type = "Byte Lock";
					}else if(code == "IS") {
						type = "Intent Shared Lock";
					}else if(code == "S") {
						type = "Shared Lock";
					}else if(code == "XS") {
						type = "Shared Key Value hekd by a repeatable reader";
					}else if(code == "U") {
						type = "Update Lock";
					}else if(code == "IX") {
						type = "intent exclusive Lock";
					}else if(code == "SIX") {
						type = "Shared intent exclusive Lock ";
					}else if(code == "X") {
						type = "Exclusive Lock";
					}else if(code == "XR") {
						type = "Exclusive Key Value held by a repeatable reader";
					}
					bean.setType(type);
					*/
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
