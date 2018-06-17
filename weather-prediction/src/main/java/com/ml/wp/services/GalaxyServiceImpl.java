package com.ml.wp.services;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ml.wp.model.Galaxy;
import com.ml.wp.model.WeatherCondition;
import com.ml.wp.model.WeatherPredictionResult;
import com.ml.wp.repositorys.WeatherConditionRepository;

@Service
public class GalaxyServiceImpl implements GalaxyService{

	private static final Logger LOGGER = Logger.getLogger(GalaxyServiceImpl.class.getName());
	
	private static final int TEN_YEARS_DAYS = 3650;
	private static final int REPEAT_PATTERN = 360;
	
	@Autowired
	private WeatherService weatherService;
	
	@Autowired
	private WeatherConditionRepository weatherConditionRepository;
	
	@Override
	public WeatherPredictionResult predictWeatherConditionsInYearsAndSave(Galaxy galaxy, Integer years, boolean save) {
		WeatherPredictionResult result = new WeatherPredictionResult();
		Integer days = years * 365;
		try {
			for(int i = 1; i <= days; i++) {
					galaxy.simuletedOneDay();
					WeatherPredictionResult resultTemp = weatherService.getPredictionResult(galaxy);
					resultTemp.getDaysOfMaxPerimeter().add(i);
					result.addResult(resultTemp);
					if(save){
						WeatherCondition condition = new WeatherCondition(new Long(resultTemp.getDaysOfMaxPerimeter().get(0)), WeatherCondition.getWeatherConditionDesc(resultTemp));
						weatherConditionRepository.save(condition);
					}
					System.out.println(resultTemp.toString());
			}
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

	private Long getEquivalentDay(Long dayNumber) {
		while(dayNumber>TEN_YEARS_DAYS) {
			dayNumber -= REPEAT_PATTERN; 
		}
		return dayNumber;
	}

}
