package model;

import java.sql.ResultSet;

import dbconnection.InformixConnection;

public class MemSelect {
	private InformixConnection conn;

	private String sql = "select sum(lru_nmod) used_buf,sum(lru_nfree) free_buf,sum(lru_nfree+lru_nmod) buf_total "
			+ "	from syslrus where bpoolindx=7;";

	public MemSelect(InformixConnection conn) {
		super();
		this.conn = conn;
	}
	
	public MemBean select() {
		MemBean bean= new MemBean();
		System.out.println(sql);
		ResultSet rs = conn.ExecuteQuery(sql);
		int bufSize = 16; 
		if (null == rs) {
			return null;
		} else {
			try {
				while (rs.next()) {
					bean.setUsedMem(rs.getInt(1)*bufSize);
					bean.setFreeMem(rs.getInt(2)*bufSize);
					bean.setTotalMem(rs.getInt(3)*bufSize);
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
