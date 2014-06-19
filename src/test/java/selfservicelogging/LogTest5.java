package selfservicelogging;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.self.service.logging.log.LogUtil;
import com.self.service.logging.monitor.LogMonitorService;

public class LogTest5 {

	private final String CLASS_NAME = this.getClass().getName();
	
	@Before
	public void init(){
		LogMonitorService.getInstance().startScheduler();
	}

	@After
	public void close(){
		LogMonitorService.getInstance().shutdownScheduler();
	}
	
	@Test
	public void setLoggingAndScheduling(){
		
		LogUtil.getInstance(CLASS_NAME).info("Test info");
		LogUtil.getInstance(CLASS_NAME).warn("Test warning");
		LogUtil.getInstance(CLASS_NAME).error("Test error");
				
		System.out.println("Scheduler started:"+LogMonitorService.getInstance().isScheduleStarted());
		
		Assert.assertEquals(true,LogMonitorService.getInstance().getIsError());
		
		try{
			Thread.sleep(40*1000); //just 60 seconds
		}catch(Exception e){
			e.printStackTrace();
		}
		
		LogMonitorService.getInstance().stopScheduler();
		System.out.println("Scheduler started:"+LogMonitorService.getInstance().isScheduleStarted());
		Assert.assertEquals(false,LogMonitorService.getInstance().isScheduleStarted());

		LogUtil.getInstance(CLASS_NAME).info("Test info");
		LogUtil.getInstance(CLASS_NAME).warn("Test warning");
		LogUtil.getInstance(CLASS_NAME).error("Test error");
		
		Assert.assertEquals(true,LogMonitorService.getInstance().getIsError());
		
		try{
			Thread.sleep(40*1000); //just 60 seconds
		}catch(Exception e){
			e.printStackTrace();
		}
		
		LogMonitorService.getInstance().startScheduler();
		System.out.println("Scheduler started:"+LogMonitorService.getInstance().isScheduleStarted());
		Assert.assertEquals(true,LogMonitorService.getInstance().isScheduleStarted());
		
		LogUtil.getInstance(CLASS_NAME).info("Test info");
		LogUtil.getInstance(CLASS_NAME).warn("Test warning");
		LogUtil.getInstance(CLASS_NAME).error("Test error");
		
		try{
			Thread.sleep(40*1000); //just 60 seconds
		}catch(Exception e){
			e.printStackTrace();
		}
		
		LogMonitorService.getInstance().shutdownScheduler();
		
		System.out.println("END Test");
	}
	
	@Ignore	
	@Test
	public void testFlush(){
		
		LogUtil.getInstance(CLASS_NAME).info("Test info");
		LogUtil.getInstance(CLASS_NAME).warn("Test warning");
		LogUtil.getInstance(CLASS_NAME).error("Test error");
		
		LogMonitorService.getInstance().flushMessages();
		Assert.assertEquals("Warn:0,Error:0",LogMonitorService.getInstance().getMessageCounts());

	}
	
}
