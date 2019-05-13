package meli.tmr.sistemasolar.services;

import meli.tmr.sistemasolar.daos.interfaces.DayWeatherDAO;
import meli.tmr.sistemasolar.daos.interfaces.WeatherReportDAO;
import meli.tmr.sistemasolar.exceptions.SolarSystemException;
import meli.tmr.sistemasolar.exceptions.YearsException;
import meli.tmr.sistemasolar.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public  class WeatherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherService.class);
    public static final Integer DAYS_PER_YEAR = 365;

    private WeatherReportDAO weatherReportDAO;
    private DayWeatherDAO dayWeatherDAO;
    private WeatherTypeResolver weatherTypeResolver;

    @Autowired
    public WeatherService(WeatherReportDAO weatherReportDAO, DayWeatherDAO dayWeatherDAO, WeatherTypeResolver weatherTypeResolver){
        this.dayWeatherDAO = dayWeatherDAO;
        this.weatherReportDAO = weatherReportDAO;
        this.weatherTypeResolver = weatherTypeResolver;
    }

    public WeatherReport getWeatherReport(SolarSystem solarSystem, Integer years) {
        checkErrors(solarSystem, years);
        LOGGER.info("Obtener reporte para los próximos " + years + " años");
        WeatherReport report = iterateDays(solarSystem, getDays(years));
        weatherReportDAO.save(report);
        return report;
    }

    private WeatherReport iterateDays(SolarSystem solarSystem, Integer days){
        WeatherReport report = new WeatherReport();
        for (int dayNumber = 1; dayNumber <= days; dayNumber++) {
            LOGGER.info("Se produce la prediccion del clima para el dia numero: " + dayNumber);
            solarSystem.advanceOneDay();
            updateReportAndSaveDay(report, solarSystem.getPlanets(), dayNumber);
        }
        return report;
    }

    private void updateReportAndSaveDay(WeatherReport report, List<Planet> planets, Integer dayNumber){
        if(weatherTypeResolver == null){
            int i = 1;
        }
        Weather weather = weatherTypeResolver.getWeatherType(planets);
        weather.updateReport(report, dayNumber);
        dayWeatherDAO.save(new DayWeather(dayNumber, weather.getWeatherDescription()));
    }

    private void checkErrors(SolarSystem solarSystem, Integer years){
        if(years < 1 || years > 10) throw new YearsException("Los años deben variar entre 1 y 10");
        if(solarSystem.getPlanets() == null || solarSystem.getPlanets().size() != 3) throw new SolarSystemException("Solo es posible predecir el clima de sistemas con 3 planetas");
    }

    private Integer getDays(int years){
        return years * DAYS_PER_YEAR;
    }

}
