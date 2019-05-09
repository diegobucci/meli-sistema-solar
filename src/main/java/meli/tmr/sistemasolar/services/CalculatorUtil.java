package meli.tmr.sistemasolar.services;

import meli.tmr.sistemasolar.models.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@Component
public class CalculatorUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalculatorUtil.class);

     protected double getPerimeter(Position position1, Position position2, Position position3) {
         LOGGER.warn("Obtener perimetro del triangulo formado por: ");
         LOGGER.info(Position.getPositionAsString(position1));
         LOGGER.info(Position.getPositionAsString(position2));
         LOGGER.info(Position.getPositionAsString(position3));
        return distanceBetween(position1, position2) + distanceBetween(position2, position3) + distanceBetween(position3, position1);
    }

    protected double distanceBetween(Position position1, Position position2) {
        double xDifference = position1.getX() - position2.getX(); // No importa que sea negativo porque lo elevo al cuadrado después
        double yDifference = position1.getY() - position2.getY();
        return Math.sqrt(Math.pow(xDifference,2) + Math.pow(yDifference,2));
    }

    protected boolean areInline(Position position1, Position position2, Position position3) {
        BigDecimal x1 = BigDecimal.valueOf(position1.getX()).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal y1 = BigDecimal.valueOf(position1.getY()).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal x2 = BigDecimal.valueOf(position2.getX()).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal y2 = BigDecimal.valueOf(position2.getY()).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal x3 = BigDecimal.valueOf(position3.getX()).setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal y3 = BigDecimal.valueOf(position3.getY()).setScale(2, RoundingMode.HALF_EVEN);
        if((x1.equals(x2) && x2.equals(x3) && x1.equals(x3)) || (y1.equals(y2) && y2.equals(y3) && y1.equals(y3))) {
            // Si están en el mismo eje X o Y => están alineados
            return true;
        }
        BigDecimal result1 = (y2.subtract(y1)).divide(x3.subtract(x1), MathContext.DECIMAL128);
        BigDecimal result2 = (y3.subtract(y1)).divide(x2.subtract(x1), MathContext.DECIMAL128);
        return result1.equals(result2); // TODO: esto da siempre false
    }

    protected boolean areInlineWithTheSun(int grade1, int grade2, int grade3){
        return gradesAreInline(grade1, grade2) && gradesAreInline(grade2, grade3) && gradesAreInline(grade3, grade1);
    }

    protected boolean gradesAreInline(int grades1, int grades2){
         if(grades1 >= 180) grades1 -= 180;
         if(grades2 >= 180) grades2 -= 180;
         return grades1 == grades2;
    }

    protected boolean sunIsInside(Position position1, Position position2, Position position3) {
        LOGGER.warn("Verificar si el sol se encuentra dentro del área formada por: ");
        LOGGER.info(Position.getPositionAsString(position1));
        LOGGER.info(Position.getPositionAsString(position2));
        LOGGER.info(Position.getPositionAsString(position3));
        Position sunPosition = new Position(0,0);
        double d1 = sign(sunPosition, position1, position2);
        double d2 = sign(sunPosition, position2, position3);
        double d3 = sign(sunPosition, position3, position1);

        boolean has_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        boolean has_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(has_neg && has_pos);
    }

    private double sign(Position p1, Position p2, Position p3) {
        return (p1.getX() - p3.getX()) * (p2.getY() - p3.getY()) - (p2.getX() - p3.getX()) * (p1.getY() - p3.getY());
    }
    
    public static double getCos(int grades){
        // Math.cos(Math.toRadians(90)) da mal el resultado
        if(grades == 0 || grades == 360) return 1;
        else if(grades == 90 || grades == 270) return 0;
        else if(grades == 180) return -1;
        else return Math.cos(Math.toRadians(grades));
    }

    public static double getSin(int grades){
        if(grades == 0 || grades == 180 || grades == 360) return 0;
        else if(grades == 90) return 1;
        else if(grades == 270) return -1;
        else return Math.sin(Math.toRadians(grades));
    }


}