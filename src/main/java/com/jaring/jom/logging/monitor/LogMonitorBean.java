package com.jaring.jom.logging.monitor;

import static com.jaring.jom.logging.impl.PropertyFiles.CRON_DEFAULT_SCHEDULE;
import static com.jaring.jom.logging.impl.PropertyFiles.CRON_SCHEDULE_RUN;

import java.util.Properties;

import com.jaring.jom.util.impl.PropertyMapperImpl;

class LogMonitorBean implements PropertyMapperImpl {

	private String conScheduler; 
	
	@Override
	public void map(Properties property) throws IllegalAccessException {
		conScheduler = property.getProperty(CRON_SCHEDULE_RUN,CRON_DEFAULT_SCHEDULE);
	}
	
	public String getConScheduler(){
		return conScheduler;
	}

}
