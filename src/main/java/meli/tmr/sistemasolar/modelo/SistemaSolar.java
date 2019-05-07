package meli.tmr.sistemasolar.modelo;

import meli.tmr.sistemasolar.exceptions.SistemaSolarException;
import meli.tmr.sistemasolar.utils.DoubleUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SistemaSolar {

    private List<Planeta> planetas;

    public SistemaSolar(List<Planeta> planetas) throws SistemaSolarException {
        if(planetas == null || planetas.size() != 3) throw new SistemaSolarException("Se esperan tres planetas");
        this.setPlanetas(planetas);
    }

    public List<Planeta> getPlanetas() {
        return planetas;
    }

    public void setPlanetas(List<Planeta> planetas) {
        this.planetas = planetas;
    }


}
