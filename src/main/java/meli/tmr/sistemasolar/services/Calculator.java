package meli.tmr.sistemasolar.services;

import meli.tmr.sistemasolar.modelo.Posicion;
import meli.tmr.sistemasolar.utils.DoubleUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Calculator {

     protected Double obtenerPerimetro(List<Posicion> posiciones) {
        if (posiciones == null || posiciones.size() != 3)
            throw new RuntimeException("Se necesitan tres posiciones para obtener el perimetro");
        return distanciaEntre(posiciones.get(0), posiciones.get(1))
                + distanciaEntre(posiciones.get(1), posiciones.get(2))
                + distanciaEntre(posiciones.get(2), posiciones.get(0));
    }

    protected Double distanciaEntre(Posicion posicion1, Posicion posicion2) {
        double diferenciaEnX = Math.abs(posicion1.getX() - posicion2.getX());
        double diferenciaEnY = Math.abs(posicion1.getY() - posicion2.getY());
        return Math.sqrt((diferenciaEnX) * (diferenciaEnX) + (diferenciaEnY) * (diferenciaEnY));
    }

    protected boolean isInside(List<Posicion> posicionesPlanetas, Posicion posicionSol){
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

    protected boolean areInline(Posicion posicion1, Posicion posicion2, Posicion posicion3) {
        // chequeo si el area es 0. Evito dividir por 2 ya que la finalidad es saber si forman una linea.
        double val = posicion1.getX() * (posicion2.getY() - posicion3.getY()) + posicion2.getX() *
                (posicion3.getY() - posicion1.getY()) + posicion3.getX() * (posicion1.getY() - posicion2.getY());
        return val == 0.0;
    }

    protected boolean estanAlineados(Posicion posicion1, Posicion posicion2, Posicion posicion3) {
        // ecuacion -> (y2 - y1) * (x3 - x2) = (y3 - y2) * (x2 - x1)
        double result1 = DoubleUtil.round((posicion2.getY() - posicion1.getY()) * (posicion3.getX() - posicion2.getX()));
        double result2 = DoubleUtil.round((posicion3.getY() - posicion2.getY()) * (posicion2.getX() - posicion1.getX()));
        return result1 == result2;
    }

}