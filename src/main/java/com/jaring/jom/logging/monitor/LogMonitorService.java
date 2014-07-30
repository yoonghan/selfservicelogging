package com.jaring.jom.logging.monitor;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;

import org.quartz.impl.StdSchedulerFactory;

import static com.jaring.jom.logging.impl.PropertyFiles.*;

import com.google.common.base.Optional;
import com.jaring.jom.util.common.PropertyLoaderUtil;

public class LogMonitorService {

	private Optional<Scheduler> scheduler = Optional.absent();
	private boolean jobCreated = false;
	private boolean jobStarted = false;
	private final JobKey JOB_KEY = new JobKey("logJob");
	
	private String cronSchedule = CRON_DEFAULT_SCHEDULE;

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
		
		LogMonitorBean logMonBean = new LogMonitorBean();
		try{
			PropertyLoaderUtil.loadProperty(LOGFILE, logMonBean);
			String cronSchedule = logMonBean.getCronScheduler();
			if(cronSchedule != null && cronSchedule.isEmpty() == false)
				this.cronSchedule = cronSchedule;
		}catch(Exception e){
			System.err.println("Property loaded with error:"+e.getMessage());
			e.printStackTrace();
		}

		this.scheduler = Optional.fromNullable(scheduleObj);
	}

	public boolean isScheduleStarted() {

		boolean started = false;

		if (scheduler.isPresent()) {
			try {
				started = scheduler.get().isStarted()
						&& scheduler.get().isShutdown() == false
						&& jobStarted;
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}

		return started;
	}

	public void startScheduler() {
		try {
			if (this.scheduler.isPresent()){
				if (isScheduleStarted() == false && jobCreated==false) {
					createLogScheduler();
					jobCreated = true;
					scheduler.get().start();
				}else{
					scheduler.get().resumeJob(JOB_KEY);
				}
				jobStarted = true;
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Once shutdown it cannot be recreated.
	 */
	public void shutdownScheduler() {
		try {
			if (this.scheduler.isPresent() && scheduler.get().isStarted())
				scheduler.get().shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public void stopScheduler() {
		try {
			if (this.scheduler.isPresent() && scheduler.get().isStarted()){
				scheduler.get().pauseJob(JOB_KEY);
				jobStarted = false;
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	private void createLogScheduler() {

		if (scheduler.isPresent() == false)
			return;

		JobDetail job = newJob(LogMonitorJob.class).withIdentity(JOB_KEY)
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
