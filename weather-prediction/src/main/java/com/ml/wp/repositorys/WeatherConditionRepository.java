package com.ml.wp.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ml.wp.model.WeatherCondition;

@Repository
public interface WeatherConditionRepository extends JpaRepository<WeatherCondition, Long>{

}
