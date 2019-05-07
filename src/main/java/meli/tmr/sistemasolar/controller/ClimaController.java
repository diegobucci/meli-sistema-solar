package meli.tmr.sistemasolar.controller;

import meli.tmr.sistemasolar.exceptions.AniosException;
import meli.tmr.sistemasolar.modelo.Planeta;
import meli.tmr.sistemasolar.modelo.Posicion;
import meli.tmr.sistemasolar.modelo.ReporteClima;
import meli.tmr.sistemasolar.modelo.SistemaSolar;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ClimaController {
    public static final String LLUVIA = "lluvia";
    public static final String SEQUIA = "sequia";
    public static final String OPTIMO = "optimo";
    public static final String INDETERMINADO = "indeterminado"; // forman un triangulo en el cual el sol no esta dentro
    private static final Integer DIAS_POR_ANIO = 365;

      public ReporteClima obtenerReporte(SistemaSolar sistemaSolar, Integer cantidadDeAnios) throws Exception {
        if(cantidadDeAnios < 1 || cantidadDeAnios > 10) throw new AniosException("Los años deben variar entre 1 y 10");
        ReporteClima reporte = new ReporteClima();
        for (int diaNro = 0; diaNro < cantidadDeAnios * DIAS_POR_ANIO; diaNro++) {
            List<Posicion> posiciones = getPosiciones(sistemaSolar.getPlanetas(), diaNro);
            String clima = obtenerClima(posiciones, sistemaSolar.getPosicionDelSol());
            if(clima == LLUVIA){
                reporte.setCantidadDeDiasDeLluvias(reporte.getCantidadDeDiasDeLluvias() + 1);
                Double perimetro = obtenerPerimetro(posiciones);
                if(reporte.getDiaPicoMaximoDeLluvia() == null || perimetro > reporte.getDiaPicoMaximoDeLluvia()){
                    reporte.setDiaPicoMaximoDeLluvia(diaNro);
                }
            } else if (clima == OPTIMO){
                reporte.setCantidadDeDiasOptimos(reporte.getCantidadDeDiasOptimos() + 1);
            } else if( clima == SEQUIA) {
                reporte.setCantidadDeDiasConSequia(reporte.getCantidadDeDiasConSequia() + 1);
            }
        }
        return reporte;
    }

    public String obtenerClima(List<Posicion> posicionesPlanetas, Posicion posicionSol) throws Exception {
        if(posicionesPlanetas == null || posicionesPlanetas.size() != 3) throw new Exception("Se necesitan tres posiciones para predecir el clima");
        String clima = INDETERMINADO;
        double area = obtenerArea(posicionesPlanetas);
        if(area > 0 && isInside(area, posicionesPlanetas, posicionSol)) clima = LLUVIA;
        else if (area == 0) { // forman una linea
            // si el area es 0 es porque los planetas forman una linea
            // agarro dos planetas de la linea y chequeo si forman una linea con el sol
            if(estanAlineados(posicionesPlanetas.get(0), posicionesPlanetas.get(1), posicionSol)) clima = SEQUIA;
            else clima = OPTIMO;
        }
        return clima;
    }

    public Double obtenerPerimetro(List<Posicion> posiciones) throws Exception {
        if(posiciones == null || posiciones.size() != 3) throw new Exception("Se necesitan tres posiciones para obtener el perimetro");
        return distanciaEntre(posiciones.get(0), posiciones.get(1))
                + distanciaEntre(posiciones.get(1), posiciones.get(2))
                + distanciaEntre(posiciones.get(2), posiciones.get(0));
    }

    private Double distanciaEntre(Posicion posicion1, Posicion posicion2){
        double diferenciaEnX = Math.abs(posicion1.getX() - posicion2.getX());
        double diferenciaEnY = Math.abs(posicion1.getY() - posicion2.getY());
        return Math.sqrt((diferenciaEnX) * (diferenciaEnX) + (diferenciaEnY) * (diferenciaEnY));
    }

    private List<Posicion> getPosiciones(List<Planeta> planetas, Integer diaNumero){
        List<Posicion> posiciones = new ArrayList<>();
        planetas.forEach(p -> posiciones.add(p.getPosicion(diaNumero)));
        return posiciones;
    }

    private Double obtenerArea(List<Posicion> posiciones) throws Exception {
        if(posiciones == null || posiciones.size() != 3) throw new Exception("Se necesitan tres posiciones para obtener el perimetro");
        double x1 = posiciones.get(0).getX();
        double x2 = posiciones.get(1).getX();
        double x3 = posiciones.get(2).getX();
        double y1 = posiciones.get(0).getY();
        double y2 = posiciones.get(1).getY();
        double y3 = posiciones.get(2).getY();
        return Math.abs((x1*(y2-y3) + x2*(y3-y1)+ x3*(y1-y2))/2.0);
    }

    private boolean isInside(double areaFormadaPorPlanetas, List<Posicion> posicionesPlanetas, Posicion posicionSol){
        try {
            /* Calculo area formada por los primeros dos planetas y el sol */
            double A1 = this.obtenerArea(Arrays.asList(posicionesPlanetas.get(0), posicionesPlanetas.get(1), posicionSol));

            /* Calculo area formada por otros dos planetas y el sol */
            double A2 = this.obtenerArea(Arrays.asList(posicionesPlanetas.get(1), posicionesPlanetas.get(2), posicionSol));

            /* Calculo area formada por otros dos planetas y el sol */
            double A3 = this.obtenerArea(Arrays.asList(posicionesPlanetas.get(2), posicionesPlanetas.get(0), posicionSol));

            /* Check if sum of A1, A2 and A3 is same as A */
            return (areaFormadaPorPlanetas == A1 + A2 + A3);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean estanAlineados(Posicion posicion1, Posicion posicion2, Posicion posicion3) {
        double x1 = posicion1.getX();
        double x2 = posicion2.getX();
        double x3 = posicion3.getX();
        double y1 = posicion1.getY();
        double y2 = posicion2.getY();
        double y3 = posicion3.getY();
        return (y3 - y2) * (x2 - x1) == (y2 - y1) * (x3 - x2);
    }
}
