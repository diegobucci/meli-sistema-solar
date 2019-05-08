package meli.tmr.sistemasolar.services;

import meli.tmr.sistemasolar.model.Position;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CalculatorUtil {

    // Para realizar cuentas aritmeticas se utilizó BigDecimal ya que proporciona una mejor precisión

     protected double getPerimeter(Position position1, Position position2, Position position3) {
        return distanceBetween(position1, position2) + distanceBetween(position2, position3) + distanceBetween(position3, position1);
    }

    protected double distanceBetween(Position position1, Position position2) {
        double xDifference = position1.getX() - position2.getX();
        double yDifference = position1.getY() - position2.getY();
        return Math.sqrt(Math.pow(xDifference,2) + Math.pow(yDifference,2));
    }


    public boolean isInside(Position position1, Position position2, Position position3, Position positionInside) {
        double d1, d2, d3;
        boolean has_neg, has_pos;

        d1 = sign(positionInside, position1, position2);
        d2 = sign(positionInside, position2, position3);
        d3 = sign(positionInside, position3, position1);

        has_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        has_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(has_neg && has_pos);
    }

      protected boolean areInline(Position position1, Position position2, Position position3) {
         BigDecimal x1 = BigDecimal.valueOf(position1.getX());
         BigDecimal y1 = BigDecimal.valueOf(position1.getX());
         BigDecimal x2 = BigDecimal.valueOf(position2.getX());
         BigDecimal y2 = BigDecimal.valueOf(position2.getX());
         BigDecimal x3 = BigDecimal.valueOf(position3.getX());
         BigDecimal y3 = BigDecimal.valueOf(position3.getX());
         if(x1.equals(x2)&& x2.equals(x3) && x1.equals(x3)) return true;
         if(y1.equals(y2) && y2.equals(y3) && y1.equals(y3)) return true;
         BigDecimal result1 = (x2.subtract(x1)).divide(x3.subtract(x2), 2, RoundingMode.HALF_UP);
         BigDecimal result2 = (x2.subtract(x1)).divide(x3.subtract(x2), 2, RoundingMode.HALF_UP);
//         BigDecimal result2 = (x2.subtract(x1)).divide(x3.subtract(x2), MathContext.DECIMAL128);
         return result1.equals(result2);
    }

    private double sign(Position position1, Position position2, Position position3) {
        return (position1.getX() - position3.getX())
                * (position2.getY() - position3.getY())
                - (position2.getX() - position3.getX())
                * (position1.getY() - position3.getY());
    }

}