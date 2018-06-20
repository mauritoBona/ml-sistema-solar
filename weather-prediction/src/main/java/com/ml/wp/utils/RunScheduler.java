package com.ml.wp.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RunScheduler {

	private final Logger logger = Logger.getLogger(RunScheduler.class.getName());
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job simulateWeatherConditionsJob;
	
	public void runWeatherconditionJob() {
		try {
			JobParameters param = new JobParameters();		
			jobLauncher.run(simulateWeatherConditionsJob, param);
	    } catch (Exception e) {
	    	logger.log(Level.SEVERE, e.getMessage());
	    }
	}
}