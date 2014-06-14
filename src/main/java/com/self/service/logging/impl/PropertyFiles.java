package com.self.service.logging.impl;

public interface PropertyFiles {
	
	public final static String LOGFILE = "monitorlog.properties"; 
	
	public final static String ENABLED = "1";
	
	//for log files
	public final static String CRON_SCHEDULE_RUN="log.cron.schedule.expr";
	public final static String ENABLE_MESSAGE_SENDING = "log.msg.send.enable";
	public final static String ENABLE_LOG_INFO = "log.info.enable";
	public final static String ENABLE_LOG_WARN = "log.warn.enable";
	public final static String ENABLE_LOG_ERROR = "log.error.enable";
	
	//for scheduler, update for performance.
	public final static String CRON_DEFAULT_SCHEDULE = "* 0 0 * * ?";
	public final static String LOG_POOL_QUEUE_NAME = "log";
}


