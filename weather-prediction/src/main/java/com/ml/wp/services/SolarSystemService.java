package com.ml.wp.services;

import java.util.List;

import com.ml.wp.model.SolarSystem;
import com.ml.wp.model.WeatherCondition;
import com.ml.wp.model.WeatherPredictionResult;

public interface SolarSystemService {

	/**
	 * this method simulates day by day and get the WeatherPredictionResult, 
	 * when finished, return the WeatherPredictionResult
	 *
	 * @param solarSystem the solar system with the planets
	 * @param years the years to simulate
	 * @return WeatherPredictionResult
	 */
	public WeatherPredictionResult simulateWeatherConditionsInYears(SolarSystem solarSystem, Integer years) throws Exception;
	
	/**
	 * this method finds the weatherCondition by day in the dataBase
	 *
	 * @param day the day we want to know the weather condition
	 * @return WeatherCondition
	 */
	public WeatherCondition getWeatherConditionByDay(Long day) throws Exception;

	/**
	 * this method simulates day by day and get the weatherCondition with the day of this, 
	 * and return a list with all WeatherCondition 
	 *
	 * @param solarSystem the solar system with the planets to simulate
	 * @return List of WeatherCondition
	 */
	public List<WeatherCondition> simulatedWeatherConditionForTheNextTenYears(SolarSystem solarSystem);
}
