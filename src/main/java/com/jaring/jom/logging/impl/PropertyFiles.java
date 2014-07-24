package com.jaring.jom.logging.impl;

public interface PropertyFiles {
	
	public final static String LOGFILE = "monitorlog.properties"; 
	
	//for log files
	public final static String CRON_SCHEDULE_RUN="log.cron.schedule.expr";
	public final static String ENABLE_LOG = "log.enable";
	
	//for scheduler, update for performance.
	public final static String CRON_DEFAULT_SCHEDULE = "* 0 0 * * ?";
	public final static String LOG_POOL_QUEUE_NAME = "log";
}


