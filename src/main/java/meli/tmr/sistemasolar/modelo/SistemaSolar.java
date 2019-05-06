package meli.tmr.sistemasolar.modelo;

import meli.tmr.sistemasolar.controller.ClimaController;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class SistemaSolar {

    private List<Civilizacion> planetas;

    @Inject
    private ClimaController climaController;

    public SistemaSolar(){
        this.setPlanetas(Arrays.asList(new Betasoide(), new Ferengi(), new Vulcano()));
    }

    public List<Civilizacion> getPlanetas() {
        return planetas;
    }

    public void setPlanetas(List<Civilizacion> planetas) {
        this.planetas = planetas;
    }

    public Reporte obtenerReporte(Integer cantidadDeAnios) throws Exception {
        return climaController.obtenerReporte(this.getPlanetas(), cantidadDeAnios);
    }

}
