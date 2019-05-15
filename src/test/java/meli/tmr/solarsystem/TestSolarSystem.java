package meli.tmr.solarsystem;

import meli.tmr.solarsystem.daos.implementations.WeatherReportDAOImpl;
import meli.tmr.solarsystem.daos.interfaces.DayWeatherDAO;
import meli.tmr.solarsystem.exceptions.SolarSystemException;
import meli.tmr.solarsystem.exceptions.YearsException;
import meli.tmr.solarsystem.models.*;
import meli.tmr.solarsystem.services.CalculatorUtil;
import meli.tmr.solarsystem.services.WeatherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
public class TestSolarSystem {
    @Mock
    private DayWeatherDAO dayWeatherDAO;
    @Mock
    private WeatherReportDAOImpl weatherReportDAO;
    @Autowired
    @InjectMocks
    private WeatherService weatherService;
    @Autowired
    private CalculatorUtil calculatorUtil;


    @Test
    void testGetReportMoreThan10YearsException() {
        SolarSystem solarSystem = new SolarSystem(AppInitializator.buildPlanetList());
        Assertions.assertThrows(YearsException.class, () -> weatherService.getWeatherReport(solarSystem, 99));
    }

    @Test
    void testGetReportLessThan1YearException() {
        SolarSystem solarSystem = new SolarSystem(AppInitializator.buildPlanetList());
        Assertions.assertThrows(YearsException.class, () -> weatherService.getWeatherReport(solarSystem, -1));
    }

    @Test
    void testCreateSolarSystemWithTwoPlanetsException() {
        Planet planet1 = new Planet("planet1", 1,1,true);
        Planet planet2 = new Planet("planet2", 1,1,true);
        Assertions.assertThrows(SolarSystemException.class, () -> new SolarSystem(Arrays.asList(planet1, planet2)));
    }

    @Test
    void testAddPlanetsToSolarSystemException() {
        Planet planet1 = new Planet("planet1", 1,1,true);
        Planet planet2 = new Planet("planet2", 1,1,true);
        Planet planet3 = new Planet("planet3", 1,1,true);
        Planet planet4 = new Planet("planet4", 1,1,true);
        SolarSystem solarSystem = new SolarSystem();
        solarSystem.addPlanet(planet1);
        solarSystem.addPlanet(planet2);
        solarSystem.addPlanet(planet3);
        Assertions.assertThrows(SolarSystemException.class, () -> solarSystem.addPlanet(planet4));
    }

    @Test
    void testWeatherIn10Years()  {
        doNothing().when(dayWeatherDAO).save(any(DayWeather.class));
        doNothing().when(weatherReportDAO).save(any(WeatherReport.class));
        SolarSystem solarSystem = new SolarSystem(AppInitializator.buildPlanetList());
        WeatherReport reporte = weatherService.getWeatherReport(solarSystem,10);
        Assertions.assertEquals(40 ,reporte.getDiasDeSequia(),0.01);
        Assertions.assertEquals(1188 ,reporte.getDiasDeLluvia(),0.01);
        Assertions.assertEquals(72 ,reporte.getDiaDeMayorLluvia(),0.01);
        Assertions.assertEquals(204 ,reporte.getDiasOptimos(),0.01);
    }

    @Test
    void testCalculatorUtilCos90(){
        Assertions.assertEquals(0, CalculatorUtil.getCos(90),0.01);
    }

    @Test
    void testCalculatorUtilSin90(){
        Assertions.assertEquals(1, CalculatorUtil.getSin(90),0.01);
    }

    @Test
    void testCalculatorUtilCos80(){
        Assertions.assertEquals(0.17, CalculatorUtil.getCos(80),0.01);
    }

    @Test
    void testAreInline(){
        Assertions.assertTrue(calculatorUtil.areInline(new Position(2, 4), new Position(4,6), new Position(6,8)));
    }



}




