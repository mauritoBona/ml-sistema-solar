package com.ml.wp.tasklets;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.ml.wp.model.SolarSystem;

public class WeatherPredictionReader implements ItemReader<SolarSystem>{

	//TODO mejora: levantar los systemas solares de una base para predecirlo
	@Autowired
	private SolarSystem solarSystem;

	private int runTimes = 0;
	
	@Override
	public SolarSystem read()
			throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		if(runTimes == 0) {
			runTimes++;
			return solarSystem;
		} 
		return null;
	}
	
}
