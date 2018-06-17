package com.ml.wp.model;

import java.util.ArrayList;
import java.util.List;

public class WeatherPredictionResult {

	private Integer periodsOfDrought = 0;
	private Integer periodsOfRain = 0;
	private Double maxPerimeter = 0.0;
	private List<Integer> daysOfMaxPerimeter = new ArrayList<Integer>();
	private Integer periodsOfOptimalConditions = 0;
	
	public WeatherPredictionResult() {
	}

	public void addResult(WeatherPredictionResult result) {
		this.periodsOfDrought += result.getPeriodsOfDrought();
		this.periodsOfRain += result.getPeriodsOfRain();
		this.periodsOfOptimalConditions += result.getPeriodsOfOptimalConditions();
		if(result.getMaxPerimeter() > this.maxPerimeter) {
			this.maxPerimeter = result.getMaxPerimeter();
			this.daysOfMaxPerimeter = result.getDaysOfMaxPerimeter();
		} else if(Double.compare(result.getMaxPerimeter(), this.getMaxPerimeter()) == 0) {
			this.daysOfMaxPerimeter.addAll(result.getDaysOfMaxPerimeter());
		}
	}
	
	public Integer getPeriodsOfDrought() {
		return periodsOfDrought;
	}

	public void setPeriodsOfDrought(Integer periodsOfDrought) {
		this.periodsOfDrought = periodsOfDrought;
	}

	public Integer getPeriodsOfRain() {
		return periodsOfRain;
	}

	public void setPeriodsOfRain(Integer periodsOfRain) {
		this.periodsOfRain = periodsOfRain;
	}

	public Double getMaxPerimeter() {
		return maxPerimeter;
	}

	public void setMaxPerimeter(Double maxPerimeter) {
		this.maxPerimeter = maxPerimeter;
	}

	public List<Integer> getDaysOfMaxPerimeter() {
		return daysOfMaxPerimeter;
	}

	public void setDaysOfMaxPerimeter(List<Integer> daysOfMaxPerimeter) {
		this.daysOfMaxPerimeter = daysOfMaxPerimeter;
	}

	public Integer getPeriodsOfOptimalConditions() {
		return periodsOfOptimalConditions;
	}

	public void setPeriodsOfOptimalConditions(Integer periodsOfOptimalConditions) {
		this.periodsOfOptimalConditions = periodsOfOptimalConditions;
	}
	
	@Override
	public String toString() {
		return "WeathersPredictionResult [periodsOfDrought=" + periodsOfDrought + ", periodsOfRain=" + periodsOfRain
				+ ", maxPerimeter=" + maxPerimeter + ", maxPerimeterDays=" + this.getDaysOfMaxPerimeterAsString()
				+ ", periodsOfOptimalConditions=" + periodsOfOptimalConditions + "]";
	}

	private String getDaysOfMaxPerimeterAsString() {
		StringBuffer str = new StringBuffer("(");
		if(!daysOfMaxPerimeter.isEmpty()) {
			for (Integer integer : daysOfMaxPerimeter) {
				str.append(integer.toString()+";");
			}
		}
		str.append(")");
		return str.toString();
	}
}
