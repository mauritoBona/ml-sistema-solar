package com.ml.wp.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.ml.wp.model.SolarSystem;
import com.ml.wp.model.Planet;

@Configuration
@ComponentScan("com.ml.wp")
public class AppConfiguration {

	@Bean("solarSystem")
	public SolarSystem solarSystem() {
		List<Planet> planets = new ArrayList<Planet>();
		planets.add(new Planet("Ferengis", 500.0, -1.0, 0.0, 500.0, 0.0));
		planets.add(new Planet("Betasoides", 2000.0, -3.0, 0.0, 2000.0, 0.0));
		planets.add(new Planet("Vulcanos", 1000.0, 5.0, 0.0, 1000.0, 0.0));
		
		return new SolarSystem(1L, planets);
	}
}
