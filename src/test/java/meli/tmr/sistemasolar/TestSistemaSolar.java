package meli.tmr.sistemasolar;

import meli.tmr.sistemasolar.controller.ClimaController;
import meli.tmr.sistemasolar.modelo.Reporte;
import meli.tmr.sistemasolar.modelo.SistemaSolar;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class TestSistemaSolar {

    @Autowired
    private ClimaController climaController;

    @Test
    public void testReporte() throws Exception {
        SistemaSolar sistemaSolar = new SistemaSolar(this.climaController);
        Reporte reporte = sistemaSolar.obtenerReporte(1);
        Assert.assertEquals("El valor esperado es 0", new Long(0), new Long(reporte.getCantidadDeDiasConSequia()));
    }
}
