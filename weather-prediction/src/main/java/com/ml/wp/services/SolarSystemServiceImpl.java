package com.ml.wp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ml.wp.model.SolarSystem;
import com.ml.wp.model.WeatherCondition;
import com.ml.wp.model.WeatherPredictionResult;
import com.ml.wp.repositories.WeatherConditionRepository;

@Service
public class SolarSystemServiceImpl implements SolarSystemService{

	private static final Logger LOGGER = Logger.getLogger(SolarSystemServiceImpl.class.getName());
	
	private static final int TEN_YEARS_DAYS = 3650;
	private static final int REPEAT_PATTERN = 360;
	
	@Autowired
	private WeatherService weatherService;
	
	@Autowired
	private WeatherConditionRepository weatherConditionRepository;
	
	@Override
	public WeatherPredictionResult simulateWeatherConditionsInYears(SolarSystem solarSystem, Integer years) {
		WeatherPredictionResult result = new WeatherPredictionResult();
		Integer days = years * 365;
		try {
			for(int i = 1; i <= days; i++) {
				solarSystem.simuletedOneDay();
				WeatherPredictionResult resultTemp = weatherService.getPredictionResult(solarSystem.getPlanets());
				resultTemp.getDaysOfMaxPerimeter().add(i);
				result.addResult(resultTemp);
				LOGGER.log(Level.INFO, resultTemp.toString());
			}
			System.out.println("===RESULTADO TOTAL PARA LOS 10 ANOS===");
			LOGGER.log(Level.INFO, result.toString());
			return result;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			return null;
		}
	}

	@Override
	public WeatherCondition getWeatherConditionByDay(Long day) throws Exception {
		if (day<1) throw new Exception("El dia consultado no puede ser menor o igual a cero");
		else {
			if(day > TEN_YEARS_DAYS) day = getEquivalentDay(day);
			Optional<WeatherCondition> obj = weatherConditionRepository.findById(day);
			return obj.get();
			}
		}

	@Override
	public List<WeatherCondition> simulatedWeatherConditionForTheNextTenYears(SolarSystem solarSystem) {
		List<WeatherCondition> weatherConditions = new ArrayList<WeatherCondition>();
		solarSystem.resetCoordinatesOfPlanets();
		try {
			for(int i = 1; i <= TEN_YEARS_DAYS; i++) {
				solarSystem.simuletedOneDay();
				WeatherCondition condition = weatherService.getWeatherPrediction(solarSystem.getPlanets());
				condition.setDay(new Long(i));
				weatherConditions.add(condition);
			}
			return weatherConditions;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
		return null;
	}
	
	private Long getEquivalentDay(Long dayNumber) {
		while(dayNumber>TEN_YEARS_DAYS) {
			dayNumber -= REPEAT_PATTERN; 
		}
		return dayNumber;
	}

}
