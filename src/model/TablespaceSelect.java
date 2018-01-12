package model;

import java.sql.ResultSet;
import java.util.LinkedList;

import dbconnection.InformixConnection;

public class TablespaceSelect {
	private InformixConnection conn;

	private String sql = "select trim(t1.dbsname),sum(ti_nptotal)*max(ti_pagesize)/1024.0/1024.0 allocated_size,"
			+ "sum(ti_npused)*max(ti_pagesize)/1024.0/1024.0 used_size "
			+ "from systabnames t1,systabinfo t2,sysdatabases t3 where t1.partnum =t2.ti_partnum and trim(t3.name) = trim(t1.dbsname) "
			+ "group by dbsname order by sum(ti_nptotal) desc;";

	public TablespaceSelect(InformixConnection conn) {
		super();
		this.conn = conn;
	}
	
	public LinkedList<TablespaceBean> select() {
		LinkedList<TablespaceBean> list= new LinkedList<TablespaceBean>();
		System.out.println(sql);
		ResultSet rs = conn.ExecuteQuery(sql);
		if (null == rs) {
			return null;
		} else {
			try {
				while (rs.next()) {
					TablespaceBean bean = new TablespaceBean();
					bean.setTableName(rs.getString(1));
					bean.setTotalSize(rs.getLong(2));
					bean.setUsedSize(rs.getLong(3));
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
