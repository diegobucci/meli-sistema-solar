package meli.tmr.sistemasolar;

import meli.tmr.sistemasolar.controller.ClimaController;
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
    private ClimaController climaController;

    @Test
    public void testFerengiPositionXDia1(){
        Planeta ferengi = new Ferengi();
        Posicion posicionDia1 = ferengi.getPosicion(1);
        Assert.assertEquals(309.76030628, posicionDia1.getX(), 1);
    }

    @Test
    public void testFerengiPositionYDia1(){
        Planeta ferengi = new Ferengi();
        Posicion posicionDia1 = ferengi.getPosicion(1);
        Assert.assertEquals(107.509805659, posicionDia1.getY(), 1);
    }

    @Test
    public void testSequiasEn1Dia() throws Exception {
        SistemaSolar sistemaSolar = new SistemaSolar(Arrays.asList(new Betasoide(), new Ferengi(), new Vulcano()));
        Reporte reporte = climaController.obtenerReporte(sistemaSolar,1);
        Assert.assertEquals("El valor esperado es 0", new Long(0), new Long(reporte.getCantidadDeDiasConSequia()));
    }

    @Test
    public void testObtenerReporteExceptionAniosSuperiorADiez() {
        SistemaSolar sistemaSolar = new SistemaSolar(Arrays.asList(new Betasoide(), new Ferengi(), new Vulcano()));
        try {
            climaController.obtenerReporte(sistemaSolar,99);
        } catch (Exception e) {
            Assert.assertThat(e.getMessage(), is("Los años deben variar entre 1 y 10"));
        }
    }
}




