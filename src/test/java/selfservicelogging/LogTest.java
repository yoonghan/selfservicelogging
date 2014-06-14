package selfservicelogging;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.self.service.logging.log.LogUtil;
import com.self.service.logging.monitor.LogMonitorService;

public class LogTest {

	private final String CLASS_NAME = this.getClass().getName();
	
	@Ignore
	@Test
	public void setLoggingAndScheduling(){
		
		LogUtil.getInstance(CLASS_NAME).info("Test info");
		LogUtil.getInstance(CLASS_NAME).warn("Test warning");
		LogUtil.getInstance(CLASS_NAME).error("Test error");
		
		LogMonitorService.getInstance().stopScheduler();
		LogMonitorService.getInstance().startScheduler();
		
		System.out.println("Scheduler started:"+LogMonitorService.getInstance().isScheduleStarted());
		
		Assert.assertEquals(true,LogMonitorService.getInstance().getIsError());
		
		try{
			Thread.sleep(5*1000); //just 60 seconds
		}catch(Exception e){
			e.printStackTrace();
		}
		
		LogMonitorService.getInstance().stopScheduler();
	}
	
	@Test
	public void testFlush(){
		
		LogUtil.getInstance(CLASS_NAME).info("Test info");
		LogUtil.getInstance(CLASS_NAME).warn("Test warning");
		LogUtil.getInstance(CLASS_NAME).error("Test error");
		
		LogMonitorService.getInstance().stopScheduler();
		LogMonitorService.getInstance().startScheduler();
		
		LogMonitorService.getInstance().flushMessages();
		Assert.assertEquals("Warn:0,Error:0",LogMonitorService.getInstance().getMessageCounts());
		
		LogMonitorService.getInstance().stopScheduler();
		
	}
}
