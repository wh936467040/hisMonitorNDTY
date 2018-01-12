package dbconnection;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ListIterator;

import model.*;
import myUtil.myUtil;


public class DmWrite {

	private DmConnection conn = null;

	public DmWrite(DmConnection conn){
		this.conn = conn;
	}
	
	public void DbIOWrite(DbIOBean bean,String time,String nodeId) throws Exception {
		String sqlFormat = "INSERT INTO TB_DB_IO_STAT_" + myUtil.getToday()
		+ "(read_count,write_count,read_bytes,write_bytes,NODEID,stat_time) values(%s,%s,%s,%s,%s,'%s')";
		String sql = String.format(sqlFormat,"0","0",String.valueOf(bean.getDiskRead()),String.valueOf(bean.getDiskWrite()),nodeId,time);
		System.out.println("sql" + sql);
		conn.ExecuteSql(sql);
	}
	
	
	public void memWrite(MemBean bean,String time,String nodeId)throws Exception {
		String sqlFormat = "INSERT INTO TB_DB_MEM_STAT_" + myUtil.getToday()
		+ " values(%s,%s,%s,%s,%s,%s,'%s')";
		String sql = String.format(sqlFormat,String.valueOf(bean.getTotalMem()),String.valueOf(bean.getUsedMem()),"0","0","0",nodeId,time);
		System.out.println("sql" + sql);
		conn.ExecuteSql(sql);
	}
	
	public void statWrite(StatBean bean,String time,String nodeId)throws Exception {
		String sqlFormat = "INSERT INTO TB_DB_STAT_" + myUtil.getToday()
		+ " values(%s,%s,%s,'%s','%s')";
		String sql = String.format(sqlFormat,String.valueOf(bean.getSessionCount()),"0",nodeId,time,bean.getStartTime());
		System.out.println("sql" + sql);
		conn.ExecuteSql(sql);
	}
	
	public void staticWrite(StaticBean bean,String time,String nodeId)throws Exception {
		String sqlFormat = "INSERT INTO TB_DB_CONST_STAT" 
		+ " values('%s',%s,%s,'%s')";
		String sql = String.format(sqlFormat,"informix",String.valueOf(bean.getMaxSessionCount()),nodeId,"9088");
		System.out.println("sql" + sql);
		conn.ExecuteSql(sql);
	}
	
	public void tablespaceWrite(LinkedList<TablespaceBean> list,
			String time,String nodeId)throws Exception {
		
		System.out.println(list.size());
		String sqlFormat = "INSERT INTO TB_DB_TABLESPACE_STAT_" + myUtil.getToday()
		+ " values(%s,'%s',%s,'%s',%s,'%s','%s',%s,'%s',%s,%s,'%s')";
		ListIterator<TablespaceBean> it = list.listIterator();
		while(it.hasNext()) {
			TablespaceBean item = it.next();
			double useRatio = 0;
			String  userRatioStr = "0";
			if(item.getTotalSize() >1 ) {
				useRatio = item.getUsedSize()*1.0/item.getTotalSize();
				userRatioStr = String.format("%.2f",useRatio);
			} 
				
			double freeSize  = item.getTotalSize() - item.getUsedSize();
			
			String sql = String.format(sqlFormat,"0",item.getTableName(),"0","0","0","0","0",String.valueOf(item.getTotalSize()),String.valueOf(freeSize),userRatioStr,nodeId,time);
			System.out.println("sql" + sql);
			conn.ExecuteSql(sql);
		}		
	}

	public void sessionWrite(LinkedList<SessionBean>list,
			String time, String nodeId) throws SQLException {
		System.out.println(list.size());
		String sqlFormat = "INSERT INTO TB_DB_SESSION_STAT_" + myUtil.getToday()
		+ " values('%s','%s','%s',%s,'%s',%s)";
		ListIterator<SessionBean> it = list.listIterator();
		while(it.hasNext()) {
			SessionBean item = it.next();
			System.out.println(item.getAppName());
			String sql = String.format(sqlFormat,item.getAppName(),item.getHost(),item.getUser(),String.valueOf(item.getSessionCount()),time,nodeId);
			System.out.println("sql" + sql);
			conn.ExecuteSql(sql);
		}	
		
	}
	public void DbBufWrite(DbBufBean bean,
			String time,String nodeId)throws Exception {
		
		String sqlFormat = "INSERT INTO TB_DB_Buf_STAT_" + myUtil.getToday()
		+ " values(%s,%s,%s,%s,'%s')";
		String bufWaitRate = String.format("%.2f", bean.getBuffWaitRate());
		String bufReadRate = String.format("%.2f",bean.getBuffReadRate());
		String bufWriteRate = String.format("%.2f",bean.getBuffWriteRate());
		String sql = String.format(sqlFormat,bufWaitRate,bufReadRate,bufWriteRate,nodeId,time);
		System.out.println("sql" + sql);
		conn.ExecuteSql(sql);
		
	}
	
	public void DbLockWrite(LinkedList<DbLockBean> list,
			String time,String nodeId)throws Exception {
		
		System.out.println(list.size());
		String sqlFormat = "INSERT INTO TB_DB_LOCK_STAT_" + myUtil.getToday()
		+ " values('%s','%s','%s','%s','%s','%s',%s,'%s')";
		ListIterator<DbLockBean> it = list.listIterator();
		while(it.hasNext()) {
			DbLockBean item = it.next();

			String sql = String.format(sqlFormat,item.getDbsName(),item.getTabName(),item.getHostName(),item.getType(),
					item.getOwner(),item.getUserName(),nodeId,time);
			System.out.println("sql" + sql);
			conn.ExecuteSql(sql);
		}		
	}
	

}