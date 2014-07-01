package com.self.service.logging.log;

import java.io.IOException;
import java.util.HashMap;

import com.self.service.logging.impl.PropertyFiles;
import com.self.service.logging.monitor.LogMonitorService;
import com.self.service.util.common.PropertyLoaderUtil;

public class LogUtil {
	
	private final static int LOG_SIZE = 200;
	
	private final static HashMap<String, LogUtil> setOfLogs = new HashMap<String,LogUtil>(LOG_SIZE); 
	
	private final String CLASS_NAME_LOG;
	
	private static boolean enableMsgSend = true;
	private static boolean enableInfo = true;
	private static boolean enableWarn = true;
	private static boolean enableError = true;

	static{
		initLogs();
	}
	
	private LogUtil(String CLASS_NAME_LOG){
		this.CLASS_NAME_LOG = CLASS_NAME_LOG;
	}
	
	private static void initLogs() {
		LogBean logBean = new LogBean();
		try {
			new PropertyLoaderUtil().loadProperty(PropertyFiles.LOGFILE, logBean);

			enableError = logBean.isErrorEnabled();
			enableInfo = logBean.isInfoEnabled();
			enableWarn = logBean.isWarningEnabled();
			enableMsgSend = logBean.isEmailSendEnabled();
		} catch (ClassNotFoundException | IllegalAccessException | IOException e) {
			e.printStackTrace();
		}
	}

	private synchronized static LogUtil createLog(String className){
		LogUtil logUtil = new LogUtil(className);
		setOfLogs.put(className, logUtil);
		return logUtil;
	}
	
	public static LogUtil getInstance(String className){
		if(setOfLogs.containsKey(className)){
			return setOfLogs.get(className);
		}else{
			return createLog(className);
		}
	}
	
	public void error(String error){
		error(error, null);
	}
	
	public void error(String error, Exception e){
		if(enableError){
			String msgSend = String.format("%s : %s\n%s",
					CLASS_NAME_LOG,
					error,
					e==null?"":e.toString());
			
			System.err.println(msgSend);
			LogMonitorService.getInstance().increaseErrorCounter();
			
			if(enableMsgSend){
				LogMonitorService.getInstance().logErrorMessage(msgSend);
			}
		}
	}
	
	public void info(String info){
		if(enableInfo){
			System.out.println(String.format("%s : %s",CLASS_NAME_LOG,info));
		}
	}
	
	public void warn(String warn){
		if(enableWarn){
			System.out.println(String.format("WARNING: %s : %s",CLASS_NAME_LOG,warn));
			LogMonitorService.getInstance().increaseWarningCounter();
		}
	}
}
