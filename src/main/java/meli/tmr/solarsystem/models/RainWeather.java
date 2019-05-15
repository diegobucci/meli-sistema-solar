package meli.tmr.solarsystem.models;

import meli.tmr.solarsystem.services.CalculatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RainWeather extends Weather {

    public static final String LLUVIA = "lluvia";
    private CalculatorUtil calculatorUtil;
    private List<Planet> planets;
    private double maxPerimeter;

    @Autowired
    public RainWeather(CalculatorUtil calculatorUtil) {
        this.calculatorUtil = calculatorUtil;
        setWeatherDescription(LLUVIA);
        this.reset();
    }

    @Override
    public void updateReport(WeatherReport report, Integer dayNumber){
        LOGGER.info("Se espera " + LLUVIA + " para el día " + dayNumber);
        report.setDiasDeLluvia(report.getDiasDeLluvia() + 1);
        double perimeter = getPerimeter(planets);
        if(perimeter > maxPerimeter) {
            report.setDiaDeMayorLluvia(dayNumber);
            maxPerimeter = perimeter;
        }
    }

    private double getPerimeter(List<Planet> planets) {
        return calculatorUtil.getPerimeter(planets.get(0).getPosition(), planets.get(1).getPosition(), planets.get(2).getPosition());
    }

    public void setPlanets(List<Planet> planets) {
        this.planets = planets;
    }

    public void reset(){
        this.maxPerimeter = 0;
    }
}
