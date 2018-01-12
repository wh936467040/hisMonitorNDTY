

import java.util.LinkedList;

import sendInfo.SendInfo;
import model.*;
import myConf.MyConf;
import myUtil.myUtil;
import dbconnection.DmConnection;
import dbconnection.DmWrite;
import dbconnection.InformixConnection;

public class RunClass  extends Thread{
	
	
	public  void run()  {
		
		//String path = "C:\\Users\\WH\\Desktop\\hisMonitor.conf";
		String path = System.getenv("D5000_HOME");
		path = path + "/conf/hisMonitor.conf";
		MyConf conf = new MyConf();
		conf.read(path);
		SendInfo  sendInfo = new SendInfo(conf.getIP(),conf.getPort(),conf.getNodeId());
		
		System.out.print("2222");
		InformixConnection informixConn = new InformixConnection(conf.getDbUrl(),conf.getDbUser(),conf.getDbPassword());
		informixConn.initJdbcConnection();
		
		
		
		DbIOSelect dbIOReader = new DbIOSelect(informixConn);
		MemSelect memReader = new MemSelect(informixConn);
		StatSelect StatReader = new StatSelect(informixConn);	
		TablespaceSelect tablespaceReader = new TablespaceSelect(informixConn);
		DbBufSelect dbBufReader = new DbBufSelect(informixConn);
		DbLockSelect dbLockReader = new DbLockSelect(informixConn);
		SessionSelect sessionReader = new SessionSelect(informixConn);
		try {	
			MemBean memData= memReader.select();
			DbIOBean ioData= dbIOReader.select();
			StatBean statData = StatReader.select();
			DbBufBean bufData = dbBufReader.select();
			
			LinkedList<TablespaceBean> tablespaceDataList = tablespaceReader.select();
			LinkedList<SessionBean> sessionDataList = sessionReader.select();
			//LinkedList<DbLockBean> lockDataList = dbLockReader.select();
			String time = myUtil.getTime();
			
			sendInfo.sendMemInfo(memData, time);
			sendInfo.SendIoInfo(ioData, time);
			sendInfo.SendStatInfo(statData, time);
			sendInfo.SendDbBufInfo(bufData, time);
			sendInfo.SendTableSpaceInfo(tablespaceDataList, time);
			sendInfo.SendSessionInfo(sessionDataList, time);	
		} catch(Exception e) {
			e.printStackTrace();
		}
	informixConn.destoryConnection();
	}
}
