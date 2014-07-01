package com.self.service.logging.monitor;

import static com.self.service.logging.impl.PropertyFiles.CRON_DEFAULT_SCHEDULE;
import static com.self.service.logging.impl.PropertyFiles.CRON_SCHEDULE_RUN;

import java.util.Properties;

import com.self.service.util.impl.PropertyMapperImpl;

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
