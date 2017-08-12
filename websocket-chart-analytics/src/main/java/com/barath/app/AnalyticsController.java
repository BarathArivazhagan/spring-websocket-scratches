package com.barath.app;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class AnalyticsController {
	
	private static final Logger logger=LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	
	private AnalyticsService service;
	
	public AnalyticsController(AnalyticsService service){
		this.service=service;
	}
	
	
	@MessageMapping("/feedback")
	@SendTo("/app/analytics")
	public List<Analytics> collectAnalytics(AnalyticsDTO analyticDTO){
		
		
		if( analyticDTO != null && !StringUtils.isEmpty(analyticDTO.getName())){
			
			if(logger.isInfoEnabled()){
				logger.info("Saving the analytics with details {}",analyticDTO.toString());
			}
			this.service.saveAnalytics(analyticDTO);
		}
		
		return this.service.getAnalytics();
	}

}
