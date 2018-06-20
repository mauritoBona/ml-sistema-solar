package com.ml.wp.tasklets;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.ml.wp.model.SolarSystem;
import com.ml.wp.model.WeatherCondition;
import com.ml.wp.services.SolarSystemService;

public class WeatherPredictionProcessor implements ItemProcessor<SolarSystem, List<WeatherCondition>>{

	@Autowired
	private SolarSystemService solarSystemService;
	
	@Override
	public List<WeatherCondition> process(SolarSystem solarSystem) throws Exception {
		return solarSystemService.simulatedWeatherConditionForTheNextTenYears(solarSystem);
	}

}
