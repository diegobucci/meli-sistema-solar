package meli.tmr.sistemasolar.controller;

import meli.tmr.sistemasolar.exceptions.AniosException;
import meli.tmr.sistemasolar.modelo.Planeta;
import meli.tmr.sistemasolar.modelo.Posicion;
import meli.tmr.sistemasolar.modelo.ReporteClima;
import meli.tmr.sistemasolar.modelo.SistemaSolar;
import meli.tmr.sistemasolar.utils.DoubleUtil;
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
    private Posicion posicionDelSol = new Posicion(0,0);

      public ReporteClima obtenerReporte(SistemaSolar sistemaSolar, Integer cantidadDeAnios) throws Exception {
        if(cantidadDeAnios < 1 || cantidadDeAnios > 10) throw new AniosException("Los años deben variar entre 1 y 10");
        ReporteClima reporte = new ReporteClima();
        Double maxPerimetro = 0.0;
        for (int diaNro = 0; diaNro < (cantidadDeAnios * DIAS_POR_ANIO); diaNro++) {
            List<Posicion> posiciones = getPosiciones(sistemaSolar.getPlanetas(), diaNro);
            String clima = obtenerClima(posiciones);
            if(clima == LLUVIA){
                reporte.setCantidadDeDiasDeLluvias(reporte.getCantidadDeDiasDeLluvias() + 1);
                Double perimetroActual = obtenerPerimetro(posiciones);
                if(perimetroActual > maxPerimetro){
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

    public String obtenerClima(List<Posicion> posicionesPlanetas) throws Exception {
        if(posicionesPlanetas == null || posicionesPlanetas.size() != 3) throw new Exception("Se necesitan tres posiciones para predecir el clima");
        String clima = INDETERMINADO;
        if(estanAlineados(posicionesPlanetas.get(0), posicionesPlanetas.get(1), posicionesPlanetas.get(2))){
            if(estanAlineados(posicionesPlanetas.get(0), posicionesPlanetas.get(2), posicionDelSol)){
                clima = SEQUIA;
            } else {
                clima = OPTIMO;
            }
        } else if(isInside(posicionesPlanetas, new Posicion(0,0))) {
            clima = LLUVIA;
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



    private boolean isInside(List<Posicion> posicionesPlanetas, Posicion posicionSol){
        // http://totologic.blogspot.com/2014/01/accurate-point-in-triangle-test.html
        /********************************************
         *
         *
         *  Barycentric coordinate allows to express new p coordinates as a linear combination of p1, p2, p3. More precisely, it defines 3 scalars a, b, c such that :
         *
         * x = a * x1 + b * x2  + c * x3
         * y = a * y1 + b * y2 + c * y3
         * a + b + c = 1
         *
         *
         */
        double x = posicionSol.getX();
        double y = posicionSol.getY();

        double x1 = posicionesPlanetas.get(0).getX();
        double x2 = posicionesPlanetas.get(1).getX();
        double x3 = posicionesPlanetas.get(2).getX();
        double y1 = posicionesPlanetas.get(0).getY();
        double y2 = posicionesPlanetas.get(1).getY();
        double y3 = posicionesPlanetas.get(2).getY();

        double denominador = ((y2 - y3)*(x1 - x3) + (x3 - x2)*(y1 - y3));
        double a = ((y2 - y3)*(x - x3) + (x3 - x2)*(y - y3)) / denominador;
        double b = ((y3 - y1)*(x - x3) + (x1 - x3)*(y - y3)) / denominador;
        double c = 1 - a - b;
        return 0 <= a && a <= 1 && 0 <= b && b <= 1 && 0 <= c && c <= 1;
    }


    private boolean areInline(Posicion posicion1, Posicion posicion2, Posicion posicion3) {
        // chequeo si el area es 0. Evito dividir por 2 ya que la finalidad es saber si forman una linea.
        double val = posicion1.getX() * (posicion2.getY() - posicion3.getY()) + posicion2.getX() * (posicion3.getY() - posicion1.getY()) + posicion3.getX() * (posicion1.getY() - posicion2.getY());
        return val == 0.0;
    }

    private boolean estanAlineados(Posicion posicion1, Posicion posicion2, Posicion posicion3) {
        // ecuacion -> (y2 - y1) * (x3 - x2) = (y3 - y2) * (x2 - x1)
        double result1 = DoubleUtil.round((posicion2.getY() - posicion1.getY()) * (posicion3.getX() - posicion2.getX()));
        double result2 = DoubleUtil.round((posicion3.getY() - posicion2.getY()) * (posicion2.getX() - posicion1.getX()));
        return result1 == result2;
    }

}
