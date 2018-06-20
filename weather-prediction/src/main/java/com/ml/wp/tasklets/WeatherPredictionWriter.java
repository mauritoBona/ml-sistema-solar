package com.ml.wp.tasklets;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.ml.wp.model.WeatherCondition;
import com.ml.wp.repositories.WeatherConditionRepository;

public class WeatherPredictionWriter implements ItemWriter<List<WeatherCondition>>{

	@Autowired
	private WeatherConditionRepository weatherConditionRepository;
	
	@Override
	public void write(List<? extends List<WeatherCondition>> items) throws Exception {
		for(List<WeatherCondition> item : items) {
			weatherConditionRepository.saveAll((Iterable<WeatherCondition>) item);
	
		}
	}	
}
