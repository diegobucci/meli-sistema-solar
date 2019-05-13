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

    @Autowired
    public RainWeather(CalculatorUtil calculatorUtil) {
        this.calculatorUtil = calculatorUtil;
        setWeatherDescription(LLUVIA);
    }

    @Override
    public void updateReport(WeatherReport report, Integer dayNumber){
        LOGGER.info("Se espera " + LLUVIA + " para el día " + dayNumber);
        report.setNumberOfRainyDays(report.getNumberOfRainyDays() + 1);
        double perimeter = getPerimeter(planets);
        if(perimeter > report.getMaxPerimeterRain()) {
            report.setDayOfGreatestRain(dayNumber);
            report.setMaxPerimeterRain(perimeter);
        }
    }

    private double getPerimeter(List<Planet> planets) {
        return calculatorUtil.getPerimeter(planets.get(0).getPosition(), planets.get(1).getPosition(), planets.get(2).getPosition());
    }

    public void setPlanets(List<Planet> planets) {
        this.planets = planets;
    }
}
