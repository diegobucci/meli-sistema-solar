package meli.tmr.sistemasolar;

import meli.tmr.sistemasolar.controllers.implementations.WeatherControllerImpl;
import meli.tmr.sistemasolar.daos.implementations.WeatherReportDAOImpl;
import meli.tmr.sistemasolar.daos.interfaces.DayWeatherDAO;
import meli.tmr.sistemasolar.exceptions.SolarSystemException;
import meli.tmr.sistemasolar.exceptions.YearsException;
import meli.tmr.sistemasolar.models.*;
import meli.tmr.sistemasolar.services.CalculatorUtil;
import meli.tmr.sistemasolar.services.WeatherService;
import meli.tmr.sistemasolar.services.WeatherTypeResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestSolarSystem {

    @Mock
    private DayWeatherDAO dayWeatherDAO;
    @Mock
    private WeatherReportDAOImpl weatherReportDAO;
    @Mock
    private WeatherTypeResolver weatherTypeResolver;
    @Mock
    private CalculatorUtil calculatorUtil;
    @InjectMocks
    private WeatherService weatherService;
    @LocalServerPort
    private int port;

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
        Assertions.assertEquals(40 ,reporte.getNumberOfDroughtDays(),0);
        Assertions.assertEquals(1188 ,reporte.getNumberOfRainyDays(),0);
        Assertions.assertEquals(72 ,reporte.getDayOfGreatestRain(),0);
        Assertions.assertEquals(204 ,reporte.getNumberOfOptimalDays(),0);
    }

    @Test
    void testCalculatorUtilCos90(){
        Assertions.assertEquals(0, CalculatorUtil.getCos(90),0);
    }

    @Test
    void testCalculatorUtilSin90(){
        Assertions.assertEquals(1, CalculatorUtil.getSin(90),0);
    }

    @Test
    void testCalculatorUtilCos80(){
        Assertions.assertEquals(0.17, CalculatorUtil.getCos(80),0.01);
    }

    @Test
    void testAreInline(){
        Assertions.assertTrue(calculatorUtil.areInline(new Position(-3, 10), new Position(-6,12), new Position(3,6)));
    }

    @Test
    void testErrorRequestNegativeNumber() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(
                new URL("http://localhost:" + port + "/clima?dia=-1").toString(), String.class);
        String asd = response.getBody();
        Assertions.assertEquals(WeatherControllerImpl.NUMERO_POSITIVO_ERROR, response.getBody());
    }

    @Test
    void testErrorRequestWrongFormat() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(
                new URL("http://localhost:" + port + "/clima?dia=asd").toString(), String.class);
        Assertions.assertEquals(WeatherControllerImpl.EL_FORMATO_ES_INCORRECTO, response.getBody());
    }

    @Test
    void testErrorRequestNoParams() throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(
                new URL("http://localhost:" + port + "/clima").toString(), String.class);
        Assertions.assertEquals(WeatherControllerImpl.DIA_PARA_OBTENER_EL_CLIMA_ERROR, response.getBody());
    }

}




