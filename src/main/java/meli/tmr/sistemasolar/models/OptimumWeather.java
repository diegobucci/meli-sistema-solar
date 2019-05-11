package meli.tmr.sistemasolar.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OptimumWeather extends Weather {

    @Autowired
    public OptimumWeather(){
        setWeatherDescription("optimo");
    }

    @Override
    public void updateReport(WeatherReport report, Integer dayNumber) {
        report.setNumberOfOptimalDays(report.getNumberOfOptimalDays() + 1);
    }
}
