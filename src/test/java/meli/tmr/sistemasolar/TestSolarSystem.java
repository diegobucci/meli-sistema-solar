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
@ContextConfiguration(classes = {AppConfig.class})
public class TestSolarSystem {

    @Autowired
    private WeatherService weatherService;

//    @Test
//    public void testFerengiPositionXDia1(){
//        Planet ferengi = new Ferengi();
//        Position posicionDia1 = ferengi.getPosition(1);
//        Assert.assertEquals(309.76030628, posicionDia1.getX(), 1);
//    }
//
//    @Test
//    public void testFerengiPositionYDia1() throws SolarSystemException {
//        Planet ferengi = new Ferengi();
//        new SolarSystem(Arrays.asList(ferengi, new Vulcano(), new Betasoide()));
//        Position posicionDia1 = ferengi.getPosition(1);
//        Assert.assertEquals(2712.08950367, posicionDia1.getY(), 1);
//    }

/*
    @Test
    public void testSequiasEn1Anio() {
        SolarSystem solarSystem = new SolarSystem(Arrays.asList(new Betasoide(), new Ferengi(), new Vulcano()));
        WeatherReport reporte = weatherService.getWeatherReport(solarSystem,1);
        Assert.assertEquals("El valor esperado es 0", new Long(0), new Long(reporte.getNumberOfDroughtDays()));
    }*/


    @Test
    public void testObtenerReporteExceptionAniosSuperiorADiez() throws SolarSystemException {
        SolarSystem solarSystem = new SolarSystem(Arrays.asList(new Betasoide(), new Ferengi(), new Vulcano()));
        try {
            weatherService.getWeatherReport(solarSystem,99);
        } catch (Exception e) {
            Assert.assertThat(e.getMessage(), is("Los años deben variar entre 1 y 10"));
        }
    }

    @Test
    public void testCreacionSistemaSolarException() {
        try {
            new SolarSystem(Arrays.asList(new Betasoide(), new Ferengi()));
        } catch (SolarSystemException e) {
            Assert.assertThat(e.getMessage(), is("Se esperan tres planetas"));
        }
    }

    @Test
    public void test10Anios()  {
        SolarSystem solarSystem = new SolarSystem(Arrays.asList(new Betasoide(), new Ferengi(), new Vulcano()));
        WeatherReport reporte = weatherService.getWeatherReport(solarSystem,10);
        Assert.assertEquals(2,reporte.getNumberOfDroughtDays(),0);
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
    public void areInline(){
        Assert.assertTrue(weatherService.getCalculatorUtil().areInline(new Position(-3, 10), new Position(-6,12), new Position(3,6)));
    }

}




