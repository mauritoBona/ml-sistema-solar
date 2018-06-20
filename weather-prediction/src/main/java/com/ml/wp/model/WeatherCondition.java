package com.ml.wp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WEATHER_CONDITION_DATE")
public class WeatherCondition implements Serializable{
	
	private static final long serialVersionUID = 7877816799580649234L;
	
	public static final String PERIOD_OF_DROUGHT = "PERIODO DE SEQUIAS";
	public static final String PERIOD_OF_RAIN = "PERIODO DE LLUVIAS";
	public static final String PERIOD_OF_OPTIMAL_CONDITIONS= "PERIODO DE CONDICIONES OPTIMAS DE PRESION Y TEMPERATURA";
	public static final String PERIOD_OF_NORMAL_CONDITIONS= "PERIODO DE CONDICIONES NORMALES";
	
	@Id
	@Column(name="DAY_NUMBER")
	private Long day;
	
	@Column(name="WEATHER_CONDITION_DESC" , nullable=false)
	private String description;

	public WeatherCondition() {
		super();
	}
	
	public WeatherCondition(String description) {
		super();
		this.description = description;
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
		if (prediction.getPeriodsOfDrought()>=1) return PERIOD_OF_DROUGHT; 
		if (prediction.getPeriodsOfRain()>=1) return PERIOD_OF_RAIN;
		if (prediction.getPeriodsOfOptimalConditions() >= 1) return PERIOD_OF_OPTIMAL_CONDITIONS;
		return PERIOD_OF_NORMAL_CONDITIONS;
	}
}
