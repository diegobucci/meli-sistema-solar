package meli.tmr.sistemasolar.services;

import meli.tmr.sistemasolar.model.Position;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CalculatorUtil {

     protected double getPerimeter(Position position1, Position position2, Position position3) {
        return distanceBetween(position1, position2) + distanceBetween(position2, position3) + distanceBetween(position3, position1);
    }

    protected double distanceBetween(Position position1, Position position2) {
        double xDifference = position1.getX() - position2.getX(); // No importa que sea negativo porque lo elevo al cuadrado después
        double yDifference = position1.getY() - position2.getY();
        return Math.sqrt(Math.pow(xDifference,2) + Math.pow(yDifference,2));
    }

     protected boolean areInline(Position position1, Position position2, Position position3) {
         // Para realizar cuentas aritméticas se utilizó BigDecimal ya que proporciona una mejor precisión
         BigDecimal x1 = BigDecimal.valueOf(position1.getX());
         BigDecimal y1 = BigDecimal.valueOf(position1.getY());
         BigDecimal x2 = BigDecimal.valueOf(position2.getX());
         BigDecimal y2 = BigDecimal.valueOf(position2.getY());
         BigDecimal x3 = BigDecimal.valueOf(position3.getX());
         BigDecimal y3 = BigDecimal.valueOf(position3.getY());
         if((x1.equals(x2) && x2.equals(x3) && x1.equals(x3)) || (y1.equals(y2) && y2.equals(y3) && y1.equals(y3))) {
             // Si están en el mismo eje X o Y => están alineados
             return true;
         }
         BigDecimal result1 = (x2.subtract(x1)).divide(x3.subtract(x2), 2, RoundingMode.HALF_UP);
         BigDecimal result2 = (x2.subtract(x1)).divide(x3.subtract(x2), 2, RoundingMode.HALF_UP);
         return result1.equals(result2);
     }

    protected boolean isInside(Position position1, Position position2, Position position3, Position positionInside) {
        double d1 = sign(positionInside, position1, position2);
        double d2 = sign(positionInside, position2, position3);
        double d3 = sign(positionInside, position3, position1);

        boolean has_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        boolean has_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(has_neg && has_pos);
    }

    private double sign(Position p1, Position p2, Position p3) {
        return (p1.getX() - p3.getX()) * (p2.getY() - p3.getY()) - (p2.getX() - p3.getX()) * (p1.getY() - p3.getY());
    }

}