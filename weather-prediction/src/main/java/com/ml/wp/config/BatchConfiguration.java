package com.ml.wp.config;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ml.wp.model.SolarSystem;
import com.ml.wp.model.WeatherCondition;
import com.ml.wp.tasklets.WeatherPredictionProcessor;
import com.ml.wp.tasklets.WeatherPredictionReader;
import com.ml.wp.tasklets.WeatherPredictionWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	@Autowired
    private JobBuilderFactory jobs;
 
    @Autowired
    private StepBuilderFactory steps;
 
    @Bean
    public ItemReader<SolarSystem> weatherPredictionReader() {
        return new WeatherPredictionReader();
    }
 
    @Bean
    public ItemProcessor<SolarSystem, List<WeatherCondition>> weatherPredictionProcessor() {
        return new WeatherPredictionProcessor();
    }
 
    @Bean
    public ItemWriter<List<WeatherCondition>> weatherPredictionWriter() {
        return new WeatherPredictionWriter();
    }
 
    @Bean
    protected Step simulateWeatherConditionsStep(ItemReader<SolarSystem> reader,
      ItemProcessor<SolarSystem, List<WeatherCondition>> processor, ItemWriter<List<WeatherCondition>> writer) {
        return steps.get("simulateWeatherConditionStep").<SolarSystem, List<WeatherCondition>>chunk(100)
          .reader(reader)
          .processor(processor)
          .writer(writer)
          .build();
    }
 
    @Bean
    public Job simulateWeatherConditionsJob() {
        return jobs
          .get("simulateWeatherConditionsJob")
          .start(simulateWeatherConditionsStep(weatherPredictionReader(), weatherPredictionProcessor(), weatherPredictionWriter()))
          .build();
    }
}
