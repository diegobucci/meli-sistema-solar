package meli.tmr.sistemasolar.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DroughtWeather extends Weather {

    @Autowired
    public DroughtWeather() {
        setWeatherDescription("sequia");
    }

    @Override
    public void updateReport(WeatherReport report, Integer dayNumber) {
        report.setNumberOfDroughtDays(report.getNumberOfDroughtDays() + 1);
    }
}
