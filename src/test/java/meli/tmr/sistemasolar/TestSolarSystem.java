package meli.tmr.sistemasolar;

import meli.tmr.sistemasolar.exceptions.SolarSystemException;
import meli.tmr.sistemasolar.services.CalculatorUtil;
import meli.tmr.sistemasolar.services.WeatherService;
import meli.tmr.sistemasolar.models.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppTestConfig.class})
public class TestSolarSystem {

    @Autowired
    private WeatherService weatherService;


    @Test
    public void testGetReportMoreThan10YearsException() throws SolarSystemException {
        SolarSystem solarSystem = new SolarSystem(Arrays.asList(new Betasoide(), new Ferengi(), new Vulcano()));
        try {
            weatherService.getWeatherReport(solarSystem,99);
        } catch (Exception e) {
            Assert.assertThat(e.getMessage(), is("Los años deben variar entre 1 y 10"));
        }
    }

    @Test
    public void testGetReportLessThan1YearException() throws SolarSystemException {
        SolarSystem solarSystem = new SolarSystem(Arrays.asList(new Betasoide(), new Ferengi(), new Vulcano()));
        try {
            weatherService.getWeatherReport(solarSystem,-10);
        } catch (Exception e) {
            Assert.assertThat(e.getMessage(), is("Los años deben variar entre 1 y 10"));
        }
    }

    @Test
    public void testCreateSolarSystemWithTwoPlanetsException() {
        try {
            new SolarSystem(Arrays.asList(new Betasoide(), new Ferengi()));
        } catch (SolarSystemException e) {
            Assert.assertThat(e.getMessage(), is("Se esperan tres planetas"));
        }
    }

    @Test
    public void testCreateSolarSystemWithFourPlanetsException() {
        try {
            SolarSystem solarSystem = new SolarSystem(Arrays.asList(new Betasoide(), new Ferengi(), new Vulcano()));
            solarSystem.addPlanet(new Vulcano());
        } catch (SolarSystemException e) {
            Assert.assertThat(e.getMessage(), is("El sistema solar ya contiene tres planetas"));
        }
    }

    @Test
    public void testWeatherIn10Years()  {
        // TODO estan bien los resultados?
        SolarSystem solarSystem = new SolarSystem(Arrays.asList(new Betasoide(), new Ferengi(), new Vulcano()));
        WeatherReport reporte = weatherService.getWeatherReport(solarSystem,10);
        Assert.assertEquals(40 ,reporte.getNumberOfDroughtDays(),0);
        Assert.assertEquals(1148 ,reporte.getNumberOfRainyDays(),0);
        Assert.assertEquals(3630 ,reporte.getDayOfGreatestRain(),0);
        Assert.assertEquals(122 ,reporte.getNumberOfOptimalDays(),0);
    }

    @Test
    public void testCalculatorUtilCos90(){
        Assert.assertEquals(0, CalculatorUtil.getCos(90),0);
    }

    @Test
    public void testCalculatorUtilSin90(){
        Assert.assertEquals(1, CalculatorUtil.getSin(90),0);
    }

    @Test
    public void testCalculatorUtilCos80(){
        Assert.assertEquals(0.17, CalculatorUtil.getCos(80),0.01);
    }

    @Test
    public void testAreInline(){
        Assert.assertTrue(weatherService.getCalculatorUtil().areInline(new Position(-3, 10), new Position(-6,12), new Position(3,6)));
    }

}




