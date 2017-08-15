package com.barath.app;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.net.AprEndpoint.Poller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
//import org.springframework.integration.annotation.InboundChannelAdapter;
//import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class InventoryStreamConsumer implements StreamConsumer {
	
	private static final Logger logger=LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private RestTemplate restTemplate=new RestTemplate();
	
	
	@Override
	public List<InventoryDTO> consumeData()  {
		ResponseEntity<String> responseEntity=restTemplate.getForEntity("http://localhost:8009/api/inventory", String.class);
		
		if( responseEntity !=null && responseEntity.getStatusCode().is2xxSuccessful()){
			ObjectMapper mapper=new ObjectMapper();
			try {
				List<InventoryDTO> data= mapper.readValue(responseEntity.getBody(), new TypeReference<List<InventoryDTO>>(){});
				Optional.ofNullable(data).ifPresent( localData -> localData.forEach(System.out::println));
				return data;
			} catch (IOException e) {
				logger.error("error ");
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	
	

}
