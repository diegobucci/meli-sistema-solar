package meli.tmr.sistemasolar.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleUtil {

    public static double round(double val){
        return new BigDecimal(val).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
