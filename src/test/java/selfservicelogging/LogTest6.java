package selfservicelogging;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.jaring.jom.logging.impl.Log;
import com.jaring.jom.logging.log.LogFactory;
import com.jaring.jom.logging.monitor.LogMonitorService;

public class LogTest6 {
	private final Log log = LogFactory.getLogger("selfservicelogging.LogTest5");
	
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
		log.info("Test info");
		log.warn("Test warning");
		log.error("Test error");
				
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

		log.info("Test info");
		log.warn("Test warning");
		log.error("Test error");
		
		Assert.assertEquals(true,LogMonitorService.getInstance().getIsError());
		
		try{
			Thread.sleep(40*1000); //just 60 seconds
		}catch(Exception e){
			e.printStackTrace();
		}
		
		LogMonitorService.getInstance().startScheduler();
		System.out.println("Scheduler started:"+LogMonitorService.getInstance().isScheduleStarted());
		Assert.assertEquals(true,LogMonitorService.getInstance().isScheduleStarted());
		
		log.info("Test info");
		log.warn("Test warning");
		log.error("Test error");
		
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
		
		log.info("Test info");
		log.warn("Test warning");
		log.error("Test error");
		
		LogMonitorService.getInstance().flushMessages();
		Assert.assertEquals("Warn:0,Error:0",LogMonitorService.getInstance().getMessageCounts());

	}
	
}
