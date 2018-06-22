package com.ml.wp;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.ml.wp.model.SolarSystem;
import com.ml.wp.services.SolarSystemService;
import com.ml.wp.utils.RunScheduler;

@SpringBootApplication
public class WeatherPredictionApplication {

	private static final Logger LOGGER = Logger.getLogger(WeatherPredictionApplication.class.getName());
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(WeatherPredictionApplication.class, args);
		try {
			context.getBean(SolarSystemService.class).simulateWeatherConditionsInYears(context.getBean(SolarSystem.class), 10);
			context.getBean(RunScheduler.class).runWeatherconditionJob();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}

	}
}
