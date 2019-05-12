package meli.tmr.sistemasolar.services;

import meli.tmr.sistemasolar.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeatherTypeResolver {
    private RainWeather rainWeather;
    private DroughtWeather droughtWeather;
    private OptimumWeather optimumWeather;
    private StandarWeather standarWeather;
    private CalculatorUtil calculatorUtil;

    @Autowired
    public WeatherTypeResolver(RainWeather rainWeather, DroughtWeather droughtWeather, StandarWeather standarWeather, OptimumWeather optimumWeather, CalculatorUtil calculatorUtil){
        this.rainWeather = rainWeather;
        this.droughtWeather = droughtWeather;
        this.standarWeather = standarWeather;
        this.optimumWeather = optimumWeather;
        this.calculatorUtil = calculatorUtil;
    }

    Weather getWeatherType(List<Planet> planets){
        // Previamente se validó el tamaño del array así que no debería lanzarse la excepción IndexOutOfBounds
        Planet planet1 = planets.get(0);
        Planet planet2 = planets.get(1);
        Planet planet3 = planets.get(2);

        if(calculatorUtil.areInline(planet1.getPosition(), planet2.getPosition(), planet3.getPosition())){
            if(calculatorUtil.areInlineWithTheSun(planet1.getActualGrade(), planet2.getActualGrade(), planet3.getActualGrade())){
                return droughtWeather; // Si además están alineados con el sol el clima es SEQUIA
            }
            return optimumWeather;
        } else if(calculatorUtil.sunIsInside(planet1.getPosition(), planet2.getPosition(), planet3.getPosition())) {
            rainWeather.setPlanets(planets);
            return rainWeather;
        }
        return standarWeather;
    }
}
