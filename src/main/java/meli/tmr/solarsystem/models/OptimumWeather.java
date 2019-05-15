package meli.tmr.solarsystem.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OptimumWeather extends Weather {

    public static final String OPTIMO = "optimo";

    @Autowired
    public OptimumWeather(){
        setWeatherDescription(OPTIMO);
    }

    @Override
    public void updateReport(WeatherReport report, Integer dayNumber) {
        LOGGER.info("Se espera un clima " + OPTIMO + " para el día " + dayNumber);
        report.setDiasOptimos(report.getDiasOptimos() + 1);
    }
}
