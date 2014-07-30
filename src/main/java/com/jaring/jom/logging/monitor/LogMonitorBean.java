package com.jaring.jom.logging.monitor;

import static com.jaring.jom.logging.impl.PropertyFiles.CRON_DEFAULT_SCHEDULE;
import static com.jaring.jom.logging.impl.PropertyFiles.CRON_SCHEDULE_RUN;

import java.util.Properties;

import com.jaring.jom.util.bean.PropertyBeanMapper;

class LogMonitorBean implements PropertyBeanMapper {

	private String cronScheduler; 
	
	@Override
	public void map(Properties property) {
		cronScheduler = property.getProperty(CRON_SCHEDULE_RUN,CRON_DEFAULT_SCHEDULE);
	}
	
	public String getCronScheduler(){
		return cronScheduler;
	}

}
