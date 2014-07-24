package com.jaring.jom.logging.monitor;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


/**
 * Created to send Email about server status
 * **/
public class LogMonitorJob implements Job{
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		LogStatus.getInstance().flushMessages();		
	}

	
}
