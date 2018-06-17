package com.ml.wp.services;

import com.ml.wp.model.Galaxy;
import com.ml.wp.model.WeatherCondition;
import com.ml.wp.model.WeatherPredictionResult;

public interface GalaxyService {

	public WeatherPredictionResult predictWeatherConditionsInYearsAndSave(Galaxy galaxy, Integer years, boolean save) throws Exception;
	public WeatherCondition getWeatherConditionByDay(Long day) throws Exception;
}
