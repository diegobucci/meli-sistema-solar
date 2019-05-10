package meli.tmr.sistemasolar.services;

import meli.tmr.sistemasolar.daos.interfaces.DayWeatherDAO;
import meli.tmr.sistemasolar.daos.interfaces.WeatherReportDAO;
import meli.tmr.sistemasolar.exceptions.YearsException;
import meli.tmr.sistemasolar.exceptions.SolarSystemException;
import meli.tmr.sistemasolar.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeatherService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherService.class);
    public static final Integer DAYS_PER_YEAR = 365;

    private CalculatorUtil calculatorUtil;
    private WeatherReportDAO weatherReportDAO;
    private DayWeatherDAO dayWeatherDAO;

    @Autowired
    public WeatherService(CalculatorUtil calculatorUtil, WeatherReportDAO weatherReportDAO, DayWeatherDAO dayWeatherDAO) {
        this.calculatorUtil = calculatorUtil;
        this.dayWeatherDAO = dayWeatherDAO;
        this.weatherReportDAO = weatherReportDAO;
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
//        for(LocalDate date = LocalDate.now(); date.isBefore(date.plusYears(10)); date = date.plusDays(1)){ // Demasiado procesamiento
        for (int dayNumber = 1; dayNumber <= days; dayNumber++) {
            LOGGER.info("Día numero: " + dayNumber);
            solarSystem.advanceOneDay();
            WeatherEnum weather = completeReportForDay(report, solarSystem.getPlanets(), dayNumber);
            dayWeatherDAO.save(new DayWeather(dayNumber, weather.getWeather()));
        }
        return report;
    }

    private WeatherEnum completeReportForDay(WeatherReport report, List<Planet> planets, Integer dayNumber){
        WeatherEnum weather = getWeather(planets);
        switch(weather){
            case RAIN:
                LOGGER.info("Se espera LLUVIA para el día " + dayNumber);
                report.setNumberOfRainyDays(report.getNumberOfRainyDays() + 1);
                double perimeter = getPerimeter(planets);
                if(perimeter > report.getMaxPerimeterRain()) {
                    report.setDayOfGreatestRain(dayNumber);
                    report.setMaxPerimeterRain(perimeter);
                }
                break;
            case DROUGHT:
                LOGGER.info("Se espera SEQUÍA para el día " + dayNumber);
                report.setNumberOfDroughtDays(report.getNumberOfDroughtDays() + 1);
                break;
            case OPTIMUM:
                LOGGER.info("Se espera un clima ÓPTIMO para el día " + dayNumber);
                report.setNumberOfOptimalDays(report.getNumberOfOptimalDays() + 1);
                break;
            case UNDEFINED:
                LOGGER.warn("No se detectó ningún clima");
                break;
        }
        return weather;
    }

    private WeatherEnum getWeather(List<Planet> planets){
        // Previamente se validó el tamaño del array así que no debería lanzarse la excepción IndexOutOfBounds
        Planet planet1 = planets.get(0);
        Planet planet2 = planets.get(1);
        Planet planet3 = planets.get(2);

        WeatherEnum clima = WeatherEnum.UNDEFINED;
        if(calculatorUtil.areInline(planet1.getPosition(), planet2.getPosition(), planet3.getPosition())){
            clima = WeatherEnum.OPTIMUM;
            if(calculatorUtil.areInlineWithTheSun(planet1.getActualGrade(), planet2.getActualGrade(), planet3.getActualGrade())){
                clima = WeatherEnum.DROUGHT; // Si además están alineados con el sol el clima es SEQUIA
            }
        } else if(calculatorUtil.sunIsInside(planet1.getPosition(), planet2.getPosition(), planet3.getPosition())) {
            clima = WeatherEnum.RAIN;
        }
        return clima;
    }

    private double getPerimeter(List<Planet> planets) {
        return this.calculatorUtil.getPerimeter(planets.get(0).getPosition(), planets.get(1).getPosition(), planets.get(2).getPosition());
    }

    private void checkErrors(SolarSystem solarSystem, Integer years){
        if(years < 1 || years > 10) throw new YearsException("Los años deben variar entre 1 y 10");
        if(solarSystem.getPlanets() == null || solarSystem.getPlanets().size() != 3) throw new SolarSystemException("Solo es posible predecir el clima de sistemas con 3 planetas");
    }

    private Integer getDays(int years){
        return years * DAYS_PER_YEAR;
    }

    public CalculatorUtil getCalculatorUtil(){
        return this.calculatorUtil;
    }

}
