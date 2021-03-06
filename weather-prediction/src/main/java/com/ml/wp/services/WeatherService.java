package com.ml.wp.services;

import java.util.List;

import com.ml.wp.model.Planet;
import com.ml.wp.model.WeatherCondition;
import com.ml.wp.model.WeatherPredictionResult;

public interface WeatherService {

	/**
	 * this method checks the different conditions to known the Weather. 
	 * <p>
	 * If the planets are Aligned, and aligned with the sun, is a period of drought
	 * else is a period of optimal conditions. 
	 * <p>
	 * If the planets are not aligned, they form a triangle. If the sun is inside the triangle 
	 * is a period of rain, and save the perimeter of the triangle in the WeatherPredictionResult
	 * 
	 * @param planets planets of the system solar
	 * @return WeatherPredictionResult
	 */
	public WeatherPredictionResult getPredictionResult(List<Planet> planets) throws Exception;
	
	/**
	 * this method checks the different conditions to known the Weather. 
	 * <p>
	 * If the planets are Aligned, and aligned with the sun, is a period of drought
	 * else is a period of optimal conditions. 
	 * <p>
	 * If the planets are not aligned, they form a triangle. If the sun is inside the triangle 
	 * is a period of rain
	 * 
	 * @param planets planets of the system solar
	 * @return WeatherCondition
	 */
	public WeatherCondition getWeatherPrediction(List<Planet> planets) throws Exception;
}
