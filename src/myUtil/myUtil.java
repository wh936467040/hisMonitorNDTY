package myUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class myUtil {
	public static String getToday() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String time = formatter.format(date);
		return time;
	}
	
	@SuppressWarnings("deprecation")
	public static String getTime() {
		Date date = new Date();
		int sec = date.getSeconds();
		if(sec >30) {
			date.setMinutes(date.getMinutes()+1);
			date.setSeconds(0);
		} else {
			date.setSeconds(30);
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = formatter.format(date);
		return time;
		
	}

}
