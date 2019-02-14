package com.barath.app;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SocketController {
	
	private static final Logger logger=LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
    private final SimpMessagingTemplate template;
    private final List<Message> messages;
    
    @Value("${websocket.destination.name}")
    private String destination;
			
	public SocketController(SimpMessagingTemplate template) {
		this.template=template;
		this.messages = new ArrayList<>();
	}
			
	@MessageMapping("/user")
	public void welcomeUser( String userName){

		if(!StringUtils.isEmpty(userName)){
			if(logger.isInfoEnabled()){
				logger.info("content received {}",userName);
				logger.info("Destination to be sent {}",destination);
			}

			this.template.convertAndSend(destination,userName);
		}
		
	}



	@MessageMapping("/message")
	@SendTo("/topic/messages")
	public List<Message> handleMessage(Message message) {

		if (!Objects.isNull(message)) {
			if (logger.isInfoEnabled()) {
				logger.info("message received {}", Objects.toString(message));
			}
			this.messages.add(message);
		}
		return this.messages;
	}

}
