package com.barath.app;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service
public class AnalyticsService {
	
	private static final Logger logger=LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	private AnalyticsRepository repository;
	
	public AnalyticsService(AnalyticsRepository repository){
		this.repository=repository;
	}
	
	public Analytics saveAnalytics(AnalyticsDTO analyticsDTO){
		
		if(!isAnalyticsExistsWithName(analyticsDTO.getName())){
			Analytics analytics=new Analytics();
			return this.repository.save(analytics);
		}else{
			return updateAnalytics(analyticsDTO);
		}
	}
	
	
	public Analytics updateAnalytics(AnalyticsDTO analyticsDTO){
		
			Analytics analytics=getAnalyticsByName(analyticsDTO.getName());
			if( analytics !=null ){						
				analytics.setLikes(analyticsDTO.getLikes());
				analyticsDTO.setLoves(analyticsDTO.getLoves());
				if(logger.isInfoEnabled()){
					logger.info("Updating the analytics with details {}",analytics.toString());
				}
				return this.repository.save(analytics);
			}
			
			return analytics;
	}
	
	
	public List<Analytics> getAnalytics(){
		
		return this.repository.findAll();
	}
	
	
	public Analytics getAnalyticsByName(String name){
		
		return Optional.ofNullable(this.repository.findByName(name)).orElse(null);
	}
	
	
	public boolean isAnalyticsExistsWithName(String name){
		
		return this.repository.findByName(name) !=null ? true : false;
	}
	
	@PostConstruct
	public void init(){
		
		AnalyticsDTO data1=new AnalyticsDTO("chart1", 100L, 100L);
		AnalyticsDTO data2=new AnalyticsDTO("chart2", 100L, 100L);
		AnalyticsDTO data3=new AnalyticsDTO("chart3", 100L, 100L);
		AnalyticsDTO data4=new AnalyticsDTO("chart4", 100L, 100L);
		AnalyticsDTO data5=new AnalyticsDTO("chart5", 100L, 100L);
		
		Arrays.asList(data1,data2,data3,data4,data5).forEach(this::saveAnalytics);
	}

}
