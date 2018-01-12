

import java.util.LinkedList;

import sendInfo.SendInfo;
import model.*;
import myConf.MyConf;
import myUtil.myUtil;
import dbconnection.DmConnection;
import dbconnection.DmWrite;
import dbconnection.InformixConnection;

public class RunFirstTimeClass {
	
		
	public  void run() throws Exception {
		
		//String path = "C:\\Users\\WH\\Desktop\\hisMonitor.conf";
		
		String path = System.getenv("D5000_HOME");
		path = path + "/conf/hisMonitor.conf";
		
		MyConf conf = new MyConf();
		conf.read(path);
		SendInfo  sendInfo = new SendInfo(conf.getIP(),conf.getPort(),conf.getNodeId());
		
		InformixConnection informixConn = new InformixConnection(conf.getDbUrl(),conf.getDbUser(),conf.getDbPassword());
		informixConn.initJdbcConnection();
		

		StaticSelect staticReader = new StaticSelect(informixConn);
		
		StaticBean staticData = staticReader.select();
		
		sendInfo.SendDbStaticInfo(staticData, myUtil.getTime());
		
		//dmWritor.staticWrite(staticData, myUtil.getTime(),nodeId);
		
		informixConn.destoryConnection();
	}
}
