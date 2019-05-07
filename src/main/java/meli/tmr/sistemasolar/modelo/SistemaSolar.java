package meli.tmr.sistemasolar.modelo;

import meli.tmr.sistemasolar.controller.ClimaController;
import meli.tmr.sistemasolar.exceptions.AniosException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SistemaSolar {

    private List<Planeta> planetas;
    private Posicion posicionDelSol;
    private ClimaController climaController;

    public SistemaSolar(ClimaController climaController){
        this.setClimaController(climaController);
        this.setPlanetas(Arrays.asList(new Betasoide(), new Ferengi(), new Vulcano()));
        this.calcularPosicionDelSol();
    }

    private void calcularPosicionDelSol(){
        double posicionMaxima = Collections.max(this.getPlanetas(), Comparator.comparing(Planeta::getDistanciaALaTierraEnKm)).getDistanciaALaTierraEnKm();
        this.setPosicionDelSol(new Posicion(posicionMaxima, posicionMaxima));
    }

    public List<Planeta> getPlanetas() {
        return planetas;
    }

    public void setPlanetas(List<Planeta> planetas) {
        this.planetas = planetas;
    }

    public Reporte obtenerReporte(Integer cantidadDeAnios) throws Exception {
        if(cantidadDeAnios < 1 || cantidadDeAnios > 10) throw new AniosException("Los años deben variar entre 1 y 10");
        return climaController.obtenerReporte(this.getPlanetas(), this.getPosicionDelSol(), cantidadDeAnios);
    }

    public Posicion getPosicionDelSol() {
        return posicionDelSol;
    }

    public void setPosicionDelSol(Posicion posicionDelSol) {
        this.posicionDelSol = posicionDelSol;
    }

    public void setClimaController(ClimaController climaController) {
        this.climaController = climaController;
    }
}
