package com.self.service.logging.monitor;

import java.io.IOException;
import java.util.Properties;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;

import org.quartz.impl.StdSchedulerFactory;

import static com.self.service.logging.impl.PropertyFiles.*;

import com.google.common.base.Optional;
import com.self.service.util.common.PropertyLoaderUtil;

public class LogMonitorService {

	private Optional<Scheduler> scheduler = Optional.absent();
	private boolean jobCreated = false;
	
	private String cronSchedule = CRON_DEFAULT_SCHEDULE;
	private final String CLASS_NAME="com.self.service.logging.monitor.LogMonitorService";

	private final static class Singleton{
		public final static LogMonitorService instance = new LogMonitorService();
	}
	
	public static LogMonitorService getInstance(){
		return Singleton.instance;
	}
	
	private LogMonitorService() {
		Scheduler scheduleObj = null;

		try {
			scheduleObj = StdSchedulerFactory.getDefaultScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
		try {
			Properties prop = new Properties();
			new PropertyLoaderUtil().loadProperty(prop, CLASS_NAME, LOGFILE);
			cronSchedule = prop.getProperty(CRON_SCHEDULE_RUN,CRON_DEFAULT_SCHEDULE);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

		this.scheduler = Optional.fromNullable(scheduleObj);
	}

	public boolean isScheduleStarted() {

		boolean started = false;

		if (scheduler.isPresent()) {
			try {
				started = scheduler.get().isStarted()
						&& scheduler.get().isShutdown() == false;
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}

		return started;
	}

	public void startScheduler() {
		try {
			if (this.scheduler.isPresent() && isScheduleStarted() == false) {
				scheduler.get().start();

				if (jobCreated == false) {
					createLogScheduler();
					jobCreated = true;
				}
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public void stopScheduler() {
		try {
			if (this.scheduler.isPresent() && scheduler.get().isStarted())
				scheduler.get().shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	private void createLogScheduler() {

		if (scheduler.isPresent() == false)
			return;

		JobDetail job = newJob(LogMonitorJob.class).withIdentity("logJob")
				.build();

		
		// Trigger the job to run now, and then repeat every 40 seconds
		Trigger trigger = newTrigger()
				.withIdentity("logTrigger")
				.startNow()
				.withSchedule(
						CronScheduleBuilder.cronSchedule(cronSchedule)
						).build();

		// Tell quartz to schedule the job using our trigger
		try {
			scheduler.get().scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	
	public void increaseWarningCounter(){
		LogStatus.getInstance().incrWarningCount();
	}
	
	public void increaseErrorCounter(){
		LogStatus.getInstance().incrErrorCount();
	}
	
	public void logErrorMessage(String logMessage){
		//only send 1 log message.
		if(LogStatus.getInstance().isToSendError() == false){
			LogStatus.getInstance().setFirstError(logMessage);
			LogStatus.getInstance().setToSendError(true);
		}
	}
	
	public boolean getIsError(){
		return LogStatus.getInstance().isToSendError();
	}
	
	public void flushMessages(){
		LogStatus.getInstance().flushMessages();
	}
	
	public String getMessageCounts(){
		LogStatus logStatus = LogStatus.getInstance();
		return String.format("Warn:%s,Error:%s", logStatus.getWarningCount(), logStatus.getErrorCount());
	}
}