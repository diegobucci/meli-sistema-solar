package meli.tmr.sistemasolar.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StandarWeather extends Weather {

    @Autowired
    public StandarWeather(){
        this.setWeatherDescription("indefinido");
    }

    @Override
    public void updateReport(WeatherReport weatherReport, Integer dayNumber) {
        LOGGER.warn("No se detecto ningun clima para el dia " + dayNumber);
    }
}
