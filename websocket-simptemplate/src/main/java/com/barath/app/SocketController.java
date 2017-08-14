package com.barath.app;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SocketController {
	
	private static final Logger logger=LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())
	
	@MessageMapping("/hello")
	public void getMessage(@RequestParam String message);

}
