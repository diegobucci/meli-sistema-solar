package meli.tmr.sistemasolar.modelo;

import meli.tmr.sistemasolar.controller.ClimaController;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class SistemaSolar {

    private List<Civilizacion> planetas;
    private Posicion posicionDelSol; // siempre está en el medio

    @Inject
    private ClimaController climaController;

    public SistemaSolar(){
        this.setPlanetas(Arrays.asList(new Betasoide(), new Ferengi(), new Vulcano()));
        this.calcularPosicionDelSol();
    }

    private void calcularPosicionDelSol(){
        Float posicionMaxima = new Float(0);
        for(Civilizacion p : planetas) {
            if(p.getDistanciaALaTierraEnKm() > posicionMaxima) posicionMaxima = p.getDistanciaALaTierraEnKm();
        }
        this.setPosicionDelSol(new Posicion(posicionMaxima, posicionMaxima));
    }

    public List<Civilizacion> getPlanetas() {
        return planetas;
    }

    public void setPlanetas(List<Civilizacion> planetas) {
        this.planetas = planetas;
    }

    public Reporte obtenerReporte(Integer cantidadDeAnios) throws Exception {
        if(cantidadDeAnios < 1 || cantidadDeAnios > 10) throw new Exception("Los años deben variar entre 1 y 10");
        return climaController.obtenerReporte(this.getPlanetas(), this.getPosicionDelSol(), cantidadDeAnios);
    }

    public Posicion getPosicionDelSol() {
        return posicionDelSol;
    }

    public void setPosicionDelSol(Posicion posicionDelSol) {
        this.posicionDelSol = posicionDelSol;
    }
}
