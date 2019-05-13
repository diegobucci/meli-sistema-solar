package meli.tmr.solarsystem.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Weather {
    protected static final Logger LOGGER = LoggerFactory.getLogger(Weather.class);

    private String weatherDescription;

    public abstract void updateReport(WeatherReport weatherReport, Integer dayNumber);

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

}
