package com.ml.wp.exceptions;

public class WeatherPredictionException extends Exception{

	private static final long serialVersionUID = -652621516833251607L;

	public WeatherPredictionException() {
		super();
	}

	public WeatherPredictionException(String message) {
		super(message);
	}
}
