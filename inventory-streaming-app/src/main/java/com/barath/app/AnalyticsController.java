package com.barath.app;

import java.lang.invoke.MethodHandles;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;


@Controller
public class AnalyticsController {
	
	private static final Logger logger=LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	
	private SimpMessagingTemplate template;
	
	private StreamConsumer service;
	
	public AnalyticsController(StreamConsumer service,SimpMessagingTemplate template){
		this.service=service;
		this.template=template;
	}
	
	
	@MessageMapping("/get")
	//@SendTo("/analytics/data")
	@Scheduled(fixedRate=5000)
	public void collectAnalytics(){
		
		System.out.println("collect Analytics");
		List<InventoryDTO> data= this.service.consumeData();
		data.forEach(System.out::println);
		this.template.convertAndSend("/analytics/data",data);
	}

}
