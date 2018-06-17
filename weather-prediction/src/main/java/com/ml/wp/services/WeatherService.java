package com.ml.wp.services;

import com.ml.wp.model.Galaxy;
import com.ml.wp.model.WeatherPredictionResult;

public interface WeatherService {

	public WeatherPredictionResult getPredictionResult(Galaxy galaxy) throws Exception;
}
