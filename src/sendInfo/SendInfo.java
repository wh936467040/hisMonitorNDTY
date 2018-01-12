package sendInfo;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import model.*;


public class SendInfo {
	private String IP;
	private int port;
	private String nodeId;
	
	public SendInfo(String IP,int port,long nodeId){
		this.IP =IP;
		this.port = port;
		this.nodeId = String.valueOf(nodeId);
	}
	
	public int SendIoInfo(DbIOBean bean,String time) {
		List<String> list = new ArrayList<>();
		list.add("0");
		list.add("0");
		list.add(String.valueOf(bean.getDiskRead()));
		list.add(String.valueOf(bean.getDiskWrite()));
		sendXml("10011", time, this.nodeId, this.IP,this.port,list);
		return 1;
	}
	
	public int sendMemInfo(MemBean bean, String time) {
		List<String> list = new ArrayList<>();
		list.add(String.valueOf(bean.getTotalMem()));
		list.add(String.valueOf(bean.getUsedMem()));
		list.add("0");
		list.add("0");
		list.add("0");
		sendXml("10012", time, this.nodeId, this.IP,this.port,list);
		return 1;
	}
	
	public int SendSessionInfo(List<SessionBean> sessionList,String time) {
	
		String APP_NAME = " ";
		String USER_IP = " ";
		String USER = " ";
		String SESSION_COUNT = "";
		
		int i =0;
		for(i=0 ; i < sessionList.size() ;i++) {
			List<String> list = new ArrayList<>();
			APP_NAME = sessionList.get(i).getAppName() ;
			if(APP_NAME.length() <=1 ) {
				APP_NAME = "unkown";
			}
			
			USER_IP = sessionList.get(i).getHost() ;
			if(USER_IP.length() <= 1) {
				USER_IP = "unkown";
			}
			
			USER = sessionList.get(i).getUser();
			if(USER.length() <=1 ){
				USER = "unkown";
			}
			SESSION_COUNT =  String.valueOf(sessionList.get(i).getSessionCount());
			list.add(APP_NAME);
			list.add(USER_IP);
			list.add(USER);
			list.add(SESSION_COUNT);
			sendXml("10013",time, this.nodeId, this.IP,this.port,list);
			try{
				Thread.sleep(2*1000);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		

		

		
		return 1;
	}
	
	public int SendStatInfo(StatBean bean,String time) {
		List<String> list = new ArrayList<>();
		list.add(String.valueOf(bean.getSessionCount()));
		list.add("0");
		list.add(bean.getStartTime());
		sendXml("10015", time, this.nodeId, this.IP,this.port,list);
		return 1;
	}
	
	public int SendDbBufInfo(DbBufBean bean,String time) {
		List<String> list = new ArrayList<>();
		list.add(String.valueOf(bean.getBuffWaitRate()));
		list.add(String.valueOf(bean.getBuffReadRate()));
		list.add(String.valueOf(bean.getBuffWriteRate()));
		sendXml("10051", time, this.nodeId, this.IP,this.port,list);
		return 1;
	}
	
	public int SendDbStaticInfo(StaticBean bean,String time){
		// input staticInfoBean
		List<String> list = new ArrayList<>();
		String db ="informix";
		String dbPort = "9088";
		String sqlFormat = "delete TB_DB_CONST_STAT  where nodeid = %s ;insert into "
				+ "TB_DB_CONST_STAT (nodeid,version,max_session_count,port) "
				+ "values (%s,'%s','%s',%s);";
		String sql = String.format(sqlFormat,this.nodeId, this.nodeId,db,String.valueOf(bean.getMaxSessionCount()),dbPort);
		list.add(sql);
		System.out.println(sql);
		sendXml("5000", time, this.nodeId, this.IP,this.port,list);
		return 1;
	}

	public int SendTableSpaceInfo(List<TablespaceBean> tpList,String time) {
		String dbname = "null";
		
		String TP_ID = " ";
		String TP_NAME =" ";
		String GROUP_ID = " ";
		String GROUP_NAME = " ";
		String FILE_ID = " ";
		String FILE_PATH =" ";
		String FILE_NUM = " ";
		String TOTAL_BYTES = " ";
		String FREE_BYTES = " ";
		String USE_RATIO = " ";

		int i =0;
		for(i=0 ; i < tpList.size() - 1;i++) {
			TP_ID = TP_ID + "null" + ";";
			TP_NAME =TP_NAME + tpList.get(i).getTableName()+ ";";
			GROUP_ID = GROUP_ID + "null" + ";";
			GROUP_NAME = GROUP_NAME + "null" + ";";
			FILE_ID = FILE_ID + "null" + ";";
			FILE_PATH = FILE_PATH + "null" + ";";
			FILE_NUM = FILE_NUM + "null" + ";";
			TOTAL_BYTES = TOTAL_BYTES + String.valueOf(tpList.get(i).getTotalSize()) + ";" ;
			FREE_BYTES = FREE_BYTES +  String.valueOf(tpList.get(i).getUsedSize()) + ";" ;
			
			double use = 0;
			String userRatioStr = "";
			if(tpList.get(i).getTotalSize() >1 ) {
				use = tpList.get(i).getUsedSize()*1.0/tpList.get(i).getTotalSize();
				userRatioStr = String.format("%.2f",use);
			} 
			USE_RATIO = USE_RATIO + userRatioStr + ";";	
		}
		
		if(TP_NAME.length() >=1) {
			TP_ID = TP_ID + "null" + ";";
			TP_NAME =TP_NAME + tpList.get(i).getTableName()+ ";";
			GROUP_ID = GROUP_ID + "null" + ";";
			GROUP_NAME = GROUP_NAME + "null" + ";";
			FILE_ID = FILE_ID + "null" + ";";
			FILE_PATH = FILE_PATH + "null" + ";";
			FILE_NUM = FILE_NUM + "null" + ";";
			TOTAL_BYTES = TOTAL_BYTES + String.valueOf(tpList.get(i).getTotalSize()) + ";" ;
			FREE_BYTES = FREE_BYTES +  String.valueOf(tpList.get(i).getUsedSize()) + ";" ;
		
			double use = 0;
			String userRatioStr = "null";
			if(tpList.get(i).getTotalSize() >1 ) {
				use = tpList.get(i).getUsedSize()*1.0/tpList.get(i).getTotalSize();
				userRatioStr = String.format("%.2f",use);
			} 
			USE_RATIO = USE_RATIO + userRatioStr + ";";	
		}
		List<String> list = new ArrayList<>();
		list.add(TP_ID);
		list.add(TP_NAME);
		list.add(GROUP_ID);
		list.add(GROUP_NAME);
		list.add(FILE_ID);
		list.add(FILE_PATH);
		list.add(FILE_NUM );
		list.add(TOTAL_BYTES) ;
		list.add(FREE_BYTES) ;
		list.add(USE_RATIO);
		sendXml("10016",time, this.nodeId, this.IP,this.port,list);
		return 1;
	}

	public int sendXml(String type, String time, String nodeid, String ip,int port,List<String> list) {
		try {
			Socket socket = new Socket(ip, port);
			OutputStream outputStream = socket.getOutputStream();

			PrintWriter printWriter = new PrintWriter(outputStream);

			String str = "";
			StringBuilder builder = new StringBuilder();

			builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			builder.append("<type id=\"" + type + "\">");
			builder.append("<nodeid id=\"" + nodeid + "\" />");
			builder.append("<time value=\"" + time + "\" />");
			//builder.append("<ip value=\"" + url + "\" />");
			for(int i=0;i<list.size();i++){
				builder.append("<data value=\"" + list.get(i) + "\" />");
			}
			//builder.append("<runtime value=\"" + runTime + "\" />");
			builder.append("</type>");
			str = builder.toString();
			System.out.println(str);
			printWriter.println(str);
			printWriter.flush();
			socket.shutdownOutput();
			printWriter.close();
			outputStream.close();
			socket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 连接服务器
		return 1;

	}
	


}
