package meli.tmr.sistemasolar.modelo;

import meli.tmr.sistemasolar.controller.ClimaController;

import java.util.Arrays;
import java.util.List;

public class SistemaSolar {

    private List<Planeta> planetas;
    private Posicion posicionDelSol;
    private ClimaController climaController;

    public SistemaSolar(ClimaController climaController){
        this.setPlanetas(Arrays.asList(new Betasoide(), new Ferengi(), new Vulcano()));
        this.calcularPosicionDelSol();
        this.setClimaController(climaController);
    }

    private void calcularPosicionDelSol(){
        double posicionMaxima = 0.0;
        for(Planeta p : planetas) {
            if(p.getDistanciaALaTierraEnKm() > posicionMaxima) posicionMaxima = p.getDistanciaALaTierraEnKm();
        }
        this.setPosicionDelSol(new Posicion(posicionMaxima, posicionMaxima));
    }

    public List<Planeta> getPlanetas() {
        return planetas;
    }

    public void setPlanetas(List<Planeta> planetas) {
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

    public void setClimaController(ClimaController climaController) {
        this.climaController = climaController;
    }
}
