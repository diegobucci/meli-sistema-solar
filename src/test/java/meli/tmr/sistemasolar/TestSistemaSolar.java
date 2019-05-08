package meli.tmr.sistemasolar;

import meli.tmr.sistemasolar.services.ClimaService;
import meli.tmr.sistemasolar.exceptions.SistemaSolarException;
import meli.tmr.sistemasolar.modelo.*;
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
public class TestSistemaSolar {

    @Autowired
    private ClimaService climaService;

//    @Test
//    public void testFerengiPositionXDia1(){
//        Planeta ferengi = new Ferengi();
//        Posicion posicionDia1 = ferengi.getPosicion(1);
//        Assert.assertEquals(309.76030628, posicionDia1.getX(), 1);
//    }
//
//    @Test
//    public void testFerengiPositionYDia1() throws SistemaSolarException {
//        Planeta ferengi = new Ferengi();
//        new SistemaSolar(Arrays.asList(ferengi, new Vulcano(), new Betasoide()));
//        Posicion posicionDia1 = ferengi.getPosicion(1);
//        Assert.assertEquals(2712.08950367, posicionDia1.getY(), 1);
//    }


    @Test
    public void testSequiasEn1Anio() throws Exception {
        SistemaSolar sistemaSolar = new SistemaSolar(Arrays.asList(new Betasoide(), new Ferengi(), new Vulcano()));
        ReporteClima reporte = climaService.obtenerReporte(sistemaSolar,1);
        Assert.assertEquals("El valor esperado es 0", new Long(0), new Long(reporte.getCantidadDeDiasConSequia()));
    }

    @Test
    public void testReporteEn10Anios() throws Exception {
        SistemaSolar sistemaSolar = new SistemaSolar(Arrays.asList(new Betasoide(), new Ferengi(), new Vulcano()));
        ReporteClima reporte = climaService.obtenerReporte(sistemaSolar,10);
        Assert.assertEquals(0, reporte.getCantidadDeDiasConSequia(), 0);
    }

    @Test
    public void testObtenerReporteExceptionAniosSuperiorADiez() throws SistemaSolarException {
        SistemaSolar sistemaSolar = new SistemaSolar(Arrays.asList(new Betasoide(), new Ferengi(), new Vulcano()));
        try {
            climaService.obtenerReporte(sistemaSolar,99);
        } catch (Exception e) {
            Assert.assertThat(e.getMessage(), is("Los años deben variar entre 1 y 10"));
        }
    }

    @Test
    public void testCreacionSistemaSolarException() {
        try {
            new SistemaSolar(Arrays.asList(new Betasoide(), new Ferengi()));
        } catch (SistemaSolarException e) {
            Assert.assertThat(e.getMessage(), is("Se esperan tres planetas"));
        }
    }

}




