package com.ml.wp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WEATHER_CONDITION_DATE")
public class WeatherCondition {
	
	@Id
	@Column(name="DAY_NUMBER")
	private Long day;
	
	@Column(name="WEATHER_CONDITION_DESC" , nullable=false)
	private String description;

	public WeatherCondition() {
		super();
	}
	
	public WeatherCondition(Long day, String description) {
		super();
		this.day = day;
		this.description = description;
	}

	public Long getDay() {
		return day;
	}

	public void setDay(Long day) {
		this.day = day;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static String getWeatherConditionDesc(WeatherPredictionResult prediction) {
		if (prediction.getPeriodsOfDrought()>=1) return "Periodo de sequias"; 
		if (prediction.getPeriodsOfRain()>=1) return "Periodo de lluvias";
		if (prediction.getPeriodsOfOptimalConditions() >= 1) return "Periodo de condiciones optimas de presion y temperatura";
		return "Condiciones Normales";
	}
}
