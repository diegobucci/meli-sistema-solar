package meli.tmr.solarsystem.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DroughtWeather extends Weather {

    private static final String SEQUIA = "sequia";

    @Autowired
    public DroughtWeather() {
        setWeatherDescription(SEQUIA);
    }

    @Override
    public void updateReport(WeatherReport report, Integer dayNumber) {
        LOGGER.info("Se espera un clima de " + SEQUIA + " para el dia " + dayNumber);
        report.setDiasDeSequia(report.getDiasDeSequia() + 1);
    }
}
