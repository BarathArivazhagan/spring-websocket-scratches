package com.barath.app;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AnalyticsRepository extends JpaRepository<Analytics,Long>{
	
	Analytics findByName(String name);

}


