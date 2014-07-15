package com.self.service.logging.log;

import com.self.service.logging.impl.Log;
import com.self.service.logging.monitor.LogMonitorService;

public class LogUtil implements Log{
	
	private final String CLASS_NAME_LOG;
	
	private final boolean ENABLE_MSG_SEND;
	private final boolean ENABLE_INFO;
	private final boolean ENABLE_WARN;
	private final boolean ENABLE_ERROR;
	
	private static final LogMonitorService logMonService= LogMonitorService.getInstance();
	
	LogUtil(String CLASS_NAME_LOG, boolean enableMsgSend, boolean enableInfo, boolean enableWarn, boolean enableError){
		this.CLASS_NAME_LOG = CLASS_NAME_LOG;
		this.ENABLE_MSG_SEND = enableMsgSend;
		this.ENABLE_INFO = enableInfo;
		this.ENABLE_WARN = enableWarn;
		this.ENABLE_ERROR = enableError;
	}
	
	@Override
	public void error(String error){
		error(error, null);
	}
	
	@Override
	public void error(String error, Exception e){
		if(ENABLE_ERROR){
			String msgSend = String.format("%s : %s\n%s",
					CLASS_NAME_LOG,
					error,
					e==null?"":e.toString());
			
			System.err.println(msgSend);
			logMonService.increaseErrorCounter();
			
			if(ENABLE_MSG_SEND){
				logMonService.logErrorMessage(msgSend);
			}
		}
	}
	
	@Override	
	public void info(String info){
		if(ENABLE_INFO){
			System.out.println(String.format("%s : %s",CLASS_NAME_LOG,info));
		}
	}
	
	@Override
	public void warn(String warn){
		if(ENABLE_WARN){
			System.out.println(String.format("WARNING: %s : %s",CLASS_NAME_LOG,warn));
			logMonService.increaseWarningCounter();
		}
	}
}
