package com.jaring.jom.logging.log;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import com.jaring.jom.logging.impl.Log;
import com.jaring.jom.logging.impl.PropertyFiles;
import com.jaring.jom.util.common.PropertyLoaderUtil;

public class LogFactory {
	
	private final static int LOG_SIZE = 20;
	
	private final static ConcurrentHashMap<String, Log> setOfLogs = new ConcurrentHashMap<String,Log>(LOG_SIZE);
	private static boolean enableError = true;
	private static boolean enableInfo = true;
	private static boolean enableWarn = true;
	private static boolean enableMsgSend = true;
	
	private static void initLogs() {
		LogBean logBean = new LogBean();
		try {
			new PropertyLoaderUtil().loadProperty(PropertyFiles.LOGFILE, logBean);

			enableError = logBean.isErrorEnabled();
			enableInfo = logBean.isInfoEnabled();
			enableWarn = logBean.isWarningEnabled();
			enableMsgSend = logBean.isEmailSendEnabled();
		} catch (ClassNotFoundException | IllegalAccessException | IOException e) {
			//e.printStackTrace();
		}
	}

	static{
		initLogs();
	}
	
	public static Log getLogger(String className){
		
		Log log = setOfLogs.get(className);
		
		if(log == null){
			log = createLog(className);
		}
		
		return log;
	}
	
	private synchronized static LogUtil createLog(String className){
		LogUtil logUtil = new LogUtil(className,
				enableMsgSend, enableInfo, enableWarn, enableError);
		
		setOfLogs.put(className, logUtil);
		
		return logUtil;
	}
}
