package com.barath.app;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SocketController {
	
	private static final Logger logger=LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
    private SimpMessagingTemplate template;
    
    @Value("${websocket.destination.name}")
    private String destination;
			
	public SocketController(SimpMessagingTemplate template) {
		this.template=template;
	}
			
	@MessageMapping("/hello")
	public void getMessage(@RequestParam String message){
		
		if(!StringUtils.isEmpty(message)){
			if(logger.isInfoEnabled()){
				logger.info("Message received {}",message);
			}
			this.template.convertAndSend(destination,new HelloMessage(message));
		}
		
	}

}
