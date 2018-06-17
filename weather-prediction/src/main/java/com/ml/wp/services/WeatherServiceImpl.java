package com.ml.wp.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ml.wp.model.Coordinates;
import com.ml.wp.model.Galaxy;
import com.ml.wp.model.Planet;
import com.ml.wp.model.WeatherPredictionResult;
import com.ml.wp.utils.CoordinatesUtil;

@Service
public class WeatherServiceImpl implements WeatherService{

	private Coordinates sunCoordinates = new Coordinates();
	
	@Override
	public WeatherPredictionResult getPredictionResult(Galaxy galaxy) throws Exception {
		WeatherPredictionResult result = new WeatherPredictionResult();
		List<Planet> planets = galaxy.getPlanets();
		if(!planets.isEmpty()) {
			if(CoordinatesUtil.areAligned(planets.get(0).getCoordinates(), planets.get(1).getCoordinates(), planets.get(2).getCoordinates())) {
				if(CoordinatesUtil.areAligned(planets.get(0).getCoordinates(), planets.get(1).getCoordinates(), sunCoordinates)) {
					result.setPeriodsOfDrought(1);
				} else {
					result.setPeriodsOfOptimalConditions(1);
				}
			} else {
				if(CoordinatesUtil.isCoordinateInsideTriangle(planets.get(0).getCoordinates(), planets.get(1).getCoordinates(), planets.get(2).getCoordinates(), sunCoordinates)) {
					result.setPeriodsOfRain(1);
					result.setMaxPerimeter(CoordinatesUtil.getTrianglePerimeter(planets.get(0).getCoordinates(), planets.get(1).getCoordinates(), planets.get(2).getCoordinates()));
				}	
			}
		}
		
		return result;
	}

}
