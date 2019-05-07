package meli.tmr.sistemasolar.modelo;

import meli.tmr.sistemasolar.exceptions.SistemaSolarException;
import meli.tmr.sistemasolar.utils.DoubleUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SistemaSolar {

    private List<Planeta> planetas;
    private Posicion posicionDelSol;

    public SistemaSolar(List<Planeta> planetas) throws SistemaSolarException {
        if(planetas == null || planetas.size() != 3) throw new SistemaSolarException("Se esperan tres planetas");
        this.setPlanetas(planetas);
        this.calcularPosicionDelSol();
        this.planetas.forEach(p -> p.setPosicionDelSol(this.getPosicionDelSol())); // necesitan esta coordenada ya que será el eje central
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
