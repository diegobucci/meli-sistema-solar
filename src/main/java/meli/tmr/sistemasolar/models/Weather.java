package meli.tmr.sistemasolar.models;

import org.springframework.stereotype.Component;

@Component
public abstract class Weather {

    private String weatherDescription;

    public abstract void updateReport(WeatherReport weatherReport, Integer dayNumber);

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

}
