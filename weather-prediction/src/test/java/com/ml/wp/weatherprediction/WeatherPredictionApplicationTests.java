package com.ml.wp.weatherprediction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import com.ml.wp.WeatherPredictionApplication;
import com.ml.wp.controllers.WeatherConditionController;
import com.ml.wp.model.Coordinates;
import com.ml.wp.model.SolarSystem;
import com.ml.wp.model.WeatherCondition;
import com.ml.wp.services.SolarSystemService;
import com.ml.wp.utils.CoordinatesUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeatherPredictionApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class WeatherPredictionApplicationTests {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private WeatherConditionController wcController;
	
	@Autowired
	private SolarSystemService solarSystemService;

	@Autowired
	private SolarSystem solarSystem;
	
	@Test
	public void contextLoads() {
		assertNotNull(solarSystem);
		assertNotNull(solarSystemService);
		assertNotNull(wcController);
	}

	@Test
	public void isCoordinateInsideTriangle() {
		try {
			Coordinates t1 = new Coordinates(45.0, 45.0);
			Coordinates t2 = new Coordinates(-135.0, 45.0);
			Coordinates t3 = new Coordinates(0.0, -90.0);
			Coordinates x = new Coordinates(0.00, 0.00);
			assertTrue(CoordinatesUtil.isCoordinateInsideTriangle(t1, t2, t3, x));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void calculateTrianglePerimeter() {
		try {
			Coordinates t1 = new Coordinates(45.0, 45.0);
			Coordinates t2 = new Coordinates(-135.0, 45.0);
			Coordinates t3 = new Coordinates(0.0, -90.0);
			assertEquals(new Double(513.221), CoordinatesUtil.getTrianglePerimeter(t1, t2, t3));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void areAlignButNotWithTheSun() {
		try {
			Coordinates t1 = new Coordinates(1.0, -2.0);
			Coordinates t2 = new Coordinates(-2.0, 1.0);
			Coordinates t3 = new Coordinates(2.0, -3.0);
			Coordinates sun = new Coordinates(0.0, 0.0);
			if (CoordinatesUtil.areAligned(t1, t2, t3)) {
				assertFalse(CoordinatesUtil.areAligned(t1, t2, sun));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void areAlignWithTheSun() {
		try {
			Coordinates t1 = new Coordinates(-225.0, -225.0);
			Coordinates t2 = new Coordinates(45.0, 45.0);
			Coordinates t3 = new Coordinates(90.0, 90.0);
			Coordinates sun = new Coordinates(0.0, 0.0);
			if (CoordinatesUtil.areAligned(t1, t2, t3)) {
				assertTrue(CoordinatesUtil.areAligned(t1, t2, sun));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void calculateWeatherPredictionIn10YearsAndNotSave() throws Exception {
		solarSystemService.simulateWeatherConditionsInYears(solarSystem, 10);
	}

	@Test
	public void getWeatherConditionDateRestTest() {
		WeatherCondition wcd = this.restTemplate.getForObject("http://localhost:" + port + wcController.CLIMA_URL + "?dia=830", WeatherCondition.class);
		assertTrue(wcd.getDescription().contains("PERIODO DE LLUVIAS"));
		
		wcd = this.restTemplate.getForObject("http://localhost:" + port + wcController.CLIMA_URL + "?dia=1170", WeatherCondition.class);
		assertTrue(wcd.getDescription().contains("PERIODO DE SEQUIAS"));
		
		wcd = this.restTemplate.getForObject("http://localhost:" + port + wcController.CLIMA_URL + "?dia=85", WeatherCondition.class);
		assertTrue(wcd.getDescription().contains("PERIODO DE CONDICIONES NORMALES"));
		
		wcd = this.restTemplate.getForObject("http://localhost:" + port + wcController.CLIMA_URL + "?dia=615", WeatherCondition.class);
		assertFalse(wcd.getDescription().contains("PERIODO DE CONDICIONES OPTIMAS DE PRESION Y TEMPERATURA"));
		
		wcd = this.restTemplate.getForObject("http://localhost:" + port + wcController.CLIMA_URL + "?dia=3750", WeatherCondition.class);
		assertTrue(wcd.getDescription().contains("PERIODO DE LLUVIAS"));
		
		wcd = this.restTemplate.getForObject("http://localhost:" + port + wcController.CLIMA_URL + "?dia=3690", WeatherCondition.class);
		assertTrue(wcd.getDescription().contains("PERIODO DE SEQUIAS"));
	}
}
