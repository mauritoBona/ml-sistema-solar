package com.ml.wp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ml.wp.model.WeatherCondition;
import com.ml.wp.services.SolarSystemService;

@RestController
public class WeatherConditionController {

	public final String CLIMA_URL = "/clima";
	
	@Autowired
	private SolarSystemService solarSystemService;
	
	@RequestMapping(path= CLIMA_URL, method= RequestMethod.GET)
	public WeatherCondition getWeatherCondition(@RequestParam(value = "dia") Long dayNumber) throws Exception{
		 if (dayNumber != null) return solarSystemService.getWeatherConditionByDay(dayNumber);
			else { throw new Exception("El dia ingresado no puede ser nulo o vacio"); }
    }
}
