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

    public SistemaSolar(List<Planeta> planetas){
        this.setPlanetas(planetas);
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


    public Posicion getPosicionDelSol() {
        return posicionDelSol;
    }

    public void setPosicionDelSol(Posicion posicionDelSol) {
        this.posicionDelSol = posicionDelSol;
    }

}
